package com.goorm.devlink.mentoringservice.service;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.vo.MentoringDetailResponse;
import com.goorm.devlink.mentoringservice.vo.MentoringSimpleResponse;

import java.util.List;

public interface MentoringService {

    public String applyMentoring(MentoringApplyDto mentoringApplyDto);

    MentoringDetailResponse findMentoringDetail(String mentoringUuid);

    List<MentoringSimpleResponse> findApplyMentoringList(String userUuid);
}
