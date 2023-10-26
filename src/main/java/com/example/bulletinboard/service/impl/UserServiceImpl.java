package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.User;
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


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final UserDetailsManager userDetailsManager;
    private final UserDetails userDetails;
    private final ImageService imageService;

    @Override
    @Transactional
    public UserDto getInfoAboutUser() {
        log.info("getUser");
        User user = getUser();
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void updateImage(MultipartFile image) throws IOException {
        User user = getUser();
        user.setImage(imageService.upload(image));
        userRepo.save(user);
        log.debug("User avatar with id - {} was update", user.getId());
    }

    @Override
    @Transactional
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
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Такого пользователя не существует"));
    }

}
