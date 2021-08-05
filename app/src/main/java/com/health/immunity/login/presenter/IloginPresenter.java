package com.health.immunity.login.presenter;

import android.content.Context;

public interface IloginPresenter {
    void doLogin (String Phonenum, String Otp, Context context);
    void sendOtp (String Phonenum);
    void checkInApi(String token);
}
