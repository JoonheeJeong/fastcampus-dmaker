package com.fastcampus.yangseyeol.dmaker.controller;

import com.fastcampus.yangseyeol.dmaker.dto.DMakerErrorResponse;
import com.fastcampus.yangseyeol.dmaker.exception.DMakerException;
import com.fastcampus.yangseyeol.dmaker.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class ExceptionHandlerScopeTest {

    @GetMapping("/testException")
    public void testException() {
        log.info("testException");
        throw new DMakerException(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleDMakerException(
            DMakerException e,
            HttpServletRequest req
    ) {
        log.info("uri: {}, error code: {}, msg: {}",
                req.getRequestURI(), e.getErrorCode(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .message("ExceptionHandlerScopeTest(같은 클래스)의 exception handler")
                .build();
    }
}
