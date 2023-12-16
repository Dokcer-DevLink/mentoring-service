package com.goorm.devlink.mentoringservice.service.impl;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.entity.Mentoring;
import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.repository.MentoringApplyRepository;
import com.goorm.devlink.mentoringservice.repository.MentoringRepository;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import com.goorm.devlink.mentoringservice.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MentoringServiceImpl implements MentoringService {

    private final ModelMapperUtil modelMapperUtil;
    private final MentoringRepository mentoringRepository;
    private final MentoringApplyRepository mentoringApplyRepository;

    @Override
    public String applyMentoring(MentoringApplyDto mentoringApplyDto) {
        MentoringApply mentoringApply = modelMapperUtil.convertToMentoringApply(mentoringApplyDto);
        mentoringApplyRepository.save(mentoringApply);
        return mentoringApply.getApplyUuid();
    }

    @Override
    public MentoringDetailResponse findMentoringDetail(String mentoringUuid) {
        Mentoring mentoring = Optional.ofNullable(mentoringRepository.findMentoringByMentoringUuid(mentoringUuid))
                .orElseThrow(() -> { throw new NoSuchElementException(); });
        return modelMapperUtil.convertToMentoringDetailResponse(mentoring);
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

    @Override
    public String doMentoringAcceptProcess(String applyUuid) {
        MentoringApply mentoringApply = Optional.ofNullable(mentoringApplyRepository.findMentoringApplyByApplyUuid(applyUuid))
                .orElseThrow( () -> { throw new NoSuchElementException(); }); // 1. Mentoring Apply 조회
        mentoringApply.updateAcceptStatus(); // 2. Mentoring Apply Accept로 상태 변경
        Mentoring mentoring = Mentoring.convertToMentoring(mentoringApply); // 3. Mentoring 생성
        mentoringRepository.save(mentoring);
        // 4. 멘토링 생성 알림 ( 추후 로직 구현 )

        return mentoring.getMentoringUuid();
    }

    @Override
    public String doMentoringRejectProcess(String applyUuid) {
        MentoringApply mentoringApply = Optional.ofNullable(mentoringApplyRepository.findMentoringApplyByApplyUuid(applyUuid))
                .orElseThrow( () -> { throw new NoSuchElementException(); }); // 1. Mentoring Apply 조회
        mentoringApply.updateRejectStatus(); // 2. Mentoring Apply Reject로 상태 변경
        mentoringApplyRepository.save(mentoringApply);
        // 3. 멘토링 거절 알림 ( 추후 로직 구현 )
        return mentoringApply.getApplyUuid();
    }


}
