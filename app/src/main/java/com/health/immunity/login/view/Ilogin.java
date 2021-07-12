package com.health.immunity.login.view;


public interface Ilogin {

    void onSuccess(String message);
    void onError(String message);
    void saveDataToPreferenceAndJumpToHome(String hrid,String token,String phone, int isVerifiedCommunity,String onboardingStatus,String name,
                              String dateOfBirth,String gender, String email);

}
