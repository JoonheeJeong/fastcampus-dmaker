package com.fastcampus.yangseyeol.dmaker.service;

import com.fastcampus.yangseyeol.dmaker.dto.DevDto;
import com.fastcampus.yangseyeol.dmaker.entity.Developer;
import com.fastcampus.yangseyeol.dmaker.exception.DMakerException;
import com.fastcampus.yangseyeol.dmaker.exception.ErrorCode;
import com.fastcampus.yangseyeol.dmaker.repository.DeveloperRepository;
import com.fastcampus.yangseyeol.dmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperLevel;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperStatus;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DMakerServiceTests {

    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DMakerService dMakerService;

    private final Developer backendJuniorDev = Developer.builder()
            .memberId("backend junior")
            .developerType(DeveloperType.BACK_END)
            .developerLevel(DeveloperLevel.JUNIOR)
            .experienceYears(2)
            .name("mongmong")
            .age(32)
            .build();

    private final DevDto backendJuniorDevDto = DevDto.builder()
            .memberId("backend junior")
            .developerType(DeveloperType.BACK_END)
            .developerLevel(DeveloperLevel.JUNIOR)
            .experienceYears(2)
            .name("mongmong")
            .age(32)
            .build();

    private final Developer retiredBackendSeniorDev = Developer.builder()
            .memberId("retired backend senior")
            .developerType(DeveloperType.BACK_END)
            .developerLevel(DeveloperLevel.SENIOR)
            .experienceYears(30)
            .name("mongmong")
            .age(62)
            .developerStatus(DeveloperStatus.RETIRED)
            .build();

    private final DevDto retiredBackendSeniorDevDto = DevDto.builder()
            .memberId("retired backend senior")
            .developerType(DeveloperType.BACK_END)
            .developerLevel(DeveloperLevel.SENIOR)
            .experienceYears(30)
            .name("mongmong")
            .age(62)
            .developerStatus(DeveloperStatus.RETIRED)
            .build();

    @Test
    void createDeveloper_Success() {
        // given
        DevDto devDto = backendJuniorDevDto;
        given(modelMapper.map(devDto, Developer.class))
                .willReturn(backendJuniorDev);
        given(developerRepository.findDeveloperByMemberId(devDto.getMemberId()))
                .willReturn(Optional.empty());
        given(developerRepository.save(backendJuniorDev))
                .willReturn(backendJuniorDev);
        given(modelMapper.map(backendJuniorDev, DevDto.class))
                .willReturn(devDto);
        ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);

        // when
        DevDto ret = dMakerService.createDeveloper(devDto);

        // then
        verify(developerRepository, times(1))
                .save(captor.capture());
        Developer savedDeveloper = captor.getValue();
        assertEquals(backendJuniorDev, savedDeveloper);
        assertEquals(devDto, ret);
    }

    @Test
    void createDeveloper_failedWithDuplicateMemberId() {
        // given
        DevDto devDto = backendJuniorDevDto;
        given(developerRepository.findDeveloperByMemberId(devDto.getMemberId()))
                .willReturn(Optional.of(backendJuniorDev));

        // when
        // then
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(devDto));
        assertEquals(ErrorCode.DUPLICATE_MEMBER_ID, dMakerException.getErrorCode());
    }

    @Test
    void createDeveloper_failedWithUnMatchedExpYear() {
        createDeveloper_failedWithHighExperienceJunior();
        createDeveloper_failedWithHighNew();
        createDeveloper_failedWithLowSenior();
    }

    private void createDeveloper_failedWithHighExperienceJunior() {
        // given
        DevDto devDto = backendJuniorDevDto.clone();
        devDto.setExperienceYears(5);

        // when
        // then
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(devDto));
        assertEquals(ErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED, dMakerException.getErrorCode());
    }

    private void createDeveloper_failedWithHighNew() {
        // given
        DevDto devDto = backendJuniorDevDto.clone();
        devDto.setExperienceYears(1);
        devDto.setDeveloperLevel(DeveloperLevel.NEW);

        // when
        // then
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(devDto));
        assertEquals(ErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED, dMakerException.getErrorCode());
    }

    private void createDeveloper_failedWithLowSenior() {
        // given
        DevDto devDto = retiredBackendSeniorDevDto.clone();
        devDto.setExperienceYears(9);
        devDto.setDeveloperLevel(DeveloperLevel.SENIOR);

        // when
        // then
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(devDto));
        assertEquals(ErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED, dMakerException.getErrorCode());
    }

    @Test
    public void 개발자_검색() {
        // given
        given(developerRepository.findDeveloperByMemberId(backendJuniorDev.getMemberId()))
                .willReturn(Optional.of(backendJuniorDev));

        // when
        when(modelMapper.map(backendJuniorDev, DevDto.class)).thenReturn(backendJuniorDevDto);
        DevDto devDto = dMakerService.getDevDtoByMemberId(backendJuniorDev.getMemberId());

        // then
        assertEquals(DeveloperType.BACK_END, devDto.getDeveloperType());
        assertEquals(DeveloperLevel.JUNIOR, devDto.getDeveloperLevel());
        assertEquals(2, devDto.getExperienceYears());
    }

    @Test
    public void 은퇴_개발자들_검색() {
       // given
       given(developerRepository.findDevelopersByDeveloperStatusEquals(DeveloperStatus.RETIRED))
               .willReturn(List.of(retiredBackendSeniorDev));

       // when
        when(modelMapper.map(retiredBackendSeniorDev, DevDto.class)).thenReturn(retiredBackendSeniorDevDto);
        List<DevDto> dtoList = dMakerService.getDevelopersByStatus(DeveloperStatus.RETIRED);

        // then
        assertEquals(1, dtoList.size());
        DevDto retiredDev = dtoList.get(0);
        assertEquals(retiredDev.getDeveloperStatus(), DeveloperStatus.RETIRED);
        assertEquals(retiredDev.getDeveloperLevel(), DeveloperLevel.SENIOR);
        assertEquals(retiredDev.getDeveloperType(), DeveloperType.BACK_END);
        assertEquals(retiredDev.getMemberId(), retiredBackendSeniorDev.getMemberId());
    }
}
