package com.example.bulletinboard.service;

import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.security.AuthUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    @Mapping(source = "user.email",target = "username")
    AuthUserDto toAuthUser(User user);
}
