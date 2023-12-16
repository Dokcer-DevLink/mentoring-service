package com.goorm.devlink.mentoringservice.repository;

import com.goorm.devlink.mentoringservice.entity.Mentoring;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringRepository extends JpaRepository<Mentoring,Long>, MentoringRepositoryCustom {


    Mentoring findMentoringByMentoringUuid(String mentoringUuid);

    Slice<Mentoring> findMyMentoringListByMentorUuid(String mentorUuid);
    Slice<Mentoring> findMyMentoringListByMenteeUuid(String menteeUuid);

}
