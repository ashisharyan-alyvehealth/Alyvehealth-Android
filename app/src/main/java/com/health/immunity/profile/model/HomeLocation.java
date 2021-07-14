package com.health.immunity.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeLocation {

    @SerializedName("home_lat")
    @Expose
    private double homeLat;
    @SerializedName("home_lon")
    @Expose
    private double homeLon;

    public double getHomeLat() {
        return homeLat;
    }

    public void setHomeLat(double homeLat) {
        this.homeLat = homeLat;
    }

    public double getHomeLon() {
        return homeLon;
    }

    public void setHomeLon(double homeLon) {
        this.homeLon = homeLon;
    }
}

