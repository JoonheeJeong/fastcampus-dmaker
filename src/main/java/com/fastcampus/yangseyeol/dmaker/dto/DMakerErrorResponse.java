package com.fastcampus.yangseyeol.dmaker.dto;

import com.fastcampus.yangseyeol.dmaker.exception.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DMakerErrorResponse {
    private ErrorCode errorCode;
    private String message;
}
