package com.fastcampus.yangseyeol.dmaker.entity;

import com.fastcampus.yangseyeol.dmaker.type.DeveloperStatus;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperLevel;
import com.fastcampus.yangseyeol.dmaker.type.DeveloperType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private String memberId;
    @Enumerated(EnumType.STRING)
    private DeveloperType developerType;
    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;
    private Integer experienceYears;
    private String name;
    private Integer age;
    @Builder.Default
    private DeveloperStatus developerStatus = DeveloperStatus.EMPLOYED;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
