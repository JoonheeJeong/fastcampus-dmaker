package com.fastcampus.yangseyeol.dmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeveloperStatus {
    EMPLOYED("고용"),
    RETIRED("퇴직");

    private final String description;
}
