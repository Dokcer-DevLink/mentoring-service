package com.goorm.devlink.mentoringservice.naverapi.impl;

import com.google.gson.JsonElement;
import com.goorm.devlink.mentoringservice.config.properties.vo.NaverSpeechConfigVo;
import com.goorm.devlink.mentoringservice.naverapi.NaverClovaApi;
import com.goorm.devlink.mentoringservice.naverapi.vo.JsonSpeechVo;
import com.goorm.devlink.mentoringservice.naverapi.vo.NestRequestEntity;
import com.goorm.devlink.mentoringservice.naverapi.vo.SpeechHttpHeader;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import java.io.File;
import java.util.UUID;


@RequiredArgsConstructor
public class NaverClovaSpeech implements NaverClovaApi {

    private final HttpConnectionUtil httpConnectionUtil;
    private final NaverSpeechConfigVo naverSpeechConfigVo;
    private final NestRequestEntity nestRequestEntity;

    @Override
    public String sendDataToNaverClova(String encoding) {
        String response = sendVoiceFileToClovaSpeech(encoding);
        JsonSpeechVo jsonSpeechVo = JsonSpeechVo.getInstance(response);
        if(!jsonSpeechVo.isSucceeded()) { throw new RuntimeException(); }
        return parsingResponse(jsonSpeechVo);

    }

    private String sendVoiceFileToClovaSpeech(String encoding){
        String fileUrl = generateFileUrl(naverSpeechConfigVo.getFileUrl());
        File voiceFile = httpConnectionUtil.convertEncodingToFile(encoding,fileUrl);
        HttpEntity httpEntity = httpConnectionUtil.createHttpEntity(voiceFile, nestRequestEntity);
        HttpPost httpPost = httpConnectionUtil.createHttpPost(naverSpeechConfigVo.getInvokeUrl(),
                naverSpeechConfigVo.getHeaders(), httpEntity);
        return httpConnectionUtil.executeHttpPost(httpPost);
    }

    private String generateFileUrl(String fileDirectory){
        return fileDirectory + UUID.randomUUID().toString();
    }

    private String parsingResponse(JsonSpeechVo jsonSpeechVo){
        String pivotSpeakerName = "";
        StringBuilder pivotSpeakerText = new StringBuilder();

        for (JsonElement jsonElement : jsonSpeechVo.getSpeechArray()) {
            String speakerName = jsonSpeechVo.getSpeakerName(jsonElement);
            String speakerText = jsonSpeechVo.getSpeakerText(jsonElement);

            if(pivotSpeakerName.isEmpty()){
                pivotSpeakerName = speakerName;
                pivotSpeakerText.append(speakerText);
                continue;
            }

            if(pivotSpeakerName.equals(speakerName)){
                pivotSpeakerText.append(" ").append(speakerText);
            }else{
                jsonSpeechVo.appendToParsedResponse(pivotSpeakerName,pivotSpeakerText.toString());
                pivotSpeakerName = speakerName;
                pivotSpeakerText = new StringBuilder(speakerText);
            }
        }

        jsonSpeechVo.appendToParsedResponse(pivotSpeakerName,pivotSpeakerText.toString());
        return jsonSpeechVo.getParsedResponse();
    }



}
