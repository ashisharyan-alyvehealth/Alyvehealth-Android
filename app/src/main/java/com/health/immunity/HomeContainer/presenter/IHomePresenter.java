package com.health.immunity.HomeContainer.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.hardware.SensorEvent;
import android.renderscript.Element;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;

public interface IHomePresenter {

    void savePedometerUserKPI(String steps, String distance);
    void saveUserKPI(String steps, String distance);
    boolean checkSensorAvailability();
    boolean checkPedometerPermission();
    void startPedometerService();
    void requestPedometerPermissionCustomDialog(AlertDialog.Builder builderpedo);
    void stopPedometerService();
    void savePedometerData(String steps,String distance);
    //void getGoogleFitData(Activity activity, GoogleSignInAccount account, Long startTime, Long endTime, DataType TYPE, Field field);
   // void saveGoogleFitData();
}
