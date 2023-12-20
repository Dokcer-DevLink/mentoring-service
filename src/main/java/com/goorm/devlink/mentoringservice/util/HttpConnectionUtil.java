package com.goorm.devlink.mentoringservice.util;


import com.google.gson.Gson;
import com.goorm.devlink.mentoringservice.naverapi.vo.NestRequestEntity;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
public class HttpConnectionUtil {


    public HttpEntity createHttpEntity(File file, NestRequestEntity nestRequestEntity){
        Gson gson = new Gson();
        return MultipartEntityBuilder.create()
                .addTextBody("params", gson.toJson(nestRequestEntity), ContentType.APPLICATION_JSON)
                .addBinaryBody("media", file, ContentType.MULTIPART_FORM_DATA, file.getName())
                .build();
    }

    public HttpPost createHttpPost(String url, Header[] headers, HttpEntity httpEntity){
        HttpPost httpPost = new HttpPost(url);
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

    public File convertEncodingToFile(String encoding, String fileUrl){
        try {
            byte[] decodedFile = Base64.getDecoder().decode(encoding);
            FileOutputStream fileOutputStream = new FileOutputStream(fileUrl);
            fileOutputStream.write(decodedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(fileUrl);
    }



}
