package com.health.immunity.splash;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.BaseActivity;
//import com.health.immunity.activity.HomeActivity;
//import com.health.immunity.activity.LocationTrack;
import com.health.immunity.IConstant;
//import com.health.immunity.model.ForcefullyResponse;
//import com.health.immunity.model.TokenRespose;
//import com.health.immunity.model.onboard.OnBoardResponse;
import com.health.immunity.login.LoginActivity;
import com.health.immunity.retrofit.RetrofitClient;
//import com.health.immunity.utility.CommonUtils;
//import com.health.immunity.utility.PreferenceHelper;

import org.jsoup.Jsoup;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {
    Locale myLocale;
    String currentLanguage = "en";

    private String fcmToken = "";
//    LocationTrack locationTrack;
    String currentVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        FirebaseApp.initializeApp(this);
        try
        {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        new GetVersionCode().execute();

//        Thread background = new Thread() {
//            public void run() {
//                try {
//                    // Thread will sleep for 5 seconds
//                    sleep(5*1000);
//
//                    // After 5 seconds redirect to another intent
//                    Intent i=new Intent(getBaseContext(), LoginActivity.class);
//                    startActivity(i);
//
//                    //Remove activity
//                    finish();
//                } catch (Exception e) {
//                }
//            }
//        };
//        // start thread
//        background.start();

    }

        private class GetVersionCode extends AsyncTask<Void, String, String>
        {
            @Override
            protected String doInBackground(Void... voids) {
                String newVersion = null;
                try {
                    newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + SplashActivity.this.getPackageName() + "&hl=it")
                            .timeout(30000)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                            .get()
                            .select(".hAyfc .htlgb")
                            .get(7)
                            .ownText();
                    return newVersion;
                } catch (Exception e) {
                    return newVersion;
                }

            }


            @Override

            protected void onPostExecute(String onlineVersion) {

                super.onPostExecute(onlineVersion);

                if (onlineVersion != null && !onlineVersion.isEmpty()) {

                    if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {

                        //show dialog
                        AlertDialog.Builder  dialogUpdate= new AlertDialog.Builder(SplashActivity.this,AlertDialog.THEME_TRADITIONAL).setTitle("New Version Available")
                                .setIcon(getResources().getDrawable(android.R.drawable.stat_sys_download))
                                .setMessage("New version of app is available. Update your app")
                                .setCancelable(false)
                                .setNeutralButton("UPDATE", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub

                                        try
                                        {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                                        }
                                        catch (ActivityNotFoundException e)
                                        {
                                            // TODO: handle exception

                                            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));

                                        }




                                    }
                                });

                        AlertDialog updatedialog = dialogUpdate.create();
                        updatedialog.show();




                    }
                    else
                    {
                        //  Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(() -> {
                            if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_LOGIN)) {
                                Intent intent1 = new Intent(context, HomeActivity.class);
                                startActivity(intent1);
                                finish();
                            } else {
                                if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_ONBOARD)) {

                                    Intent intent1 = new Intent(context, HomeActivity.class);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent1);
                                    finish();


                                } else if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_CHECKIN)) {
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }, 1000);
                    }

                }

                Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

            }
        }




}
