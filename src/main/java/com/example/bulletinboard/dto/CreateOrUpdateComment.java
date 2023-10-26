package com.example.bulletinboard.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateOrUpdateComment {

    @Length(min = 8, max = 64)
    private String text;

}
