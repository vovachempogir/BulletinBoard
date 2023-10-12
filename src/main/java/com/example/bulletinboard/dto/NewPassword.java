package com.example.bulletinboard.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewPassword {
    @Length(min = 8, max = 16)
    private String newPassword;

    @Length(min = 8, max = 16)
    private String currentPassword;
}
