package com.fastcampus.yangseyeol.dmaker.type;

import com.fastcampus.yangseyeol.dmaker.exception.DMakerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.fastcampus.yangseyeol.dmaker.exception.ErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@Getter
@RequiredArgsConstructor
public enum DeveloperLevel {
    NEW("신입 개발자", 0, 0),
    JUNIOR("주니어 개발자", 1, 4),
    JUNGNIOR("중니어 개발자", 5, 9),
    SENIOR("시니어 개발자", 10, 70)
    ;

    private final String description;
    private final int minExpYear;
    private final int maxExpYear;

    public void validateExpYear(int expYear) {
        if (!isValidExpYear(expYear))
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
    }

    private boolean isValidExpYear(int expYear) {
        return (expYear >= minExpYear && expYear <= maxExpYear);
    }
}
