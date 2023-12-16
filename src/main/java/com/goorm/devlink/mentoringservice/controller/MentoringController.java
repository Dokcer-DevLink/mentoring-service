package com.goorm.devlink.mentoringservice.controller;


import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.vo.ApplyMessageResponse;
import com.goorm.devlink.mentoringservice.vo.ApplyPostResponse;
import com.goorm.devlink.mentoringservice.vo.ApplyProfileResponse;
import com.goorm.devlink.mentoringservice.vo.MentoringApplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MentoringController {

    private final MentoringService mentoringService;

    // 멘토링 신청
    @PostMapping("/api/mentoring/apply")
    public ResponseEntity<ApplyMessageResponse> applyMentoring(@RequestBody @Valid MentoringApplyRequest mentoringApplyRequest,
                                                               @RequestHeader("userUuid") String userUuid){
        String applyUuid =
                mentoringService.applyMentoring(MentoringApplyDto.getInstance(mentoringApplyRequest, userUuid));
        return new ResponseEntity<>(ApplyMessageResponse.getApplyInstance(applyUuid), HttpStatus.OK);

    }

    // 보낸 멘토링 제안 리스트 조회 ( Slice )
    @GetMapping("/api/mentoring/send")
    public ResponseEntity<Slice<ApplyPostResponse>> getApplySendMentoringList(@RequestHeader("userUuid") String userUuid){
        Slice<ApplyPostResponse> sendApplies = mentoringService.findApplySendMentoringList(userUuid);
        return new ResponseEntity<>(sendApplies,HttpStatus.OK);
    }

    // 받은 멘토링 제안 리스트 조회 ( Slice )
    @GetMapping("/api/mentoring/receive")
    public ResponseEntity<Slice<ApplyProfileResponse>> getApplyMentoringList(@RequestHeader("userUuid") String userUuid){
        Slice<ApplyProfileResponse> receiveApplies = mentoringService.findApplyReceiveMentoringList(userUuid);
        return new ResponseEntity<>(receiveApplies,HttpStatus.OK);
    }

//    // 멘토링 상세 조회
//    @GetMapping("/api/mentoring")
//    public ResponseEntity<MentoringDetailResponse> getMentoringDetail(@RequestParam String mentoringUuid){
//        MentoringDetailResponse mentoringDetailResponse = mentoringService.findMentoringDetail(mentoringUuid);
//        // 서비스간 통신으로 멘토 멘티 프로파일 정보 가져오는 로직 필요
//        return new ResponseEntity<>(mentoringDetailResponse,HttpStatus.OK);
//    }


}
