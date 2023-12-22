package com.sparta.plusweek.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
}
