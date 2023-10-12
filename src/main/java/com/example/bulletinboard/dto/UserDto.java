package com.example.bulletinboard.dto;

import lombok.*;

@Data
@Builder
public class UserDto {
    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String image;

    private Role role;
}