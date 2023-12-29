package com.goorm.devlink.mentoringservice.naverapi.impl;

import com.google.gson.JsonElement;
import com.goorm.devlink.mentoringservice.config.properties.vo.NaverSpeechConfigVo;
import com.goorm.devlink.mentoringservice.naverapi.NaverClovaApi;
import com.goorm.devlink.mentoringservice.naverapi.vo.JsonSpeechVo;
import com.goorm.devlink.mentoringservice.naverapi.vo.NestRequestEntity;
import com.goorm.devlink.mentoringservice.util.AwsUtil;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import com.goorm.devlink.mentoringservice.vo.S3RecordVo;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import java.util.UUID;


@RequiredArgsConstructor
public class NaverClovaSpeech implements NaverClovaApi {

    private final HttpConnectionUtil httpConnectionUtil;
    private final NaverSpeechConfigVo naverSpeechConfigVo;
    private final NestRequestEntity nestRequestEntity;
    private final AwsUtil awsUtil;

    @Override
    public String sendDataToNaverClova(S3RecordVo s3RecordVo) {
        String response = sendVoiceFileToClovaSpeech(s3RecordVo);
        JsonSpeechVo jsonSpeechVo = JsonSpeechVo.getInstance(response);
        if(!jsonSpeechVo.isSucceeded()) { throw new RuntimeException(); } // 에러처리!!
        return parsingResponse(jsonSpeechVo);

    }

    private String pushFileInS3Bucket(S3RecordVo s3RecordVo){
        return awsUtil.saveRecordInS3Bucket(s3RecordVo);
    }

    private String sendVoiceFileToClovaSpeech(S3RecordVo s3RecordVo){
        String fileUrl = pushFileInS3Bucket(s3RecordVo);
        HttpEntity httpEntity = httpConnectionUtil.createHttpEntity(fileUrl, nestRequestEntity);
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
