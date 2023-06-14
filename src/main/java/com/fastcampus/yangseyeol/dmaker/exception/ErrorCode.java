package com.fastcampus.yangseyeol.dmaker.exception;

public enum ErrorCode {
    NO_DEVELOPER("해당되는 개발자가 없습니다."),
    LEVEL_EXPERIENCE_YEARS_NOT_MATCHED("개발자 수준과 연차가 맞지 않습니다."),
    DUPLICATE_MEMBER_ID("이미 동일한 ID가 존재합니다."),
    INVALID_REQUEST("잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.")
    ;

    private final String message;

    ErrorCode(String msg) { this.message = msg; }

    public String getMessage() { return message; }
}
