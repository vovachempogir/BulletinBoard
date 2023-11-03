package com.example.bulletinboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Класс описывающий сущность <b>Image</b>
 * Класс использует библиотеку <b>lombok</b>lombok, <b>javax.persistence</b>
 * На базе класса создана таблица в базе данных
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "images")
public class Image {

    /**
     * это поле генерирует id изображения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * это поле хранит изображение в байтах
     * аннтоация <b>Lob</b> указывает тип данных, используемый для хранения больших обьектов
     */
    @Lob
    private byte[] data;
}
