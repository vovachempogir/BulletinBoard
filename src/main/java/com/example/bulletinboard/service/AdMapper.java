package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.Ads;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
import com.example.bulletinboard.dto.ExtendedAd;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.Image;
import com.example.bulletinboard.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {

    default Ads to(List<Ad> results) {
        return to(results.size(), results);
    }

    Ads to(Integer count, List<Ad> results);

    @Mapping(source = "user", target = "author", qualifiedByName = "authorToInt")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPathString")
    @Mapping(source = "id", target = "pk")
    AdDto toDto(Ad ad);

    CreateOrUpdateAd fromUpdateAd(Ad ad);

    Ad createAd(CreateOrUpdateAd create);

    @Mapping(source = "id",target = "pk")
    @Mapping(source = "user.firstName",target = "authorFirstName")
    @Mapping(source = "user.lastName",target = "authorLastName")
    @Mapping(source = "user.email",target = "email")
    @Mapping(source = "user.phone",target = "phone")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPathString")
    ExtendedAd toExtendAd(Ad ad);

    @Named("imageToPathString")
    default String imageToPathString(Image image) {
        return "/ads/image/" + image.getId();
    }

    @Named("authorToInt")
    default Integer authorToInt(User user) {
        return user.getId();
    }
}
