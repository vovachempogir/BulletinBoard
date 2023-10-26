package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.*;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.AdRepo;
import com.example.bulletinboard.repository.CommentRepo;
import com.example.bulletinboard.repository.ImageRepo;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.AdMapper;
import com.example.bulletinboard.service.AdService;
import com.example.bulletinboard.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepo adRepo;
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    private final AdMapper adMapper;
    private final UserDetails userDetails;
    private final ImageService imageService;

    @Override
    @Transactional
    public AdDto create(CreateOrUpdateAd createAd, MultipartFile image) throws IOException {
        Ad ad = adMapper.createAd(createAd);
        User user = getUser();
        ad.setUser(user);
        ad.setImage(imageService.upload(image));
        adRepo.save(ad);
        log.info("createAd");
        return adMapper.toDto(ad);
    }

    @Override
    @Transactional
    public AdDto getById(Integer id) {
        log.info("getAd");
        return adMapper.toDto(adRepo.findById(id).orElseThrow(AdNotFoundException::new));
    }

    @Override
    @Transactional
    public void updateImage(Integer id, MultipartFile image) throws IOException {
        Ad ad = adRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Объявление не найдено"));
        imageRepo.delete(ad.getImage());
        ad.setImage(imageService.upload(image));
        adRepo.save(ad);
        log.info("Update image ad with id - {}", id);
    }

    @Override
    @Transactional
    public Ads getAll() {
        log.info("getAllAdd");
        return adMapper.to(adRepo.findAll());
    }

    @Override
    @Transactional
    public Ads getAllByUserName() {
        User user = getUser();
        return adMapper.to(adRepo.findAllByUserEmail(user.getEmail()));
    }

    @Override
    @Transactional
    public void updateAd(Integer adId, CreateOrUpdateAd ad) {
        User user = getUser();
        adRepo.findById(adId).map(foundAd -> {
            if (rightsVerification(user, foundAd)) {
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
    @Transactional
    public void deleteById(Integer id){
        User user = getUser();
        Ad ad = getAdById(id);
        if (rightsVerification(user, ad)) {
            adRepo.deleteById(id);
            commentRepo.deleteAllByAd_Id(id);
            imageRepo.deleteById(ad.getImage().getId());
        }
    }

    @Override
    @Transactional
    public ExtendedAd getAdFullInfo(Integer id) {
        return adMapper.toExtendAd(getAdById(id));
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
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Такого пользователя не существует"));
    }

    private Ad getAdById(Integer id) {
        return adRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Объявление не найдено"));
    }

}