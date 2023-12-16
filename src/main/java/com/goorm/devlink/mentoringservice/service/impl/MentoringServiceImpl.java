package com.goorm.devlink.mentoringservice.service.impl;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.repository.MentoringApplyRepository;
import com.goorm.devlink.mentoringservice.repository.MentoringRepository;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import com.goorm.devlink.mentoringservice.vo.ApplyPostResponse;
import com.goorm.devlink.mentoringservice.vo.ApplyProfileResponse;
import com.goorm.devlink.mentoringservice.vo.MentoringDetailResponse;
import com.goorm.devlink.mentoringservice.vo.MentoringSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
@RequiredArgsConstructor
public class MentoringServiceImpl implements MentoringService {

    private final ModelMapperUtil modelMapperUtil;
    private final MentoringRepository mentoringRepository;
    private final MentoringApplyRepository mentoringApplyRepository;

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
    public Slice<ApplyPostResponse> findApplySendMentoringList(String userUuid) {
        PageRequest pageRequest = PageRequest.of(0,8,Sort.Direction.DESC,"createdDate");
        Slice<MentoringApply> mentoringApplies = mentoringApplyRepository.findMentoringAppliesByFromUuid(userUuid,pageRequest);
        return mentoringApplies.map( mentoringApply -> ApplyPostResponse.getInstance(mentoringApply.getPostUuid()));
    }

    @Override
    public Slice<ApplyProfileResponse> findApplyReceiveMentoringList(String userUuid) {
        PageRequest pageRequest = PageRequest.of(0,8,Sort.Direction.DESC,"createdDate");
        Slice<MentoringApply> mentoringApplies = mentoringApplyRepository.findMentoringAppliesByTargetUuid(userUuid,pageRequest);
        return mentoringApplies.map(mentoringApply -> ApplyProfileResponse.getInstance(mentoringApply.getFromUuid()));
    }


}
