package com.example.bulletinboard.service;

import com.example.bulletinboard.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image upload(MultipartFile imageFile) throws IOException;

    byte[] getImage(Integer imageId);
}
