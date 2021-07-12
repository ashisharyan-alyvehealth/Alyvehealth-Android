package com.health.immunity.HomeContainer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class PEDORESPONSE {

    @SerializedName("jsonData")
    @Expose
    private JsonData jsonData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public JsonData getJsonData() {
        return jsonData;
    }

    public void setJsonData(JsonData jsonData) {
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

    public class JsonData {

        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("health_kpi_master_id")
        @Expose
        private Integer healthKpiMasterId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("value")
        @Expose
        private Integer value;
        @SerializedName("month")
        @Expose
        private String month;
        @SerializedName("attribute")
        @Expose
        private String attribute;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("id")
        @Expose
        private Integer id;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getHealthKpiMasterId() {
            return healthKpiMasterId;
        }

        public void setHealthKpiMasterId(Integer healthKpiMasterId) {
            this.healthKpiMasterId = healthKpiMasterId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }



}

