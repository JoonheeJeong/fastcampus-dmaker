package com.fastcampus.yangseyeol.dmaker.repository;

import com.fastcampus.yangseyeol.dmaker.type.DeveloperStatus;
import com.fastcampus.yangseyeol.dmaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaAuditing
@EnableJpaRepositories
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findDeveloperByMemberId(String memberId);

    List<Developer> findDevelopersByDeveloperStatusEquals(DeveloperStatus developerStatus);
}
