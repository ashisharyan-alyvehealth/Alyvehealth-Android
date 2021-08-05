package com.health.immunity.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivityGetOnBoardBinding;
import com.health.immunity.login.model.OnBoardResponse;
import com.health.immunity.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetOnBoard extends BaseActivity implements View.OnClickListener {
    ActivityGetOnBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Reached Onboard");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_get_on_board);
        initViews();
    }

    private void initViews() {
        binding.ivNext.setOnClickListener(this);
        binding.btnhelp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:

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

            case R.id.btnhelp:
                Intent intent1 = new Intent(context, SurveyDOBActivity.class);
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intent1.putExtra("name", getIntent().getStringExtra("name"));
                intent1.putExtra("email", getIntent().getStringExtra("email"));
                intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                startActivity(intent1);
                break;
        }
    }

    private void onBoardApi() {
        System.out.println("Reached Onboardapi");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("personal_email", getIntent().getStringExtra("email"));
        jsonObject.addProperty("mobile", getIntent().getStringExtra("mobileNumber"));
        jsonObject.addProperty("name", getIntent().getStringExtra("name"));
        jsonObject.addProperty("gender", getIntent().getStringExtra("genderStatus"));
        jsonObject.addProperty("isd_code", "+91");

        //jsonObject.addProperty("dob", getIntent().getStringExtra("dob"));
        //jsonObject.addProperty("access_code", getIntent().getStringExtra("accessCode"));
        //jsonObject.addProperty("home_address", getIntent().getStringExtra("homeLocation"));
        //jsonObject.addProperty("office_address", getIntent().getStringExtra("officeLocation"));
        //jsonObject.addProperty("home_lat", getIntent().getStringExtra("homelatitude"));
        //jsonObject.addProperty("home_lon", getIntent().getStringExtra("homelongitude"));
        //jsonObject.addProperty("office_lat", getIntent().getStringExtra("officelatitude"));
        //jsonObject.addProperty("office_lon", getIntent().getStringExtra("officelongitude"));
        //jsonObject.addProperty("google_healthkit_status", PreferenceHelper.getStringPreference(context, IConstant.FIT_STATUS_BACK));
        //jsonObject.addProperty("question", "");

        CommonUtils.showSpotoProgressDialog(context);
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
    private void hrOnBoardApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("personal_email", getIntent().getStringExtra("email"));
        jsonObject.addProperty("mobile", getIntent().getStringExtra("mobileNumber"));
        jsonObject.addProperty("name", getIntent().getStringExtra("name"));
        jsonObject.addProperty("gender", getIntent().getStringExtra("genderStatus"));
        jsonObject.addProperty("isd_code", "+91");

        /*jsonObject.addProperty("dob", getIntent().getStringExtra("dob"));
        jsonObject.addProperty("access_code", getIntent().getStringExtra("accessCode"));
        jsonObject.addProperty("home_address", getIntent().getStringExtra("homeLocation"));
        jsonObject.addProperty("office_address", getIntent().getStringExtra("officeLocation"));
        jsonObject.addProperty("home_lat", getIntent().getStringExtra("homelatitude"));
        jsonObject.addProperty("home_lon", getIntent().getStringExtra("homelongitude"));
        jsonObject.addProperty("office_lat", getIntent().getStringExtra("officelatitude"));
        jsonObject.addProperty("office_lon", getIntent().getStringExtra("officelongitude"));
        jsonObject.addProperty("google_healthkit_status", PreferenceHelper.getStringPreference(context, IConstant.FIT_STATUS_BACK));
        jsonObject.addProperty("question", "");
*/
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
                                // PreferenceHelper.setStringPreference(context, IConstant.TOKEN, response.body().getJsonData().getToken());
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

}
