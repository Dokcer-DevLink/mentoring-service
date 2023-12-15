package com.goorm.devlink.mentoringservice.service;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.vo.MentoringDetailResponse;

public interface MentoringService {

    public String applyMentoring(MentoringApplyDto mentoringApplyDto);

    MentoringDetailResponse findMentoringDetail(String mentoringUuid);
}
