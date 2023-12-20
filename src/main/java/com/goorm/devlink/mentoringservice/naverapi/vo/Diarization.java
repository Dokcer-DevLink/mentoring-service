package com.goorm.devlink.mentoringservice.naverapi.vo;

public class Diarization {
    private Boolean enable = Boolean.FALSE;
    private Integer speakerCountMin;
    private Integer speakerCountMax;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getSpeakerCountMin() {
        return speakerCountMin;
    }

    public void setSpeakerCountMin(Integer speakerCountMin) {
        this.speakerCountMin = speakerCountMin;
    }

    public Integer getSpeakerCountMax() {
        return speakerCountMax;
    }

    public void setSpeakerCountMax(Integer speakerCountMax) {
        this.speakerCountMax = speakerCountMax;
    }

}
