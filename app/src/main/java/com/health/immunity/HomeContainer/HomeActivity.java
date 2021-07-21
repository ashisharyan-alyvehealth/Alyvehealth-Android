package com.health.immunity.HomeContainer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.BuildConfig;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.model.TokenResponse;
import com.health.immunity.HomeContainer.presenter.HomePresenter;
import com.health.immunity.HomeContainer.presenter.IHomePresenter;
import com.health.immunity.HomeContainer.view.IHomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.aboutus.AboutUsFragment;
import com.health.immunity.act.ActFragment;
import com.health.immunity.alyvecash.AlyveCashFragment;
import com.health.immunity.challenges.ChallengesFragment;
import com.health.immunity.community.DashboardFragment;
import com.health.immunity.databinding.ActivityHomeBinding;
import com.health.immunity.expertchat.ExpertChatFragment;
import com.health.immunity.insight.InsightFragment;
import com.health.immunity.login.LoginActivity;
import com.health.immunity.myprogram.MyProgramFragment;
import com.health.immunity.pedo.*;
import com.health.immunity.pedo.ui.Fragment_Settings;
import com.health.immunity.pedo.util.Logger;
import com.health.immunity.pedo.util.Util;
import com.health.immunity.profile.ProfileActivity;
import com.health.immunity.profile.model.GetProfileResponse;
import com.health.immunity.retrofit.RetrofitClient;
import com.health.immunity.todo.TodoFragment;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, IHomeActivity, SensorEventListener {
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;
    public static DrawerLayout drawer;
    private ActivityHomeBinding binding;
    BottomNavigationView navView,bottomNav;
    BottomNavigationMenuView menuView;
    AlertDialog.Builder builderPedometer;
    public static int userid =0;
    private boolean showSteps = true;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    private int todayOffset;
    private int total_start;
    private int goal;
    private String strImage = "";
    private int extend_count = 0;
    String fcmToken = "";
    private int since_boot;
    private int total_days;
    FitnessOptions fitnessOptions;
    private String steps;
    private String distance;
    public final static NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
    final public static int DEFAULT_GOAL = 10000;
    final public static float DEFAULT_STEP_SIZE = Locale.getDefault() == Locale.US ? 2.5f : 75f;
    final public static String DEFAULT_STEP_UNIT = Locale.getDefault() == Locale.US ? "ft" : "cm";
    IHomePresenter presenter;
    private static final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_home);
        presenter=new HomePresenter(this,context);
        drawer = findViewById(R.id.drawer_layout);
        navView=findViewById(R.id.bottomNav);
        builderPedometer = new AlertDialog.Builder(this);
        bottomNav=findViewById(R.id.bottomNav);
        binding.ivNavBar.setOnClickListener(this);
        binding.ivScan.setOnClickListener(this);
        binding.homeTv.setOnClickListener(this);
        binding.ivHeader.setOnClickListener(this);
        binding.tvVer.setText("Version "+setVersionName());
        binding.tvLogout.setOnClickListener(this);
        binding.tvedit.setOnClickListener(this);
        binding.imvdwn.setOnClickListener(this);
        binding.imvdwn1.setOnClickListener(this);
        binding.tvPrvcy.setOnClickListener(this);
        binding.tvmyprograms.setOnClickListener(this);
        binding.tvmyreports.setOnClickListener(this);
        binding.tvAbtus.setOnClickListener(this);
        binding.tvwallet.setOnClickListener(this);
        binding.tvActivitylog.setOnClickListener(this);
        binding.imageProfic.setOnClickListener(this);
//        binding.bottomNav.setClipChildren(false);
//        binding.bottomNav.setClipToPadding(false);
//        binding.bottomNav.setClipToOutline(false);
//        menuView = (BottomNavigationMenuView) binding.bottomNav.getChildAt(0);
//        menuView.setClipChildren(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                presenter.savePedometerData(steps,distance);
            }
        }, 3000);

