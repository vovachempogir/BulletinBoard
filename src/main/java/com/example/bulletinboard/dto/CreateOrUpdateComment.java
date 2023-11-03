package com.example.bulletinboard.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Класс <i>CreateOrUpdateComment</i> представляет собой шаблон используемый для изменения или создания нового комментария.
 * Класс включает в себя аннотации валидации, такие как <i>@Length`</i> чтобы гарантировать, что введенные данные
 * соответствуют определенным ограничениям.
 * Класс использует библиотеку <b>lombok</b>,
 */
@Data
public class CreateOrUpdateComment {

    /**
     * Текст комментария.
     * Должен содержать от 8 до 64 символов в длину.
     */
    @Length(min = 8, max = 64)
    private String text;
}
