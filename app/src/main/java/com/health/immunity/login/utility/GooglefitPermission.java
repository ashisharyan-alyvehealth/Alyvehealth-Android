package com.health.immunity.login.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.act.model.SourceResponse;
import com.health.immunity.databinding.ActivityGooglefitPermissionBinding;
import com.health.immunity.login.view.WATnCActivity;
import com.health.immunity.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GooglefitPermission extends BaseActivity {
    private ActivityGooglefitPermissionBinding binding;
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;

    FitnessOptions fitnessOptions;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_googlefit_permission);
        initViews();
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
                .addDataType(DataType.TYPE_HEART_RATE_BPM)
                .addDataType(DataType.TYPE_BODY_FAT_PERCENTAGE)
                .addDataType(DataType.TYPE_WORKOUT_EXERCISE)
                .addDataType(DataType.TYPE_ACTIVITY_SEGMENT)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.dismissSpotoProgressDialog();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(GooglefitPermission.this), fitnessOptions))
        {

            PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"0");
            binding.swtchbtn.setChecked(false);


        }
        else
        {
            PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"1");
            binding.swtchbtn.setChecked(true);




        }

    }

    private void initViews()
    {
        binding.textskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"0");
                Intent intent1 = new Intent(context, WATnCActivity.class);
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intent1.putExtra("name", getIntent().getStringExtra("name"));
                intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                if (getIntent().getStringExtra("email") != null) {
                    intent1.putExtra("email", getIntent().getStringExtra("email"));
                }
                intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                //intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);

            }
        });

    /*    binding.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"2");


                Intent intent1 = new Intent(context, SurveyNameActivity.class);
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intent1.putExtra("name", getIntent().getStringExtra("name"));
                intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                if (getIntent().getStringExtra("email") != null) {
                    intent1.putExtra("email", getIntent().getStringExtra("email"));
                }
                Log.e(TAG, "onClick: Terms And Condition " + getIntent().getStringExtra("email"));
                intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                Log.e(TAG, "Terms And Condition: " + getIntent().getStringExtra("mobileNumber"));
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);

            }
        });

        binding.tcskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"2");


                Intent intent1 = new Intent(context, SurveyNameActivity.class);
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intent1.putExtra("name", getIntent().getStringExtra("name"));
                intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                if (getIntent().getStringExtra("email") != null) {
                    intent1.putExtra("email", getIntent().getStringExtra("email"));
                }
                Log.e(TAG, "onClick: Terms And Condition " + getIntent().getStringExtra("email"));
                intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                Log.e(TAG, "Terms And Condition: " + getIntent().getStringExtra("mobileNumber"));
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);

            }
        });*/

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(GooglefitPermission.this), fitnessOptions))
                {

                    PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"0");
                    Intent intent1 = new Intent(context, WATnCActivity.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intent1.putExtra("name", getIntent().getStringExtra("name"));
                    intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                    intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                    if (getIntent().getStringExtra("email") != null) {
                        intent1.putExtra("email", getIntent().getStringExtra("email"));
                    }
                    intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    //intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);

                }
                else
                {
                    PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"1");
                    Intent intent1 = new Intent(context, WATnCActivity.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intent1.putExtra("name", getIntent().getStringExtra("name"));
                    intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                    intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                    if (getIntent().getStringExtra("email") != null) {
                        intent1.putExtra("email", getIntent().getStringExtra("email"));
                    }
                    intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    //intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);

                }
            }
        });

        binding.swtchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.swtchbtn.isChecked()) {
                    binding.swtchbtn.setChecked(true);

                    if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(GooglefitPermission.this), fitnessOptions)) {
                        CommonUtils.showSpotoProgressDialog(context);
                        GoogleSignIn.requestPermissions((Activity) context, REQUEST_OAUTH_REQUEST_CODE,
                                GoogleSignIn.getLastSignedInAccount(context), fitnessOptions);
                    }
                    else {
                        CommonUtils.dismissSpotoProgressDialog();
                        //subscribe();
                    }
                }else {

                    CommonUtils.dismissSpotoProgressDialog();
                    //subscribe();
                    binding.swtchbtn.setChecked(false);
                    PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,"2");

                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .requestScopes(new Scope(Scopes.FITNESS_ACTIVITY_READ), new Scope(Scopes.FITNESS_BODY_READ), new Scope(Scopes.FITNESS_LOCATION_READ))
                            .requestIdToken("303818861029-tkuc5is3lsi2a56l8tk1og5p4hckc10h.apps.googleusercontent.com")
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {


                                }


                            });

                }
            }
        });

      /*  binding.etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(GooglefitPermission.this), fitnessOptions))
                {
                    CommonUtils.showSpotoProgressDialog(context);
                    GoogleSignIn.requestPermissions((Activity) context, REQUEST_OAUTH_REQUEST_CODE,
                            GoogleSignIn.getLastSignedInAccount(context), fitnessOptions);
                } else {

                    CommonUtils.dismissSpotoProgressDialog();
                    subscribe();
                }
            }
        });*/




    }


    public void subscribe() {
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(GooglefitPermission.this, "Thank you for you subscription", Toast.LENGTH_SHORT);
                                } else {
                                    Toast.makeText(
                                            GooglefitPermission.this,
                                            "Authorization Failed",
                                            Toast.LENGTH_SHORT);
                                    Log.d(
                                            "USER AUTHORIZATION",
                                            "There was a problem subscribing.",
                                            task.getException());
                                }
                            }
                        });
    }



    private void sourceApi() {
        CommonUtils.showSpotoProgressDialog(context);
        Call<SourceResponse> call = RetrofitClient.getUniqInstance().getApi()
                .sourceCall("google");
        call.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true"))
                        {

                            System.out.println(response.body().getJsonData().get(0).getTokenValue());

                        }
                    }
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<SourceResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }
}

