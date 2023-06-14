package com.fastcampus.yangseyeol.dmaker.controller;

import com.fastcampus.yangseyeol.dmaker.dto.DMakerErrorResponse;
import com.fastcampus.yangseyeol.dmaker.dto.DevDto;
import com.fastcampus.yangseyeol.dmaker.exception.DMakerException;
import com.fastcampus.yangseyeol.dmaker.service.DMakerService;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developer")
    public List<DevDto> getDevelopersByStatus(
            @RequestParam final DeveloperStatus status)
    {
        log.info("GET /developer?status={} HTTP/1.1", status);

        return dMakerService.getDevelopersByStatus(status);
    }

    @GetMapping("/developer/{memberId}")
    public DevDto getDeveloperByMemberId(
            @PathVariable final String memberId)
    {
        log.info("GET /developer/{} HTTP/1.1", memberId);

        return dMakerService.getDevDtoByMemberId(memberId);
    }

    @PostMapping("/developer")
    public DevDto createDeveloper(
            @Valid @RequestBody final DevDto dto)
    {
        log.info("POST /developer HTTP/1.1");
        log.info("request: {}", dto);

        return dMakerService.createDeveloper(dto);
    }

    @PutMapping("/developer")
    public void editDeveloper(
            @Valid @RequestBody final DevDto dto)
    {
        log.info("PUT /developer HTTP/1.1");
        log.info("request: {}", dto);

        dMakerService.editDeveloper(dto);
    }

    @DeleteMapping("/developer/{memberId}")
    public void deleteDeveloper(
            @PathVariable final String memberId)
    {
        log.info("DELETE /developer/{} HTTP/1.1", memberId);

        dMakerService.deleteDeveloper(memberId);
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
                .message("DMakerController(다른 패키지)의 exception handler")
                .build();
    }
}
