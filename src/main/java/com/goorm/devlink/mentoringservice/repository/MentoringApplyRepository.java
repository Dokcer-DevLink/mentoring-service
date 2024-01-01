package com.goorm.devlink.mentoringservice.repository;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentoringApplyRepository extends JpaRepository<MentoringApply,Long>, MentoringApplyRepositoryCustom {

    List<MentoringApply> findAllByFromUuidOrderByCreatedDateDesc(String userUuid);

    List<MentoringApply> findAllByTargetUuidOrderByCreatedDateDesc(String userUuid);
    Optional<MentoringApply> findMentoringApplyByApplyUuid(String applyUuid);
}
