package com.goorm.devlink.mentoringservice.repository;

import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.vo.MentoringApplyStatus;
import org.springframework.data.domain.Slice;

public interface MentoringRepositoryCustom {

    Slice<MentoringApply> findMentoringListByUserUuidAndStatus(String userUuid, MentoringApplyStatus mentoringApplyStatus);
}
