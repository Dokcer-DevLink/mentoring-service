package com.goorm.devlink.mentoringservice.service.impl;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.entity.Mentoring;
import com.goorm.devlink.mentoringservice.repository.MentoringRepository;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import com.goorm.devlink.mentoringservice.vo.MentoringDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Override
    public MentoringDetailResponse findMentoringDetail(String mentoringUuid) {
        Mentoring mentoring = Optional.ofNullable(mentoringRepository.findMentoringByMentoringUuid(mentoringUuid))
                .orElseThrow(() -> { throw new NoSuchElementException(); });
        return modelMapperUtil.convertToMentoringDetailResponse(mentoring);
    }
}
