package com.example.bulletinboard.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * Класс <i>Register</i> представляет собой шаблон используемый для регистрации пользователей в системе.
 * Этот класс используется для передачи информации об объявлении между различными слоями приложения.
 * DTO представляет собой объект, который содержит данные,
 * необходимые для выполнения операции или запроса в приложении.
 * Класс включает в себя аннотации валидации, такие как <i>@Length`</i> и <i>`@Pattern`</i>, чтобы гарантировать, что введенные данные
 * соответствуют определенным ограничениям.
 * Класс использует библиотеку <b>lombok</b>,
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    /**
     * Имя пользователя пользователя.
     * Должно быть уникальным и содержать от 4 до 32 символов в длину.
     */
    @Length(min = 4, max = 32)
    private String username;

    /**
     * Пароль пользователя.
     * Должен содержать от 8 до 16 символов в длину.
     */
    @Length(min = 8, max = 16)
    private String password;

    /**
     * Имя пользователя.
     * Должно содержать от 2 до 16 символов в длину.
     */
    @Length(min = 2, max = 16)
    private String firstName;

    /**
     * Фамилия пользователя.
     * Должна содержать от 2 до 16 символов в длину.
     */
    @Length(min = 2, max = 16)
    private String lastName;

    /**
     * Номер телефона пользователя в формате "+7 (XXX) XXX-XX-XX".
     */
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    /**
     * Роль пользователя, представляющая его права доступа и уровень доступа в системе.
     */
    private Role role;
}