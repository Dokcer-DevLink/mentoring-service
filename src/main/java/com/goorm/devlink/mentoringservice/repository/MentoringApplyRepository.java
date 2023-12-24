package com.goorm.devlink.mentoringservice.repository;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentoringApplyRepository extends JpaRepository<MentoringApply,Long>, MentoringApplyRepositoryCustom {

    Slice<MentoringApply> findMentoringAppliesByFromUuid(String userUuid, Pageable pageable);

    Slice<MentoringApply> findMentoringAppliesByTargetUuid(String userUuid, Pageable pageable);
    Optional<MentoringApply> findMentoringApplyByApplyUuid(String applyUuid);
}
