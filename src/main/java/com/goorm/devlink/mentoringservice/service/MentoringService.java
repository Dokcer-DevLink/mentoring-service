package com.goorm.devlink.mentoringservice.service;

import com.goorm.devlink.mentoringservice.dto.MentoringApplyDto;
import com.goorm.devlink.mentoringservice.dto.MentoringStatusDto;
import com.goorm.devlink.mentoringservice.vo.*;
import com.goorm.devlink.mentoringservice.vo.response.ApplyPostResponse;
import com.goorm.devlink.mentoringservice.vo.response.ApplyProfileResponse;
import com.goorm.devlink.mentoringservice.vo.response.MentoringDetailResponse;
import com.goorm.devlink.mentoringservice.vo.response.MentoringBasicResponse;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MentoringService {

    public String applyMentoring(MentoringApplyDto mentoringApplyDto);

    MentoringDetailResponse findMentoringDetail(String mentoringUuid);

    List<ApplyPostResponse> findApplySendMentoringList(String userUuid);

    List<ApplyProfileResponse> findApplyReceiveMentoringList(String userUuid);

    String doMentoringAcceptProcess(String userUuid, String applyUuid);

    String doMentoringRejectProcess(String applyUuid);

    Slice<MentoringBasicResponse> findMyMentoringList(String userUuid, MentoringType mentoringType);
    void saveRecordContent(String mentoringUuid, String content);

    String updateMentoringStatus(MentoringStatusDto mentoringStatusDto);
}
