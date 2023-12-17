package com.goorm.devlink.mentoringservice.config.properties.vo;


import com.goorm.devlink.mentoringservice.config.properties.NaverApiProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Slf4j
@Getter
@Setter
public class NaverApiConfigVo {
    private final String clientId;
    private final String clientSecret;
    private final NaverApiProperties.NaverApiVo stt;
    private final NaverApiProperties.NaverApiVo summary;

    @PostConstruct
    public void init(){
        log.info("clientId : {} ", clientId);
        log.info("clientSecret : {}", clientSecret);
        log.info("sst : {}", stt.toString());
        log.info("summary : {}", summary.toString());
    }


}
