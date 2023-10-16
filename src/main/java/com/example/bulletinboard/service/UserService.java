package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface UserService {

    UserDto getInfoAboutUser(String email);

    UserDto updateImage(Integer id, MultipartFile image) throws IOException;

    UserDto updateUser(String email, CreateOrUpdateUser user);

    boolean updatePassword(NewPassword newPassword);
}
