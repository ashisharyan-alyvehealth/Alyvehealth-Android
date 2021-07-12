package com.health.immunity.login.presenter;

public interface IloginPresenter {
    void doLogin (String Phonenum,String Otp);
    void sendOtp (String Phonenum);
    void checkInApi(String token);
}
