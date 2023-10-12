package com.example.bulletinboard.dto;

import lombok.*;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Comments {
    private Integer count;
    private List<CommentDto> results;
}
