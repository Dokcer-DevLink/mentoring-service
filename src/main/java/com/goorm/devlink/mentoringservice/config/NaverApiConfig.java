package com.goorm.devlink.mentoringservice.config;


import com.goorm.devlink.mentoringservice.config.properties.NaverApiProperties;
import com.goorm.devlink.mentoringservice.config.properties.vo.NaverApiConfigVo;
import com.goorm.devlink.mentoringservice.record.NaverClovaFactory;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(NaverApiProperties.class)
@RequiredArgsConstructor
public class NaverApiConfig {

    private final NaverApiProperties naverApiProperties;

    @Bean
    public NaverApiConfigVo naverApiConfigVo(){
        return new NaverApiConfigVo(naverApiProperties.getClientId(),naverApiProperties.getClientSecret(),
                naverApiProperties.getStt(),naverApiProperties.getSummary());
    }

    @Bean
    public HttpConnectionUtil httpConnectionUtil(){
        return new HttpConnectionUtil();
    }

    @Bean
    public NaverClovaFactory naverClovaFactory(HttpConnectionUtil httpConnectionUtil, NaverApiConfigVo naverApiConfigVo){
        return new NaverClovaFactory(httpConnectionUtil,naverApiConfigVo);
    }


}
