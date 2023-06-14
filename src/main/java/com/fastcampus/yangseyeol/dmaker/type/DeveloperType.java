package com.fastcampus.yangseyeol.dmaker.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DeveloperType {
    FRONT_END("프론트엔드 개발자"),
    BACK_END("백엔드 개발자"),
    FULL_STACK("풀스택 개발자")
    ;

    private final String description;
}
