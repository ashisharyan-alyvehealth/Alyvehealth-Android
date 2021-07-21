package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LastFiveLocation implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("location_type")
    @Expose
    private Object locationType;
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
    private String containmentAvailability;
    @SerializedName("district_type")
    @Expose
    private String districtType;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("containment_zone")
    @Expose
    private String containmentZone;
    @SerializedName("containment_zone_name")
    @Expose
    private String containment_zone_name;
    private final static long serialVersionUID = 3451061603729367667L;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getLocationType() {
        return locationType;
    }

    public void setLocationType(Object locationType) {
        this.locationType = locationType;
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

    public String getContainmentAvailability() {
        return containmentAvailability;
    }

    public void setContainmentAvailability(String containmentAvailability) {
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

    public String getContainmentZone() {
        return containmentZone;
    }

    public void setContainmentZone(String containmentZone) {
        this.containmentZone = containmentZone;
    }

    public String getContainment_zone_name() {
        return containment_zone_name;
    }

    public void setContainment_zone_name(String containment_zone_name) {
        this.containment_zone_name = containment_zone_name;
    }
}