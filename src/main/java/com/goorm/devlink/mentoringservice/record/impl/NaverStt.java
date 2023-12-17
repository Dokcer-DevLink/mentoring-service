package com.goorm.devlink.mentoringservice.record.impl;

import com.goorm.devlink.mentoringservice.config.properties.vo.NaverApiConfigVo;
import com.goorm.devlink.mentoringservice.record.NaverClovaApi;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.net.HttpURLConnection;


@RequiredArgsConstructor
public class NaverStt implements NaverClovaApi {

    private final HttpConnectionUtil httpConnectionUtil;
    private final NaverApiConfigVo naverApiConfigVo;
    @Override
    public String sendDataToNaverClova(String encoding) {
        System.out.println("============ NaverStt ================");
        HttpURLConnection conn = httpConnectionUtil.getHttpUrlConnection(naverApiConfigVo.getStt().getUrl(),
                naverApiConfigVo.getStt().getContentType(),
                naverApiConfigVo.getClientId(),
                naverApiConfigVo.getClientSecret()
        );

        File voiceFile = httpConnectionUtil.convertEncodingToFile(encoding, naverApiConfigVo.getStt().getFileUrl());
        httpConnectionUtil.sendFileToServer(conn,voiceFile);
        return extractContent(httpConnectionUtil.receiveResponseFromServer(conn));

    }

    private String extractContent(String response){
        String replace1 = response.replace("{\"text\":\"", "");
        String replace2 = replace1.replace("\"}", "");
        return replace2;
    }
}
