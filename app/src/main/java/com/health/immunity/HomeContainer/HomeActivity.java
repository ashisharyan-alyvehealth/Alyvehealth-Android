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
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
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
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.BuildConfig;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.model.TokenResponse;
import com.health.immunity.HomeContainer.model.FitResponse;
import com.health.immunity.HomeContainer.model.JsonObjectResponse;
import com.health.immunity.HomeContainer.model.updatrPedoIdResponse;
import com.health.immunity.HomeContainer.presenter.HomePresenter;
import com.health.immunity.HomeContainer.presenter.IHomePresenter;
import com.health.immunity.HomeContainer.view.IHomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.aboutus.AboutUsFragment;
import com.health.immunity.act.ActFragment;
import com.health.immunity.act.model.SourceResponse;
import com.health.immunity.alyvecash.AlyveCashFragment;
import com.health.immunity.challenges.ChallengesFragment;
import com.health.immunity.community.DashboardFragment;
import com.health.immunity.databinding.ActivityHomeBinding;
import com.health.immunity.expertchat.ExpertChatFragment;
import com.health.immunity.insight.BodyAdapter;
import com.health.immunity.insight.InsightFragment;
import com.health.immunity.login.LoginActivity;
import com.health.immunity.myprogram.MyProgramFragment;
import com.health.immunity.pedo.*;
import com.health.immunity.pedo.ui.Fragment_Settings;
import com.health.immunity.pedo.util.Logger;
import com.health.immunity.pedo.util.Util;
import com.health.immunity.privacypolicy.PrivacypolicyFragment;
import com.health.immunity.profile.ProfileActivity;
import com.health.immunity.profile.model.GetProfileResponse;
import com.health.immunity.profile.model.updateZohoIdResponse;
import com.health.immunity.retrofit.ApiInterface;
import com.health.immunity.retrofit.RetrofitClient;
import com.health.immunity.retrofit.ServiceGenerator;
import com.health.immunity.screentime.ScreentimeActivity;
import com.health.immunity.todo.TodoFragment;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
    public static String totalStepsString = "", totalHeightsString, totalWeightsString, totalDistancesString;
    long endTime, startTime;
    public static int userid =0;
    public static int  permissionCount;
    public static String lastAct="noone";
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private boolean showSteps = true;
    public static boolean report=false;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    private int todayOffset;
    String currentVersion;
    public static boolean vit=false;
    private int total_start;
    public static Switch googleswitch;
    private int goal;
    private static final int PERMISSIONS_REQUEST_WRITE_CAL = 1999;
    private String strImage = "";
    String runningstr="",walkingstr="",zumbastr="",joggingstr="",aerobicsstr="",weightputstr="",heightputstr="",spo2putstr="";
    private int extend_count = 0;
    String fcmToken = "";
    private int since_boot;
    private int total_days;
    private Context context1;
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
        googleswitch=(Switch)findViewById(R.id.swtchbtn);
        builderPedometer = new AlertDialog.Builder(this);
        bottomNav=findViewById(R.id.bottomNav);
        binding.ivNavBar.setOnClickListener(this);
        binding.ivScan.setOnClickListener(this);
        binding.homeTv.setOnClickListener(this);
        binding.ivHeader.setOnClickListener(this);
        binding.ivHeader1.setOnClickListener(this);
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

        setBottomNavigationView();
        try
        {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        new GetVersionCode().execute();


        {

           buildFitnessOptionRequest();
            System.out.println("###############permission");

            if (!TextUtils.isEmpty(getIntent().getStringExtra("weightbody")))
            {
                if (getIntent().getStringExtra("weightbody").equalsIgnoreCase("weight"))
                {buildFitnessOptionRequest();

                    weightputstr=getIntent().getStringExtra("weightvalue");
                    if (GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions))
                    {
                        System.out.println("##########################wewe");
                        insertWeightData(getIntent().getStringExtra("weightvalue"));

                    }

                    onfitweightClicked();
                    System.out.println("##########################wewew");



                    binding.bottomNav.getMenu().getItem(0).setChecked(true);
                    Fragment argumentFragment = new InsightFragment();
                    vit=true;
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("vitalcoming", "vital");//put string, int, etc in bundle with a key value
                    argumentFragment.setArguments(data);//Finally set argument bundle to fragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homeContainer, argumentFragment);
                    fragmentTransaction.commit();

                }

            }
            else if (!TextUtils.isEmpty(getIntent().getStringExtra("heightbody")))
            {buildFitnessOptionRequest();
                if (getIntent().getStringExtra("heightbody").equalsIgnoreCase("height"))
                {
                    heightputstr=getIntent().getStringExtra("heightvalue");
                    buildFitnessOptionRequest();

                    if (GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions))
                    {
                        insertHeightData(getIntent().getStringExtra("heightvalue"));
                    }



                    onfitheightClicked();


                    binding.bottomNav.getMenu().getItem(0).setChecked(true);
                    Fragment argumentFragment = new InsightFragment();
                    vit=true;
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("vitalcoming", "vital");//put string, int, etc in bundle with a key value
                    argumentFragment.setArguments(data);//Finally set argument bundle to fragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homeContainer, argumentFragment);
                    fragmentTransaction.commit();

                }

            }
            else if (!TextUtils.isEmpty(getIntent().getStringExtra("spo2body")))
            {buildFitnessOptionRequest();
                if (getIntent().getStringExtra("spo2body").equalsIgnoreCase("spo2"))
                {
                    spo2putstr=getIntent().getStringExtra("spo2value");

                    onfitspo2Clicked();

                    binding.bottomNav.getMenu().getItem(0).setChecked(true);
                    Fragment argumentFragment = new InsightFragment();
                    vit=true;
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("vitalcoming", "vital");//put string, int, etc in bundle with a key value
                    argumentFragment.setArguments(data);//Finally set argument bundle to fragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homeContainer, argumentFragment);
                    fragmentTransaction.commit();


                }

            }

            else if (!TextUtils.isEmpty(getIntent().getStringExtra("actcoming")))
            {
                if (getIntent().getStringExtra("actcoming").equalsIgnoreCase("act"))
                {
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);

                    Fragment argumentFragment = new ActFragment();
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("actcoming", "act");//put string, int, etc in bundle with a key value
                    argumentFragment.setArguments(data);//Finally set argument bundle to fragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.homeContainer, argumentFragment);
                    fragmentTransaction.commit();
                }


            }
            else if (!TextUtils.isEmpty(getIntent().getStringExtra("homeactcompare")))
            {

                binding.bottomNav.getMenu().getItem(0).setChecked(true);
                Fragment argumentFragment = new InsightFragment();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("comparecoming", getIntent().getStringExtra("homeactcompare"));
                data.putString("comparenamecoming", getIntent().getStringExtra("homeactcomparename"));
                argumentFragment.setArguments(data);//Finally set argument bundle to fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeContainer, argumentFragment);
                fragmentTransaction.commit();




            }
            else  if(lastAct.equals("Graph")){
                lastAct="noone";
                binding.bottomNav.getMenu().getItem(0).setChecked(true);
                Fragment argumentFragment = new InsightFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeContainer, argumentFragment);
                fragmentTransaction.commit();

            }else
            {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeContainer, new ActFragment());
                fragmentTransaction.commit();
                binding.bottomNav.getMenu().getItem(2).setChecked(true);
            }



        }









        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.FITNESS_ACTIVITY_READ), new Scope(Scopes.FITNESS_BODY_READ), new Scope(Scopes.FITNESS_LOCATION_READ))
                .requestIdToken("303818861029-tkuc5is3lsi2a56l8tk1og5p4hckc10h.apps.googleusercontent.com")
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            binding.swtchbtncal.setChecked(true);
        }

        account = GoogleSignIn.getLastSignedInAccount(this);
        if(PreferenceHelper.getStringPreference(context,IConstant.FIT_PEDOMETER).equals("1")) {
            pedoFitStatusUpdateApi(1,1);
         //   binding.swtchbtnpedo.setChecked(true);
        }else{
            pedoFitStatusUpdateApi(1,0);
         //   binding.swtchbtnpedo.setChecked(false);
        }

        if(account != null) {
            binding.swtchbtn.setChecked(true);
            pedoFitStatusUpdateApi(2,1);
            System.out.println("##########################acc"+account.getEmail());
            PreferenceHelper.setStringPreference(context,PERSONAL_EMAIL_ADD,account.getEmail());
            getSteps();
            getDistancemeter();
            getHeightnew();
            getWeightnew();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    oneplusfitClicked();
                    presenter.savePedometerUserKPI(steps,distance);

                }
            }, 3000);
            if(totalStepsString==" "){
                presenter.saveUserKPI(steps,distance);
            }

        }else {

            pedoFitStatusUpdateApi(2,0);
            if(PreferenceHelper.getStringPreference(context,IConstant.FIT_PEDOMETER).equals("1")) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        presenter.savePedometerData(steps, distance);
                    }
                }, 3000);
            }
        }

        binding.swtchbtncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.swtchbtncal.isChecked()) {
                    checkcalpermission();
                }else {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }

            }
        });

        sourcePermissioncountApi();




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


