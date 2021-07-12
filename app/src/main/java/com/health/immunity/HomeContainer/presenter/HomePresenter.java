package com.health.immunity.HomeContainer.presenter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.gson.JsonObject;
import com.health.immunity.BuildConfig;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.HomeContainer.model.JsonObjectResponse;
import com.health.immunity.HomeContainer.model.PEDORESPONSE;
import com.health.immunity.HomeContainer.view.IHomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.pedo.util.Logger;
import com.health.immunity.pedo.util.Util;
import com.health.immunity.retrofit.RetrofitClient;
import com.health.immunity.pedo.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements IHomePresenter{
    Context context;
    IHomeActivity view;
    private boolean showSteps = true;
    private int todayOffset;
    private int total_start;
    private int goal;
    private int since_boot;
    private int total_days;
    FitnessOptions fitnessOptions;
    private String totalSteps=" ";
    private String distance;
    public final static NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
    final public static int DEFAULT_GOAL = 10000;
    final public static float DEFAULT_STEP_SIZE = Locale.getDefault() == Locale.US ? 2.5f : 75f;
    final public static String DEFAULT_STEP_UNIT = Locale.getDefault() == Locale.US ? "ft" : "cm";

   public HomePresenter(IHomeActivity view,Context context){
        this.context=context;
        this.view=view;
    }

    @Override
    public void savePedometerUserKPI(String steps, String distance) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("Steps",steps);
        jsonObject.addProperty("distance",distance);
        System.out.println("onfitClickedpedo1"+String.valueOf(jsonObject));
        System.out.println("onfitClickedpedo1"+steps);

        Call<PEDORESPONSE> call =  RetrofitClient.getUniqInstance().getApi()
                .pedoCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), String.valueOf(jsonObject));
        call.enqueue(new Callback<PEDORESPONSE>() {

            @Override
            public void onResponse(Call<PEDORESPONSE> call, Response<PEDORESPONSE> response) {
                //   CommonUtils.dismissSpotoProgressDialog();
                try{
                    Log.e("response-success1", response.body().getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<PEDORESPONSE> call, Throwable t) {
                // CommonUtils.dismissSpotoProgressDialog();

                Log.e("response-failure", call.toString());
            }
        });

    }

    @Override
    public void saveUserKPI(String steps, String distance) {
        JsonObject jsonObject = new JsonObject();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!pedo"+steps);

        jsonObject.addProperty("steps",steps);
        jsonObject.addProperty("distance",distance);
        System.out.println("onfitClickedsaveuserkpi"+jsonObject);

        Call<JsonObjectResponse> call =  RetrofitClient.getUniqInstance().getApi()
                .fitCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), String.valueOf(jsonObject));
        call.enqueue(new Callback<JsonObjectResponse>() {

            @Override
            public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {

                try{
                    Log.e("response-success11", response.body().getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<JsonObjectResponse> call, Throwable t) {


                Log.e("response-failure", call.toString());
            }
        });
    }

    @Override
    public boolean checkSensorAvailability() {

        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List list;
        list = sensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER);
        System.out.println("sensor@@@@@"+list);
        if(list.size()>0)
        {return true;}
        else {
            return false;
        }

    }

    @Override
    public boolean checkPedometerPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION)
                        != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }



    @Override
    public void startPedometerService() {
        context.startService(new Intent(context, SensorListener.class));
        PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER,"1");
        view.onSuccess("service started");

    }

    @Override
    public void requestPedometerPermissionCustomDialog(AlertDialog.Builder builderpedo) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        builderpedo.setMessage("Allow Alyve Health to access your physical activity")
                .setCancelable(false)
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        context.startService(new Intent(context, SensorListener.class));

                        System.out.println("pedonewapi"+" 3");

                        PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "1");

                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.stopService(new Intent(context, SensorListener.class));
                        System.out.println("pedonewapi"+"20");
                        PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "0");
                        //  Action for 'NO' Button
                        dialog.cancel();
                        //       Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                        //             Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builderpedo.create();
        //Setting the title manually
        alert.setIcon(context.getDrawable(R.drawable.logo));
        alert.show();

    }

    @Override
    public void stopPedometerService() {
        context.stopService(new Intent(context, SensorListener.class));
        PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER,"0");
    }


    @Override
    public void savePedometerData(String steps2,String distance2) {
        savePedometerUserKPI(steps2,distance2);
        saveUserKPI(steps2,distance2);
    }

    @Override
    public void getGoogleFitData(Activity activity, GoogleSignInAccount account, Long startTime, Long endTime, DataType dataType, Field field) {
        final Task<DataReadResponse> response = Fitness.getHistoryClient(activity, account)
                .readData(new DataReadRequest.Builder()
                        .read(dataType)
                        .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                        .build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                totalSteps = "";
                DataReadResponse readDataResult = null;
                try {
                    readDataResult = Tasks.await(response);
                    final DataSet dataSet = readDataResult.getDataSet(dataType);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            int steps = 0;
                            // Toast.makeText(HomeActivity.this, "Size: "+dataSet.getDataPoints().size(), Toast.LENGTH_SHORT).show();

                            Calendar date = Calendar.getInstance();
                            for(int i=0;i<dataSet.getDataPoints().size();i++)
                            {
                                long time = dataSet.getDataPoints().get(i).getTimestamp(TimeUnit.MILLISECONDS);
                                date.setTimeInMillis(time);
                                System.out.println("##############stepsnew1"+ dataSet.getDataPoints().get(i).getOriginalDataSource().getStreamName());
                                if(! "user_input".equals(dataSet.getDataPoints().get(i).getOriginalDataSource().getStreamName())) {
                                    totalSteps += dataSet.getDataPoints().get(i).getValue(field).toString() + " " + date.get(Calendar.DAY_OF_MONTH) + ",";
                                }
                            }

                            System.out.println("##############stepsnew"+totalSteps);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
