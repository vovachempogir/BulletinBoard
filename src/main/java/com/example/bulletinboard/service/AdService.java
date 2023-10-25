package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.Ads;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
import com.example.bulletinboard.entity.Ad;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AdService {
    AdDto create(CreateOrUpdateAd ad, MultipartFile image) throws IOException;
    AdDto getById(Integer id);
    byte[] updateImage(Integer id, MultipartFile image) throws IOException;
    Ads getAll();
    Ads getAllByUserName();
    void updateAd(Integer adId, CreateOrUpdateAd createOrUpdateAd);
    void deleteById(Integer id) throws IOException;
    void downloadImage(Integer id, HttpServletResponse response) throws IOException;
}
