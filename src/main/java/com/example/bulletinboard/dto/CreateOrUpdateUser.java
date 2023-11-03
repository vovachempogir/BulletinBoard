package com.example.bulletinboard.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Класс <i>CreateOrUpdateUser</i> представляет собой шаблон используемый для изменения или создания нового пользователя.
 * Класс включает в себя аннотации валидации, такие как <i>@Length`</i>, <i>@NotBlank`</i>, <i>@Pattern`</i> чтобы гарантировать, что введенные данные
 * соответствуют определенным ограничениям.
 * Класс использует библиотеку <b>lombok</b>,
 * методы <b>org.hibernate.validator.constraints.Length</b>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateUser {

    /**
     * Имя пользователя.
     * Должен содержать от 3 до 10 символов в длину.
     * Поле не может быть пустным
     */
    @NotBlank
    @Length(min = 3, max = 10)
    private String firstName;

    /**
     * Фамилия пользователя.
     * Должен содержать от 3 до 10 символов в длину.
     * Поле не может быть пустным
     */
    @NotBlank
    @Length(min = 3, max = 10)
    private String lastName;

    /**
     * Номер телефона пользователя в формате "+7 (XXX) XXX-XX-XX".
     */
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
}
