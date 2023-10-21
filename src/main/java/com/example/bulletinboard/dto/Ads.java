package com.example.bulletinboard.dto;

import lombok.*;
import java.util.List;

@Data
@RequiredArgsConstructor

public class Ads {
    private Integer count;
    private List<AdDto> results;
}