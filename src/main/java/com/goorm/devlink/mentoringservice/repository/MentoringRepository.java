package com.goorm.devlink.mentoringservice.repository;

import com.goorm.devlink.mentoringservice.entity.Mentoring;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentoringRepository extends JpaRepository<Mentoring,Long>, MentoringRepositoryCustom {


    Optional<Mentoring> findMentoringByMentoringUuid(String mentoringUuid);

    List<Mentoring> findMyMentoringListByMentorUuid(String mentorUuid);
    List<Mentoring> findMyMentoringListByMenteeUuid(String menteeUuid);

}
