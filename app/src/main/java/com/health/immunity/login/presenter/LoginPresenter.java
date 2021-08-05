package com.health.immunity.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.login.model.LoginMobile;
import com.health.immunity.login.model.MobileOTPModel;
import com.health.immunity.login.model.OnBoardResponse;
import com.health.immunity.login.view.DummyHomeActivity;
import com.health.immunity.login.view.DummyTncActivity;
import com.health.immunity.login.view.Ilogin;
import com.health.immunity.login.view.OTPActivity;
import com.health.immunity.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BaseActivity implements IloginPresenter{
    Ilogin view ;
    Context context;

    public LoginPresenter(Ilogin view){
        this.view=view;

    }
    @Override
    public void doLogin(String Phonenum,String Otp,Context context1) {
        view.onSuccess("reached here"+ Phonenum +"  "+ Otp);
        verifyMobileOTPApi(Phonenum,Otp,context1);
    }

    @Override
    public void sendOtp(String Phonenum) {
        Call<LoginMobile> call = RetrofitClient.getUniqInstance().getApi().loginCall(Phonenum);
        call.enqueue(new Callback<LoginMobile>() {
            @Override
            public void onResponse(Call<LoginMobile> call, Response<LoginMobile> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        //showToast(response.body().getMessage());
                        view.onSuccess(response.body().getMessage());

                    } else {
                        view.onSuccess(response.body().getMessage());
                    }
                } else {
                    view.onError(SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<LoginMobile> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void checkInApi(String token) {
        checkIn(token);
    }


    private void verifyMobileOTPApi(String phone, String otp,Context context1) {

        Call<MobileOTPModel> call = RetrofitClient.getUniqInstance().getApi().mobileOtpCall(phone,otp);
        call.enqueue(new Callback<MobileOTPModel>() {
            @Override
            public void onResponse(Call<MobileOTPModel> call, Response<MobileOTPModel> response) {
                if (response.body() != null) {

                    if (response.body().getStatus().equalsIgnoreCase("true")) {

                        if (response.body().getLogin().equalsIgnoreCase("true")) {
                            view.saveDataToPreferenceAndJumpToHome(response.body().getJsonData().getHrId(),response.body().getJsonData().getToken(),response.body().getJsonData().getPhoneNumber(),
                                    response.body().getJsonData().getIs_verified_community(),response.body().getJsonData().getOnboarding_status(),
                                    response.body().getJsonData().getName(),response.body().getJsonData().getDateOfBirth(),response.body().getJsonData().getGender(),
                                    response.body().getJsonData().getEmail());
                        } else {


                            Intent intent1 = new Intent(context1, DummyTncActivity.class);
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


    private void checkIn(String token) {
        Call<OnBoardResponse> call = RetrofitClient.getUniqInstance().getApi().checkInStatusCall("Bearer " + token);
        call.enqueue(new Callback<OnBoardResponse>() {
            @Override
            public void onResponse(Call<OnBoardResponse> call, Response<OnBoardResponse> response) {
                if (response.body() != null) {
                    Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
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


}
