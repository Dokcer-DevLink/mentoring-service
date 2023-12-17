package com.goorm.devlink.mentoringservice.record.impl;

import com.goorm.devlink.mentoringservice.config.properties.vo.NaverApiConfigVo;
import com.goorm.devlink.mentoringservice.record.NaverClovaApi;
import com.goorm.devlink.mentoringservice.util.HttpConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.net.HttpURLConnection;


@RequiredArgsConstructor
public class NaverSummary implements NaverClovaApi {

    private final HttpConnectionUtil httpConnectionUtil;
    private final NaverApiConfigVo naverApiConfigVo;

    @Override
    public String sendDataToNaverClova(String content) {

        HttpURLConnection conn = httpConnectionUtil.getHttpUrlConnection(naverApiConfigVo.getSummary().getUrl(),
                naverApiConfigVo.getSummary().getContentType(),
                naverApiConfigVo.getClientId(),
                naverApiConfigVo.getClientSecret()
        );

        JSONObject document = new JSONObject();
        document.put("title","title");
        document.put("content",content);

        JSONObject option = new JSONObject();
        option.put("language","ko");
        option.put("model","news");
        option.put("tone",2);
        option.put("summaryCount",3);

        JSONObject sendJson = new JSONObject();
        sendJson.put("document",document);
        sendJson.put("option",option);

        httpConnectionUtil.sendJsonToServer(conn,sendJson);
        return httpConnectionUtil.receiveResponseFromServer(conn);
    }
}
