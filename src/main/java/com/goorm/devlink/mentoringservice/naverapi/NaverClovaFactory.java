package com.goorm.devlink.mentoringservice.naverapi;

import com.goorm.devlink.mentoringservice.config.properties.vo.NaverSpeechConfigVo;
import com.goorm.devlink.mentoringservice.naverapi.impl.NaverClovaSpeech;
import com.goorm.devlink.mentoringservice.naverapi.impl.NaverClovaSummary;
import com.goorm.devlink.mentoringservice.naverapi.vo.NestRequestEntity;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NaverClovaFactory {

    private final HttpConnectionUtil httpConnectionUtil;
    private final NaverSpeechConfigVo naverClovaApiConfigVo;
    private final NestRequestEntity nestRequestEntity;

    public NaverClovaApi getInstance(NaverClovaType naverClovaType){

        switch(naverClovaType){
            case SPEECH : return new NaverClovaSpeech(httpConnectionUtil, naverClovaApiConfigVo, nestRequestEntity);
            case SUMMARY: return new NaverClovaSummary(httpConnectionUtil,naverClovaApiConfigVo);
            default: return null;
        }
    }
}
