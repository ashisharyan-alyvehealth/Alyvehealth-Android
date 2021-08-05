package com.health.immunity.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.JsonObject;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivitySurveyGenderBinding;
import com.health.immunity.login.model.OnBoardResponse;
import com.health.immunity.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyGenderActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySurveyGenderBinding binding;
    private boolean isSelected = true;
    private boolean isGender = false;
    private String genderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_survey_gender);

        initViews();
    }

    private void initViews() {
        //  requestPermission();
    /*    binding.tvDateOfBirth.setText(getIntent().getStringExtra("dob"));
        binding.tvDateOfBirth.setOnClickListener(this);
        binding.textDOB.setOnClickListener(this);
        binding.layoutName.setOnClickListener(this);*/
        binding.ivNext.setOnClickListener(this);
        binding.tvFemale.setOnClickListener(this);
        binding.tvMale.setOnClickListener(this);
        binding.tvNonBinary.setOnClickListener(this);
        binding.tvDisclose.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        if (getIntent().getStringExtra("genderStatus") != null) {
            if (getIntent().getStringExtra("genderStatus").equalsIgnoreCase("Male")) {
                genderStatus = "Male";
                //binding.tvMale.setBackground(getResources().getDrawable(R.drawable.male_selected, null));
            } else if (getIntent().getStringExtra("genderStatus").equalsIgnoreCase("Female")) {
                genderStatus = "Female";
                //binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.english_selected, null));
            } else if (getIntent().getStringExtra("genderStatus").equalsIgnoreCase("Others")) {
                genderStatus = "Others";
                //binding.tvNonBinary.setBackground(getResources().getDrawable(R.drawable.hindi_selected, null));
                //binding.tvNonBinary.setTextColor(Color.WHITE);
            } else if (getIntent().getStringExtra("genderStatus").equalsIgnoreCase("Prefer not to choose")) {
                genderStatus = "Prefer not to choose";
                //binding.tvDisclose.setBackground(getResources().getDrawable(R.drawable.button_background, null));
                //binding.tvDisclose.setTextColor(Color.WHITE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //  case R.id.tvDateOfBirth:
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivNext:
                if (!TextUtils.isEmpty(genderStatus))
                {
                    //if (PreferenceHelper.getIntPreference(context, IConstant.IS_VERIFIEDUSER)==1)
                    {
                        binding.ivNext.setEnabled(true);
                        binding.ivNext.setBackground(getResources().getDrawable(R.drawable.gradiant, null));

                        Intent intentFemale = new Intent(context, SurveyNameActivity.class);
                        Log.e(TAG, "initViews: Gender - " + getIntent().getStringExtra("comesFrom"));
                        if (getIntent().getStringExtra("comesFrom") != null) {
                            intentFemale.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                        }
                        intentFemale.putExtra("genderStatus", genderStatus);
                        intentFemale.putExtra("dob", getIntent().getStringExtra("dob"));
                        intentFemale.putExtra("name", getIntent().getStringExtra("name"));
                        intentFemale.putExtra("email", getIntent().getStringExtra("email"));
                        intentFemale.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                        intentFemale.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                        startActivity(intentFemale);
                    }
              /*    else if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
                  {
                      Intent intentFemale = new Intent(context, OfficeAddActivity.class);
                      Log.e(TAG, "initViews: Gender - " + getIntent().getStringExtra("comesFrom"));
                      if (getIntent().getStringExtra("comesFrom") != null) {
                          intentFemale.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                      }
                      intentFemale.putExtra("genderStatus", genderStatus);
                      intentFemale.putExtra("dob", getIntent().getStringExtra("dob"));
                      intentFemale.putExtra("name", getIntent().getStringExtra("name"));
                      intentFemale.putExtra("email", getIntent().getStringExtra("email"));
                      intentFemale.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                      intentFemale.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                      startActivity(intentFemale);
                  }
                  else
                  {
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
                  }*/
                }
                else
                {
                    showToast("Please Select Gender");
                }
                break;
            case R.id.tvFemale:
             /*   binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.english_selected, null));
                binding.tvFemale.setTextColor(Color.WHITE);
                binding.tvMale.setBackground(getResources().getDrawable(R.drawable.male_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvMale.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.tvNonBinary.setBackground(getResources().getDrawable(R.drawable.hindi_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvNonBinary.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.tvDisclose.setBackground(getResources().getDrawable(R.drawable.background_language, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvDisclose.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }*/
                binding.ivNext.setEnabled(true);
                binding.ivNext.setBackground(getResources().getDrawable(R.drawable.gradiant, null));

                genderStatus = "Female";
                binding.tvFemale.setImageDrawable(getResources().getDrawable(R.drawable.femaleselected, null));
                binding.tvMale.setImageDrawable(getResources().getDrawable(R.drawable.maledeselect, null));

                binding.tvNonBinary.setBackgroundTintList(getResources().getColorStateList(R.color.colorBG));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvNonBinary.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }

              /*  if (PreferenceHelper.getIntPreference(context, IConstant.IS_VERIFIEDUSER)==1)
                {
                    Intent intentFemale = new Intent(context, OfficeAddActivity.class);
                    Log.e(TAG, "initViews: Gender - " + getIntent().getStringExtra("comesFrom"));
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intentFemale.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intentFemale.putExtra("genderStatus", genderStatus);
                    intentFemale.putExtra("dob", getIntent().getStringExtra("dob"));
                    intentFemale.putExtra("name", getIntent().getStringExtra("name"));
                    intentFemale.putExtra("email", getIntent().getStringExtra("email"));
                    intentFemale.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intentFemale.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intentFemale);
                }
                else if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
                {
                    Intent intentFemale = new Intent(context, OfficeAddActivity.class);
                    Log.e(TAG, "initViews: Gender - " + getIntent().getStringExtra("comesFrom"));
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intentFemale.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intentFemale.putExtra("genderStatus", genderStatus);
                    intentFemale.putExtra("dob", getIntent().getStringExtra("dob"));
                    intentFemale.putExtra("name", getIntent().getStringExtra("name"));
                    intentFemale.putExtra("email", getIntent().getStringExtra("email"));
                    intentFemale.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intentFemale.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intentFemale);
                }
                else
                {
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
                }*/

                break;
            case R.id.tvMale:
               /* binding.tvMale.setBackground(getResources().getDrawable(R.drawable.male_selected, null));
                binding.tvMale.setTextColor(Color.WHITE);

                binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.english_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvFemale.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.tvNonBinary.setBackground(getResources().getDrawable(R.drawable.hindi_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvNonBinary.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.tvDisclose.setBackground(getResources().getDrawable(R.drawable.background_language, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvDisclose.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
          */    genderStatus = "Male";
                binding.tvFemale.setImageDrawable(getResources().getDrawable(R.drawable.femaledeselect, null));
                binding.tvMale.setImageDrawable(getResources().getDrawable(R.drawable.maleselected, null));

                binding.tvNonBinary.setBackgroundTintList(getResources().getColorStateList(R.color.colorBG));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvNonBinary.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.ivNext.setEnabled(true);
                binding.ivNext.setBackground(getResources().getDrawable(R.drawable.gradiant, null));


              /*  if (PreferenceHelper.getIntPreference(context, IConstant.IS_VERIFIEDUSER)==1)
                {
                    Intent intentMale = new Intent(context, OfficeAddActivity.class);
                    Log.e(TAG, "initViews: Gender - " + getIntent().getStringExtra("comesFrom"));
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intentMale.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intentMale.putExtra("genderStatus", genderStatus);
                    intentMale.putExtra("dob", getIntent().getStringExtra("dob"));
                    intentMale.putExtra("name", getIntent().getStringExtra("name"));
                    intentMale.putExtra("email", getIntent().getStringExtra("email"));
                    intentMale.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intentMale.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intentMale);
                }
                else if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
                {
                    Intent intentMale = new Intent(context, OfficeAddActivity.class);
                    Log.e(TAG, "initViews: Gender - " + getIntent().getStringExtra("comesFrom"));
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intentMale.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intentMale.putExtra("genderStatus", genderStatus);
                    intentMale.putExtra("dob", getIntent().getStringExtra("dob"));
                    intentMale.putExtra("name", getIntent().getStringExtra("name"));
                    intentMale.putExtra("email", getIntent().getStringExtra("email"));
                    intentMale.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intentMale.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intentMale);
                }
                else
                {
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
                }*/

                break;
            case R.id.tvNonBinary:
                binding.ivNext.setEnabled(true);
                binding.ivNext.setBackground(getResources().getDrawable(R.drawable.gradiant, null));

                binding.tvNonBinary.setBackgroundTintList(getResources().getColorStateList(R.color.othercolor));
                binding.tvNonBinary.setTextColor(Color.WHITE);
                genderStatus = "Others";
                binding.tvFemale.setImageDrawable(getResources().getDrawable(R.drawable.femaledeselect, null));
                binding.tvMale.setImageDrawable(getResources().getDrawable(R.drawable.maledeselect, null));

                 /* binding.tvMale.setBackground(getResources().getDrawable(R.drawable.male_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvMale.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.english_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvFemale.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.tvDisclose.setBackground(getResources().getDrawable(R.drawable.background_language, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvDisclose.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                */

               /* if (PreferenceHelper.getIntPreference(context, IConstant.IS_VERIFIEDUSER)==1)
                {
                    Intent intentBinary = new Intent(context, OfficeAddActivity.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intentBinary.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intentBinary.putExtra("dob", getIntent().getStringExtra("dob"));
                    intentBinary.putExtra("name", getIntent().getStringExtra("name"));
                    intentBinary.putExtra("genderStatus", genderStatus);
                    intentBinary.putExtra("email", getIntent().getStringExtra("email"));
                    intentBinary.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intentBinary.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intentBinary);
                }
                else if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
                {
                    Intent intentBinary = new Intent(context, OfficeAddActivity.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intentBinary.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intentBinary.putExtra("dob", getIntent().getStringExtra("dob"));
                    intentBinary.putExtra("name", getIntent().getStringExtra("name"));
                    intentBinary.putExtra("genderStatus", genderStatus);
                    intentBinary.putExtra("email", getIntent().getStringExtra("email"));
                    intentBinary.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intentBinary.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intentBinary);
                }
                else
                {
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
                }*/

                break;
            case R.id.tvDisclose:
               /* binding.tvDisclose.setBackground(getResources().getDrawable(R.drawable.button_background, null));
                binding.tvDisclose.setTextColor(Color.WHITE);

                binding.tvMale.setBackground(getResources().getDrawable(R.drawable.male_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvMale.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.english_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvFemale.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                binding.tvNonBinary.setBackground(getResources().getDrawable(R.drawable.hindi_non_selected, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvNonBinary.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }*/
                genderStatus = "Prefer not to choose";
                binding.tvFemale.setImageDrawable(getResources().getDrawable(R.drawable.femaledeselect, null));
                binding.tvMale.setImageDrawable(getResources().getDrawable(R.drawable.maledeselect, null));
                binding.ivNext.setEnabled(true);
                binding.ivNext.setBackground(getResources().getDrawable(R.drawable.gradiant, null));

                binding.tvNonBinary.setBackgroundTintList(getResources().getColorStateList(R.color.colorBG));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvNonBinary.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                }
                Intent intentFemale = new Intent(context, SurveyNameActivity.class);
                Log.e(TAG, "initViews: Gender - " + getIntent().getStringExtra("comesFrom"));
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intentFemale.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intentFemale.putExtra("genderStatus", genderStatus);
                intentFemale.putExtra("dob", getIntent().getStringExtra("dob"));
                intentFemale.putExtra("name", getIntent().getStringExtra("name"));
                intentFemale.putExtra("email", getIntent().getStringExtra("email"));
                intentFemale.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                intentFemale.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                startActivity(intentFemale);
                /*if (PreferenceHelper.getIntPreference(context, IConstant.IS_VERIFIEDUSER)==1)
                {
                    Intent intentDisclose = new Intent(context, OfficeAddActivity.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intentDisclose.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intentDisclose.putExtra("genderStatus", genderStatus);
                    intentDisclose.putExtra("dob", getIntent().getStringExtra("dob"));
                    intentDisclose.putExtra("name", getIntent().getStringExtra("name"));
                    intentDisclose.putExtra("email", getIntent().getStringExtra("email"));
                    intentDisclose.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intentDisclose.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intentDisclose);
                }
                else if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
                {
                    Intent intentDisclose = new Intent(context, OfficeAddActivity.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intentDisclose.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intentDisclose.putExtra("genderStatus", genderStatus);
                    intentDisclose.putExtra("dob", getIntent().getStringExtra("dob"));
                    intentDisclose.putExtra("name", getIntent().getStringExtra("name"));
                    intentDisclose.putExtra("email", getIntent().getStringExtra("email"));
                    intentDisclose.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intentDisclose.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intentDisclose);
                }
                else
                {
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
                }*/

                break;
        }
    }





    private void hrOnBoardApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", getIntent().getStringExtra("email"));
        jsonObject.addProperty("mobile", getIntent().getStringExtra("mobileNumber"));
        jsonObject.addProperty("access_code", getIntent().getStringExtra("accessCode"));
        jsonObject.addProperty("name", getIntent().getStringExtra("name"));
        jsonObject.addProperty("dob", getIntent().getStringExtra("dob"));
        jsonObject.addProperty("gender", genderStatus);
        jsonObject.addProperty("home_address", getIntent().getStringExtra("homeLocation"));
        jsonObject.addProperty("office_address", getIntent().getStringExtra("officeLocation"));
        jsonObject.addProperty("home_lat", getIntent().getStringExtra("homelatitude"));
        jsonObject.addProperty("home_lon", getIntent().getStringExtra("homelongitude"));
        jsonObject.addProperty("office_lat", getIntent().getStringExtra("officelatitude"));
        jsonObject.addProperty("office_lon", getIntent().getStringExtra("officelongitude"));
        jsonObject.addProperty("google_healthkit_status", PreferenceHelper.getStringPreference(context, IConstant.FIT_STATUS_BACK));
        jsonObject.addProperty("question", "");

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
                                showToast(response.body().getMessage());
                                Log.e(TAG, "onResponse: +Success");
                                PreferenceHelper.setBooleanPreference(context, IConstant.IS_ONBOARD, true);
                                PreferenceHelper.setBooleanPreference(context, IConstant.IS_CHECKIN, false);
                                PreferenceHelper.setStringPreference(context, IConstant.USER_NAME, response.body().getJsonData().getName());
                                PreferenceHelper.setStringPreference(context, IConstant.TOKEN, response.body().getJsonData().getToken());
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", getIntent().getStringExtra("email"));
        jsonObject.addProperty("mobile", getIntent().getStringExtra("mobileNumber"));
        jsonObject.addProperty("access_code", getIntent().getStringExtra("accessCode"));
        jsonObject.addProperty("name", getIntent().getStringExtra("name"));
        jsonObject.addProperty("dob", getIntent().getStringExtra("dob"));
        jsonObject.addProperty("gender", genderStatus);
        jsonObject.addProperty("home_address", getIntent().getStringExtra("homeLocation"));
        jsonObject.addProperty("office_address", getIntent().getStringExtra("officeLocation"));
        jsonObject.addProperty("home_lat", getIntent().getStringExtra("homelatitude"));
        jsonObject.addProperty("home_lon", getIntent().getStringExtra("homelongitude"));
        jsonObject.addProperty("office_lat", getIntent().getStringExtra("officelatitude"));
        jsonObject.addProperty("office_lon", getIntent().getStringExtra("officelongitude"));
        jsonObject.addProperty("google_healthkit_status", PreferenceHelper.getStringPreference(context, IConstant.FIT_STATUS_BACK));
        jsonObject.addProperty("question", "");

        CommonUtils.showSpotoProgressDialog(context);
        Call<OnBoardResponse> call = RetrofitClient.getUniqInstance().getApi().onBoardCall(jsonObject);
        call.enqueue(new Callback<OnBoardResponse>() {
            @Override
            public void onResponse(Call<OnBoardResponse> call, Response<OnBoardResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("true")) {
                                showToast(response.body().getMessage());
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
}
