package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.Register;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.utils.EncodedMapping;
import com.example.bulletinboard.utils.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(source = "username",target = "email")
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toUser(Register register);

    CreateOrUpdateUser fromUpdateUser(User user);
}