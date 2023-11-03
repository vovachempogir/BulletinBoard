package com.example.bulletinboard.dto;

import lombok.*;

/**
 * Класс <i>ExtendedAd</i> представляет расширенную информацию об объявлении.
 * Класс использует библиотеку <b>lombok</b>,
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedAd {

    /**
     * Идентификатор объявления.
     */
    private Integer pk;

    /**
     * Имя автора объявления.
     */
    private String authorFirstName;

    /**
     * Фамилия автора объявления.
     */
    private String authorLastName;

    /**
     * Описание объявления.
     */
    private String description;

    /**
     * Электронная почта автора объявления.
     */
    private String email;

    /**
     * Изображение объявления.
     */
    private String image;

    /**
     * Телефон автора объявления.
     */
    private String phone;

    /**
     * Цена объявления.
     */
    private Integer price;

    /**
     * Заголовок объявления.
     */
    private String title;
}
