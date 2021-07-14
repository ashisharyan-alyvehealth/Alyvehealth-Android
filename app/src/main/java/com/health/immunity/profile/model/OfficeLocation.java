package com.health.immunity.profile.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfficeLocation {

    @SerializedName("office_lat")
    @Expose
    private double officeLat;
    @SerializedName("office_lon")
    @Expose
    private double officeLon;

    public double getOfficeLat() {
        return officeLat;
    }

    public void setOfficeLat(double officeLat) {
        this.officeLat = officeLat;
    }

    public double getOfficeLon() {
        return officeLon;
    }

    public void setOfficeLon(double officeLon) {
        this.officeLon = officeLon;
    }
}

