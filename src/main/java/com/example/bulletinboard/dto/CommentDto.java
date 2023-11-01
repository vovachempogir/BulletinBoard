package com.example.bulletinboard.dto;

import lombok.*;

import java.time.Instant;

/**
 * Класс <i>CommentDto</i> представляет собой шаблон передачи данных для комментариев.
 * Этот класс используется для передачи информации об объявлении между различными слоями приложения.
 * DTO представляет собой объект, который содержит данные,
 * необходимые для выполнения операции или запроса в приложении.
 * Класс использует библиотеку <b>lombok</b>, <b>java.time</b>.
 */
@Data
@AllArgsConstructor
@Builder
public class CommentDto {
    /**
     * Идентификатор автора объявления.
     */
    private Integer author;
    /**
     * Изображение объявления.
     */
    private String authorImage;
    /**
     * Имя пользователя комментария.
     */
    private String authorFirstName;
    /**
     * Время выставления комментария.
     */
    private Instant createdAt;
    /**
     * Идентификатор объявления.
     */
    private Integer pk;
    /**
     * Текст комментария.
     */
    private String text;
}
