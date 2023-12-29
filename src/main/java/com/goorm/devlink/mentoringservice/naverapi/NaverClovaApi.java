package com.goorm.devlink.mentoringservice.naverapi;

import com.goorm.devlink.mentoringservice.vo.S3RecordVo;

public interface NaverClovaApi {

    public String sendDataToNaverClova(S3RecordVo s3RecordVo);
}
