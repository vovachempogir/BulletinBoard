package com.example.bulletinboard.dto;

import lombok.*;

/**
 * Класс <i>AdDto</i> представляет собой шаблон передачи данных для объявления.
 * Этот класс используется для передачи информации об объявлении между различными слоями приложения.
 * DTO представляет собой объект, который содержит данные,
 * необходимые для выполнения операции или запроса в приложении.
 * Класс использует библиотеку <b>lombok</b>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdDto {
    /**
     * Идентификатор автора объявления.
     */
    private Integer author;
    /**
     * Изображение объявления.
     */
    private String image;
    /**
     * Идентификатор объявления.
     */
    private Integer pk;
    /**
     * Цена объявления.
     */
    private Integer price;
    /**
     * Заголовок объявления.
     */
    private String title;
}
