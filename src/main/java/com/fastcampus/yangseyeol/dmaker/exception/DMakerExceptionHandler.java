package com.fastcampus.yangseyeol.dmaker.exception;

import com.fastcampus.yangseyeol.dmaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {

    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleDMakerException(
            DMakerException e,
            HttpServletRequest req
    ) {
        log.error("uri: {}, error code: {}, msg: {}",
                req.getRequestURI(), e.getErrorCode(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public DMakerErrorResponse handleBadRequest(
            Exception e,
            HttpServletRequest req
    ) {
        log.error("uri: {}, msg: {}",
                req.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public DMakerErrorResponse handleException(
            Exception e,
            HttpServletRequest req
    ) {
        log.error("uri: {}, msg: {}",
                req.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
    }
}
