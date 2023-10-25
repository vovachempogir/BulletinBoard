package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface UserService {

    UserDto getInfoAboutUser();

    byte[] updateImage(MultipartFile image) throws IOException;

    UserDto updateUser(CreateOrUpdateUser user);

    boolean updatePassword(NewPassword newPassword);

    boolean downloadAvatar(int id, HttpServletResponse response) throws IOException;
}
