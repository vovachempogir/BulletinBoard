package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.Register;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);
}