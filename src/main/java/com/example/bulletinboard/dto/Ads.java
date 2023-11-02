package com.example.bulletinboard.dto;

import lombok.*;
import java.util.List;

/**
 * Класс <i>Ads</i> представляет собой шаблон всех обьявлений.
 * Класс использует библиотеку <b>lombok</b>.
 */
@Data
@RequiredArgsConstructor

public class Ads {

    /**
     * Поле необходимо для выявления количества обьявлений.
     */
    private Integer count;

    /**
     * Список всех обьявлений.
     */
    private List<AdDto> results;
}