//        binding.bottomNav.setItemTextColor(myList);
//        binding.bottomNav.setItemIconTintList(myList);
//        binding.bottomNav.setItemRippleColor(myList1);
 //       binding.bottomNav.setItemBackgroundResource(R.drawable.botton_back);

//        if(lastAct.equals("Graph")){
//            lastAct="noone";
//            binding.bottomNav.getMenu().getItem(0).setChecked(true);
//            Fragment argumentFragment = new InsightFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.homeContainer, argumentFragment);
//            fragmentTransaction.commit();
//
//        }else {
//
//            ActFragment actFragment = new ActFragment();
//            viewFragment(actFragment, "ACT");
//            System.out.println("Reached here");
//        }



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
                    buildFitnessOptionRequest();
                    if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)) {
                       requestGoogleFitPermission();
                    }


                }
                else
                {
                    PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"0");
                    binding.swtchbtn.setChecked(false);
                    usefitApi("0");
                    signOut();
                }

            }
        });

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestScopes(new Scope(Scopes.FITNESS_ACTIVITY_READ), new Scope(Scopes.FITNESS_BODY_READ), new Scope(Scopes.FITNESS_LOCATION_READ))
//                .requestIdToken("303818861029-tkuc5is3lsi2a56l8tk1og5p4hckc10h.apps.googleusercontent.com")
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        account = GoogleSignIn.getLastSignedInAccount(this);



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
                case R.id.ivHeader1:
                    buildFitnessOptionRequest();
                    if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)) {
                        binding.swtchbtn.setChecked(false);
                    }else {
                        binding.swtchbtn.setChecked(true);
                    }

                    if (!presenter.checkPedometerPermission()) {
                        // showToast("permission not found");
                        binding.swtchbtnpedo.setChecked(false);
                        presenter.stopPedometerService();
                    }else {
                        binding.swtchbtnpedo.setChecked(true);
                    }
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                       binding.swtchbtncal.setChecked(false);
                    }else {
                        binding.swtchbtncal.setChecked(true);

                    }


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
                    drawer.closeDrawer(GravityCompat.START);
                    PrivacypolicyFragment myProgramFragment=new PrivacypolicyFragment();
                    viewFragment(myProgramFragment,"PRG");

