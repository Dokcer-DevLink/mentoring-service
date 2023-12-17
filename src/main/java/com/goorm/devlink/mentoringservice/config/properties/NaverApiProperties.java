package com.goorm.devlink.mentoringservice.config.properties;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("data.naverapi")
@Getter @Setter
public class NaverApiProperties {

    private String clientId;
    private String clientSecret;
    private NaverApiVo stt = new NaverApiVo();
    private NaverApiVo summary = new NaverApiVo();
    @Data
    public class NaverApiVo {
        private String url;
        private String contentType;
        private String fileUrl;
    }

}
