package com.example.bulletinboard.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

/**
 * Класс <i>CreateOrUpdateAd</i> представляет собой шаблон используемый для изменения или создания нового обьявления.
 * Класс включает в себя аннотации валидации, такие как <i>@Length`</i> и <i>@Size`</i>  чтобы гарантировать, что введенные данные
 * соответствуют определенным ограничениям.
 * Класс использует библиотеку <b>lombok</b>,
 */
@Data
@Builder
public class CreateOrUpdateAd {

    /**
     * Заголовок обьявления.
     * Должен содержать от 4 до 32 символов в длину.
     */
    @Length(min = 4, max = 32)
    private String title;

    /**
     * Цена товара обьявления.
     * Цена не может быть больше 10000000.
     */
    @Size(max = 10000000)
    private Integer price;

    /**
     * Описание товара обьявления.
     * Должен содержать от 8 до 1000000 символов в длину.
     */
    @Length(min = 8, max = 1000000)
    private String description;
}
