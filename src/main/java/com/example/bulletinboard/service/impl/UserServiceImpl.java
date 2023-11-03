package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.ImageRepo;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.ImageService;
import com.example.bulletinboard.service.UserMapper;
import com.example.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


/**
 * Реализация интерфейса UserService, предоставляющая функциональность по работе с пользователями.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    private final UserMapper userMapper;
    private final UserDetailsManager userDetailsManager;
    private final UserDetails userDetails;
    private final ImageService imageService;

    /**
     * Возвращает информацию о текущем пользователе.
     *
     * @return объект UserDto, представляющий информацию о пользователе
     */
    @Override
    @Transactional
    public UserDto getInfoAboutUser() {
        log.info("getUser");
        User user = getUser();
        return userMapper.toDto(user);
    }

    /**
     * Обновляет изображение аватара пользователя.
     *
     * @param image мультифайл изображения для обновления аватара
     * @throws IOException если возникает ошибка ввода-вывода при загрузке изображения
     */
    @Override
    @Transactional
    public void updateImage(MultipartFile image) throws IOException {
        User user = getUser();
        imageRepo.delete(user.getImage());
        user.setImage(imageService.upload(image));
        userRepo.save(user);
        log.info("User avatar with id - {} was update", user.getId());
    }

    /**
     * Обновляет информацию о пользователе на основе данных из объекта CreateOrUpdateUser.
     *
     * @param updateUser объект CreateOrUpdateUser с обновленными данными о пользователе
     * @return объект UserDto, представляющий обновленные данные о пользователе
     */
    @Override
    @Transactional
    public UserDto updateUser(CreateOrUpdateUser updateUser) {
        log.info("updateUser");
        User user = getUser();
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        return userMapper.toDto(userRepo.save(user));
    }

    /**
     * Обновляет пароль пользователя на основе данных из объекта NewPassword.
     *
     * @param newPassword объект NewPassword с новым паролем пользователя
     * @return true, если пароль был успешно обновлен, иначе false
     */
    @Override
    @Transactional
    public boolean updatePassword(NewPassword newPassword) {
        if (checkPassword(newPassword)) {
            userDetailsManager.changePassword(newPassword.getCurrentPassword(), newPassword.getNewPassword());
            log.info("updatePassword");
            return true;
        }
        log.info("notUpdateUser");
        return false;
    }

    /**
     * Проверяет данные пароля на корректность.
     *
     * @param password объект NewPassword с данными пароля
     * @return true, если данные пароля являются корректными, иначе false
     */
    private boolean checkPassword(NewPassword password) {
        return (password != null && !password.getNewPassword().isEmpty() && !password.getNewPassword().isBlank()
                && !password.getCurrentPassword().isEmpty() && !password.getCurrentPassword().isBlank());
    }

    /**
     * Возвращает объект пользователя на основе информации о текущем пользователе.
     *
     * @return объект User, представляющий текущего пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));
    }
}