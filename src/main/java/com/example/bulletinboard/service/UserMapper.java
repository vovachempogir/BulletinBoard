package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.Register;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);


    User toUser(Register register);

    CreateOrUpdateUser fromUpdateUser(User user);

    UserDto updateToDto(CreateOrUpdateUser createOrUpdateUser);

    User updateToUser(CreateOrUpdateUser createOrUpdateUser);
}