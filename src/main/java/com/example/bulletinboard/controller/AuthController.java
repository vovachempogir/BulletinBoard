package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.Login;
import com.example.bulletinboard.dto.Register;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.security.AuthUserManager;
import com.example.bulletinboard.service.AuthService;
import com.example.bulletinboard.service.UserMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Авторизация")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final AuthUserManager authUserManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        if (authService.login(login.getUsername(), login.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register register) {
        if (!authUserManager.userExists(register.getUsername())) {
            userRepo.save(userMapper.toUser(register));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
