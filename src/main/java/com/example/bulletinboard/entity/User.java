package com.example.bulletinboard.entity;

import com.example.bulletinboard.dto.Role;
import lombok.*;

import javax.persistence.*;

/**
 * Класс описывающий сущность <b>User</b>
 * Класс использует библиотеку <b>lombok</b>lombok, <b>javax.persistence</b>
 * На базе класса создана таблица в
 * базе данных со связью  <b>OneToOne</b> с таблицей <b>Image</b>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    /**
     * это поле генерирует id пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * это поле электронной почты пользователя
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    private String email;

    /**
     * это поле пароля пользователя,
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    private String password;

    /**
     * это поле имя пользователя
     * ячейка в таблице не может быть пустой
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * это поле фамилии пользователя
     * ячейка в таблице не может быть пустой
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * это поле номера телефона пользователя
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    private String phone;

    /**
     * это поле роли пользователя
     * привязана к шаблону <b>Role</b>
     * ячейка в таблице не может быть пустой
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * это поле <b>id изображения</b> пользователя
     * привязана к сущности <b>Image</b>
     * поле связывает сущность <b>User</b> с сущностью <b>Image</b>, тип связи <b>OneToOne</b>
     */
    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
}