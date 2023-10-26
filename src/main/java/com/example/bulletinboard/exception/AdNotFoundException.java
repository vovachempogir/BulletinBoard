package com.example.bulletinboard.exception;

public class AdNotFoundException extends NotFoundException{
    public AdNotFoundException() {
        super("Объявление не найдено");
    }
}
