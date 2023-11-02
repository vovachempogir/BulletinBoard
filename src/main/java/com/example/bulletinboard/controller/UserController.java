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

/**
 * Контроллер для работы с пользователями.
 */
@Api(tags = "Пользователи")
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageService imageService;


    /**
     * Устанавливает новый пароль для пользователя.
     *
     * @param newPassword информация о новом пароле
     * @return ответ с указанием успешности обновления пароля
     */
    @PostMapping("/set_password")
    public ResponseEntity<Boolean> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok(userService.updatePassword(newPassword));
    }

    /**
     * Получает информацию о текущем пользователе.
     *
     * @return UserDto с информацией о пользователе
     */
    @GetMapping("/me")
    public UserDto getUser() {
        return userService.getInfoAboutUser();
    }

    /**
     * Изменение информации о текущем пользователе.
     * @param createOrUpdateUser информация о пользователе, которую необходимо обновить
     * @return UserDto с обновлённой информацией о пользователе
     */
    @PatchMapping("/me")
    public UserDto updateUser(@RequestBody CreateOrUpdateUser createOrUpdateUser) {
        return userService.updateUser(createOrUpdateUser);
    }

    /**
     * Обновляет изображение текущего пользователя.
     * @param image изображение пользователя в формате MultipartFile
     * @return ответ с указанием успешности обновления изображения
     * @throws IOException при возникновении ошибки ввода-вывода
     */
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImage(@RequestParam("image") MultipartFile image) throws IOException {
        userService.updateImage(image);
        return ResponseEntity.ok().build();
    }


    /**
     * Получает изображение пользователя по заданному идентификатору.
     * @param imageId идентификатор изображения пользователя
     * @return ответ с изображением в виде массива байтов
     */
    @GetMapping(value = "/image/{imageId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable int imageId) {
        return ResponseEntity.ok(imageService.getImage(imageId));
    }

}
