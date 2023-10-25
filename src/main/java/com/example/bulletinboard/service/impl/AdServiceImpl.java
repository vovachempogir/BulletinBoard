package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.Ads;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
import com.example.bulletinboard.dto.Role;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.AdRepo;
import com.example.bulletinboard.repository.CommentRepo;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.AdMapper;
import com.example.bulletinboard.service.AdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepo adRepo;
    private final CommentRepo commentRepo;
    private final AdMapper adMapper;
    private final UserRepo userRepo;
    private final UserDetails userDetails;
    private final FilesService filesService;
    @Value("${upload.path.ad}")
    private String uploadPath;

    @Override
    public AdDto create(CreateOrUpdateAd createAd, MultipartFile image) throws IOException {
        Ad ad = adMapper.createAd(createAd);
        User user = getUser();
        ad.setUser(user);
        loadImage(ad, image);
        adRepo.save(ad);
        log.info("createAd");
        return adMapper.toDto(ad);
    }

    @Override
    public AdDto getById(Integer id) {
        log.info("getAd");
        return adMapper.toDto(adRepo.findById(id).orElseThrow(AdNotFoundException::new));
    }

    @Override
    public byte[] updateImage(Integer id, MultipartFile image) throws IOException {
        Ad ad = adRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Объявление не найдено"));
        if (ad.getImage()!=null){
            Files.deleteIfExists(Path.of(ad.getImage()));
        }
        loadImage(ad, image);
        adRepo.save(ad);
        return image.getBytes();
    }

    @Override
    public Ads getAll() {
        log.info("getAllAdd");
        return adMapper.to(adRepo.findAll());
    }

    @Override
    public Ads getAllByUserName() {
        User user = getUser();
        return adMapper.to(adRepo.findAllByUserEmail(user.getEmail()));
    }

    @Override
    public void updateAd(Integer adId, CreateOrUpdateAd ad) {
        User user = getUser();
        Ad ad1 = getAdById(adId);
        adRepo.findById(adId).map(foundAd -> {
            if (rightsVerification(user, ad1)) {
                foundAd.setTitle(ad.getTitle());
                foundAd.setPrice(ad.getPrice());
                foundAd.setDescription(ad.getDescription());
                return adMapper.fromUpdateAd(adRepo.save(foundAd));
            }else {
                throw new UnsupportedOperationException("Нет прав на изменение комментария");
            }
        }).orElseThrow(() -> new AdNotFoundException());
    }

    @Override
    public void deleteById(Integer id) throws IOException {
        User user = getUser();
        Ad ad = getAdById(id);
        if (rightsVerification(user, ad)) {
            adRepo.deleteById(id);
            commentRepo.deleteAllByAd_Id(id);
            Files.deleteIfExists(Path.of(ad.getImage()));
        }
    }

    @Override
    public void downloadImage(Integer id, HttpServletResponse response) throws IOException {
        Ad ad  = getAdById(id);
        String imagePath = ad.getImage();
        filesService.downloadFile(response, imagePath);
    }

    private class AdNotFoundException extends RuntimeException {
        public AdNotFoundException() {
            super("Объявление не существует");
        }
    }

    private boolean rightsVerification(User user, Ad ad) {
        return (user.getRole() == Role.ADMIN || ad.getUser().equals(user));
    }

    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    private Ad getAdById(Integer id) {
        return adRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Объявление не найдено"));
    }

    private void loadImage(Ad ad, MultipartFile image) throws IOException {
        Path path = getPath(image);
        filesService.uploadFile(image, path);
        ad.setImage(path.toAbsolutePath().toString());
    }

    private Path getPath(MultipartFile image) {
        return Path.of(uploadPath, image.getOriginalFilename());
    }

}