package com.fastcampus.yangseyeol.dmaker.service;

import com.fastcampus.yangseyeol.dmaker.dto.DevDto;
import com.fastcampus.yangseyeol.dmaker.entity.Developer;
import com.fastcampus.yangseyeol.dmaker.entity.RetiredDeveloper;
import com.fastcampus.yangseyeol.dmaker.exception.DMakerException;
import com.fastcampus.yangseyeol.dmaker.exception.ErrorCode;
import com.fastcampus.yangseyeol.dmaker.repository.DeveloperRepository;
import com.fastcampus.yangseyeol.dmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.fastcampus.yangseyeol.dmaker.exception.ErrorCode.NO_DEVELOPER;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DevDto createDeveloper(DevDto dto) {
        validateCreateDeveloper(dto);

        return modelMapper.map(
                developerRepository.save(
                        modelMapper.map(dto, Developer.class)),
                DevDto.class);
    }

    public List<DevDto> getDevelopersByStatus(DeveloperStatus status) {
        return developerRepository.findDevelopersByDeveloperStatusEquals(status).stream()
                .map(developer -> modelMapper.map(developer, DevDto.class))
                .collect(Collectors.toList());
    }

    public DevDto getDevDtoByMemberId(String memberId) {
        return modelMapper.map(getDeveloperByMemberId(memberId), DevDto.class);
    }

    private Developer getDeveloperByMemberId(String memberId) {
        return developerRepository.findDeveloperByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    public void validateCreateDeveloper(DevDto dto) {
        validateDeveloper(dto);

        developerRepository.findDeveloperByMemberId(dto.getMemberId())
                .ifPresent(developer -> {
                    throw new DMakerException(ErrorCode.DUPLICATE_MEMBER_ID);
                });
    }

    private void validateDeveloper(@NonNull DevDto dto) {
        dto.getDeveloperLevel().validateExpYear(dto.getExperienceYears());
    }

    public void editDeveloper(DevDto dto) {
        validateDeveloper(dto);
        Developer developer = getDeveloperByMemberId(dto.getMemberId());
        modelMapper.map(dto, developer);
        developerRepository.save(developer);
    }

    @Transactional
    public void deleteDeveloper(String memberId) {
        Developer developer = developerRepository.findDeveloperByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setDeveloperStatus(DeveloperStatus.RETIRED);
        developerRepository.save(developer);

        RetiredDeveloper retiredDeveloper = modelMapper.map(developer, RetiredDeveloper.class);
        retiredDeveloperRepository.save(retiredDeveloper);
    }

}
