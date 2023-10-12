package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Api(tags = "Пользователи")
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${rc/main/resources/user}")
    private String uploadPath;

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
    public ResponseEntity<UserDto> updateImage(@PathVariable Integer id,
                                              @RequestParam("image") MultipartFile image) throws IOException {
        UserDto user = userService.updateImage(id);
        if (image != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String idFile = String.valueOf(userService.updateImage(id));
            String resultFile = idFile + "." + image.getOriginalFilename();
            image.transferTo(new File(uploadPath + "/" + resultFile));
            user.setImage(resultFile);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
