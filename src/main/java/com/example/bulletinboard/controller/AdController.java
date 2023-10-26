package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.Ads;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
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

@Api(tags = "Объявления")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    private final ImageService imageService;

    @GetMapping()
    public Ads getAllAds() {
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
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        adService.deleteById(id);
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
        return adService.getAllByUserName();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAdImage(@PathVariable Integer id,
                                @RequestParam("image") MultipartFile image) throws IOException {
        adService.updateImage(id, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/image/{adId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable int adId) {
        return ResponseEntity.ok(imageService.getImage(adId));
    }
}