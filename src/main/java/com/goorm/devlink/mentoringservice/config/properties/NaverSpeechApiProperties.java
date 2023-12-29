package com.goorm.devlink.mentoringservice.config.properties;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConfigurationProperties("data.naverapi.speech")
@ConstructorBinding
@RequiredArgsConstructor
public class NaverSpeechApiProperties {

    private final String secret;
    private final String invokeUrl;
    private final String contentType;


}
