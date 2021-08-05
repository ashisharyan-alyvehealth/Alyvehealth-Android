package com.health.immunity.login.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivityHomeAddBinding;
import com.health.immunity.login.model.OnBoardResponse;
import com.health.immunity.profile.MapActivity;
import com.health.immunity.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeAddActivity extends BaseActivity implements View.OnClickListener {

    private ActivityHomeAddBinding binding;
    private String latitude, longitude, city, strCurrentLatitude, strCurrentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_home_add);
        initViews();
    }

    private void initViews() {
        //  requestPermission();
        //  binding.layoutName.setOnClickListener(this);
        binding.etLocation.setOnClickListener(this);
        binding.ivNext.setOnClickListener(this);
        binding.textskip.setOnClickListener(this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //   binding.etOffLocation.setText(getIntent().getStringExtra("officeLocation"));
//        binding.etLocation.setAdapter(new PlaceAutoSuggestAdapter(context, R.layout.list_item));

      /*  binding.etLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                        showToast("Please enter Home Address");
                    } else {
                        Intent intent1 = new Intent(context, HealthConditionActivity.class);
                        if (getIntent().getStringExtra("comesFrom") != null) {
                            intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                        }
                        intent1.putExtra("homelongitude", longitude);
                        intent1.putExtra("homelatitude", latitude);
                        intent1.putExtra("officelatitude", getIntent().getStringExtra("officelatitude"));
                        intent1.putExtra("officelongitude", getIntent().getStringExtra("officelongitude"));
                        intent1.putExtra("homeLocation", binding.etLocation.getText().toString());
                        intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                        intent1.putExtra("name", getIntent().getStringExtra("name"));
                        intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                        intent1.putExtra("officeLocation", getIntent().getStringExtra("officeLocation"));
                        intent1.putExtra("email", getIntent().getStringExtra("email"));
                        intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                        intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                        Log.e(TAG, "onEditorAction: Home Addreess" + getIntent().getStringExtra("latitude"));
                        startActivity(intent1);
                    }
                }
                return false;
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textskip:
                if (CommonUtils.isInternetAvail(context)) {
                    Log.e(TAG, "onClickComesFrom: " + getIntent().getStringExtra("comesFrom"));
                    if (!TextUtils.isEmpty(getIntent().getStringExtra("comesFrom") ))
                    {
                        if (getIntent().getStringExtra("comesFrom").equalsIgnoreCase("HRPrefilled"))
                        {
                            hrOnBoardApi();
                        }
                    }
                    else
                    {
                        onBoardApi();
                    }
                }
                break;
            case R.id.ivNext:
                /*if (PreferenceHelper.getIntPreference(context, IConstant.IS_VERIFIEDUSER)==1 )
                {*/
                   /* if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                        showToast("Please enter home address");
                    } else*/ {
//                    reverseGeocode(binding.etLocation.getText().toString());
                if (CommonUtils.isInternetAvail(context)) {
                    Log.e(TAG, "onClickComesFrom: " + getIntent().getStringExtra("comesFrom"));
                    if (!TextUtils.isEmpty(getIntent().getStringExtra("comesFrom") ))
                    {
                        if (getIntent().getStringExtra("comesFrom").equalsIgnoreCase("HRPrefilled"))
                        {
                            hrOnBoardApi();
                        }
                    }
                    else
                    {
                        onBoardApi();
                    }
                }
                       /* Intent intent1 = new Intent(context, HealthConditionActivity.class);
                        if (getIntent().getStringExtra("comesFrom") != null) {
                            intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                        }
                        intent1.putExtra("homelongitude", longitude);
                        intent1.putExtra("homelatitude", latitude);
                        intent1.putExtra("homeLocation", binding.etLocation.getText().toString());
                        intent1.putExtra("officeLocation", getIntent().getStringExtra("officeLocation"));
                        intent1.putExtra("officelatitude", getIntent().getStringExtra("officelatitude"));
                        intent1.putExtra("officelongitude", getIntent().getStringExtra("officelongitude"));
                        intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                        intent1.putExtra("name", getIntent().getStringExtra("name"));
                        intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                        intent1.putExtra("email", getIntent().getStringExtra("email"));
                        intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                        startActivity(intent1);*/
            }
              /*  }
                else if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
                {
                    if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                        showToast("Please enter home address");
                    } else {
//                    reverseGeocode(binding.etLocation.getText().toString());
                        Intent intent1 = new Intent(context, HealthConditionActivity.class);
                        if (getIntent().getStringExtra("comesFrom") != null) {
                            intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                        }
                        intent1.putExtra("homelongitude", longitude);
                        intent1.putExtra("homelatitude", latitude);
                        intent1.putExtra("officelatitude", getIntent().getStringExtra("officelatitude"));
                        intent1.putExtra("officelongitude", getIntent().getStringExtra("officelongitude"));
                        intent1.putExtra("homeLocation", binding.etLocation.getText().toString());
                        intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                        intent1.putExtra("name", getIntent().getStringExtra("name"));
                        intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                        intent1.putExtra("officeLocation", getIntent().getStringExtra("officeLocation"));
                        intent1.putExtra("email", getIntent().getStringExtra("email"));
                        intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                        intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                        startActivity(intent1);
                    }
                }
                else
                {
                    Intent intent1 = new Intent(context, HealthConditionActivity.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intent1.putExtra("homelongitude", longitude);
                    intent1.putExtra("homelatitude", latitude);
                    intent1.putExtra("officelatitude", getIntent().getStringExtra("officelatitude"));
                    intent1.putExtra("officelongitude", getIntent().getStringExtra("officelongitude"));
                    intent1.putExtra("homeLocation", binding.etLocation.getText().toString());
                    intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                    intent1.putExtra("name", getIntent().getStringExtra("name"));
                    intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                    intent1.putExtra("officeLocation", getIntent().getStringExtra("officeLocation"));
                    intent1.putExtra("email", getIntent().getStringExtra("email"));
                    intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intent1);
                }*/
            break;
            case R.id.etLocation:

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {

                    askForPermissionnew();
                   /* AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_profile, null);
                    Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                    Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
                    TextView textView = (TextView) dialogView.findViewById(R.id.textView);
                    textView.setText("Please provide location access to 'Immunity Health' in settings");

                    button2.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view) {


                            dialogBuilder.dismiss();

                        }
                    });
                    button1.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view) {

                            dialogBuilder.dismiss();

                            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));


                        }
                    });

                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();*/
                }
                else
                {
                    Intent mapIntent = new Intent(context, MapActivity.class);
                    startActivityForResult(mapIntent, ADDRESS_CAPTURE);
                }

/*//                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(context, MapActivity.class);
//                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent, ADDRESS_CAPTURE);*/

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_CAPTURE) {
            if (resultCode == RESULT_OK) {
                String address = data.getStringExtra("address");
                latitude = data.getStringExtra("latitude");
                longitude = data.getStringExtra("longitude");
//                LatLng location = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                Log.e(TAG, "onActivityResult: Home Latitue " + latitude);
                Log.e(TAG, "onActivityResult: " + address);
                if (latitude != null) {
                    binding.etLocation.setText(address);
                } else {
                    Log.e(TAG, "onActivityResult: City -- = " + city);
                    binding.etLocation.setText(city);
                    latitude = strCurrentLatitude;
                    longitude = strCurrentLongitude;
                }
            }
        }
        else if (requestCode == LOCATION_REQUEST_CODE) {

            Intent mapIntent = new Intent(context, MapActivity.class);
            startActivityForResult(mapIntent, ADDRESS_CAPTURE);


        }
        else if (requestCode == 7) {

            Intent mapIntent = new Intent(context, MapActivity.class);
            startActivityForResult(mapIntent, ADDRESS_CAPTURE);


        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        //getDeviceLocation();
    }



    private void hrOnBoardApi() {

        String datestr="";

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            Date newDate = format.parse(getIntent().getStringExtra("dob"));
            format = new SimpleDateFormat("yyyy-MM-dd");
            datestr = format.format(newDate);
            Log.e(TAG, "onBindViewHolder: Date - " + datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("personal_email", getIntent().getStringExtra("email"));
        jsonObject.addProperty("mobile", getIntent().getStringExtra("mobileNumber"));
        jsonObject.addProperty("name", getIntent().getStringExtra("name"));
        jsonObject.addProperty("dob", datestr);
        jsonObject.addProperty("gender", getIntent().getStringExtra("genderStatus"));
        jsonObject.addProperty("office_address", getIntent().getStringExtra("officeLocation"));
        jsonObject.addProperty("office_lat", getIntent().getStringExtra("officelatitude"));
        jsonObject.addProperty("office_lon", getIntent().getStringExtra("officelongitude"));
        jsonObject.addProperty("home_address", binding.etLocation.getText().toString());
        jsonObject.addProperty("home_lat",latitude );
        jsonObject.addProperty("home_lon",longitude );
        jsonObject.addProperty("isd_code", "+91");

        jsonObject.addProperty("google_healthkit_status", PreferenceHelper.getStringPreference(context, IConstant.FIT_STATUS_BACK));

        CommonUtils.showSpotoProgressDialog(context);
        Call<OnBoardResponse> call = RetrofitClient.getUniqInstance().getApi()
                .hrOnBoardCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), jsonObject);
        call.enqueue(new Callback<OnBoardResponse>() {
            @Override
            public void onResponse(Call<OnBoardResponse> call, Response<OnBoardResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("true")) {
                                //showToast(response.body().getMessage());
                                Log.e(TAG, "onResponse: +Success");
                                PreferenceHelper.setBooleanPreference(context, IConstant.IS_ONBOARD, true);
                                PreferenceHelper.setBooleanPreference(context, IConstant.IS_CHECKIN, false);
                                PreferenceHelper.setStringPreference(context, IConstant.USER_NAME, response.body().getJsonData().getName());
                                //PreferenceHelper.setStringPreference(context, IConstant.TOKEN, response.body().getJsonData().getToken());
                                PreferenceHelper.setStringPreference(context, IConstant.MOBILENO, response.body().getJsonData().getPhoneNumber());
//                                PreferenceHelper.setStringPreference(context, IConstant.USER_NAME, response.body().getJsonData().getName());
                                //            deviceApi(response.body().getJsonData().getPhoneNumber());

                                if (!TextUtils.isEmpty(response.body().getJsonData().getHr_id()))
                                {
                                    PreferenceHelper.setBooleanPreference(context, IConstant.IS_HR_ONBOARD, true);

                                    Intent intent = new Intent(context, WelcomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else
                                {
                                    PreferenceHelper.setBooleanPreference(context, IConstant.IS_HR_ONBOARD, false);

                                    Intent intent = new Intent(context, WelcomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }


                            } else {
                                Log.e(TAG, "onResponse: +Failure");
                                CommonUtils.showCustomDialog(context, response.body().getMessage() + "\n Either your mobile or email already exists", IConstant.OKAY);
                            }
                        }
                    } else {
                        showToast(SERVER_ERROR);
                    }
                } else {
                    showToast(SERVER_ERROR);
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<OnBoardResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "onFailure: " + t.getMessage());
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }

    private void onBoardApi() {
        String datestr="";

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            Date newDate = format.parse(getIntent().getStringExtra("dob"));
            format = new SimpleDateFormat("yyyy-MM-dd");
            datestr = format.format(newDate);
            Log.e(TAG, "onBindViewHolder: Date - " + datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("personal_email", getIntent().getStringExtra("email"));
        jsonObject.addProperty("mobile", getIntent().getStringExtra("mobileNumber"));
        jsonObject.addProperty("name", getIntent().getStringExtra("name"));
        jsonObject.addProperty("dob", datestr);
        jsonObject.addProperty("gender", getIntent().getStringExtra("genderStatus"));
        jsonObject.addProperty("office_address", getIntent().getStringExtra("officeLocation"));
        jsonObject.addProperty("office_lat", getIntent().getStringExtra("officelatitude"));
        jsonObject.addProperty("office_lon", getIntent().getStringExtra("officelongitude"));
        jsonObject.addProperty("home_address", binding.etLocation.getText().toString());
        jsonObject.addProperty("home_lat",latitude );
        jsonObject.addProperty("home_lon",longitude );
        jsonObject.addProperty("isd_code", "+91");

        jsonObject.addProperty("google_healthkit_status", PreferenceHelper.getStringPreference(context, IConstant.FIT_STATUS_BACK));

//        CommonUtils.showSpotoProgressDialog(context);
        Call<OnBoardResponse> call = RetrofitClient.getUniqInstance().getApi().onBoardCall(jsonObject);
        call.enqueue(new Callback<OnBoardResponse>() {
            @Override
            public void onResponse(Call<OnBoardResponse> call, Response<OnBoardResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("true")) {
                                //showToast(response.body().getMessage());
                                Log.e(TAG, "onResponse: +Success");
                                PreferenceHelper.setBooleanPreference(context, IConstant.IS_ONBOARD, true);
                                PreferenceHelper.setBooleanPreference(context, IConstant.IS_CHECKIN, false);
                                PreferenceHelper.setStringPreference(context, IConstant.USER_NAME, response.body().getJsonData().getName());
                                PreferenceHelper.setStringPreference(context, IConstant.TOKEN, response.body().getJsonData().getToken());
                                PreferenceHelper.setStringPreference(context, IConstant.MOBILENO, response.body().getJsonData().getPhoneNumber());
//                                PreferenceHelper.setStringPreference(context, IConstant.USER_NAME, response.body().getJsonData().getName());
                                //            deviceApi(response.body().getJsonData().getPhoneNumber());
                               /* Intent intent = new Intent(context, DashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/


                                if (!TextUtils.isEmpty(response.body().getJsonData().getHr_id()))
                                {
                                    PreferenceHelper.setBooleanPreference(context, IConstant.IS_HR_ONBOARD, true);

                                    Intent intent = new Intent(context, WelcomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else
                                {
                                    PreferenceHelper.setBooleanPreference(context, IConstant.IS_HR_ONBOARD, false);

                                    Intent intent = new Intent(context, WelcomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }

                            } else {
                                Log.e(TAG, "onResponse: +Failure");
                                CommonUtils.showCustomDialog(context, response.body().getMessage() + "\n Either your mobile or email already exists", IConstant.OKAY);
                            }
                        }
                    } else {
                        showToast(SERVER_ERROR);
                    }
                } else {
                    showToast(SERVER_ERROR);
                }
              //  CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<OnBoardResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "onFailure: " + t.getMessage());
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }

    private void askForPermissionnew() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                Intent mapIntent = new Intent(context, MapActivity.class);
                startActivityForResult(mapIntent, ADDRESS_CAPTURE);
            }

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        7);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case LOCATION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        Intent mapIntent = new Intent(context, MapActivity.class);
                        startActivityForResult(mapIntent, ADDRESS_CAPTURE);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.


                }
                break;


        }
    }

}

