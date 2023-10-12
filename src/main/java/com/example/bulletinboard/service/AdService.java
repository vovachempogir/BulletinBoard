package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.CreateOrUpdateAd;

import java.util.List;
import java.util.Optional;

public interface AdService {
    AdDto create(AdDto ad);
    Optional<AdDto> getById(Integer id);

    AdDto updateImage(Integer id);

    List<AdDto> getAll();
    public void updateAd(Integer adId, CreateOrUpdateAd createOrUpdateAd);

    Boolean deleteByID(Integer adId);
}
