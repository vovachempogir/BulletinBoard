package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.service.ImageService;
import com.example.bulletinboard.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
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
    private final ImageService imageService;

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
    public ResponseEntity<Void> updateImage(@RequestParam("image") MultipartFile image) throws IOException {
        userService.updateImage(image);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/image/{imageId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable int imageId) {
        return ResponseEntity.ok(imageService.getImage(imageId));
    }

}
