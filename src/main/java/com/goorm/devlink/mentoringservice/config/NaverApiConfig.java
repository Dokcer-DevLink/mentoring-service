package com.goorm.devlink.mentoringservice.config;


import com.goorm.devlink.mentoringservice.config.properties.NaverSpeechApiProperties;
import com.goorm.devlink.mentoringservice.config.properties.vo.NaverSpeechConfigVo;
import com.goorm.devlink.mentoringservice.naverapi.NaverClovaFactory;
import com.goorm.devlink.mentoringservice.naverapi.vo.NestRequestEntity;
import com.goorm.devlink.mentoringservice.naverapi.vo.SpeechHttpHeader;
import com.goorm.devlink.mentoringservice.util.AwsUtil;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(NaverSpeechApiProperties.class)
@RequiredArgsConstructor
public class NaverApiConfig {

    private final NaverSpeechApiProperties naverSpeechApiProperties;

    @Bean
    public NaverSpeechConfigVo naverApiConfigVo(){
        return new NaverSpeechConfigVo(naverSpeechApiProperties.getSecret(),naverSpeechApiProperties.getContentType(),
                naverSpeechApiProperties.getInvokeUrl() );
    }

    @Bean
    public NestRequestEntity nestRequestEntity(){
        return new NestRequestEntity();
    }


    @Bean
    public HttpConnectionUtil httpConnectionUtil(){
        return new HttpConnectionUtil();
    }

    @Bean
    public NaverClovaFactory naverClovaFactory(HttpConnectionUtil httpConnectionUtil, NaverSpeechConfigVo naverSpeechConfigVo,
                                               NestRequestEntity nestRequestEntity, AwsUtil awsUtil){
        return new NaverClovaFactory(httpConnectionUtil, naverSpeechConfigVo,nestRequestEntity,awsUtil);
    }




}
