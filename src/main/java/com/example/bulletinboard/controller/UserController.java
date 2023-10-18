package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Api(tags = "Пользователи")
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${upload.path.user}")
    private String uploadPath;

    @PostMapping("/setPassword")
    public ResponseEntity<Boolean> setPassword (@RequestBody NewPassword newPassword){
        return ResponseEntity.ok(userService.updatePassword(newPassword));
    }

    @GetMapping("/me")
    public UserDto getUser(String email){
        return userService.getInfoAboutUser(email);
    }

    @PatchMapping("/me")
    public UserDto updateUser(@PathVariable String email,
                                     @RequestBody CreateOrUpdateUser createOrUpdateUser){
        return userService.updateUser(email, createOrUpdateUser);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDto updateImage(@PathVariable Integer id,
                               @RequestParam("image") MultipartFile image) throws IOException {
        return userService.updateImage(id,image);
    }
}
