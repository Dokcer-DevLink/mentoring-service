package com.goorm.devlink.mentoringservice.service;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.vo.*;
import com.goorm.devlink.mentoringservice.vo.response.ApplyPostResponse;
import com.goorm.devlink.mentoringservice.vo.response.ApplyProfileResponse;
import com.goorm.devlink.mentoringservice.vo.response.MentoringDetailResponse;
import com.goorm.devlink.mentoringservice.vo.response.MentoringSimpleResponse;
import org.springframework.data.domain.Slice;

public interface MentoringService {

    public String applyMentoring(MentoringApplyDto mentoringApplyDto);

    MentoringDetailResponse findMentoringDetail(String mentoringUuid);

    Slice<ApplyPostResponse> findApplySendMentoringList(String userUuid);

    Slice<ApplyProfileResponse> findApplyReceiveMentoringList(String userUuid);

    String doMentoringAcceptProcess(String applyUuid);

    String doMentoringRejectProcess(String applyUuid);

    Slice<MentoringSimpleResponse> findMyMentoringList(String userUuid, MentoringType mentoringType);
    void saveRecordContent(String mentoringUuid, String content);
}
