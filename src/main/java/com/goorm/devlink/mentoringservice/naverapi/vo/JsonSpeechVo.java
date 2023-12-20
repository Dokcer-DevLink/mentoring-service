package com.goorm.devlink.mentoringservice.naverapi.vo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JsonSpeechVo {

    private JsonObject responseJson;
    private StringBuilder parsedResponse;

    public static JsonSpeechVo getInstance(String response){
        JsonParser jsonParser = new JsonParser();
        return JsonSpeechVo.builder()
                .responseJson((JsonObject)jsonParser.parse(response))
                .parsedResponse(new StringBuilder())
                .build();
    }

    public boolean isSucceeded(){
        String status = responseJson.get("message").toString().replaceAll("\"","");
        return ( status.equals("Succeeded") ) ? true : false;
    }

    public JsonArray getSpeechArray(){
        return (JsonArray)responseJson.get("segments");
    }

    public String getSpeakerName(JsonElement jsonElement){
        JsonObject speaker = jsonElement.getAsJsonObject().get("speaker").getAsJsonObject();
        return "개발자".concat(speaker.get("name").toString().replaceAll("\"",""));
    }

    public String getSpeakerText(JsonElement jsonElement){
        return jsonElement.getAsJsonObject().get("text").toString().replaceAll("\"","");

    }

    public void appendToParsedResponse(String speakerName, String speakerText){
        parsedResponse.append(speakerName).append(":").append(" ").append(speakerText).append("\n");
    }

    public String getParsedResponse(){
        return parsedResponse.toString();
    }


}
