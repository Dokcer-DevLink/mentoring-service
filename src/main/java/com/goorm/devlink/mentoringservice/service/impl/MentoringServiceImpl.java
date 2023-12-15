package com.goorm.devlink.mentoringservice.service.impl;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.entity.Mentoring;
import com.goorm.devlink.mentoringservice.repository.MentoringRepository;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentoringServiceImpl implements MentoringService {

    private final ModelMapperUtil modelMapperUtil;
    private final MentoringRepository mentoringRepository;
    @Override
    public String applyMentoring(MentoringApplyDto mentoringApplyDto) {
        Mentoring mentoring = modelMapperUtil.convertToMentoring(mentoringApplyDto);
        mentoringRepository.save(mentoring);
        return mentoring.getMentorUuid();


    }
}
