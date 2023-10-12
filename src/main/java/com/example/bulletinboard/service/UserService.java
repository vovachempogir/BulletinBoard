package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto create(UserDto usersCreation);
    Optional<UserDto> getById(Integer id);
    List<UserDto> getAll();
    public void updateUser(Integer userId, CreateOrUpdateUser user);
}
