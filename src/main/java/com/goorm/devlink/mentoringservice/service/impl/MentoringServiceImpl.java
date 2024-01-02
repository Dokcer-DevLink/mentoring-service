package com.goorm.devlink.mentoringservice.service.impl;

import com.goorm.devlink.mentoringservice.config.properties.vo.KafkaConfigVo;
import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.dto.MentoringStatusDto;
import com.goorm.devlink.mentoringservice.dto.NotifyDto;
import com.goorm.devlink.mentoringservice.dto.ScheduleDto;
import com.goorm.devlink.mentoringservice.entity.Mentoring;
import com.goorm.devlink.mentoringservice.entity.MentoringApply;
import com.goorm.devlink.mentoringservice.feign.PostServiceClient;
import com.goorm.devlink.mentoringservice.feign.ProfileServiceClient;
import com.goorm.devlink.mentoringservice.repository.MentoringApplyRepository;
import com.goorm.devlink.mentoringservice.repository.MentoringRepository;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.util.MessageUtil;
import com.goorm.devlink.mentoringservice.util.ModelMapperUtil;
import com.goorm.devlink.mentoringservice.vo.*;
import com.goorm.devlink.mentoringservice.vo.request.ScheduleCreateRequest;
import com.goorm.devlink.mentoringservice.vo.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class MentoringServiceImpl implements MentoringService {

    private final ModelMapperUtil modelMapperUtil;
    private final MentoringRepository mentoringRepository;
    private final MentoringApplyRepository mentoringApplyRepository;
    private final MessageUtil messageUtil;
    private final KafkaTemplate<String, NotifyDto> kafkaTemplate;
    private final KafkaConfigVo kafkaConfigVo;
    private final ProfileServiceClient profileServiceClient;
    private final PostServiceClient postServiceClient;

    @Override
    public MentoringDetailResponse findMentoringDetail(String mentoringUuid) {
        return modelMapperUtil.convertToMentoringDetailResponse(findMentoring(mentoringUuid));
    }

    /** 보낸 멘토링 신청 내역 **/
    @Override
    public List<ApplyPostResponse> findApplySendMentoringList(String userUuid) {
        //PageRequest pageRequest = PageRequest.of(0,8,Sort.Direction.DESC,"createdDate");
        List<MentoringApply> mentoringApplies = mentoringApplyRepository.findAllByFromUuidOrderByCreatedDateDesc(userUuid);
        if(mentoringApplies.size() != 0 ) return getApplyPostResponse(mentoringApplies);
        else return new ArrayList<>();
    }

    /** 받은 멘토링 신청 내역 **/
    @Override
    public List<ApplyProfileResponse> findApplyReceiveMentoringList(String userUuid) {
        //PageRequest pageRequest = PageRequest.of(0,8,Sort.Direction.DESC,"createdDate");
        List<MentoringApply> mentoringApplies = mentoringApplyRepository.findAllByTargetUuidOrderByCreatedDateDesc(userUuid);
        log.info("mentoringApplies {} ", mentoringApplies.size());
        if(mentoringApplies.size() != 0 ) return getApplyProfileResponse(mentoringApplies);
        else return new ArrayList<>();
    }

    /** 나의 멘토링 조회 **/
    @Override
    public List<MentoringBasicResponse> findMyMentoringList(String userUuid, MentoringType mentoringType) {
        //PageRequest pageRequest = PageRequest.of(0,8,Sort.Direction.DESC,"createdDate");
        return (mentoringType.equals(MentoringType.MENTOR)) ? findMyMenteeList(userUuid) : findMyMentorList(userUuid);

    }

    private List<MentoringBasicResponse> findMyMenteeList(String userUuid){
        List<Mentoring> myMentoring = mentoringRepository.findMyMentoringListByMentorUuid(userUuid);
        List<String> menteeList = myMentoring.stream().map(mentoring -> mentoring.getMenteeUuid()).collect(Collectors.toList());
        List<ProfileSimpleCard> profileMentorList = profileServiceClient.getMentoringAppliedProfiles(menteeList).getBody();
        return myMentoring.stream()
                .map(mentoring -> MentoringBasicResponse.getInstance(mentoring, profileMentorList,MentoringType.MENTEE))
                .collect(Collectors.toList());
    }

    private List<MentoringBasicResponse> findMyMentorList(String userUuid){
        List<Mentoring> myMentoring = mentoringRepository.findMyMentoringListByMenteeUuid(userUuid);
        List<String> mentorList = myMentoring.stream().map(mentoring -> mentoring.getMentorUuid()).collect(Collectors.toList());
        List<ProfileSimpleCard> profileMenteeList = profileServiceClient.getMentoringAppliedProfiles(mentorList).getBody();
        return myMentoring.stream()
                .map(mentoring->MentoringBasicResponse.getInstance(mentoring,profileMenteeList,MentoringType.MENTOR))
                .collect(Collectors.toList());
    }

    /** 멘토링 신청 메소드 **/
    @Override
    @Transactional
    public String applyMentoring(MentoringApplyDto mentoringApplyDto) {
        MentoringApply mentoringApply = modelMapperUtil.convertToMentoringApply(mentoringApplyDto);
        mentoringApplyRepository.save(mentoringApply);
        publishMessageToKafkaTopic(NotifyDto.getInstanceApply(mentoringApply));
        return mentoringApply.getApplyUuid();
    }

    /** 멘토링 수락 메소드 **/
    @Override
    @Transactional
    public String doMentoringAcceptProcess(String userUuid, String applyUuid) {
        String mentoringUuid = UUID.randomUUID().toString();

        MentoringApply mentoringApply = updateMentoringApplyStatus(applyUuid,MentoringApplyStatus.ACCEPT); // 1. MentoringApply 상태 변경
        createSchedule(ScheduleDto.getInstanceFrom(mentoringApply,mentoringUuid)); // fromUser 스케줄 생성
        createSchedule(ScheduleDto.getInstanceTarget(mentoringApply,mentoringUuid)); // targetUser 스케줄 생성
        createMentoring(mentoringApply, mentoringUuid); // 3. 멘토링 생성
        publishMessageToKafkaTopic(NotifyDto.getInstanceAccept(mentoringApply)); // 4. 알림 생성
        return mentoringUuid;
    }

    /** 멘토링 거절 메소드 **/
    @Override
    @Transactional
    public String doMentoringRejectProcess(String applyUuid) {
        MentoringApply mentoringApply = findMentoringApply(applyUuid); // 1. Mentoring Apply 조회
        mentoringApply.updateRejectStatus(); // 2. Mentoring Apply Reject로 상태 변경
        mentoringApplyRepository.save(mentoringApply);
        publishMessageToKafkaTopic(NotifyDto.getInstanceReject(mentoringApply)); // 3. 알림 생성
        return mentoringApply.getApplyUuid();
    }

    /** 멘토링 상태 변경 메소드 **/
    @Override
    @Transactional
    public String updateMentoringStatus(MentoringStatusDto mentoringStatusDto) {
        if(mentoringStatusDto.getMentoringStatus() == MentoringStatus.CANCEL) cancelSchedule(mentoringStatusDto);
        Mentoring mentoring = findMentoring(mentoringStatusDto.getMentoringUuid());
        updateMentoringStatus(mentoring,mentoringStatusDto.getMentoringStatus());
        return mentoringStatusDto.getMentoringUuid();
    }

    /** 음성 파일 내용 저장 메소드 **/
    @Override
    public void saveRecordContent(String mentoringUuid, String content) {
        Mentoring mentoring = findMentoring(mentoringUuid);
        mentoring.setRecordContent(content);
        mentoringRepository.save(mentoring);
    }

    /** Kafka Topic으로 메시지 전송 메소드 **/
    private void publishMessageToKafkaTopic(NotifyDto notifyDto){
        try {
            kafkaTemplate.send(kafkaConfigVo.getTopicName(),notifyDto).get();
        }
        catch (InterruptedException e) { throw new RuntimeException(messageUtil.getKafkaNotifyErrorMessage());}
        catch (ExecutionException e) { throw new RuntimeException(messageUtil.getKafkaNotifyErrorMessage()); }
    }


    /** 멘토링 생성 메소드 **/
    private String createMentoring(MentoringApply mentoringApply, String mentoringUuid){
        try {
            mentoringRepository.save(Mentoring.convertToMentoring(mentoringApply, mentoringUuid));
        } catch (Exception e){
            throw  new RuntimeException(messageUtil.getMentoringCreateErrorMessage());
        }
        return mentoringUuid;
    }

    /** 멘토링 상태변경 메소드 **/
    private void updateMentoringStatus(Mentoring mentoring,MentoringStatus status){
        try {
            mentoring.updateStatus(status);
            mentoringRepository.save(mentoring);
        } catch (Exception e){
            throw new RuntimeException(messageUtil.getMentoringStatusUpdateErrorMessage());
        }
    }

    /** 멘토링 신청 상태 변경 메소드 **/
    private MentoringApply updateMentoringApplyStatus(String applyUuid, MentoringApplyStatus status){
        MentoringApply mentoringApply = findMentoringApply(applyUuid);
        mentoringApply.updateApplyStatus(status);
        mentoringApplyRepository.save(mentoringApply);
        return mentoringApply;
    }

    /** 멘토링 조회 메소드 **/
    private Mentoring findMentoring(String mentoringUuid){
        return mentoringRepository.findMentoringByMentoringUuid(mentoringUuid).orElseThrow(() ->{
                    throw new NoSuchElementException(messageUtil.getMentoringUuidNoSuchMessage(mentoringUuid));});
    }

    /** 멘토링 신청 조회 메소드 **/
    private MentoringApply findMentoringApply(String applyUuid){
        return mentoringApplyRepository.findMentoringApplyByApplyUuid(applyUuid)
                .orElseThrow( () -> {
                    throw new NoSuchElementException(messageUtil.getApplyUuidNoSuchMessage(applyUuid)); });
    }

    /** Profile 서비스 스케줄 생성 메소드 **/
    private void createSchedule(ScheduleDto scheduleDto){
        ResponseEntity<Void> response = profileServiceClient.createCalendarSchedule(
                scheduleDto.getUserUuid(),
                ScheduleCreateRequest.getInstance(scheduleDto
                ));

        if(response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(messageUtil.getProfileConnectionErrorMessage()); }
    }

    /** Profile 서비스 스케줄 취소 메소드 **/
    private void cancelSchedule(MentoringStatusDto mentoringStatusDto){
        ResponseEntity<Void> response = profileServiceClient.cancelCalendarSchedule(mentoringStatusDto.getUserUuid(),
                        mentoringStatusDto.getMentoringUuid());
        log.info("cancelSchedule {} ", response.toString());
        if(response.getStatusCode() != HttpStatus.ACCEPTED) {
            throw new RuntimeException(messageUtil.getProfileConnectionErrorMessage());}
    }

    /** POST 서비스 간 통신으로 POST 데이터 가져오기 ( 보낸 멘토링 신청 내역 )**/
    private List<ApplyPostResponse> getApplyPostResponse (List<MentoringApply> mentoringApplies){
        List<String> postUuids = mentoringApplies.stream().map(MentoringApply::getPostUuid).collect(Collectors.toList());
        List<MentoringPostResponse> mentoringPostResponses = postServiceClient.getPostListForMentoring(postUuids).getBody();

        return mentoringApplies.stream()
                .map(mentoringApply -> ApplyPostResponse.getInstance(mentoringApply,mentoringPostResponses))
                .collect(Collectors.toList());
    }

    /** PROFILE 서비스 간 통신으로 PROFILE 데이터 가져오기 ( 받은 멘토링 신청 내역 )**/

    private List<ApplyProfileResponse> getApplyProfileResponse(List<MentoringApply> mentoringApplies) {
        List<String> userUuids = mentoringApplies.stream().map(mentoringApply -> mentoringApply.getFromUuid()).collect(Collectors.toList());
        List<ProfileSimpleCard> profileResponses = profileServiceClient.getMentoringAppliedProfiles(userUuids).getBody();

        return mentoringApplies.stream()
                .map(mentoringApply -> ApplyProfileResponse.getInstance(mentoringApply,profileResponses))
                .collect(Collectors.toList());
    }

}