//
//        int[][] states = new int[][] {
////                new int[] { android.R.attr.state_pressed}, // enabled
////                new int[] {android.R.attr.state_enabled}, // disabled
//                new int[] {-android.R.attr.state_selected}, // unchecked
//                new int[] {android.R.attr.state_selected}  // pressed
//        };
//
//        int[] colors = new int[] {
////                Color.BLACK,
////                Color.RED,
////                Color.GREEN,
//                  Color.parseColor("#8775ed"),
//                  Color.WHITE
//
//        };
//        int[] colors1 = new int[] {
//                Color.parseColor("#E91E63"),
//
//                Color.parseColor("#8775ed")
//
//        };
//        ColorStateList myList = new ColorStateList(states, colors);
//        ColorStateList myList1 = new ColorStateList(states, colors1);

        setBottomNavigationView();
//        binding.bottomNav.setItemTextColor(myList);
//        binding.bottomNav.setItemIconTintList(myList);
//        binding.bottomNav.setItemRippleColor(myList1);
 //       binding.bottomNav.setItemBackgroundResource(R.drawable.botton_back);



        ActFragment actFragment= new ActFragment();
        viewFragment(actFragment,"ACT");
        System.out.println("Reached here");



        binding.swtchbtnpedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedometerOnChecked();
            }
        });
        binding.swtchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.swtchbtn.isChecked())
                {
                    binding.swtchbtn.setChecked(true);
                    if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)) {
                       requestGoogleFitPermission();
                    }


                }
                else
                {
                    PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"0");
                    binding.swtchbtn.setChecked(false);
                  //  usefitApi("0");
                   // signOut();
                }

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.FITNESS_ACTIVITY_READ), new Scope(Scopes.FITNESS_BODY_READ), new Scope(Scopes.FITNESS_LOCATION_READ))
                .requestIdToken("303818861029-tkuc5is3lsi2a56l8tk1og5p4hckc10h.apps.googleusercontent.com")
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        account = GoogleSignIn.getLastSignedInAccount(this);



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag");
        wakeLock.acquire();


        FirebaseMessaging.getInstance();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                fcmToken = instanceIdResult.getToken();
                Log.e("newToken", " ---------- " + fcmToken);

                //  if (!PreferenceHelper.getStringPreference(context,"isfirebase").equalsIgnoreCase("true"))
                saveFirebaseToken(fcmToken);
            }
        });


    }



    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ivNavBar:
                    ExpertChatFragment expertChatFragment=new ExpertChatFragment();
                    viewFragment(expertChatFragment,"EXPERT");
                    break;
                case R.id.ivScan:


                    break;
                case R.id.home_tv:


                    break;
                case R.id.ivHeader:

                    drawer.openDrawer(GravityCompat.START);

                    break;


                case R.id.tvLogout:

                    drawer.closeDrawer(GravityCompat.START);

                {

                    PreferenceHelper.clearAllPreferences(context);
                    PreferenceHelper.setBooleanPreference(context, IConstant.IS_LOGIN, false);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }


                break;
                case R.id.tvedit:
                    drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(context, ProfileActivity.class);
                    startActivity(intent);

                    break;
                case R.id.tvAbtus:
                    drawer.closeDrawer(GravityCompat.START);
                    AboutUsFragment aboutUsFragment=new AboutUsFragment();
                    viewFragment(aboutUsFragment,"ABOUT");

                    break;
                case R.id.tvwallet:
                    drawer.closeDrawer(GravityCompat.START);
                    AlyveCashFragment alyveCashFragment=new AlyveCashFragment();
                    viewFragment(alyveCashFragment,"WALLET");



                    break;
                case R.id.tvActivitylog:



                    break;
                case R.id.imageProfic:

                    break;
                case R.id.tvPrvcy:

                    break;

                case R.id.tvmyprograms:
                    drawer.closeDrawer(GravityCompat.START);
                    MyProgramFragment myProgramFragment=new MyProgramFragment();
                    viewFragment(myProgramFragment,"PRG");
                    break;

                case R.id.tvmyreports:

                    break;

                case R.id.imvdwn:

                    break;
                case R.id.imvdwn1:
                    if(binding.tvActivitylog1.isShown() && binding.tvPrvcy2.isShown()){
                        binding.imvdwn1.setImageDrawable(getDrawable(R.drawable.down));
                        binding.tvActivitylog1.setVisibility(View.GONE);
                        binding.swtchbtn.setVisibility(View.GONE);
                        binding.tvPrvcy1.setVisibility(View.GONE);
                        binding.swtchbtnpedo.setVisibility(View.GONE);
                        binding.tvPrvcy2.setVisibility(View.GONE);
                        binding.swtchbtncal.setVisibility(View.GONE);

                    }
                    else{
                        binding.imvdwn1.setImageDrawable(getDrawable(R.drawable.up));
                        binding.tvActivitylog1.setVisibility(View.VISIBLE);
                        binding.swtchbtn.setVisibility(View.VISIBLE);
                        if(presenter.checkSensorAvailability()) {
                            binding.tvPrvcy1.setVisibility(View.VISIBLE);
                            binding.swtchbtnpedo.setVisibility(View.VISIBLE);
                        }
                        binding.tvPrvcy2.setVisibility(View.VISIBLE);
                        binding.swtchbtncal.setVisibility(View.VISIBLE);


                    }


                    break;






            }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                binding.swtchbtnpedo.setChecked(false);
                pedometerOnChecked();

            } else {
                binding.swtchbtnpedo.setChecked(true);

                PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "1");
            }

        }
    }

    @Override
    public void setBottomNavigationView() {
        binding.bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_insight:
                    InsightFragment insightFragment=new InsightFragment();
                    viewFragment(insightFragment,"INSIGHT");

                    return true;
                case R.id.navigation_connect:
                    DashboardFragment dashboardFragment=new DashboardFragment();
                    viewFragment(dashboardFragment,"CONNECT");

                    return true;
                case R.id.navigation_act:
                    ActFragment actFragment= new ActFragment();
                    viewFragment(actFragment,"ACT");

                    return true;
                case R.id.navigation_discover:
                    ChallengesFragment challengesFragment=new ChallengesFragment();
                    viewFragment(challengesFragment,"DISCOVER");

                    return true;
                case R.id.navigation_todo:
                    TodoFragment todoFragment=new TodoFragment();
                    viewFragment(todoFragment,"TODO");
                    return true;
            }
            return false;
        });

    }




    @Override
    public String setVersionName() {
        String versionName = "";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    @Override
    public void viewFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeContainer, fragment);
        final int count = fragmentManager.getBackStackEntryCount();
        if (name.equals("INSIGHT")) {
            fragmentTransaction.addToBackStack(name);
        }
        if (name.equals("DISCOVER")) {
            fragmentTransaction.addToBackStack(name);
        }
        if (name.equals("TODO")) {
            fragmentTransaction.addToBackStack(name);
        }
        if (name.equals("ABOUT")) {
            fragmentTransaction.addToBackStack(name);
            binding.bottomNav.getMenu().getItem(2).setChecked(true);
        }
        if (name.equals("EXPERT")) {
            fragmentTransaction.addToBackStack(name);
            binding.bottomNav.getMenu().getItem(2).setChecked(true);
        }
        if (name.equals("WALLET")) {
            fragmentTransaction.addToBackStack(name);
            binding.bottomNav.getMenu().getItem(2).setChecked(true);
        }
        if (name.equals("PRG")) {
            fragmentTransaction.addToBackStack(name);
            binding.bottomNav.getMenu().getItem(2).setChecked(true);
        }
        if (name.equals("ACT")) {
            fragmentTransaction.addToBackStack(name);
            binding.bottomNav.getMenu().getItem(2).setChecked(true);
        }
        if (name.equals("CONNECT")) {
            fragmentTransaction.addToBackStack(name);
        }

        fragmentTransaction.commit();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack("PRG", POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);
                    animateBottomIcon(2,true);
                }

                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack("WALLET", POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);
                }

                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack("EXPERT", POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);
                }
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack("DASH", POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);
                }
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack("DISCOVER", POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);
                }
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack("TODO", POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);
                    animateBottomIcon(4,true);
                }
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack("PREVENT", POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);
                    animateBottomIcon(2,true);
                }
            }
        });
    }

    @Override
    public void requestPedometerPermission() {
        ActivityCompat.requestPermissions(HomeActivity.this,
                new String[]{(Manifest.permission.ACTIVITY_RECOGNITION)},
                MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED ) {
            // Permission is not granted
            binding.swtchbtnpedo.setChecked(false);

        }
        else
        {
            binding.swtchbtnpedo.setChecked(true);

        }
        getProfileApiAgain();
        Database db = Database.getInstance(context);

        if (BuildConfig.DEBUG) db.logState();
        // read todays offset
        todayOffset = db.getSteps(Util.getToday());

        SharedPreferences prefs =
                getSharedPreferences("pedometer", Context.MODE_PRIVATE);

        goal = prefs.getInt("goal", DEFAULT_GOAL);
        since_boot = db.getCurrentSteps();
        int pauseDifference = since_boot - prefs.getInt("pauseCount", since_boot);

        // register a sensorlistener to live update the UI if a step is taken
        SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
               /* new AlertDialog.Builder(getActivity()).setTitle(R.string.no_sensor)
                        .setMessage(R.string.no_sensor_explain)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(final DialogInterface dialogInterface) {
                                //getActivity().finish();
                            }
                        }).setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();*/
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0);
            }
        }

        since_boot -= pauseDifference;

        total_start = db.getTotalWithoutToday();
        total_days = db.getDays();
        db.close();

    }

    @Override
    public void onSuccess(String msg) {
        showToast(msg);
    }

    @Override
    public void onError(String msg) {
    showToast(msg);
    }

    @Override
    public void pedometerOnChecked() {


        if(presenter.checkSensorAvailability()) {
            if (binding.swtchbtnpedo.isChecked()) {
                if(Build.VERSION.SDK_INT >= 29) {
                    if (!presenter.checkPedometerPermission()) {
                        showToast("permission not found");
                        requestPedometerPermission();


                    } else {
                        showToast("to start service");
                        presenter.startPedometerService();

                    }
                }else {
                    presenter.requestPedometerPermissionCustomDialog(builderPedometer);
                }

            }else {
                binding.swtchbtnpedo.setChecked(false);
                presenter.stopPedometerService();
            }
        }else {
            binding.swtchbtnpedo.setChecked(false);
            onError("Error: No Accelerometer.");
        }


    }
    private void getProfileApiAgain() {

        Call<GetProfileResponse> call = RetrofitClient.getUniqInstance().getApi()
                .getProfileCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.body() != null) {
                    System.out.println("userid "+response.body().getJsonData().getId());
                    userid=response.body().getJsonData().getId();

                    if (response.body().getStatus().equalsIgnoreCase("true")) {

                        if (!TextUtils.isEmpty(response.body().getJsonData().getGender())) {

                            if (response.body().getJsonData().getGender().equalsIgnoreCase("Male")) {
                                if (response.body().getJsonData().getUserImages() != null) {
                                    strImage = IMAGE_BASE_URL + response.body().getJsonData().getUserImages();
                                    System.out.println("strImage "+strImage);
                                    Glide.with(context).load(strImage).into(binding.imageProfic);
                                    Glide.with(context).load(strImage).into(binding.ivHeader);
                                }
                                if(response.body().getJsonData().getCommImages()!=null){
                                    strImage=response.body().getJsonData().getCommImages();
                                    System.out.println("strImage1 "+strImage);
                                    System.out.println("imagelink"+strImage);
                                    if(context!=null) {
                                        Glide.with(context).load(strImage).into(binding.ivHeader);
                                    }else {
                                        Glide.with(getApplicationContext()).load(strImage).into(binding.ivHeader);
                                    }
                                }else{

                                    if (response.body().getJsonData().getUserImages() != null) {
                                        strImage = IMAGE_BASE_URL + response.body().getJsonData().getUserImages();
                                        System.out.println("strImage "+strImage);
                                        System.out.println("imagelink" + strImage);
                                        Glide.with(context).load(strImage).into(binding.ivHeader);

                                    }
                                    else {

                                        binding.ivHeader.setImageResource(R.drawable.changedp);
                                    }

                                }


                            } else if (response.body().getJsonData().getGender().equalsIgnoreCase("Female")) {
                                if (response.body().getJsonData().getUserImages() != null) {
                                    strImage =IMAGE_BASE_URL +  response.body().getJsonData().getUserImages();
                                    System.out.println("strImage "+strImage);
                                    Glide.with(context).load(strImage).into(binding.imageProfic);
                                    Glide.with(context).load(strImage).into(binding.ivHeader);
                                }
                                if(response.body().getJsonData().getCommImages()!=null){
                                    strImage=response.body().getJsonData().getCommImages();
                                    System.out.println("strImage1 "+strImage);
                                    if(context!=null) {
                                        Glide.with(context).load(strImage).into(binding.ivHeader);
                                    }else {
                                        Glide.with(getApplicationContext()).load(strImage).into(binding.ivHeader);
                                    }
                                }else{
                                    if (response.body().getJsonData().getUserImages() != null) {
                                        strImage = IMAGE_BASE_URL + response.body().getJsonData().getUserImages();
                                        System.out.println("strImage2 "+strImage);
                                        Glide.with(context).load(strImage).into(binding.ivHeader);
                                    }else {
                                        binding.ivHeader.setImageResource(R.drawable.changedp);
                                    }
                                }

                            } else {
                                if (response.body().getJsonData().getUserImages() != null) {
                                    strImage = IMAGE_BASE_URL + response.body().getJsonData().getUserImages();
                                    Glide.with(context).load(strImage).into(binding.imageProfic);
                                    Glide.with(context).load(strImage).into(binding.ivHeader);
                                }
                                if(response.body().getJsonData().getCommImages()!=null){
                                    strImage=IMAGE_BASE_URL + response.body().getJsonData().getCommImages();
                                    Glide.with(context).load(strImage).into(binding.ivHeader);
                                }else{
                                    if (response.body().getJsonData().getUserImages() != null) {
                                        strImage = IMAGE_BASE_URL + response.body().getJsonData().getUserImages();
                                        Glide.with(context).load(strImage).into(binding.ivHeader);
                                    }else {
                                        binding.ivHeader.setImageResource(R.drawable.changedp);
                                    }
                                }

                            }

                        }



                        if (response.body().getJsonData().getName() != null) {
                            binding.namedash.setText(response.body().getJsonData().getName());
                        }


                        if (!TextUtils.isEmpty(response.body().getJsonData().getEmail()))
                        {
                            PreferenceHelper.setStringPreference(context, IConstant.EMAILACT,response.body().getJsonData().getEmail());
                        }
                        else
                        {
                            PreferenceHelper.setStringPreference(context, IConstant.EMAILACT,"");

                        }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getPhoneNumber()))
                        {
                            binding.emaildash.setText(response.body().getJsonData().getPhoneNumber());
                            PreferenceHelper.setStringPreference(context, IConstant.MOBILEACT,response.body().getJsonData().getPhoneNumber());
                        }
                        else
                        {
                            PreferenceHelper.setStringPreference(context, IConstant.MOBILEACT,"");

                        }

                        {
                            extend_count = response.body().getJsonData().getExtendedInvitCount();
                        }

                        if (response.body().getJsonData().getOfficeLocation() != null) {

                            CommonUtils.officelat = response.body().getJsonData().getOfficeLocation().getOfficeLat();
                            CommonUtils.officelong = response.body().getJsonData().getOfficeLocation().getOfficeLon();
                            PreferenceHelper.setStringPreference(context,IConstant.GEOLATOFF, String.valueOf(response.body().getJsonData().getOfficeLocation().getOfficeLat()));
                            PreferenceHelper.setStringPreference(context,IConstant.GEOLONGOFF, String.valueOf(response.body().getJsonData().getOfficeLocation().getOfficeLon()));
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            if (CommonUtils.officelat!=0.0 && CommonUtils.officelong!=0.0) {


                                android.location.Address address = null;
                                try {
                                    address = geocoder.getFromLocation(CommonUtils.officelat, CommonUtils.officelong, 1).get(0);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String addres = String.valueOf(address);
                                addres = addres.substring(addres.lastIndexOf(":") + 1);
                                String result = addres.substring(addres.indexOf("[") + 2, addres.indexOf("]") - 1);
                                CommonUtils.profilelong = result;
                            }



                        }
                        if (response.body().getJsonData().getHomeLocation() != null&&!TextUtils.isEmpty(response.body().getJsonData().getHomeLocation().getHomeLat().trim())) {

                            CommonUtils.homelat = Double.parseDouble(response.body().getJsonData().getHomeLocation().getHomeLat());
                            CommonUtils.homelong = Double.parseDouble(response.body().getJsonData().getHomeLocation().getHomeLon());

                            PreferenceHelper.setStringPreference(context,IConstant.GEOLAT, String.valueOf(response.body().getJsonData().getHomeLocation().getHomeLat()));
                            PreferenceHelper.setStringPreference(context,IConstant.GEOLONG, String.valueOf(response.body().getJsonData().getHomeLocation().getHomeLon()));

                        }

                    } else {
                        showToast(response.body().getMessage());
                    }
                } else {
                    showToast(SERVER_ERROR);
                }

            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void buildFitnessOptionRequest() {
        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA)
                .addDataType(DataType.TYPE_DISTANCE_CUMULATIVE)
                .addDataType(DataType.TYPE_DISTANCE_DELTA)
                .addDataType(DataType.AGGREGATE_DISTANCE_DELTA)
                .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY)
                .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.AGGREGATE_HEIGHT_SUMMARY)
                .addDataType(DataType.AGGREGATE_WEIGHT_SUMMARY)
                .build();
    }

    @Override
    public void requestGoogleFitPermission() {
        GoogleSignIn.requestPermissions((Activity) context, REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(context),
                fitnessOptions);

    }

    private void animateBottomIcon(int itemIndex, boolean isChecked) {
        final View view = menuView.getChildAt(itemIndex).findViewById(com.google.android.material.R.id.icon);
        ObjectAnimator translateUpAnimator = ObjectAnimator.ofFloat(view, "translationY",
                0,
                (float) (-(binding.bottomNav.getHeight() / 2))).setDuration(500);
        if(!isChecked) {
            translateUpAnimator.start();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (BuildConfig.DEBUG) Logger.log(
                "UI - sensorChanged | todayOffset: " + todayOffset + " since boot: " +
                        event.values[0]);
        if (event.values[0] > Integer.MAX_VALUE || event.values[0] == 0) {
            return;
        }
        if (todayOffset == Integer.MIN_VALUE) {
            // no values for today
            // we dont know when the reboot was, so set todays steps to 0 by
            // initializing them with -STEPS_SINCE_BOOT
            todayOffset = -(int) event.values[0];
            Database db = Database.getInstance(context);
            db.insertNewDay(Util.getToday(), (int) event.values[0]);
            db.close();
        }
        since_boot = (int) event.values[0];
        updatePie();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void saveFirebaseToken(String token) {
        Call<TokenResponse> call = RetrofitClient.getUniqInstance().getApi().setToken("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN),token,"android");
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.body() != null) {
                    // showToast("Success");
                    PreferenceHelper.setStringPreference(context,"isfirebase","true");
                    // sendNotification();
                }
            }
            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                showToast(SERVER_ERROR);
                t.printStackTrace();
            }
        });
    }


    private void updatePie() {
        if (BuildConfig.DEBUG) Logger.log("UI - update steps: " + since_boot);
        // todayOffset might still be Integer.MIN_VALUE on first start
        int steps_today = Math.max(todayOffset + since_boot, 0);
        String stepsstr=Integer.toString(steps_today);
        Database db = Database.getInstance(context);
        Pair<Date, Integer> record = db.getRecordData();
        ArrayList<String> aListLanguages = new ArrayList<String>();

        for (int i=1;i<7;i++)
        {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(Util.getToday());
            date.add(Calendar.DATE, -i);
            System.out.println("####################+"+"  "+date.getTimeInMillis());
            String s= String.valueOf(db.getSteps(date.getTimeInMillis()));
            if (s.contains("-"))
            {
                aListLanguages.add("0");
                System.out.println("####################+0");

            }
            else
            {
                aListLanguages.add(s);
                System.out.println("####################+"+s);

            }

        }
        // Collections.reverse(aListLanguages);
        String s = TextUtils.join(", ", aListLanguages);
        Log.d("LOGTAG", steps_today+","+s);
        steps=steps_today+","+s;
        System.out.println("####################+re"+steps);
        System.out.println("####################+re"+record);
        db.close();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@ssstepd"+formatter.format(steps_today));
            // update only every 10 steps when displaying distance
            SharedPreferences prefs =
                    getSharedPreferences("pedometer", Context.MODE_PRIVATE);
            float stepsize = prefs.getFloat("stepsize_value", Fragment_Settings.DEFAULT_STEP_SIZE);
            float distance_today = steps_today * stepsize;
            float distance_total = (total_start + steps_today) * stepsize;
            if (prefs.getString("stepsize_unit", Fragment_Settings.DEFAULT_STEP_UNIT)
                    .equals("cm")) {
                distance_today /= 100000;
                distance_total /= 100000;
            } else {
                distance_today /= 5280;
                distance_total /= 5280;
            }
            distance=Float.toString(distance_today);


    }


}