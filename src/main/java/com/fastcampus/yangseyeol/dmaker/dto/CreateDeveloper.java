package com.fastcampus.yangseyeol.dmaker.dto;

import com.fastcampus.yangseyeol.dmaker.entity.Developer;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperLevel;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CreateDeveloper {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @Size(min = 3, max = 24, message = "memberId's size is ranged in [3,24]")
        @NonNull
        private String memberId;
        @NonNull
        private DeveloperType developerType;
        @NonNull
        private DeveloperLevel developerLevel;
        @Min(0)
        @Max(120)
        @NonNull
        private Integer experienceYears;
        @Size(min = 3, max = 24, message = "name's size is ranged in [3,24")
        private String name;
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String memberId;
        private DeveloperType developerType;
        private DeveloperLevel developerLevel;
        private Integer experienceYears;

        public static Response fromEntity(Developer developer) {
            return Response.builder()
                    .memberId(developer.getMemberId())
                    .developerType(developer.getDeveloperType())
                    .developerLevel(developer.getDeveloperLevel())
                    .experienceYears(developer.getExperienceYears())
                    .build();
        }
    }

}
