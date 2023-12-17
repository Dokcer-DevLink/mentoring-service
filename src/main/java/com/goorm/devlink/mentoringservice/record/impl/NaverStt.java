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
    private final NaverApiConfigVo naverClovaApiConfigVo;
    @Override
    public String sendDataToNaverClova(String encoding) {
        System.out.println("============ NaverStt ================");
        HttpURLConnection conn = httpConnectionUtil.getHttpUrlConnection(naverClovaApiConfigVo.getStt().getUrl(),
                naverClovaApiConfigVo.getStt().getContentType(),
                naverClovaApiConfigVo.getClientId(),
                naverClovaApiConfigVo.getClientSecret()
        );

        File voiceFile = httpConnectionUtil.convertEncodingToFile(encoding,naverClovaApiConfigVo.getStt().getFileUrl());
        httpConnectionUtil.sendFileToServer(conn,voiceFile);
        return httpConnectionUtil.receiveResponseFromServer(conn);

    }
}
