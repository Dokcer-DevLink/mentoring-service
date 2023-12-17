package com.goorm.devlink.mentoringservice;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;

public class RecordTestMain {

    public static void main(String[] args) {
        String clientId = "c00jkdw2jc";             // Application Client ID";
        String clientSecret = "LOl7mvbbp7AtkwW79x0mBXCcYIAiX1ETwkSWlGXh";     // Application Client Secret";

        try {


            String imgFile = "/Users/kangmingu/Desktop/Goorm_Project/mentoring-service/devlink_test.m4a";
            File requestFile = new File(imgFile);
            byte[] originBytes = Files.readAllBytes(requestFile.toPath());
            String encoding = Base64.getEncoder().encodeToString(originBytes);

            // == 컨트롤러 ==

            // 인코딩 -> 디코딩 -> 파일로 저장
            byte[] decodedFile = Base64.getDecoder().decode(encoding);
            String fileUrl = "/Users/kangmingu/Desktop/Goorm_Project/mentoring-service/RecordFile";
            FileOutputStream fileOutputStream = new FileOutputStream(fileUrl);
            fileOutputStream.write(decodedFile);

            // == 파일 ==
            File voiceFile = new File(fileUrl);

            //URL 설정
            String language = "Kor";        // 언어 코드 ( Kor, Jpn, Eng, Chn )
            String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
            URL url = new URL(apiURL);

            //HTTP URL Connection 설정
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine;

            if(br != null) {
                StringBuffer response = new StringBuffer();
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

    }
}
