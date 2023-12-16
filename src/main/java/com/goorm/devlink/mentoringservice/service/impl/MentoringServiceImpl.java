package com.goorm.devlink.mentoringservice.service.impl;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.repository.MentoringRepository;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import com.goorm.devlink.mentoringservice.vo.MentoringDetailResponse;
import com.goorm.devlink.mentoringservice.vo.MentoringSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MentoringServiceImpl implements MentoringService {

    private final ModelMapperUtil modelMapperUtil;
    private final MentoringRepository mentoringRepository;

    @Override
    public String applyMentoring(MentoringApplyDto mentoringApplyDto) {
        MentoringApply mentoringApply = modelMapperUtil.convertToMentoringApply(mentoringApplyDto);
        mentoringRepository.save(mentoringApply);
        return mentoringApply.getApplyUuid();
    }

    @Override
    public MentoringDetailResponse findMentoringDetail(String mentoringUuid) {
//        MentoringApply mentoringApply = Optional.ofNullable(mentoringRepository.findMentoringByMentoringUuid(mentoringUuid))
//                .orElseThrow(() -> { throw new NoSuchElementException(); });
//        return modelMapperUtil.convertToMentoringDetailResponse(mentoringApply);
        return null;
    }

    @Override
    public List<MentoringSimpleResponse> findApplyMentoringList(String userUuid) {
        return null;
    }
}
