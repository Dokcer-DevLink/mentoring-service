package com.goorm.devlink.mentoringservice.controller;


import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.service.MentoringService;
import com.goorm.devlink.mentoringservice.vo.MentoringApplyRequest;
import com.goorm.devlink.mentoringservice.vo.MentoringSimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MentoringController {

    private MentoringService mentoringService;

    // 멘토링 신청
    @PostMapping("/api/mentoring")
    public ResponseEntity<MentoringSimpleResponse> applyMentoring(@RequestBody @Valid MentoringApplyRequest mentoringApplyRequest,
                                                                  @RequestHeader("userUuid") String userUuid){
        String mentoringUuid =
                mentoringService.applyMentoring(MentoringApplyDto.getInstance(mentoringApplyRequest, userUuid));
        return new ResponseEntity<>(MentoringSimpleResponse.getApplyInstance(mentoringUuid), HttpStatus.OK);

    }

}
