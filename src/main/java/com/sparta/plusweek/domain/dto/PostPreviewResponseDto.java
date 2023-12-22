package com.sparta.plusweek.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostPreviewResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdAt;
}
