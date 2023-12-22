package com.sparta.plusweek.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class PostCreateRequestDto {

    @NotBlank
    @Length(min = 1, max = 500)
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String password;

    @NotBlank
    @Length(min = 1, max = 5000)
    private String contents;

    public void changeEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
