package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@Api(tags = "Пользователи")
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/setPassword")
    public NewPassword setPassword (@RequestBody NewPassword newPassword){
        return newPassword;
    }

    @GetMapping("/me")
    public ResponseEntity<Optional<UserDto>> getUserById(@PathVariable Integer id){
        Optional<UserDto> user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/me")
    public ResponseEntity updateUser(@PathVariable Integer id,
                                     @RequestBody CreateOrUpdateUser createOrUpdateUser){
        userService.updateUser(id, createOrUpdateUser);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateImage(@RequestParam("image") MultipartFile image){

    }
}
