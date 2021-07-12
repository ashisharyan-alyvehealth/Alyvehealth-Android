package com.health.immunity.login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.health.immunity.BaseActivity;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.login.presenter.IloginPresenter;
import com.health.immunity.login.presenter.LoginPresenter;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

public class OTPActivity extends BaseActivity implements Ilogin, OnOtpCompletionListener {
    OtpView otp;
    IloginPresenter presenter;
    public static String phoneNum=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        presenter= new LoginPresenter(this);
        otp= findViewById(R.id.linearOTP);

        //getting phone number from the Login Activity
        Intent intent=getIntent();
        phoneNum=intent.getStringExtra("Phonenumber");

        //clickLogin
        otp.setOtpCompletionListener(this);

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
      presenter.doLogin(phoneNum,otp);
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
                presenter.checkInApi(token);

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


}