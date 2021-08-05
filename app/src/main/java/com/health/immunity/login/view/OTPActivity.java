package com.health.immunity.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivityOTPBinding;
import com.health.immunity.login.model.MobileOTPModel;
import com.health.immunity.login.model.OnBoardResponse;
import com.health.immunity.login.presenter.IloginPresenter;
import com.health.immunity.login.presenter.LoginPresenter;
import com.health.immunity.retrofit.RetrofitClient;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends BaseActivity implements Ilogin, OnOtpCompletionListener,View.OnClickListener {
    ActivityOTPBinding binding;
    OtpView otp;
    IloginPresenter presenter;
    public static String phoneNum=" ";
    ImageView imageView;
    TextView etEmail;
    String lastFourDigits = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_o_t_p);
        presenter= new LoginPresenter(this);
        otp= findViewById(R.id.linearOTP);
        etEmail=findViewById(R.id.etEmail);
        //getting phone number from the Login Activity
        Intent intent=getIntent();
        phoneNum=intent.getStringExtra("Phonenumber");

        //clickLogin
        otp.setOtpCompletionListener(this);
        imageView=findViewById(R.id.ivback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
         if (phoneNum.length() > 4)
            {
                lastFourDigits = phoneNum.substring(phoneNum.length() - 3);
            }
            else
            {
                lastFourDigits = phoneNum;
            }
            System.out.println(lastFourDigits);
         binding.etEmail.setText("OTP sent to *******"+lastFourDigits);
        binding.tvResend.setOnClickListener(this);
        binding.tvResend.setEnabled(false);
        binding.tvResend.setTextColor(getResources().getColor(R.color.colorGrey));
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        binding.tvResend.setEnabled(true); // The button is enabled by the timer after 5 seconds //
                        binding.tvResend.setTextColor(getResources().getColor(R.color.headingcolor));

                    }
                });
            }
        }, 60000);

    }

    @Override
    public void onSuccess(String message) {
        showToast(message);
    }

    @Override
    public void onError(String message) {
        showToast(message);
    }

    @Override
    public void saveDataToPreferenceAndJumpToHome(String hrid, String token, String phone, int isVerifiedCommunity, String onboardingStatus, String name, String dateOfBirth, String gender, String email) {
        saveDataToPreferenceHelper(hrid,token,phone,isVerifiedCommunity,onboardingStatus,name,dateOfBirth,gender,email);

    }

    @Override
    public void onOtpCompleted(String otp) {
     // presenter.doLogin(phoneNum,otp,context);
        verifyMobileOTPApi(phoneNum,otp,context);
    }


    private void saveDataToPreferenceHelper (String hrid,String token,String phone,int isVerifiedComm,String onBoardingStatus,
                                             String name, String dateOfBirth,String gender,String email){

        if (!TextUtils.isEmpty(hrid))
        {
            PreferenceHelper.setBooleanPreference(context, IConstant.IS_HR_ONBOARD, true);

        }
        else
        {
            PreferenceHelper.setBooleanPreference(context, IConstant.IS_HR_ONBOARD, false);
        }


        PreferenceHelper.setBooleanPreference(context, IConstant.IS_LOGIN, true);
        PreferenceHelper.setStringPreference(context, IConstant.TOKEN, token);
        PreferenceHelper.setStringPreference(context, IConstant.MOBILENO, phone);


        PreferenceHelper.setIntPreference(context, IConstant.IS_VERIFIEDUSER, isVerifiedComm);


        Log.e(TAG, "onResponse: OTP Activity " + phone);
        if (onBoardingStatus.equalsIgnoreCase("1")/* && response.body().getToday_status().equalsIgnoreCase("true")*/)
        {

            if (isVerifiedComm==1)
            {
                checkIn(token);

            }
            else
            {
                Intent intent1 = new Intent(context, HomeActivity.class);
                intent1.putExtra("mobileNumber", OTPActivity.phoneNum);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        } else {
            PreferenceHelper.setBooleanPreference(context, IConstant.IS_LOGIN, false);
            PreferenceHelper.setStringPreference(context, IConstant.TOKEN, token);
            Log.e(TAG, "onResponse: " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
            Intent intent1 = new Intent(context, DummyTncActivity.class);
//                                Intent intent1 = new Intent(context, ProximitySymptom.class);
            intent1.putExtra("comesFrom", "HRPrefilled");
            if ( name!= null) {
                intent1.putExtra("name", name);
            } else {
                intent1.putExtra("name", "");
            }
            if (dateOfBirth != null) {
                intent1.putExtra("dob", dateOfBirth);
            } else {
                intent1.putExtra("dob", "");
            }
            if ( gender!= null) {
                intent1.putExtra("genderStatus", gender);
            } else {
                intent1.putExtra("genderStatus", "");
            }
            if ( email!= null) {
                intent1.putExtra("email", email);
            } else {
                intent1.putExtra("email", "");
            }
            intent1.putExtra("mobileNumber", phone);
            startActivity(intent1);
//                                checkInApi(response.body().getJsonData().getToken());
        }
    }

    private void checkIn(String token) {
        Call<OnBoardResponse> call = RetrofitClient.getUniqInstance().getApi().checkInStatusCall("Bearer " + token);
        call.enqueue(new Callback<OnBoardResponse>() {
            @Override
            public void onResponse(Call<OnBoardResponse> call, Response<OnBoardResponse> response) {
                if (response.body() != null) {
                    Intent intent1 = new Intent(context, HomeActivity.class);
                    intent1.putExtra("mobileNumber", OTPActivity.phoneNum);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }
            }

            @Override
            public void onFailure(Call<OnBoardResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



    private void verifyMobileOTPApi(String phone, String otp, Context context1) {

        Call<MobileOTPModel> call = RetrofitClient.getUniqInstance().getApi().mobileOtpCall(phone,otp);
        call.enqueue(new Callback<MobileOTPModel>() {
            @Override
            public void onResponse(Call<MobileOTPModel> call, Response<MobileOTPModel> response) {
                if (response.body() != null) {

                    if (response.body().getStatus().equalsIgnoreCase("true")) {

                        if (response.body().getLogin().equalsIgnoreCase("true")) {
                            saveDataToPreferenceAndJumpToHome(response.body().getJsonData().getHrId(),response.body().getJsonData().getToken(),response.body().getJsonData().getPhoneNumber(),
                                    response.body().getJsonData().getIs_verified_community(),response.body().getJsonData().getOnboarding_status(),
                                    response.body().getJsonData().getName(),response.body().getJsonData().getDateOfBirth(),response.body().getJsonData().getGender(),
                                    response.body().getJsonData().getEmail());
                        } else {


                            Intent intent1 = new Intent(context, DummyTncActivity.class);
                            intent1.putExtra("comesFrom", "");
                            intent1.putExtra("mobileNumber", OTPActivity.phoneNum);
                            startActivity(intent1);
                        }
                    } else {

//                        CommonUtils.showDialog(context, "Kindly re-enter the valid\n"+"OTP received on\n"+
//                                "Mobile number - *******"+lastFourDigits, "OK");
                    }
                } else {
                    showToast(SERVER_ERROR);
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<MobileOTPModel> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvResend:
                otp.setText("");

                if (CommonUtils.isInternetAvail(context)) {
                    binding.tvResend.setTextColor(getResources().getColor(R.color.colorGrey));
                    binding.tvResend.setEnabled(false);
                    presenter.sendOtp(phoneNum);

                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    binding.tvResend.setEnabled(true);
                                    binding.tvResend.setTextColor(getResources().getColor(R.color.headingcolor));

                                    // The button is enabled by the timer after 5 seconds //


                                }
                            });
                        }
                    }, 60000); // Set your time period here //

                }


        }



    }
}