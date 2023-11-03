package com.example.bulletinboard.entity;

import lombok.*;
import javax.persistence.*;

/**
 * Класс описывающий сущность <b>Ad</b>
 * Класс использует библиотеку <b>lombok</b>lombok, <b>javax.persistence</b>
 * На базе класса создана таблица  <i>ads</i>
 * в базе данных со связью  <b>ManyToOne</b> с таблицей <b>User</b>
 * и <b>OneToOne</b> с таблицей <b>Image</b>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ads")
public class Ad {

    /**
     * это поле генерирует id пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * это поле <b>id изображения объявления</b> пользователя
     * привязана к сущности <b>Image</b>
     * поле связывает сущность <b>Ad</b> с сущностью <b>Image</b>, тип связи <b>OneToOne</b>
     */
    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    /**
     * это поле стоимости товара
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    private Integer price;

    /**
     * это поле заголовка объявления
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    private String title;

    /**
     * это поле описания объявления
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    private String description;

    /**
     * это поле <b>id пользователя</b> объявления
     * привязана к сущности <b>User</b>
     * поле связывает сущность <b>Ad</b> с сущностью <b>User</b>, тип связи <b>ManyToOne</b>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}