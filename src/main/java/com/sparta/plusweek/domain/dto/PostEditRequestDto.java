package com.sparta.plusweek.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

// 게시글 수정 요청 DTO
@Getter
public class PostEditRequestDto {

    @NotBlank
    @Length(max = 500)
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    @Length(max = 5000)
    private String contents;
}
