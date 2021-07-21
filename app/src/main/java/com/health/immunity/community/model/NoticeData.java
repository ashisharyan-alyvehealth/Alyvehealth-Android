package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeData {

    @SerializedName("Action")
    @Expose
    private List<NoticeAction> action = null;
    @SerializedName("guidance")
    @Expose
    private List<NoticeGuidance> guidance = null;

    public List<NoticeAction> getAction() {
        return action;
    }

    public void setAction(List<NoticeAction> action) {
        this.action = action;
    }

    public List<NoticeGuidance> getGuidance() {
        return guidance;
    }

    public void setGuidance(List<NoticeGuidance> guidance) {
        this.guidance = guidance;
    }
}

