package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@Api(tags = "Пользователи")
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;

    @PostMapping("/set_password")
    public ResponseEntity<Boolean> setPassword (@RequestBody NewPassword newPassword){
        return ResponseEntity.ok(userService.updatePassword(newPassword));
    }

    @GetMapping("/me")
    public UserDto getUser(){
        return userService.getInfoAboutUser();
    }

    @PatchMapping("/me")
    public UserDto updateUser(@RequestBody CreateOrUpdateUser createOrUpdateUser){
        return userService.updateUser(createOrUpdateUser);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] updateImage(@RequestParam("image") MultipartFile image) throws IOException {
        return userService.updateImage(image);
    }

    @GetMapping(value = "/image/{userId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Boolean> downloadImage(@PathVariable Integer userId, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(userService.downloadAvatar(userId, response));
    }

}
