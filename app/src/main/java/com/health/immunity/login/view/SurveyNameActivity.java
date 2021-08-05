package com.health.immunity.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.R;
import com.health.immunity.act.model.SourceResponse;
import com.health.immunity.databinding.ActivitySurveyNameBinding;
import com.health.immunity.login.utility.CustomTabActivityHelper;
import com.health.immunity.login.utility.WebviewFallback;
import com.health.immunity.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyNameActivity extends BaseActivity implements TextWatcher, View.OnClickListener {

    private ActivitySurveyNameBinding binding;
    private String strDOB, strGender;
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_survey_name);
        initViews();

        //sourceApi();


/*        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .addDataType(DataType.TYPE_DISTANCE_CUMULATIVE)
                .addDataType(DataType.TYPE_DISTANCE_DELTA)
                .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY)
                .addDataType(DataType.TYPE_WEIGHT)
                .addDataType(DataType.TYPE_HEIGHT)
                .addDataType(DataType.AGGREGATE_HEIGHT_SUMMARY)
                .addDataType(DataType.AGGREGATE_WEIGHT_SUMMARY)
                .addDataType(DataType.TYPE_HEART_RATE_BPM)
                .addDataType(DataType.TYPE_BODY_FAT_PERCENTAGE)
                .addDataType(DataType.TYPE_WORKOUT_EXERCISE)
                .addDataType(DataType.TYPE_ACTIVITY_SEGMENT)
                .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(this, REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        } else {
            subscribe();
        }*/


    }

    public void subscribe() {

        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SurveyNameActivity.this, "Thank you for you subscription", Toast.LENGTH_SHORT);
                                } else {
                                    Toast.makeText(
                                            SurveyNameActivity.this,
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

    private void initViews() {
        binding.ivNext.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        if (getIntent() != null) {
            binding.etName.setText(getIntent().getStringExtra("name"));
            binding.etEmailPerson.setText(getIntent().getStringExtra("email"));
            strDOB = getIntent().getStringExtra("dob");
            strGender = getIntent().getStringExtra("genderStatus");
            if (strGender.equalsIgnoreCase("Male"))
            {
                binding.tvFemale.setImageDrawable(getResources().getDrawable(R.drawable.malewithoutbg, null));

            }
            else if (strGender.equalsIgnoreCase("Female"))
            {
                binding.tvFemale.setImageDrawable(getResources().getDrawable(R.drawable.femalewithoutbg, null));

            }
            else if (strGender.equalsIgnoreCase("Others"))
            {
                binding.tvFemale.setImageDrawable(getResources().getDrawable(R.drawable.others, null));
            }
            else if (strGender.equalsIgnoreCase("Prefer not to choose"))
            {
                binding.tvFemale.setImageDrawable(getResources().getDrawable(R.drawable.others, null));
            }
        }
        Log.e(TAG, "initViews: Name - "+getIntent().getStringExtra("comesFrom"));


     /*   binding.etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (CommonUtils.isInternetAvail(context)) {
                            if (TextUtils.isEmpty(binding.etName.getText().toString())) {
                                showToast("Please enter name");
                            } else {
                                Intent intent1 = new Intent(context, SurveyDOBActivity.class);
                                if (getIntent().getStringExtra("comesFrom") != null) {
                                    intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                                }
                                intent1.putExtra("name", binding.etName.getText().toString());
                                intent1.putExtra("email", getIntent().getStringExtra("email"));
                                intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                                Log.e(TAG, "Survey Name: " + getIntent().getStringExtra("mobileNumber"));
                                intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                                //Onboard False
                                intent1.putExtra("dob", strDOB);
                                intent1.putExtra("genderStatus", strGender);
                                startActivity(intent1);
                            }
                        }

                }
                return false;
            }
        });*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:
                if (!isValid(binding.etName.getText().toString()) && TextUtils.isEmpty(binding.etName.getText().toString()) && binding.etName.getText().toString().length()<3) {
                    showToast("Please enter name");
                }
                else if (!isValid(binding.etEmailPerson.getText().toString())) {
                    showToast("Please enter valid email");
                }

                else {
                    binding.ivNext.setEnabled(true);
                    binding.ivNext.setBackground(getResources().getDrawable(R.drawable.gradiant, null));

                    Intent intent1 = new Intent(context, GetOnBoard.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intent1.putExtra("name", binding.etName.getText().toString());
                    intent1.putExtra("email",binding.etEmailPerson.getText().toString() );
                    intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    Log.e(TAG, "Survey Name: " + getIntent().getStringExtra("mobileNumber"));
                    intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                   // Onboard False
                    intent1.putExtra("dob", strDOB);
                    intent1.putExtra("genderStatus", strGender);
                    startActivity(intent1);
                }
                break;

            case R.id.ivBack:
                finish();
                break;
        }
    }

    public boolean isValid(final String email) {
       /* if(!TextUtils.isEmpty(email)){
            //email is empty and therefore valid
            return true;
        }
        else*/{
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


            return binding.etEmailPerson.getText().toString().trim().matches(emailPattern);
        }

    }
    public boolean isValidname(final String name) {
       /* if(!TextUtils.isEmpty(email)){
            //email is empty and therefore valid
            return true;
        }
        else*/{
            String namePattern = "[a-zA-Z0-9.'()-]";


            return binding.etName.getText().toString().trim().matches(namePattern);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

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
                           /* CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(context, Uri.parse(response.body().getJsonData().get(0).getTokenValue()));
*/
                            CustomTabsIntent customTabsInten = new CustomTabsIntent.Builder().build();
                            CustomTabActivityHelper.openCustomTab(
                                    SurveyNameActivity.this,
                                    customTabsInten,
                                    Uri.parse(response.body().getJsonData().get(0).getTokenValue()),
                                    new WebviewFallback()
                            );
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
