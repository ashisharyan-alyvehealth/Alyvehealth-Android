package com.health.immunity.googlefit;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.fitness.result.SessionReadResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.health.immunity.R;
import com.health.immunity.HomeContainer.model.JsonObjectResponse;
import com.health.immunity.retrofit.RetrofitClient;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This enum is used to define actions that can be performed after a successful sign in to Fit.
 * One of these values is passed to the Fit sign-in, and returned in a successful callback, allowing
 * subsequent execution of the desired action.
 */


public class Googlefit extends AppCompatActivity {


    public static final String TAG = "BasicSensorsApi";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;
//    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
//    private OnDataPointListener mListener;
//    private OnDataPointListener mListene;

    TextView steps,distance,calories,calories1;

    String s1,s2,s3,s4,s5,s6,s7,s8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlefit);
//        if (savedInstanceState != null) {
//            mSensorsStepCount = savedInstanceState.getInt("sensor_steps");
//        }
        steps=findViewById(R.id.steps);
        distance=findViewById(R.id.distance);
        calories=findViewById(R.id.calories);
        calories1=findViewById(R.id.calories1);
//Get the firebase authentication done first
        FitnessOptions fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .addDataType(DataType.TYPE_DISTANCE_CUMULATIVE)
                        .addDataType(DataType.TYPE_DISTANCE_DELTA)
                        .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY)
                        .addDataType(DataType.TYPE_WEIGHT)
                        .addDataType(DataType.TYPE_HEIGHT)
                        .addDataType(DataType.AGGREGATE_HEIGHT_SUMMARY)
                        .addDataType(DataType.AGGREGATE_WEIGHT_SUMMARY)
//                        .addDataType(DataType.TYPE_HEART_RATE_BPM)
//                        .addDataType(DataType.TYPE_BODY_FAT_PERCENTAGE)
//                        .addDataType(DataType.TYPE_WORKOUT_EXERCISE)
//                        .addDataType(DataType.TYPE_ACTIVITY_SEGMENT)
                        .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        } else {
            subscribe();
        }


