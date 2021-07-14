package com.health.immunity.profile.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkEmailResponse {

    @SerializedName("jsonData")
    @Expose
    private String jsonData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
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
