package com.example.bulletinboard.dto;

import lombok.*;
import java.util.List;


/**
 * Класс Comments представляет комментарии к записи или объекту.
 * Этот класс содержит информацию о количестве комментариев и списке комментариев.
 */
@Data
@RequiredArgsConstructor
public class Comments {

    /**
     * Количество комментариев.
     */
    private Integer count;

    /**
     * Список комментариев.
     */
    private List<CommentDto> results;
}
