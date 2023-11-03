package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.Register;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.Image;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.utils.EncodedMapping;
import com.example.bulletinboard.utils.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPathString")
    UserDto toDto(User user);

    @Mapping(source = "username",target = "email")
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toUser(Register register);

    CreateOrUpdateUser fromUpdateUser(User user);

    @Named("imageToPathString")
    default String imageToPathString(Image image) {
        if (image == null) {
            return null;
        }
        return "/users/image/" + image.getId();
    }
}