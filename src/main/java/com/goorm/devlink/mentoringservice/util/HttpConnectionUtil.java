package com.goorm.devlink.mentoringservice.util;


import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@RequiredArgsConstructor
public class HttpConnectionUtil {

    public void sendFileToServer(HttpURLConnection conn, File file){
        try {
            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendJsonToServer(HttpURLConnection conn, JSONObject jsonObject ){
        try {
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
//            byte[] input = jsonObject.toString().getBytes("utf-8");
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String receiveResponseFromServer(HttpURLConnection conn){
        BufferedReader br = null;
        StringBuffer response = new StringBuffer();
        try {
            int responseCode = conn.getResponseCode();


            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode + " responseMessage : " + conn.getResponseMessage());
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine;

            if(br != null) {
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        conn.disconnect();
        return response.toString();
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

    public HttpURLConnection getHttpUrlConnection(String apiUrl, String contentType, String clientId, String clientSecret){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(1000*60*5);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }


}
