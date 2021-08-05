package com.health.immunity.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivityDummyTncBinding;
import com.health.immunity.login.model.GetTersmApi;
import com.health.immunity.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.health.immunity.IConstant.SERVER_ERROR;

public class DummyTncActivity extends BaseActivity implements View.OnClickListener{

    private ActivityDummyTncBinding binding;
    private boolean isAgree = false;
    private String strName, strDOB, strGender;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_dummy_tnc);
        initViews();
    }

    private void initViews() {
        if (CommonUtils.isInternetAvail(context)) {
            termsAndCondApi();
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        int macAddress = wInfo.getIpAddress();
        Log.e(TAG, "initViews: " + macAddress);

        if (getIntent() != null) {
            strName = getIntent().getStringExtra("name");
            strDOB = getIntent().getStringExtra("dob");
            strGender = getIntent().getStringExtra("genderStatus");
        }
        binding.cbAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isAgree = true;
                //binding.btn.setText(getResources().getString(R.string.iagree));
                binding.btn.setBackground(getResources().getDrawable(R.drawable.gradiant, null));
            } else {
                //Not Agree
                isAgree = false;
                // binding.btn.setText(getResources().getString(R.string.i_don_t_agree));
                binding.btn.setBackground(getResources().getDrawable(R.drawable.grey_gradiant, null));
            }
        });
        binding.btn.setOnClickListener(this);
        binding.ivLogo.setOnClickListener(this);
        binding.ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void termsAndCondApi() {
        Call<GetTersmApi> getTersmApiCall = RetrofitClient.getUniqInstance().getApi().getTermsCall("1");
        getTersmApiCall.enqueue(new Callback<GetTersmApi>() {
            @Override
            public void onResponse(Call<GetTersmApi> call, Response<GetTersmApi> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        binding.tvTermsAnd.setText(response.body().getJsonData().getContent());
                        id = response.body().getJsonData().getId() + "";
                    } else {
                        showToast("");
                    }
                }
            }

            @Override
            public void onFailure(Call<GetTersmApi> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            if (isAgree) {
                postTermsApi(getIntent().getStringExtra("mobileNumber"),
                        getIntent().getStringExtra("email"),
                        Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID),
                        id, CommonUtils.getAddress(context));
            } else {
                //Intent intent1 = new Intent(context, SurveyMobileActivity.class);
                // intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent1);
            }
        }
    }

    private void postTermsApi(String mobileNumber, String email, String deviceId, String id, String macAddress) {
        Call<GetTersmApi> call = RetrofitClient.getUniqInstance().getApi()
                .postTermsCall(mobileNumber, email, deviceId, id, macAddress,"1");
        call.enqueue(new Callback<GetTersmApi>() {
            @Override
            public void onResponse(Call<GetTersmApi> call, Response<GetTersmApi> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Intent intent1 = new Intent(context, SurveyGenderActivity.class);
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
                            //intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);
                        } else {
                            showToast(response.body().getMessage());
                        }
                    } else {
                        showToast(SERVER_ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetTersmApi> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}