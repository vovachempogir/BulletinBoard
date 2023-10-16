package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.repository.AdRepo;
import com.example.bulletinboard.repository.CommentRepo;
import com.example.bulletinboard.service.AdMapper;
import com.example.bulletinboard.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepo adRepo;
    private final CommentRepo commentRepo;
    private final AdMapper adMapper;

    @Value("${upload.path.ad}")
    private String uploadPath;

    @Override
    public AdDto create(CreateOrUpdateAd createAd, MultipartFile image) throws IOException {
        Ad ad = adMapper.createAd(createAd);
        loadImage(ad, image);
        return adMapper.toDto(adRepo.save(adMapper.createAd(createAd)));
    }

    @Override
    public AdDto getById(Integer id) {
        return adMapper.toDto(adRepo.findById(id).orElseThrow(AdNotFoundException::new));
    }

    @Override
    public byte[] updateImage(Integer id, MultipartFile image) throws IOException {
        Ad ad = adRepo.findById(id).orElseThrow(AdNotFoundException::new);
        if (ad.getImage() != null) {
            Files.exists(Path.of(ad.getImage()));
        }
        ad.setImage(image.getOriginalFilename());
        adRepo.save(ad);
        return image.getBytes();
    }

    @Override
    public List<AdDto> getAll() {
        return adRepo.findAll()
                .stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAd(Integer adId, CreateOrUpdateAd ad) {
        adRepo.findById(adId).map(foundAd -> {
            foundAd.setTittle(ad.getTitle());
            foundAd.setPrice(ad.getPrice());
            foundAd.setDescription(ad.getDescription());
            return adMapper.fromUpdateAd(adRepo.save(foundAd));
        }).orElseThrow(() -> new AdNotFoundException());
    }

    @Override
    public Boolean deleteByID(Integer adId, Integer commentId) {
        if (adRepo.existsById(adId) & commentRepo.existsById(commentId)) {
            adRepo.deleteById(adId);
            commentRepo.deleteById(commentId);
            return true;
        }
        return false;
    }

    public byte[] loadImage(Ad ad, MultipartFile image) throws IOException {
        Path path = Path.of(uploadPath, image.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        ad.setImage(path.toString());
        adRepo.save(ad);
        return image.getBytes();
    }

    private class AdNotFoundException extends RuntimeException {
        public AdNotFoundException() {
            super("Объявление не существует");
        }
    }

}