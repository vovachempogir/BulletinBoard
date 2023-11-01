package com.example.bulletinboard.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Класс <i>Login</i> представляет собой шаблон используемый для передачи логина пользователя.
 * Класс включает в себя аннотации валидации, такие как <i>@Length`</i>чтобы гарантировать, что введенные данные
 * соответствуют определенным ограничениям.
 * Класс использует библиотеку <b>lombok</b>,
 */
@Data
@Builder
public class Login {

    /**
     * Пользователское имя аккаунта.
     * Должен содержать от 8 до 16 символов в длину.
     */
    @Length(min = 8, max = 16)
    private String username;

    /**
     * Пароль пользователя.
     * Должен содержать от 8 до 16 символов в длину.
     */
    @Length(min = 4, max = 32)
    private String password;
}
