package com.example.bulletinboard.exception;

public class UserNotFoundException extends NotFoundException{

    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
