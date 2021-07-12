package com.health.immunity.HomeContainer.view;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface IHomeActivity {
     void setBottomNavigationView();
     String setVersionName();
     void viewFragment(Fragment fragment, String name);
     void requestPedometerPermission();
     void onSuccess(String msg);
     void onError(String msg);
     void pedometerOnChecked();
     void buildFitnessOptionRequest();
     void requestGoogleFitPermission();

}