//        subscribe();
    }
    public void subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Googlefit.this, "Thank you for you subscription", Toast.LENGTH_SHORT);
                                } else {
                                    Toast.makeText(
                                            Googlefit.this,
                                            "Authorization Failed",
                                            Toast.LENGTH_SHORT);
                                    Log.d(
                                            "USER AUTHORIZATION",
                                            "There was a problem subscribing.",
                                            task.getException());
                                }
                            }
                        });
     //   getTodayStepsCount();
          readLastWeekSteps();
        //getTodayCalories();
      // getTodayDistance();
         readLastWeekDistance();

        //getTodayHeight();
        //getTodayWeight();
        getTodayExSLp();
        //getTodayheart();
        //getTodayFAT();
        getTodayEx();
        //readLastWeekeXER();
        //onPostClicked();
        //readLastWeekStepswght();
    }
    private void getTodayStepsCount() {
        Fitness.getHistoryClient(
                this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                long total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                                steps.setText(String.valueOf(total));
                                s1= String.valueOf(total);

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        Googlefit.this,
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                steps.setText(0);

                            }
                        });
    }
    protected void readLastWeekSteps() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -4);
        long startTime = cal.getTimeInMillis();

        DateFormat dateFormat = DateFormat.getDateInstance();
        Log.e("History", "Range Start: " + dateFormat.format(startTime));
        Log.e("History", "Range End: " + dateFormat.format(endTime));


        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        Task<DataReadResponse> response = Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(dataReadRequest).
                        addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                            @Override
                            public void onSuccess(DataReadResponse dataReadResponse) {
                                int p =0;
                                ArrayList<Integer> aListLanguages = new ArrayList<Integer>();
                                if (dataReadResponse.getBuckets().size() > 0) {
                                    Log.e("History", "Number of buckets: " + dataReadResponse.getBuckets().size());

                                    for (Bucket bucket : dataReadResponse.getBuckets()) {

                                        List<DataSet> dataSets = bucket.getDataSets();
                                        for (DataSet dataSet : dataSets) {
                                            showDataSet(dataSet);
                                        }
                                        DataSet dataSet = bucket.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
                                        //Loop through Bucket to get last 7 days of data
                                        //dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()
                                        p=dataSet.isEmpty()?0:dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                                        calories.setText(String.valueOf(p));
                                        Log.i("HElooooooooooo",String.valueOf(p));





                                        //Add elements to ArrayList
                                        aListLanguages.add(p);


                                    }
                                    String s = TextUtils.join(", ", aListLanguages);

                                    Log.d("LOGTAG", s);
                                    System.out.println("##################l" + aListLanguages.toString());


                                    StringBuilder sb = new StringBuilder();
                                    for (int j = 0; j < aListLanguages.size(); j++) {

                                        //   if (aListLanguages.get(j).isChecked() == true)
                                        {

                                            {
                                                sb.append(", ");
                                            }


                                        }
                                    }
                                    System.out.println("##################0" + sb.toString());

                                    StringBuilder sbString = new StringBuilder("");

                                    //iterate through ArrayList
                                    for(int language : aListLanguages){

                                        //append ArrayList element followed by comma
                                        sbString.append(language).append(",");
                                    }

                                    //convert StringBuffer to String
                                    String strList = sbString.toString();

                                    //remove last comma from String if you want
                                    if( strList.length() > 0 )
                                        strList = strList.substring(0, strList.length() - 1);

                                    System.out.println(strList);
                                }
                            }
                        });

    }
    private void getTodayDistance(){
        Fitness.getHistoryClient(
                this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                Log.d("Distance:", dataSet.getDataPoints().toString());
                                float total = dataSet.isEmpty()
                                        ? 0 : dataSet.getDataPoints().get(0).getValue(
                                        Field.FIELD_DISTANCE).asFloat();
                                int roundTotal = Math.round(total);
                                distance.setText(String.valueOf(roundTotal)+" m");
                                s2= String.valueOf(roundTotal);

                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        Googlefit.this,
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                Log.d("DISTANCE", "Failed" );
                                distance.setText(0+" m");
                            }
                        });
    }

    protected void readLastWeekDistance() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -4);
        long startTime = cal.getTimeInMillis();

        DateFormat dateFormat = DateFormat.getDateInstance();
        Log.e("History", "Range Start: " + dateFormat.format(startTime));
        Log.e("History", "Range End: " + dateFormat.format(endTime));


        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_DISTANCE_DELTA, DataType.AGGREGATE_DISTANCE_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        Task<DataReadResponse> response = Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(dataReadRequest).
                        addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                            @Override
                            public void onSuccess(DataReadResponse dataReadResponse) {
                                float p =0;
                                ArrayList<Float> aListLanguages = new ArrayList<Float>();
                                if (dataReadResponse.getBuckets().size() > 0) {
                                    Log.e("History", "Number of buckets: " + dataReadResponse.getBuckets().size());

                                    for (Bucket bucket : dataReadResponse.getBuckets()) {

                                        List<DataSet> dataSets = bucket.getDataSets();
                                        for (DataSet dataSet : dataSets) {
                                            showDataSet(dataSet);
                                        }
                                        DataSet dataSet = bucket.getDataSet(DataType.TYPE_DISTANCE_DELTA);
                                        //Loop through Bucket to get last 7 days of data
                                        //dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()
                                        p=dataSet.isEmpty()?0:dataSet.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
                                        calories.setText(String.valueOf(p));
                                        Log.i("HElooooooooooo",String.valueOf(p));





                                        //Add elements to ArrayList
                                        aListLanguages.add(p);


                                    }
                                    String s = TextUtils.join(", ", aListLanguages);

                                    Log.d("LOGTAG", s);
                                    System.out.println("##################l" + aListLanguages.toString());


                                    StringBuilder sb = new StringBuilder();
                                    for (int j = 0; j < aListLanguages.size(); j++) {

                                        //   if (aListLanguages.get(j).isChecked() == true)
                                        {

                                            {
                                                sb.append(", ");
                                            }


                                        }
                                    }
                                    System.out.println("##################0" + sb.toString());

                                    StringBuilder sbString = new StringBuilder("");

                                    //iterate through ArrayList
                                    for(float language : aListLanguages){

                                        //append ArrayList element followed by comma
                                        sbString.append(language).append(",");
                                    }

                                    //convert StringBuffer to String
                                    String strList = sbString.toString();

                                    //remove last comma from String if you want
                                    if( strList.length() > 0 )
                                        strList = strList.substring(0, strList.length() - 1);

                                    System.out.println(strList);
                                }
                            }
                        });

    }
    private void getTodayCalories(){
        Fitness.getHistoryClient(
                this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total = dataSet.isEmpty()
                                        ? 0 : dataSet.getDataPoints().get(0).getValue(
                                        Field.FIELD_CALORIES).asFloat();
                                int roundTotal = Math.round(total);
                                calories1.setText(String.valueOf(roundTotal)+" Cal");
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        Googlefit.this,
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                calories1.setText(0+" Cal");

                            }
                        });
    }
    private void getTodayWeight(){

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -4);
        long startTime = cal.getTimeInMillis();
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_WEIGHT)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(365, TimeUnit.DAYS)
                .build();


        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(readRequest)
                .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        Log.d(TAG, "onSuccess()");
                        if (dataReadResponse.getBuckets().size() > 0) {
                            for (Bucket bucket : dataReadResponse.getBuckets()) {
                                DataSet dataSet = bucket.getDataSet(DataType.TYPE_WEIGHT);
                                //Loop through Bucket to get last 7 days of data
                                //dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()
                                float     p   =  dataSet.isEmpty()?0:dataSet.getDataPoints().get(0).getValue(Field.FIELD_WEIGHT).asFloat();
                                Log.i("HElllooooooooooo", String.valueOf(p));

                                s3= String.valueOf(p);


                            }
                            Log.i("HElooooooooooo","K");
                        }
                        else
                        {
                            Log.i("HElooooooooooo","0");

                        }

                       /* for (Bucket bucket : dataReadResponse.getBuckets()) {
                            Log.e("History", "Data returned for Data type: " + bucket.getDataSets());



                            List<DataSet> dataSets = bucket.getDataSets();
                            for (DataSet dataSet : dataSets) {
                                //showDataSet(dataSet);
                            }
                        }*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure()", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<DataReadResponse> task) {
                        Log.d(TAG, "onComplete()");
                    }
                });
    }
    private void getTodayHeight(){
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_HEIGHT)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(365, TimeUnit.DAYS)
                .build();


        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(readRequest)
                .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        Log.d(TAG, "onSuccess()");
                        if (dataReadResponse.getBuckets().size() > 0) {
                            for (Bucket bucket : dataReadResponse.getBuckets()) {
                                DataSet dataSet = bucket.getDataSet(DataType.TYPE_HEIGHT);
                                //Loop through Bucket to get last 7 days of data
                                //dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()
                                float     p=dataSet.isEmpty()?0:dataSet.getDataPoints().get(0).getValue(Field.FIELD_HEIGHT).asFloat();
                                Log.i("HElllooooooooooohh", String.valueOf(p*100));

                                s4= String.valueOf(p*100);


                            }
                            Log.i("HElooooooooooo","K");
                        }
                        else
                        {
                            Log.i("HElooooooooooo","0");

                        }

                       /* for (Bucket bucket : dataReadResponse.getBuckets()) {
                            Log.e("History", "Data returned for Data type: " + bucket.getDataSets());



                            List<DataSet> dataSets = bucket.getDataSets();
                            for (DataSet dataSet : dataSets) {
                                //showDataSet(dataSet);
                            }
                        }*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure()", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<DataReadResponse> task) {
                        Log.d(TAG, "onComplete()");
                    }
                });
    }
    private void getTodayFAT(){
        Fitness.getHistoryClient(
                this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_BODY_FAT_PERCENTAGE)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total = dataSet.isEmpty()
                                        ? 0 : dataSet.getDataPoints().get(0).getValue(
                                        Field.FIELD_PERCENTAGE).asFloat();
                                int roundTotal = Math.round(total);
                                // calories1.setText(String.valueOf(roundTotal)+" Cal");
                                System.out.println("#############bb#####hrt"+roundTotal);

                                s5= String.valueOf(roundTotal);

                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        Googlefit.this,
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                //  calories1.setText(0+" Cal");

                            }
                        });
    }
    private void getTodayEx(){
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_WORKOUT_EXERCISE)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(1, TimeUnit.DAYS)
                .build();
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(readRequest)
                .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        Log.d(TAG, "onSuccess()");
                        if (dataReadResponse.getBuckets().size() > 0) {
                            ArrayList<Integer> aListLanguages = new ArrayList<Integer>();
                            for (Bucket bucket : dataReadResponse.getBuckets()) {

                                List<DataSet> dataSets = bucket.getDataSets();
                                for (DataSet dataSet : dataSets) {
                                    showDataSet(dataSet);
                                }
                                DataSet dataSet = bucket.getDataSet(DataType.TYPE_WORKOUT_EXERCISE);
                                //Loop through Bucket to get last 7 days of data
                                //dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()
                                int     p=dataSet.isEmpty()?0:dataSet.getDataPoints().get(0).getValue(Field.FIELD_DURATION).asInt();
                                Log.i("HEllloooooooooooee", String.valueOf(p));

                                s6= String.valueOf(p);
                                aListLanguages .add(p);



                            }
                            String s = TextUtils.join(", ", aListLanguages);

                            Log.d("LOGTAG", s);
                            System.out.println("##################l" + aListLanguages.toString());

                            Log.i("HElooooooooooo","K");
                        }
                        else
                        {
                            Log.i("HElooooooooooo","0");

                        }

                       /* for (Bucket bucket : dataReadResponse.getBuckets()) {
                            Log.e("History", "Data returned for Data type: " + bucket.getDataSets());



                            List<DataSet> dataSets = bucket.getDataSets();
                            for (DataSet dataSet : dataSets) {
                                //showDataSet(dataSet);
                            }
                        }*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure()", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<DataReadResponse> task) {
                        Log.d(TAG, "onComplete()");
                    }
                });
     /*   Fitness.getHistoryClient(
                this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_WORKOUT_EXERCISE)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total = dataSet.isEmpty()
                                        ? 0 : dataSet.getDataPoints().get(0).getValue(
                                        Field.FIELD_DURATION).asFloat();
                                int roundTotal = Math.round(total);
                                // calories1.setText(String.valueOf(roundTotal)+" Cal");
                                System.out.println("#############bbdud#####hrt"+dataSet);
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        Googlefit.this,
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                //  calories1.setText(0+" Cal");

                            }
                        });*/
    }





    protected void readLastWeekeXER() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        DateFormat dateFormat = DateFormat.getDateInstance();
        Log.e("History", "Range Start: " + dateFormat.format(startTime));
        Log.e("History", "Range End: " + dateFormat.format(endTime));


        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
               // .aggregate(DataType.TYPE_WORKOUT_EXERCISE, DataType.AGGREGATE_ACTIVITY_SUMMARY)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        Task<DataReadResponse> response = Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(dataReadRequest).
                        addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                            @Override
                            public void onSuccess(DataReadResponse dataReadResponse) {
                                int p =0;
                                ArrayList<Integer> aListLanguages = new ArrayList<Integer>();
                                if (dataReadResponse.getBuckets().size() > 0) {
                                    Log.e("History", "Number of buckets: " + dataReadResponse.getBuckets().size());

                                    for (Bucket bucket : dataReadResponse.getBuckets()) {

                                        List<DataSet> dataSets = bucket.getDataSets();
                                        for (DataSet dataSet : dataSets) {
                                            showDataSet(dataSet);
                                        }
                                        DataSet dataSet = bucket.getDataSet(DataType.TYPE_WORKOUT_EXERCISE);
                                        //Loop through Bucket to get last 7 days of data
                                        //dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()
                                        p=dataSet.isEmpty()?0:dataSet.getDataPoints().get(0).getValue(Field.FIELD_DURATION).asInt();
                                        calories.setText(String.valueOf(p));
                                        Log.i("HElooooooooooo",String.valueOf(p));





                                        //Add elements to ArrayList
                                        aListLanguages.add(p);


                                    }
                                    String s = TextUtils.join(", ", aListLanguages);

                                    Log.d("LOGTAG", s);
                                    System.out.println("##################l" + aListLanguages.toString());


                                    StringBuilder sb = new StringBuilder();
                                    for (int j = 0; j < aListLanguages.size(); j++) {

                                        //   if (aListLanguages.get(j).isChecked() == true)
                                        {

                                            {
                                                sb.append(", ");
                                            }


                                        }
                                    }
                                    System.out.println("##################0" + sb.toString());

                                    StringBuilder sbString = new StringBuilder("");

                                    //iterate through ArrayList
                                    for(int language : aListLanguages){

                                        //append ArrayList element followed by comma
                                        sbString.append(language).append(",");
                                    }

                                    //convert StringBuffer to String
                                    String strList = sbString.toString();

                                    //remove last comma from String if you want
                                    if( strList.length() > 0 )
                                        strList = strList.substring(0, strList.length() - 1);

                                    System.out.println(strList);
                                }
                            }
                        });

    }


























    private void getTodayheart(){
        Fitness.getHistoryClient(
                this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_HEART_RATE_BPM)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total = dataSet.isEmpty()
                                        ? 0 : dataSet.getDataPoints().get(0).getValue(
                                        Field.FIELD_BPM).asFloat();
                                int roundTotal = Math.round(total);
                                // calories1.setText(String.valueOf(roundTotal)+" Cal");
                                System.out.println("##################hrt"+roundTotal);
                                System.out.println("##################hrt"+dataSet);
                                s7= String.valueOf(roundTotal);

                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        Googlefit.this,
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                //  calories1.setText(0+" Cal");

                            }
                        });
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_HEART_RATE_BPM)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(365, TimeUnit.DAYS)
                .build();


      /*  Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(readRequest)
                .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        Log.d(TAG, "onSuccess()");

                        for (Bucket bucket : dataReadResponse.getBuckets()) {
                            Log.e("History", "Data returned for Data type: " + bucket.getDataSets());

                            List<DataSet> dataSets = bucket.getDataSets();
                            for (DataSet dataSet : dataSets) {
                                //showDataSet(dataSet);
                                int p =0;
                                if (dataReadResponse.getBuckets().size() > 0) {
                                    for (Bucket bucket : dataReadResponse.getBuckets()) {
                                        DataSet dataSet = bucket.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
                                        //Loop through Bucket to get last 7 days of data
                                        //dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()
                                        p+=dataSet.isEmpty()?0:dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                                        calories.setText(String.valueOf(p));
                                    }
                                    Log.i("HElooooooooooo","K");
                                }


                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure()", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<DataReadResponse> task) {
                        Log.d(TAG, "onComplete()");
                    }
                });*/
    }

    private void showDataSet(DataSet dataSet) {
        Log.e("History", "Data returned for Data type: " + dataSet.getDataType().getName());
        DateFormat dateFormat = DateFormat.getDateInstance();
        DateFormat timeFormat = DateFormat.getTimeInstance();

        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.e("History", "Data point:");
            Log.e("History", "\tType: " + dp.getDataType().getName());
            Log.e("History", "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.e("History", "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            for(Field field : dp.getDataType().getFields()) {
                Log.e("History", "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field));
            }
        }
    }

    protected void readLastWeekStepswght() {


        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_WEIGHT)
                .setTimeRange(1, endTime, TimeUnit.MILLISECONDS)
                .setLimit(1)
                .build();


////////
        //   Log.i("HELLOooooooooooo",dataReadRequest.toString());



        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(dataReadRequest).
                addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        int p =0;
                        if (dataReadResponse.getBuckets().size() > 0) {

                            Log.i("HELLOooooooooooo", dataReadResponse.getDataSet(DataType.TYPE_WEIGHT).toString());
                        }

                    }
                });

    }
    private static DataReadRequest queryFitnessData() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_WEIGHT)
                .setTimeRange(1, endTime, TimeUnit.MILLISECONDS)
                .setLimit(1)
                .build();

        return dataReadRequest;
    }

    public void onPostClicked(){
        //creating the json object to send
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("BMI","10");
        jsonObject.addProperty("Steps","10");


        System.out.println("onfitClickedg1"+jsonObject);


        // Using the Retrofit

        Call<JsonObjectResponse> call =  RetrofitClient.getUniqInstance().getApi()
                .fitCall("Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZjAxMjgzOWRhNGVhYTYyNWY2MjVhZmVlNzNlYjE2MWUyODNhZGRiNDZjYWIzYWRlNTc4ZmZmOWYzZjZjMjdhNzM4NWFhYjBhNTQxMjI4NTYiLCJpYXQiOjE1OTI2NTYwMTcsIm5iZiI6MTU5MjY1NjAxNywiZXhwIjoxNzUwNDIyNDE3LCJzdWIiOiI2Iiwic2NvcGVzIjpbXX0.LR7KPyRc_MUVirYk0szE9L6xLUGU8vD5QOKPLY5aLcDhJjGl9wzX1vxeg9YnfQQdVyN4gLM3Bjx0hjHlhs9h28c0mgZYlsump2m4gMnFfVFm37o0ECOVhY0evrOAY8gZ-zCadAUUx8SBUYFctNnWPfu26pc68r2mP78C28_lJgUsH9YQ5wAwAjY1UgLkr-2_8HHaG-thb1mxhp98-wNXPF9G6cKnZ3jxu1pG3_9J-nm2_9yauyfyOF1DD6uwmaqocLdPGD24GBV2_QJM-_n4qIxm1ob3-I-wVAFngbEj_cQLUqNl00MkoJLLJn0RAHqNac6WVmbRhAzP37xL2n6Oq-K1aTrkRrM8l_jAUAduyplk8y-MPLOB51q9ZtwmDpTZTGZvT5giPamEfuW1JZMIq73S2LZ-QzmvbwWcP16YB0eZQbWbEgRbae-zIWaoONkj_1X7Rgf9dvupmTnN1AUdnl5Np7b-LNFNB2t03Vb2SyrWg_noFlhK4emV_wMYIsf1L8QUz41CMWRC_8EgZcPMW2BQXeLEDeJp_oG32o5fMNkdlFygekeEpz6ZILgCitvvTWZ1pAmmNOGyvAFtQZtx4ufE0rCjGGGFuRA01Jpcdvt-erXU6NgbP-mGj2kLORMYaldKKuoN3h3_TIER7N6e4-5RVszIM_ZaaxAnU2Z1Cnk", String.valueOf(jsonObject));
        call.enqueue(new Callback<JsonObjectResponse>() {

            @Override
            public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                try{
                    Log.e("response-success", response.body().getMessage());
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




    private void getTodayExSLp(){
        // Note: The android.permission.ACTIVITY_RECOGNITION permission is
// required to read DataType.TYPE_ACTIVITY_SEGMENT
      /*  SessionReadRequest request = new SessionReadRequest.Builder()
                .readSessionsFromAllApps()
                // Activity segment data is required for details of the fine-
                // granularity sleep, if it is present.
                .read(DataType.TYPE_ACTIVITY_SEGMENT)
                .setTimeInterval(1576690819, 1576750401, TimeUnit.SECONDS)
                .build();

        Task<SessionReadResponse> task = sessionClient.readSession(request);

        task.addOnSuccessListener(response -> {
            // Filter the resulting list of sessions to just those that are sleep.
            List<Session> sleepSessions = response.getSessions().stream()
                    .filter(s -> s.getActivity().equals(FitnessActivities.SLEEP))
                    .collect(Collectors.toList());

            for (Session session : sleepSessions) {
                Log.d("AppName", String.format("Sleep between %d and %d",
                        session.getStartTime(TimeUnit.SECONDS),
                        session.getEndTime(TimeUnit.SECONDS)));

                // If the sleep session has finer granularity sub-components, extract them:
                List<DataSet> dataSets = response.getDataSet(session);
                for (DataSet dataSet : dataSets) {
                    for (DataPoint point : dataSet.getDataPoints()) {
                        // The Activity defines whether this segment is light, deep, REM or awake.
                        String sleepStage = point.getValue(Field.FIELD_ACTIVITY).asActivity();
                        long start = point.getStartTime(TimeUnit.SECONDS);
                        long end = point.getEndTime(TimeUnit.SECONDS);
                        Log.d("AppName",
                                String.format("\t* %s between %d and %d", sleepStage, start, end));
                    }
                }
            }
        });*/
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();
       /* DataReadRequest readRequest = new DataReadRequest.Builder()
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .read(DataType.TYPE_ACTIVITY_SEGMENT)
                .build();*/
        SessionReadRequest readRequest = new SessionReadRequest.Builder()
                .readSessionsFromAllApps()
                // Activity segment data is required for details of the fine-
                // granularity sleep, if it is present.
                .read(DataType.TYPE_ACTIVITY_SEGMENT)
                .setTimeInterval(startTime, endTime, TimeUnit.SECONDS)
                .build();

        Fitness.getSessionsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readSession(readRequest)
                .addOnSuccessListener(new OnSuccessListener<SessionReadResponse>(){
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(SessionReadResponse sessionReadResponse) {
                        List<Session> sessions = sessionReadResponse.getSessions().stream().filter(s -> s.getActivity().equals(FitnessActivities.SLEEP))
                                .collect(Collectors.toList());
                        Log.i(TAG, "Session read was successful. Number of returned sessions is: "
                                + sessions.size());

                        s8="10";

                        for (Session session : sessions) {
                            // Process the session
                            //    dumpSession(session);
                            Log.d("AppName", String.format("Sleep between %d and %d",
                                    session.getStartTime(TimeUnit.SECONDS),
                                    session.getEndTime(TimeUnit.SECONDS)));
                            // Process the data sets for this session
                            List<DataSet> dataSets = sessionReadResponse.getDataSet(session);
                            for (DataSet dataSet : dataSets) {
                                //  dumpDataSet(dataSet);
                                for (DataPoint point : dataSet.getDataPoints()) {
                                    // The Activity defines whether this segment is light, deep, REM or awake.
                                    String sleepStage = point.getValue(Field.FIELD_ACTIVITY).asActivity();
                                    long start = point.getStartTime(TimeUnit.SECONDS);
                                    long end = point.getEndTime(TimeUnit.SECONDS);
                                    Log.d("AppName",
                                            String.format("\t* %s between %d and %d", sleepStage, start, end));
                                }
                            }
                        }
                                          /*    for (DataSet dataSet : dataSets) {
                                                  for (DataPoint dp : dataSet.getDataPoints()) {
                                                      for (Field field : dp.getDataType().getFields()){
                                                          Log.i("HElllooooooooooo", field.toString());
                                                          Log.i("HElllooooooooooo1", dataSet.toString());
                                                          Log.i("HElllooooooooooo12", FitnessActivities.SLEEP);

                                                          if(field.getName().equalsIgnoreCase(FitnessActivities.SLEEP)){
                                                              Log.i("HElllooooooooooo1", field.getName());

                                                              Toast.makeText(Googlefit.this, "ENCONTRADO", Toast.LENGTH_LONG).show();
                                                          }

                                                      //    LogUtil.d("google fit Start: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + " End: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)) + " type: " + showDataType(dp.getValue(field)));
                                                      }
                                                  }
                                                  for(Field field: dataSet.getDataType().getFields()){
                                                      if(field.getName().equalsIgnoreCase(FitnessActivities.SLEEP)){
                                                          Log.i("HElllooooooooooo", field.toString());

                                                          Toast.makeText(Googlefit.this, "ENCONTRADO", Toast.LENGTH_LONG).show();
                                                      }
                                                  }
                                              }*/
                    }
                });

     /*   Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_ACTIVITY_SEGMENT)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(365, TimeUnit.DAYS)
                .build();


        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(readRequest)
                .addOnSuccessListener(new OnSuccessListener<DataReadResponse>() {
                    @Override
                    public void onSuccess(DataReadResponse dataReadResponse) {
                        Log.d(TAG, "onSuccess()");
                        if (dataReadResponse.getBuckets().size() > 0) {
                            for (Bucket bucket : dataReadResponse.getBuckets()) {
                                DataSet dataSet = bucket.getDataSet(DataType.TYPE_ACTIVITY_SEGMENT);
                                //Loop through Bucket to get last 7 days of data
                                //dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()
                                int     p=dataSet.isEmpty()?0:dataSet.getDataPoints().get(0).getValue(Field.FIELD_ACTIVITY).asInt();
                                Log.i("HElllooooooooooo", String.valueOf(p));

                            }
                            Log.i("HElooooooooooo","K");
                        }
                        else
                        {
                            Log.i("HElooooooooooo","0");

                        }

                        for (Bucket bucket : dataReadResponse.getBuckets()) {
                            Log.e("History", "Data returned for Data type: " + bucket.getDataSets());



                            List<DataSet> dataSets = bucket.getDataSets();
                            for (DataSet dataSet : dataSets) {
                                //showDataSet(dataSet);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure()", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<DataReadResponse> task) {
                        Log.d(TAG, "onComplete()");
                    }
                });*/
      /*  Fitness.getHistoryClient(
                this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_ACTIVITY_SEGMENT)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                int total = dataSet.isEmpty()
                                        ? 0 : dataSet.getDataPoints().get(0).getValue(
                                        Field.FIELD_ACTIVITY).asInt();
                                int roundTotal = Math.round(total);
                                // calories1.setText(String.valueOf(roundTotal)+" Cal");
                                System.out.println("#############bbdud#####hrt"+dataSet);
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        Googlefit.this,
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                //  calories1.setText(0+" Cal");

                            }
                        });*/
    }
}