package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.Ads;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
import com.example.bulletinboard.dto.ExtendedAd;
import com.example.bulletinboard.entity.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {

    default Ads to(List<Ad> results) {
        return to(results.size(), results);
    }

    Ads to(Integer count, List<Ad> results);

    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "id", target = "pk")
    AdDto toDto(Ad ad);

    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "pk", target = "id")
    Ad toAd(AdDto adDto);

    CreateOrUpdateAd fromUpdateAd(Ad ad);

    Ad createAd(CreateOrUpdateAd create);

    @Mapping(source = "id",target = "pk")
    @Mapping(source = "user.firstName",target = "authorFirstName")
    @Mapping(source = "user.lastName",target = "authorLastName")
    @Mapping(source = "user.email",target = "email")
    @Mapping(source = "user.phone",target = "phone")
    ExtendedAd toExtendAd(Ad ad);
}
