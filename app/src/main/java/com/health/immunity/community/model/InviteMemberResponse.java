package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InviteMemberResponse implements Serializable {

    /*   @SerializedName("jsonData")
       @Expose
       private List<JsonDatum> jsonData = null;*/
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = -4445318265416342541L;

    /*   public List<JsonDatum> getJsonData() {
           return jsonData;
       }

       public void setJsonData(List<JsonDatum> jsonData) {
           this.jsonData = jsonData;
       }
   */
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


    public class JsonDatum implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("community_id")
        @Expose
        private Integer communityId;
        @SerializedName("invited_mobile_number")
        @Expose
        private String invitedMobileNumber;
        @SerializedName("invited_name")
        @Expose
        private Object invitedName;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("updated_by")
        @Expose
        private Object updatedBy;
        private final static long serialVersionUID = -763131376175804904L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getCommunityId() {
            return communityId;
        }

        public void setCommunityId(Integer communityId) {
            this.communityId = communityId;
        }

        public String getInvitedMobileNumber() {
            return invitedMobileNumber;
        }

        public void setInvitedMobileNumber(String invitedMobileNumber) {
            this.invitedMobileNumber = invitedMobileNumber;
        }

        public Object getInvitedName() {
            return invitedName;
        }

        public void setInvitedName(Object invitedName) {
            this.invitedName = invitedName;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

    }
}
