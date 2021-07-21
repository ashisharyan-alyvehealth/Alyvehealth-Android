package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommunityResponse implements Serializable {

    @SerializedName("jsonData")
    @Expose
    private JsonData jsonData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 542380432899970571L;

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


    public class JsonData implements Serializable {

        @SerializedName("community_name")
        @Expose
        private String communityName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("community_type")
        @Expose
        private String communityType;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("access_control")
        @Expose
        private String accessControl;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("id")
        @Expose
        private Integer id;
        private final static long serialVersionUID = -9215011916712425405L;

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCommunityType() {
            return communityType;
        }

        public void setCommunityType(String communityType) {
            this.communityType = communityType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccessControl() {
            return accessControl;
        }

        public void setAccessControl(String accessControl) {
            this.accessControl = accessControl;
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
