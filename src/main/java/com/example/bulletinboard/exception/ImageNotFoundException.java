package com.example.bulletinboard.exception;

import org.springframework.data.crossstore.ChangeSetPersister;

public class ImageNotFoundException extends NotFoundException {
    public ImageNotFoundException() {
        super("Изображение не найдено");
    }
}
