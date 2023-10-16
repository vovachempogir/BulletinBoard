package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.UserMapper;
import com.example.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final UserDetailsManager userDetailsManager;


    @Value("${upload.path.user}")
    private String uploadPath;

    @Override
    public UserDto getInfoAboutUser(String email) {
        log.info("getUser");
        return userMapper.toDto(userRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Пользователя не существует")));
    }

    @Override
    public UserDto updateImage(Integer id, MultipartFile image) throws IOException {
        UserDto user = userMapper.toDto(userRepo.findById(id).orElseThrow());
        if (image != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String idFile = String.valueOf(user.getId());
            String resultFile = idFile + "." + image.getOriginalFilename();
            image.transferTo(new File(uploadPath + "/" + resultFile));
            user.setImage(resultFile);
            log.info("updateImage");
            return user;
        }
        log.info("notUpdateImage");
        return null;
    }

    @Override
    public UserDto updateUser(String email, CreateOrUpdateUser user) {
        userRepo.findByEmail(email).map(foundUser -> {
            foundUser.setFirstName(user.getFirstName());
            foundUser.setLastName(user.getLastName());
            foundUser.setPhone(user.getPhone());
            log.info("UpdateUser");
            return userMapper.fromUpdateUser(userRepo.save(foundUser));
        }).orElseThrow(()->
        new UsernameNotFoundException("Пользователя не существует"));
        log.info("notUpdateUser");
        return null;
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

}