//                    Intent intent2=new Intent(context, ScreentimeActivity.class);
//                    startActivity(intent2);
                    break;

                case R.id.tvmyprograms:
                    drawer.closeDrawer(GravityCompat.START);
                    MyProgramFragment myProgramFragment1=new MyProgramFragment();
                    viewFragment(myProgramFragment1,"err");
                    break;

                case R.id.tvmyreports:
                    InsightFragment preventFragment = new InsightFragment();
                    viewFragment(preventFragment, "INSIGHT");
                    HomeActivity.report=true;
                    binding.bottomNav.getMenu().getItem(0).setChecked(true);

                    drawer.closeDrawer(GravityCompat.START);
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
    private void sourcePermissioncountApi() {
        int[] n = {1};
        Call<SourceResponse> call = RetrofitClient.getUniqInstance().getApi()
                .sourceCall("permission");
        call.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                if(response.body()!=null){
                    permissionCount =Integer.parseInt(response.body().getJsonData().get(0).getTokenValue());
                    System.out.println("RESPONSE2 "+permissionCount);
                 //   checkfitperm(Integer.parseInt(response.body().getJsonData().get(0).getTokenValue()));
                     checkfitperm(3);
                     checkPedoperm(3);
                }
            }

            @Override
            public void onFailure(Call<SourceResponse> call, Throwable t) {

            }
        });

    }

    public void checkfitperm(int a){
        int name;
        int b=a;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        System.out.println("GoogleFitPperrmission@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + preferences.getInt("AskedPermissionCount",0));
        System.out.println("GoogleFitPperrmissioncount@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + permissionCount);

        if(preferences.getInt("AskedPermissionCount",0)<b) {
            if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)) {
                GoogleSignIn.requestPermissions((Activity) context, REQUEST_OAUTH_REQUEST_CODE,
                        GoogleSignIn.getLastSignedInAccount(context),
                        fitnessOptions);

                name = preferences.getInt("AskedPermissionCount", 0);
                /* Edit the value here*/
                editor.putInt("AskedPermissionCount", name + 1);
                editor.apply();
                System.out.println("GoogleFitPperrmission@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + name);


            } else {
                PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK, "0");
                usefitApi("0");
            }
        }

    }
    public void checkPedoperm(int a){
        int name;
        int b=a;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        System.out.println("GoogleFitPperrmission@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + preferences.getInt("AskedPermissionCount",0));
        System.out.println("GoogleFitPperrmissioncount@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + permissionCount);

        if(preferences.getInt("AskedPermissionCountpedo",0)<b) {
            if (!presenter.checkPedometerPermission()) {
                // showToast("permission not found");
                requestPedometerPermission();


                name = preferences.getInt("AskedPermissionCountpedo", 0);
                /* Edit the value here*/
                editor.putInt("AskedPermissionCountpedo", name + 1);
                editor.apply();
                System.out.println("GoogleFitPperrmission@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + name);


            } else {
                PreferenceHelper.setStringPreference(context, IConstant.PEDO_STATUS_BACK, "0");
               // usefitApi("0");
            }
        }

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        {


            if (requestCode == LOCATION_REQUEST_CODE) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        binding.swtchbtnloc.setChecked(true);
                    }
                }
            }

            if (requestCode == PERMISSIONS_REQUEST_WRITE_CAL) {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Calendar Permission granted", Toast.LENGTH_SHORT).show();
                    binding.swtchbtncal.setChecked(true);
                    //  addEvent();
                    // startPayment();
                } else {
                    //startPayment();
                    binding.swtchbtncal.setChecked(false);
                    Toast.makeText(context, "Calendar Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                binding.swtchbtnpedo.setChecked(false);
                //pedometerOnChecked();
                PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "0");

            } else {
                binding.swtchbtnpedo.setChecked(true);
                presenter.startPedometerService();
                PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "1");
            }

        }
        else if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
            com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            if(account != null)
            {
                PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"1");
                binding.swtchbtn.setChecked(true);
                usefitApi("1");


            }
            else
            {

                binding.swtchbtn.setChecked(false);

            }

            //Toast.makeText(this, "11"+account.getIdToken(), Toast.LENGTH_SHORT).show();


        } catch (ApiException e)
        {
            //Toast.makeText(this, "22"+e.getMessage(), Toast.LENGTH_LONG).show();

            //Log.e("BABABA", e.toString());
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }

    private void usefitApi(String id) {
        Call<updateZohoIdResponse> call = RetrofitClient.getUniqInstance().getApi()
                .gitstatusCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), id);
        call.enqueue(new Callback<updateZohoIdResponse>() {
            @Override
            public void onResponse(Call<updateZohoIdResponse> call, Response<updateZohoIdResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {

                            PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"1");


                            //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<updateZohoIdResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
                   // animateBottomIcon(2,true);
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
                   // animateBottomIcon(4,true);
                }
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack("PREVENT", POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    binding.bottomNav.getMenu().getItem(2).setChecked(true);
                  //  animateBottomIcon(2,true);
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
            PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "0");


        }
        else
        {
            binding.swtchbtnpedo.setChecked(true);
            presenter.startPedometerService();
            PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "1");

        }
        if (account==null) {
            // Permission is not granted
            binding.swtchbtn.setChecked(false);

        }
        else
        {
            binding.swtchbtn.setChecked(true);

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
    private void checkcalpermission(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_CALENDAR}, PERMISSIONS_REQUEST_WRITE_CAL);
            return;
        }
    }

    @Override
    public void pedometerOnChecked() {


        if(presenter.checkSensorAvailability()) {
            if (binding.swtchbtnpedo.isChecked()) {
                if(Build.VERSION.SDK_INT >= 29) {
                    if (!presenter.checkPedometerPermission()) {
                       // showToast("permission not found");
                        requestPedometerPermission();


                    } else {
                        showToast("to start service");
                        presenter.startPedometerService();
                        PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "1");
                    }
                }else {
                    presenter.requestPedometerPermissionCustomDialog(builderPedometer);
                }

            }else {
                binding.swtchbtnpedo.setChecked(false);
                presenter.stopPedometerService();
                PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "0");
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }else {
            binding.swtchbtnpedo.setChecked(false);
            PreferenceHelper.setStringPreference(context, IConstant.FIT_PEDOMETER, "0");
            onError("Error: No Accelerometer.");
        }


    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {

                        // Toast.makeText(context, "Sign out Successfully", Toast.LENGTH_SHORT).show();

                    }


                });
    }


    public void getSteps() {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DAY_OF_MONTH)-6, 0, 0);
        startTime = startCal.getTimeInMillis();
        endTime = endCal.getTimeInMillis();
        try {
            getData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void getData() throws InterruptedException, ExecutionException {


        final Task<DataReadResponse> response = Fitness.getHistoryClient(HomeActivity.this, account)
                .readData(new DataReadRequest.Builder()
                        .read(DataType.TYPE_STEP_COUNT_DELTA)
                        .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                        .build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                totalStepsString = "";
                DataReadResponse readDataResult = null;
                try {
                    readDataResult = Tasks.await(response);
                    final DataSet dataSet = readDataResult.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            int steps = 0;
                            // Toast.makeText(HomeActivity.this, "Size: "+dataSet.getDataPoints().size(), Toast.LENGTH_SHORT).show();

                            Calendar date = Calendar.getInstance();
                            for(int i=0;i<dataSet.getDataPoints().size();i++)
                            {
                                long time = dataSet.getDataPoints().get(i).getTimestamp(TimeUnit.MILLISECONDS);
                                date.setTimeInMillis(time);
                                System.out.println("##############stepsnew1"+ dataSet.getDataPoints().get(i).getOriginalDataSource().getStreamName());
                                if(! "user_input".equals(dataSet.getDataPoints().get(i).getOriginalDataSource().getStreamName())) {
                                    totalStepsString += dataSet.getDataPoints().get(i).getValue(Field.FIELD_STEPS).toString() + " " + date.get(Calendar.DAY_OF_MONTH) + ",";
                                }
                            }

                            System.out.println("##############stepsnew"+totalStepsString);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void getDistance() throws InterruptedException, ExecutionException {
        final Task<DataReadResponse> response = Fitness.getHistoryClient(HomeActivity.this, account)
                .readData(new DataReadRequest.Builder()
                        .read(DataType.TYPE_DISTANCE_DELTA)
                        .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                        .build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataReadResponse readDataResult = null;
                totalDistancesString = "";
                try {
                    readDataResult = Tasks.await(response);
                    final DataSet dataSet = readDataResult.getDataSet(DataType.TYPE_DISTANCE_DELTA);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar date = Calendar.getInstance();
                            for(int i=0;i<dataSet.getDataPoints().size();i++)
                            {
                                long time = dataSet.getDataPoints().get(i).getTimestamp(TimeUnit.MILLISECONDS);
                                date.setTimeInMillis(time);
                                totalDistancesString += dataSet.getDataPoints().get(i).getValue(Field.FIELD_DISTANCE).toString()+" "+date.get(Calendar.DAY_OF_MONTH)+",";
                            }
                            System.out.println("##############distnew"+totalStepsString);



                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void pedoFitStatusUpdateApi(int id, int status) {
        Call<updatrPedoIdResponse> call = RetrofitClient.getUniqInstance().getApi()
                .pedostatusCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), id, status);
        call.enqueue(new Callback<updatrPedoIdResponse>() {
            @Override
            public void onResponse(Call<updatrPedoIdResponse> call, Response<updatrPedoIdResponse> response) {
                System.out.println("pedonewapi" + response.toString());
            }

            @Override
            public void onFailure(Call<updatrPedoIdResponse> call, Throwable t) {
                System.out.println("pedonewapi1" + t);
            }
        });
    }

    public void getDistancemeter() {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DAY_OF_MONTH)-6, 0, 0);
        startTime = startCal.getTimeInMillis();
        endTime = endCal.getTimeInMillis();
        try {
            getDistance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void getWeightnew() {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DAY_OF_MONTH), 0, 0);
        startTime = startCal.getTimeInMillis();
        endTime = endCal.getTimeInMillis();
        try {
            getWeight();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void getHeightnew() {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DAY_OF_MONTH), 0, 0);
        startTime = startCal.getTimeInMillis();
        endTime = endCal.getTimeInMillis();
        try {
            getHeight();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void getWeight() throws InterruptedException, ExecutionException {
        final Task<DataReadResponse> response = Fitness.getHistoryClient(HomeActivity.this, account)
                .readData(new DataReadRequest.Builder()
                        .read(DataType.TYPE_WEIGHT)
                        .setTimeRange(1, endTime, TimeUnit.MILLISECONDS)
                        .setLimit(1)
                        .build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataReadResponse readDataResult = null;
                try {
                    readDataResult = Tasks.await(response);
                    final DataSet dataSet = readDataResult.getDataSet(DataType.TYPE_WEIGHT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String string = "";
                            Calendar date = Calendar.getInstance();

                            // Toast.makeText(HomeActivity.this, "Size: "+dataSet.getDataPoints().size(), Toast.LENGTH_SHORT).show();
                            for(int i=0;i<dataSet.getDataPoints().size();i++)
                            {

                                string += dataSet.getDataPoints().get(i).getValue(Field.FIELD_WEIGHT).toString() +" "+date.get(Calendar.DAY_OF_MONTH)+",";
                                //dataSet.getDataPoints().get(i).getTimestamp()
                                //TODO: Print dates using TimeUnit

                                Log.v("Weight", dataSet.getDataPoints().get(i).getValue(Field.FIELD_WEIGHT).toString());
                            }
                            totalWeightsString=string;
                            System.out.println("##############################wwer"+totalWeightsString);

                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getHeight() throws InterruptedException, ExecutionException {
        final Task<DataReadResponse> response = Fitness.getHistoryClient(HomeActivity.this, account)
                .readData(new DataReadRequest.Builder()
                        .read(DataType.TYPE_HEIGHT)
                        .setTimeRange(1, endTime, TimeUnit.MILLISECONDS)
                        .setLimit(1)
                        .build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataReadResponse readDataResult = null;
                try {
                    readDataResult = Tasks.await(response);
                    final DataSet dataSet = readDataResult.getDataSet(DataType.TYPE_HEIGHT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String string = "";
                            Calendar date = Calendar.getInstance();

                            for(int i=0;i<dataSet.getDataPoints().size();i++)
                            {
                                string += dataSet.getDataPoints().get(i).getValue(Field.FIELD_HEIGHT).toString()+" "+date.get(Calendar.DAY_OF_MONTH)+",";
                                //dataSet.getDataPoints().get(i).getTimestamp()
                                //TODO: Print dates using TimeUnit
                                Log.v("HEIGHT", dataSet.getDataPoints().get(i).getValue(Field.FIELD_HEIGHT).toString());
                            }
                            totalHeightsString=string;
                            System.out.println("#####################totalhe"+totalHeightsString);

                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void oneplusfitClicked(){

        //CommonUtils.showSpotoProgressDialog(context);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!fit"+totalStepsString);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!"+totalDistancesString);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!"+totalHeightsString);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!"+totalWeightsString);


        ApiInterface jsonPostService = ServiceGenerator.createService(ApiInterface.class, "https://protect.immunityhealth.me/");

        Call<FitResponse> call = jsonPostService.GoogleFitCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN),totalStepsString,totalDistancesString,totalHeightsString,totalWeightsString);
        call.enqueue(new Callback<FitResponse>() {

            @Override
            public void onResponse(Call<FitResponse> call, Response<FitResponse> response) {
                CommonUtils.dismissSpotoProgressDialog();

                try{

                    CommonUtils.dismissSpotoProgressDialog();

                    Log.e("response-success", response.body().getStatus());
                    Log.e("response-success", response.body().toString());



                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<FitResponse> call, Throwable t) {
                CommonUtils.dismissSpotoProgressDialog();

                Log.e("response-failure", t.toString());
            }
        });
    }


    private void insertWeightData(String weightput) {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        long startTime = cal.getTimeInMillis();
        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(this)
                        .setDataType(DataType.TYPE_WEIGHT)
                        .setStreamName(TAG + " - step count")
                        .setType(DataSource.TYPE_RAW)
                        .build();

        float stepCountDelta = Float.parseFloat((weightput));
        DataSet dataSet = DataSet.create(dataSource);
        DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_WEIGHT).setFloat(stepCountDelta);
        dataSet.add(dataPoint);
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .insertData(dataSet)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert was successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting the dataset.", task.getException());
                                }
                            }
                        });
    }
    public void insertHeightData(String heightput) {
        int stepCountDelta = Integer.parseInt(heightput);
        float height = ((float) stepCountDelta) / 100.0f;
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        long startTime = cal.getTimeInMillis();
        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(this)
                        .setDataType(DataType.TYPE_HEIGHT)
                        .setStreamName(TAG + " - step count")
                        .setType(DataSource.TYPE_RAW)
                        .build();

        DataSet dataSet = DataSet.create(dataSource);
        DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_HEIGHT).setFloat(height);
        dataSet.add(dataPoint);
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .insertData(dataSet)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert was successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting the dataset.", task.getException());
                                }
                            }
                        });
    }




    public void onfitspo2Clicked(){
      //  CommonUtils.showSpotoProgressDialog(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SPO2",spo2putstr);
        System.out.println("onfitClicked1"+jsonObject);
        Call<JsonObjectResponse> call =  RetrofitClient.getUniqInstance().getApi()
                .fitCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), String.valueOf(jsonObject));
        call.enqueue(new Callback<JsonObjectResponse>() {

            @Override
            public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
             //   CommonUtils.dismissSpotoProgressDialog();
                try{
                    Log.e("response-success11", response.body().getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<JsonObjectResponse> call, Throwable t) {
              //  CommonUtils.dismissSpotoProgressDialog();

                Log.e("response-failure", call.toString());
            }
        });
    }

    //


    public void onfitheightClicked(){
        CommonUtils.showSpotoProgressDialog(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Height",heightputstr);
        System.out.println("onfitClicked2"+jsonObject);
        Call<JsonObjectResponse> call =  RetrofitClient.getUniqInstance().getApi()
                .fitCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), String.valueOf(jsonObject));
      //  CommonUtils.dismissSpotoProgressDialog();

        call.enqueue(new Callback<JsonObjectResponse>() {
            @Override
            public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                CommonUtils.dismissSpotoProgressDialog();
                try{
                    Log.e("response-success", response.body().getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<JsonObjectResponse> call, Throwable t) {
                CommonUtils.dismissSpotoProgressDialog();

                Log.e("response-failure", call.toString());
            }
        });
    }
    public void onfitweightClicked(){
       // CommonUtils.showSpotoProgressDialog(context);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("Weight",weightputstr);
        System.out.println("onfitClicked3"+jsonObject);
        Call<JsonObjectResponse> call =  RetrofitClient.getUniqInstance().getApi()
                .fitCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), String.valueOf(jsonObject));
        call.enqueue(new Callback<JsonObjectResponse>() {

            @Override
            public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
            //    CommonUtils.dismissSpotoProgressDialog();
                try{
                    Log.e("response-success", response.body().getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<JsonObjectResponse> call, Throwable t) {
              //  CommonUtils.dismissSpotoProgressDialog();

                Log.e("response-failure", call.toString());
            }
        });
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
        buildFitnessOptionRequest();
        GoogleSignIn.requestPermissions((Activity) context, REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(context),
                fitnessOptions);

    }

    @Override
    public void setHeight(String height) {
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

    private class GetVersionCode extends AsyncTask<Void, String, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + HomeActivity.this.getPackageName() + "&hl=it")
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

                    AlertDialog.Builder  dialogUpdate= new AlertDialog.Builder(HomeActivity.this,AlertDialog.THEME_TRADITIONAL).setTitle("New Version Available")
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
                                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+getPackageName())));
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
                }

            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }

}