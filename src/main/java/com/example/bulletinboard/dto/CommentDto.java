package com.example.bulletinboard.dto;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class CommentDto {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Instant createdAt;
    private Integer pk;
    private String text;
}
