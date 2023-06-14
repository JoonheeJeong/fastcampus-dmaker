package com.fastcampus.yangseyeol.dmaker.controller;

import com.fastcampus.yangseyeol.dmaker.dto.DevDto;
import com.fastcampus.yangseyeol.dmaker.service.DMakerService;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperLevel;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperStatus;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(DMakerController.class)
public class DMakerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    @Test
    public void testGetDeveloperByStatus() throws Exception {
        DevDto retiredSeniorFrontDev = DevDto.builder()
                .memberId("retired senior frontend")
                .name("retired senior frontend")
                .developerType(DeveloperType.FRONT_END)
                .developerLevel(DeveloperLevel.SENIOR)
                .experienceYears(31)
                .age(62)
                .developerStatus(DeveloperStatus.RETIRED)
                .build();
        DevDto juniorBackendDev = DevDto.builder()
                .memberId("junior backend")
                .name("junior backend")
                .developerType(DeveloperType.BACK_END)
                .developerLevel(DeveloperLevel.JUNIOR)
                .experienceYears(1)
                .age(31)
                .build();
        given(dMakerService.getDevelopersByStatus(DeveloperStatus.RETIRED))
                .willReturn(List.of(retiredSeniorFrontDev));
        given(dMakerService.getDevelopersByStatus(DeveloperStatus.EMPLOYED))
                .willReturn(List.of(juniorBackendDev));

        mockMvc.perform(
                get("/developer")
                    .queryParam("status", DeveloperStatus.RETIRED.name())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath(
                        "$.[0].developerLevel",
                        is(DeveloperLevel.SENIOR.name())))
                .andExpect(jsonPath(
                        "$.[0].developerType",
                        is(DeveloperType.FRONT_END.name())))
                .andExpect(jsonPath(
                        "$.[0].developerStatus",
                        is(DeveloperStatus.RETIRED.name())));
    }
}
