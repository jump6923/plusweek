package com.sparta.plusweek.domain.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignUpRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "닉네임은 최소 3자 이상, 알파벳 대소문자와 숫자로 이루어져야 합니다.")
    private final String username;
    @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    private final String password;
    private final String confirmPassword;
}

