package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.UserMapper;
import com.example.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;




@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final UserDetailsManager userDetailsManager;
    private final UserDetails userDetails;
    private final FilesService filesService;


    @Value("${upload.path.user}")
    private String uploadPath;

    @Override
    public UserDto getInfoAboutUser() {
        log.info("getUser");
        User user = getUser();
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public byte[] updateImage(MultipartFile image) throws IOException {
        User user = getUser();
        Path path = Path.of(uploadPath, image.getOriginalFilename());
        filesService.uploadFile(image, path);
        user.setImage(path.toAbsolutePath().toString());
        log.info("updateImageUser");
        userRepo.save(user);
        return image.getBytes();
    }

    @Override
    public UserDto updateUser(CreateOrUpdateUser updateUser) {
        log.info("updateUser");
        User user = getUser();
        return userMapper.toDto(userRepo.save(user));
    }

    @Override
    public boolean updatePassword(NewPassword newPassword) {
        if (checkPassword(newPassword)){
            userDetailsManager.changePassword(newPassword.getCurrentPassword(), newPassword.getNewPassword());
            log.info("updatePassword");
            return true;
        }
        log.info("notUpdateUser");
        return false;
    }

   private boolean checkPassword(NewPassword password){
        return (password!= null && !password.getNewPassword().isEmpty() && !password.getNewPassword().isBlank()
                && !password.getCurrentPassword().isEmpty() && !password.getCurrentPassword().isBlank());
   }

    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователя не существует!"));
    }
}
