package com.goorm.devlink.mentoringservice.naverapi.vo;

import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;


@Getter
public class SpeechHttpHeader {

//    private static final String SECRETKEY = "#{'${data.naverapi.speech.secret}'}";
//    private static final String CONTENTTYPE = "#{'${data.naverapi.speech.secret}'}";
    private final Header[] headers;

    public SpeechHttpHeader(String secretKey, String contentType){
        headers = new Header[] {
                new BasicHeader("Accept", contentType),
                new BasicHeader("X-CLOVASPEECH-API-KEY", secretKey),
        };
    }
}
