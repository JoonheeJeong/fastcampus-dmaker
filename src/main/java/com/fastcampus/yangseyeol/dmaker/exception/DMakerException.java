package com.fastcampus.yangseyeol.dmaker.exception;

import lombok.Getter;

@Getter
public class DMakerException extends RuntimeException {
    private final ErrorCode errorCode;

    public DMakerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
