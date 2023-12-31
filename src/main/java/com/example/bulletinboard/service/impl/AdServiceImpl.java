package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.*;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.exception.AdNotFoundException;
import com.example.bulletinboard.exception.ForbiddenException;
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

/**
 * Реализация интерфейса AdService, предоставляющая функциональность по  добавлению, поиску, изменению, удалению объявлений.
 */
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


    /**
     * Создает новое объявление и сохраняет его в базе данных.
     *
     * @param createAd объект с данными для создания объявления
     * @param image мультифайл изображения для объявления
     * @return объект AdDto, представляющий созданное объявление
     * @throws IOException если происходит ошибка ввода-вывода при загрузке изображения
     */
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

    /**
     * Возвращает объявление по заданному идентификатору.
     *
     * @param id идентификатор объявления
     * @return объект AdDto, представляющий найденное объявление
     * @throws AdNotFoundException если объявление не найдено
     */
    @Override
    @Transactional
    public AdDto getById(Integer id) {
        log.info("getAd");
        return adMapper.toDto(adRepo.findById(id).orElseThrow(AdNotFoundException::new));
    }

    /**
     * Обновляет изображение для объявления.
     *
     * @param id идентификатор объявления
     * @param image мультифайл изображения для обновления
     * @throws IOException если происходит ошибка ввода-вывода при загрузке изображения
     * @throws EntityNotFoundException если объявление не найдено
     */
    @Override
    @Transactional
    public void updateImage(Integer id, MultipartFile image) throws IOException {
        Ad ad = adRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Объявление не найдено"));
        imageRepo.delete(ad.getImage());
        ad.setImage(imageService.upload(image));
        adRepo.save(ad);
        log.info("Update image ad with id - {}", id);
    }

    /**
     * Возвращает список всех объявлений.
     *
     * @return объект Ads, содержащий список всех объявлений
     */
    @Override
    @Transactional
    public Ads getAll() {
        log.info("getAllAdd");
        return adMapper.to(adRepo.findAll());
    }

    /**
     * Возвращает список всех объявлений пользователя по его имени.
     *
     * @return объект Ads, содержащий список всех объявлений пользователя
     */
    @Override
    @Transactional
    public Ads getAllByUserName() {
        User user = getUser();
        return adMapper.to(adRepo.findAllByUserEmail(user.getEmail()));
    }

    /**
     * Обновляет объявление по идентификатору.
     *
     * @param adId идентификатор объявления
     * @param ad объект CreateOrUpdateAd с обновленными данными объявления
     * @throws AdNotFoundException если объявление не найдено
     * @throws ForbiddenException если у пользователя нет прав на изменение объявления
     */
    @Override
    @Transactional
    public void updateAd(Integer adId, CreateOrUpdateAd ad) {
        User user = getUser();
        adRepo.findById(adId).map(foundAd -> {
            if (rightsVerification(user, foundAd)) {
                foundAd.setTitle(ad.getTitle());
                foundAd.setPrice(ad.getPrice());
                foundAd.setDescription(ad.getDescription());
                log.info("updateAd");
                return adMapper.fromUpdateAd(adRepo.save(foundAd));
            }else {
                log.info("notUpdatedAd");
                throw new ForbiddenException("Нет прав на изменение комментария");
            }
        }).orElseThrow(AdNotFoundException::new);
    }

    /**
     * Удаляет объявление по заданному идентификатору.
     *
     * @param id идентификатор объявления для удаления
     * @throws ForbiddenException если у пользователя нет прав на удаление объявления
     */
    @Override
    @Transactional
    public void deleteById(Integer id){
        User user = getUser();
        Ad ad = getAdById(id);
        if (rightsVerification(user, ad)) {
            commentRepo.deleteAllByAdId(ad.getId());
            adRepo.deleteById(id);
            imageRepo.deleteById(ad.getImage().getId());
        } else {
            throw new ForbiddenException("Нет прав на удаление объявления");
        }
    }

    /**
     * Возвращает полную информацию об объявлении по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return объект ExtendedAd, содержащий полную информацию об объявлении
     */
    @Override
    @Transactional
    public ExtendedAd getAdFullInfo(Integer id) {
        return adMapper.toExtendAd(getAdById(id));
    }


    /**
     * Проверяет соответствие прав пользователя для данного объявления.
     *
     * @param user пользователь для проверки прав
     * @param ad объявление для проверки прав
     * @return true, если у пользователя есть права на данное объявление, иначе false
     */
    private boolean rightsVerification(User user, Ad ad) {
        return (user.getRole() == Role.ADMIN || ad.getUser().equals(ad.getUser()));
    }

    /**
     * Возвращает объект пользователя на основе информации о текущем пользователе.
     *
     * @return объект User, представляющий текущего пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Такого пользователя не существует"));
    }

    /**
     * Возвращает объявление по заданному идентификатору.
     *
     * @param id идентификатор объявления
     * @return объект Ad, представляющий найденное объявление
     * @throws NoSuchElementException если объявление не найдено
     */
    private Ad getAdById(Integer id) {
        return adRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Объявление не найдено"));
    }
}