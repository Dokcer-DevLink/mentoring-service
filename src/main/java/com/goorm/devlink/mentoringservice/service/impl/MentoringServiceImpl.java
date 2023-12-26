package com.goorm.devlink.mentoringservice.service.impl;

import com.goorm.devlink.mentoringservice.config.properties.vo.KafkaConfigVo;
import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.dto.NotifyDto;
import com.goorm.devlink.mentoringservice.entity.Mentoring;
import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.repository.MentoringApplyRepository;
import com.goorm.devlink.mentoringservice.repository.MentoringRepository;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.util.MessageUtil;
import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import com.goorm.devlink.mentoringservice.vo.*;
import com.goorm.devlink.mentoringservice.vo.response.ApplyPostResponse;
import com.goorm.devlink.mentoringservice.vo.response.ApplyProfileResponse;
import com.goorm.devlink.mentoringservice.vo.response.MentoringDetailResponse;
import com.goorm.devlink.mentoringservice.vo.response.MentoringSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
public class MentoringServiceImpl implements MentoringService {

    private final ModelMapperUtil modelMapperUtil;
    private final MentoringRepository mentoringRepository;
    private final MentoringApplyRepository mentoringApplyRepository;
    private final MessageUtil messageUtil;
    private final KafkaTemplate<String, NotifyDto> kafkaTemplate;
    private final KafkaConfigVo kafkaConfigVo;

    @Override
    public MentoringDetailResponse findMentoringDetail(String mentoringUuid) {
        return modelMapperUtil.convertToMentoringDetailResponse(findMentoring(mentoringUuid));
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
    public Slice<MentoringSimpleResponse> findMyMentoringList(String userUuid, MentoringType mentoringType) {
        PageRequest pageRequest = PageRequest.of(0,8,Sort.Direction.DESC,"createdDate");
        Slice<Mentoring> mentoringSlice = (mentoringType.equals(MentoringType.MENTOR)) ?
                mentoringRepository.findMyMentoringListByMentorUuid(userUuid):
                mentoringRepository.findMyMentoringListByMenteeUuid(userUuid);
        return mentoringSlice.map(mentoring -> modelMapperUtil.convertToMentoringSimpleResponse(mentoring));
    }

    // 멘토링 신청
    @Override
    @Transactional
    public String applyMentoring(MentoringApplyDto mentoringApplyDto) {
        MentoringApply mentoringApply = modelMapperUtil.convertToMentoringApply(mentoringApplyDto);
        mentoringApplyRepository.save(mentoringApply);
        publishMessageToKafkaTopic(NotifyDto.getInstanceApply(mentoringApply));
        return mentoringApply.getApplyUuid();
    }

    // 멘토링 수락
    @Override
    public String doMentoringAcceptProcess(String applyUuid) {
        MentoringApply mentoringApply = findMentoringApply(applyUuid); // 1. Mentoring Apply 조회
        mentoringApply.updateAcceptStatus(); // 2. MentoringApply ACCEPT로 상태 변경
        return createMentoring(mentoringApply);
    }

    // 멘토링 거절
    @Override
    @Transactional
    public String doMentoringRejectProcess(String applyUuid) {
        MentoringApply mentoringApply = findMentoringApply(applyUuid); // 1. Mentoring Apply 조회
        mentoringApply.updateRejectStatus(); // 2. Mentoring Apply Reject로 상태 변경
        mentoringApplyRepository.save(mentoringApply);
        publishMessageToKafkaTopic(NotifyDto.getInstanceReject(mentoringApply));
        return mentoringApply.getApplyUuid();
    }

    @Override
    @Transactional
    public void saveRecordContent(String mentoringUuid, String content) {
        Mentoring mentoring = findMentoring(mentoringUuid);
        mentoring.setRecordContent(content);
        mentoringRepository.save(mentoring);
    }

    private void publishMessageToKafkaTopic(NotifyDto notifyDto){
        try {
            kafkaTemplate.send(kafkaConfigVo.getTopicName(),notifyDto).get();
        }
        catch (InterruptedException e) { throw new RuntimeException(e);}
        catch (ExecutionException e) { throw new RuntimeException(e); }
    }

    @Transactional
    private String createMentoring(MentoringApply mentoringApply){
        Mentoring mentoring = Mentoring.convertToMentoring(mentoringApply); // 3. Mentoring 생성
        mentoringRepository.save(mentoring);
        publishMessageToKafkaTopic(NotifyDto.getInstanceAccept(mentoringApply));

        return mentoring.getMentoringUuid();
    }

    private Mentoring findMentoring(String mentoringUuid){
        return mentoringRepository.findMentoringByMentoringUuid(mentoringUuid).orElseThrow(() ->{
                    throw new NoSuchElementException(messageUtil.getMentoringUuidNoSuchMessage(mentoringUuid));});
    }

    private MentoringApply findMentoringApply(String applyUuid){
        return mentoringApplyRepository.findMentoringApplyByApplyUuid(applyUuid)
                .orElseThrow( () -> {
                    throw new NoSuchElementException(messageUtil.getApplyUuidNoSuchMessage(applyUuid)); });
    }
}
