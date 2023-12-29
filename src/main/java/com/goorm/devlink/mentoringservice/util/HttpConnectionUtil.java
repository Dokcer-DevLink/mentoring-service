package com.goorm.devlink.mentoringservice.util;


import com.google.gson.Gson;
import com.goorm.devlink.mentoringservice.naverapi.vo.NestRequestEntity;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class HttpConnectionUtil {


    public HttpEntity createHttpEntity(String fileUrl, NestRequestEntity nestRequestEntity){
        Gson gson = new Gson();
        return new StringEntity(gson.toJson(createBody(nestRequestEntity,fileUrl)),ContentType.APPLICATION_JSON);
    }

    public HttpPost createHttpPost(String invokeUrl, Header[] headers, HttpEntity httpEntity){
        HttpPost httpPost = new HttpPost(invokeUrl);
        httpPost.setHeaders(headers);
        httpPost.setEntity(httpEntity);
        return httpPost;
    }

    public String executeHttpPost(HttpPost httpPost){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (final CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
            final HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,Object> createBody(NestRequestEntity nestRequestEntity, String url){
        Map<String, Object> body = new HashMap<>();
        body.put("url", url);
        body.put("language", nestRequestEntity.getLanguage());
        body.put("completion", nestRequestEntity.getCompletion());
        body.put("callback", nestRequestEntity.getCallback());
        body.put("userdata", nestRequestEntity.getCallback());
        body.put("wordAlignment", nestRequestEntity.getWordAlignment());
        body.put("fullText", nestRequestEntity.getFullText());
        body.put("forbiddens", nestRequestEntity.getForbiddens());
        body.put("boostings", nestRequestEntity.getBoostings());
        body.put("diarization", nestRequestEntity.getDiarization());
        body.put("sed", nestRequestEntity.getSed());
        return body;
    }




}
