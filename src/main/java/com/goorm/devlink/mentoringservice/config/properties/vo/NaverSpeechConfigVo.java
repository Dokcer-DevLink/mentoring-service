package com.goorm.devlink.mentoringservice.config.properties.vo;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

@Slf4j
@Getter
public class NaverSpeechConfigVo {

    private final String secret;
    private final String contentType;
    private final String invokeUrl;
    private final Header[] headers;

    public NaverSpeechConfigVo(String secret, String contentType, String invokeUrl) {
        this.secret = secret;
        this.contentType = contentType;
        this.invokeUrl = invokeUrl;

        headers = new Header[] {
                new BasicHeader("Accept", contentType),
                new BasicHeader("X-CLOVASPEECH-API-KEY", secret),
        };
    }
}
