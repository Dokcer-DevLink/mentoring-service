package com.goorm.devlink.mentoringservice.controller;


import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.vo.MentoringApplyRequest;
import com.goorm.devlink.mentoringservice.vo.MentoringDetailResponse;
import com.goorm.devlink.mentoringservice.vo.MentoringMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MentoringController {

    private final MentoringService mentoringService;

    // 멘토링 신청
    @PostMapping("/api/mentoring")
    public ResponseEntity<MentoringMessageResponse> applyMentoring(@RequestBody @Valid MentoringApplyRequest mentoringApplyRequest,
                                                                   @RequestHeader("userUuid") String userUuid){
        String mentoringUuid =
                mentoringService.applyMentoring(MentoringApplyDto.getInstance(mentoringApplyRequest, userUuid));
        return new ResponseEntity<>(MentoringMessageResponse.getApplyInstance(mentoringUuid), HttpStatus.OK);

    }

    // 멘토링 상세 조회
    @GetMapping("/api/mentoring")
    public ResponseEntity<MentoringDetailResponse> getMentoringDetail(@RequestParam String mentoringUuid){
        MentoringDetailResponse mentoringDetailResponse = mentoringService.findMentoringDetail(mentoringUuid);
        // 서비스간 통신으로 멘토 멘티 프로파일 정보 가져오는 로직 필요
        return new ResponseEntity<>(mentoringDetailResponse,HttpStatus.OK);
    }

    // 제안한 멘토링 리스트 조회


}
