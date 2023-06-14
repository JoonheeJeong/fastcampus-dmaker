package com.fastcampus.yangseyeol.dmaker.repository;

import com.fastcampus.yangseyeol.dmaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories
public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {
}
