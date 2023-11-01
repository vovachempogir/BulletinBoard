package com.example.bulletinboard.dto;

import lombok.*;

/**
 * Класс <i>UserDto</i> представляет собой шаблон передачи данных для пользователей.
 * Этот класс используется для передачи информации об объявлении между различными слоями приложения.
 * DTO представляет собой объект, который содержит данные,
 * необходимые для выполнения операции или запроса в приложении.
 * Класс использует библиотеку <b>lombok</b>.
 */
@Data
@Builder
public class UserDto {
    /**
     * Идентификатор пользователя.
     */
    private Integer id;

    /**
     * Электроная почта пользователя.
     */
    private String email;

    /**
     * Имя пользователя.
     */
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    private String lastName;

    /**
     * Номер телефона пользователя.
     */
    private String phone;

    /**
     * Аватар пользователя.
     */
    private String image;

    /**
     * Роль пользователя.
     */
    private Role role;
}