package com.health.immunity.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class OnBoardResponse {

    @SerializedName("jsonData")
    @Expose
    private OnBoardResponseData jsonData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public OnBoardResponseData getJsonData() {
        return jsonData;
    }

    public void setJsonData(OnBoardResponseData jsonData) {
        this.jsonData = jsonData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

