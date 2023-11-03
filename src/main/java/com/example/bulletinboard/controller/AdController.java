package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.Ads;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
import com.example.bulletinboard.dto.ExtendedAd;
import com.example.bulletinboard.service.AdService;
import com.example.bulletinboard.service.ImageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер для работы с объявлениями.
 */
@Api(tags = "Объявления")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    private final ImageService imageService;

    /**
     * Получает все объявления.
     *
     * @return объект с списком объявлений
     */
    @GetMapping()
    public Ads getAllAds() {
        return adService.getAll();
    }

    /**
     * Создает новое объявление с заданными параметрами и изображением.
     *
     * @param ad    информация о создаваемом объявлении
     * @param image изображение объявления в формате MultipartFile
     * @return DTO с созданным объявлением
     * @throws IOException при возникновении ошибки ввода-вывода
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdDto createAd(@RequestPart("properties") CreateOrUpdateAd ad,
                          @RequestPart("image") MultipartFile image) throws IOException {
        return adService.create(ad, image);
    }

    /**
     * Получает информацию о заданном объявлении.
     *
     * @param id идентификатор объявления
     * @return ответ с расширенной информацией о объявлении
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAdInfo(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getAdFullInfo(id));
    }

    /**
     * Удаляет заданное объявление.
     *
     * @param id идентификатор объявления
     * @return ответ с указанием успешности удаления
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        adService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет информацию о заданном объявлении.
     *
     * @param id идентификатор объявления
     * @param createOrUpdateAd информация о обновляемом объявлении
     * @return ответ без содержимого
     */
    @PatchMapping("/{id}")
    public ResponseEntity updateAd(@RequestBody CreateOrUpdateAd createOrUpdateAd,
                                   @PathVariable Integer id) {
        adService.updateAd(id, createOrUpdateAd);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Получает все объявления пользователя.
     *
     * @return объект с списком объявлений пользователя
     */
    @GetMapping("/me")
    public Ads getUsersAd() {
        return adService.getAllByUserName();
    }

    /**
     * Обновляет изображение заданного объявления.
     *
     * @param id идентификатор объявления
     * @param image изображение объявления в формате MultipartFile
     * @return ответ с сообщением об успешном обновлении изображения
     * @throws IOException при возникновении ошибки ввода-вывода
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAdImage(@PathVariable Integer id,
                                                @RequestParam("image") MultipartFile image) throws IOException {
        adService.updateImage(id, image);
        return ResponseEntity.ok().build();
    }

    /**
     * Получает изображение заданного объявления.
     *
     * @param adId идентификатор объявления
     * @return ответ с изображением в виде массива байтов
     */
    @GetMapping(value = "/image/{adId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable int adId) {
        return ResponseEntity.ok(imageService.getImage(adId));
    }
}
