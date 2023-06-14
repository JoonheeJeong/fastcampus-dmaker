package com.fastcampus.yangseyeol.dmaker.dto;

import com.fastcampus.yangseyeol.dmaker.type.DeveloperStatus;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperLevel;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevDto implements Cloneable {
    @Size(min = 3, max = 24, message = "memberId's size is ranged in [3,24]")
    @NotNull
    private String memberId;
    @NotNull
    private DeveloperType developerType;
    @NotNull
    private DeveloperLevel developerLevel;
    @Min(0)
    @Max(120)
    @NotNull
    private Integer experienceYears;
    @Size(min = 3, max = 24, message = "name's size is ranged in [3,24")
    private String name;
    @Min(18)
    private Integer age;
    @NotNull
    @Builder.Default
    private DeveloperStatus developerStatus = DeveloperStatus.EMPLOYED;

    @Override
    public DevDto clone() {
        return DevDto.builder()
                .memberId(memberId)
                .developerType(developerType)
                .developerLevel(developerLevel)
                .experienceYears(experienceYears)
                .name(name)
                .age(age)
                .developerStatus(developerStatus)
                .build();
    }
}
