package com.goorm.devlink.mentoringservice.repository;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringRepository extends JpaRepository<MentoringApply,Long>, MentoringRepositoryCustom {


//    MentoringApply findMentoringByMentoringUuid(String mentoringUuid);
}
