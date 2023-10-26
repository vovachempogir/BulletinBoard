package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.NewPassword;
import com.example.bulletinboard.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface UserService {

    UserDto getInfoAboutUser();

    void updateImage(MultipartFile image) throws IOException;

    UserDto updateUser(CreateOrUpdateUser user);

    boolean updatePassword(NewPassword newPassword);

}
