package com.goorm.devlink.mentoringservice.record;

import com.goorm.devlink.mentoringservice.config.properties.vo.NaverApiConfigVo;
import com.goorm.devlink.mentoringservice.record.impl.NaverStt;
import com.goorm.devlink.mentoringservice.record.impl.NaverSummary;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NaverClovaFactory {

    private final HttpConnectionUtil httpConnectionUtil;
    private final NaverApiConfigVo naverClovaApiConfigVo;

    public NaverClovaApi getInstance(NaverClova naverClova){

        switch(naverClova){
            case STT : return new NaverStt(httpConnectionUtil,naverClovaApiConfigVo);
            case SUMMARY: return new NaverSummary(httpConnectionUtil,naverClovaApiConfigVo);
            default: return null;
        }
    }
}
