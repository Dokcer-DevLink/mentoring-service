package com.goorm.devlink.mentoringservice.controller;


import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.util.MessageUtil;
import com.goorm.devlink.mentoringservice.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class MentoringController {

    private final MentoringService mentoringService;
    private final MessageUtil messageUtil;

    // 멘토링 신청
    @PostMapping("/api/mentoring/apply")
    public ResponseEntity<ApplyMessageResponse> applyMentoring(@RequestBody @Valid MentoringApplyRequest mentoringApplyRequest,
                                                               @RequestHeader("userUuid") String userUuid){
        if( userUuid.isEmpty() ) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage()); }
        String applyUuid =
                mentoringService.applyMentoring(MentoringApplyDto.getInstance(mentoringApplyRequest, userUuid));
        return new ResponseEntity<>(ApplyMessageResponse.getInstance(applyUuid,messageUtil.getApplyCompleteMessage()),
                HttpStatus.OK);

    }

    // 보낸 멘토링 제안 리스트 조회 ( Slice )
    @GetMapping("/api/mentoring/send")
    public ResponseEntity<Slice<ApplyPostResponse>> getApplySendMentoringList(@RequestHeader("userUuid") String userUuid){
        if( userUuid.isEmpty() ) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage()); }
        Slice<ApplyPostResponse> sendApplies = mentoringService.findApplySendMentoringList(userUuid);
        return new ResponseEntity<>(sendApplies,HttpStatus.OK);
    }

    // 받은 멘토링 제안 리스트 조회 ( Slice )
    @GetMapping("/api/mentoring/receive")
    public ResponseEntity<Slice<ApplyProfileResponse>> getApplyMentoringList(@RequestHeader("userUuid") String userUuid){
        if( userUuid.isEmpty() ) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage()); }
        Slice<ApplyProfileResponse> receiveApplies = mentoringService.findApplyReceiveMentoringList(userUuid);
        return new ResponseEntity<>(receiveApplies,HttpStatus.OK);
    }

    // 멘토린 신청 수락
    @GetMapping("/api/mentoring/accept")
    public ResponseEntity<MentoringMessageResponse> doMentoringAcceptProcess(@RequestParam String applyUuid){
        if( applyUuid.isEmpty() ) { throw new NoSuchElementException(messageUtil.getApplyUuidEmptyMessage()); }
        String mentoringUuid = mentoringService.doMentoringAcceptProcess(applyUuid);
        return new ResponseEntity<>(MentoringMessageResponse.getInstance(mentoringUuid,
                messageUtil.getMentoringCreateMessage()), HttpStatus.OK);
    }

    // 멘토링 신청 거절
    @GetMapping("/api/mentoring/reject")
    public ResponseEntity<ApplyMessageResponse> doMentoringRejectProcess(@RequestParam String applyUuid){
        if( applyUuid.isEmpty() ) { throw new NoSuchElementException(messageUtil.getApplyUuidEmptyMessage()); }
        String rejectUuid = mentoringService.doMentoringRejectProcess(applyUuid);
        return new ResponseEntity<>(ApplyMessageResponse.getInstance(rejectUuid,
                messageUtil.getMentoringRejectMessage()), HttpStatus.OK);
    }

    // 멘토링 상세 조회
    @GetMapping("/api/mentoring")
    public ResponseEntity<MentoringDetailResponse> getMentoringDetail(@RequestParam String mentoringUuid){
        if( mentoringUuid.isEmpty() ) { throw new NoSuchElementException(messageUtil.getMentoringUuidEmptyMessage()); }
        MentoringDetailResponse mentoringDetailResponse = mentoringService.findMentoringDetail(mentoringUuid);
        return new ResponseEntity<>(mentoringDetailResponse,HttpStatus.OK);
    }

    // 나의 멘토링 리스트 조회
    @GetMapping("/api/mentoring/my")
    public ResponseEntity<Slice<MentoringSimpleResponse>> getMyMentoringList(@RequestHeader("userUuid") String userUuid,
                                                                             @RequestParam MentoringType mentoringType){
        if( userUuid.isEmpty() ) { throw new NoSuchElementException(messageUtil.getUserUuidEmptyMessage()); }
        Slice<MentoringSimpleResponse> myMentoringList = mentoringService.findMyMentoringList(userUuid,mentoringType);
        return new ResponseEntity<>(myMentoringList,HttpStatus.OK);
    }



}
