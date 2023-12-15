package com.goorm.devlink.mentoringservice.controller;


import com.goorm.devlink.mentoringservice.vo.MentoringApplyRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MentoringController {

    @PostMapping("/api/mentoring")
    public void applyMentoring(@RequestBody @Valid MentoringApplyRequest mentoringApplyRequest){



    }

}
