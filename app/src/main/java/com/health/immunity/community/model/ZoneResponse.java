package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ZoneResponse implements Serializable {

    @SerializedName("jsonData")
    @Expose
    private List<JsonData> jsonData = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 542380432899970571L;


    public List<JsonData> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<JsonData> jsonData) {
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

        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("containment_availability")
        @Expose
        private Boolean containmentAvailability;
        @SerializedName("district_type")
        @Expose
        private String districtType;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("containment_zone")
        @Expose
        private Boolean containmentZone;
        @SerializedName("id")
        @Expose
        private Integer id;
        private final static long serialVersionUID = 196815934558674997L;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Boolean getContainmentAvailability() {
            return containmentAvailability;
        }

        public void setContainmentAvailability(Boolean containmentAvailability) {
            this.containmentAvailability = containmentAvailability;
        }

        public String getDistrictType() {
            return districtType;
        }

        public void setDistrictType(String districtType) {
            this.districtType = districtType;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public Boolean getContainmentZone() {
            return containmentZone;
        }

        public void setContainmentZone(Boolean containmentZone) {
            this.containmentZone = containmentZone;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }
}
