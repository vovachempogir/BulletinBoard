package com.example.bulletinboard.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

@Data
@Builder
public class CreateOrUpdateAd {
    @Length(min = 4, max = 32)
    private String title;

    @Size(max = 10000000)
    private Integer price;

    @Length(min = 8, max = 1000000)
    private String description;
}
