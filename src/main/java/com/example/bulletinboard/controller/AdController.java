package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.Ads;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.service.AdService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Api(tags = "Объявления")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    @GetMapping
    public List<AdDto> getAllAds() {
        return adService.getAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdDto createAd(@RequestPart("properties") CreateOrUpdateAd ad,
                     @RequestPart("image") MultipartFile image) throws IOException {
        return adService.create(ad,image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdDto> getAdById(@PathVariable Integer id) {
        AdDto ad = adService.getById(id);
        return ResponseEntity.ok(ad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdDto> delete(@PathVariable Integer adId, Integer commentId) {
        if (!adService.deleteByID(adId,commentId)) {
            throw new RuntimeException(String.valueOf(HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateAd(@RequestBody CreateOrUpdateAd createOrUpdateAd,
                                     @PathVariable Integer id) {
        adService.updateAd(id, createOrUpdateAd);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/me")
    public Ads getUsersAd() {
        return new Ads();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] updateAdImage(@PathVariable Integer id,
                                @RequestParam("image") MultipartFile image) throws IOException {
        return adService.updateImage(id, image);

    }
}
