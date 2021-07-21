package com.health.immunity.community.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserToday {
    @SerializedName("check-in")
    @Expose
    private String checkIn;
    @SerializedName("Report Symptoms")
    @Expose
    private String reportSymptoms;
    @SerializedName("Have you Traveled?")
    @Expose
    private String haveYouTraveled;
    @SerializedName("Doctor Consult")
    @Expose
    private String doctorConsult;

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getReportSymptoms() {
        return reportSymptoms;
    }

    public void setReportSymptoms(String reportSymptoms) {
        this.reportSymptoms = reportSymptoms;
    }

    public String getHaveYouTraveled() {
        return haveYouTraveled;
    }

    public void setHaveYouTraveled(String haveYouTraveled) {
        this.haveYouTraveled = haveYouTraveled;
    }

    public String getDoctorConsult() {
        return doctorConsult;
    }

    public void setDoctorConsult(String doctorConsult) {
        this.doctorConsult = doctorConsult;
    }
}

