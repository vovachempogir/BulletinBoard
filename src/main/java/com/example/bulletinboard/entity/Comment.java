package com.example.bulletinboard.entity;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

/**
 * Класс описывающий сущность <b>Comment</b>
 * Класс использует библиотеку <b>lombok</b>lombok, <b>javax.persistence</b>, <b>java.time</b>
 * На базе класса создана таблица  <i>comments</i>
 * в базе данных со связью  <b>ManyToOne</b> с таблицей <b>Ad</b>
 * и <b>ManyToOne</b> с таблицей <b>User</b>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {

    /**
     * это поле генерирует id комментария
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * это поле текста комментария
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    private String text;

    /**
     * это поле <b>id пользователя</b> комментария
     * привязана к сущности <b>User</b>
     * поле связывает сущность <b>Comment</b> с сущностью <b>User</b>, тип связи <b>ManyToOne</b>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * это поле <b>id объявления</b> пользователя
     * привязана к сущности <b>Ad</b>
     * поле связывает сущность <b>Comment</b> с сущностью <b>Ad</b>, тип связи <b>ManyToOne</b>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", referencedColumnName = "id")
    private Ad ad;

    /**
     * это поле комментария в формате времени <i>yyyy-MM-dd HH:mm:ss.S</i>
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    private Instant createdAt;
}