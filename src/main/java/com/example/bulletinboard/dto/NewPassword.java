package com.example.bulletinboard.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Класс <i>NewPassword</i> представляет собой шаблон используемый для изменения пароля пользователя.
 * Класс включает в себя аннотации валидации, такие как <i>@Length`</i>чтобы гарантировать, что введенные данные
 * соответствуют определенным ограничениям.
 * Класс использует библиотеку <b>lombok</b>,
 */
@Data
public class NewPassword {

    /**
     * Новый пароль пользователя.
     * Должен содержать от 8 до 16 символов в длину.
     */
    @Length(min = 8, max = 16)
    private String newPassword;

    /**
     * Подтверждение пароля пользователя.
     * Должен содержать от 8 до 16 символов в длину.
     */
    @Length(min = 8, max = 16)
    private String currentPassword;
}
