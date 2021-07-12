package com.health.immunity.login;
/* this class is for the purpose to serve the login view to the user*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.health.immunity.BaseActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivityLoginBinding;
import com.health.immunity.login.presenter.IloginPresenter;
import com.health.immunity.login.presenter.LoginPresenter;
import com.health.immunity.login.view.DummyHomeActivity;
import com.health.immunity.login.view.DummyTncActivity;
import com.health.immunity.login.view.Ilogin;
import com.health.immunity.login.view.OTPActivity;

public class LoginActivity extends BaseActivity implements Ilogin, TextWatcher {
EditText phoneNumber;
IloginPresenter presenter;
private ActivityLoginBinding binding;
String phoneN;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter= new LoginPresenter(this);
        context=this;
        phoneNumber=findViewById(R.id.etMobile);

        //onPhoneNumber completion

        phoneNumber.addTextChangedListener(this);
        phonenumEditorAction();

    }

    public void phonenumEditorAction(){
        phoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (isValidate()) {

                        presenter.sendOtp(phoneNumber.getText().toString().trim());


                    }
                }
                return false;
            }
        });

    }

    @Override
    public void onSuccess(String message) {
        showToast(message);
    }

    @Override
    public void onError(String message) {
        showToast(message);
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(phoneNumber.getText().toString().trim())) {
            showToast("Please enter mobile number");
            Log.e(TAG, "afterTextChanged: Not Valid");
        } else if (!isValidMobile(phoneNumber.getText().toString().trim())) {
            Log.e(TAG, "afterTextChanged: Not Valid");
        } else {
            //showToast("Valid Mobile");
            //    binding.ivChekc.setVisibility(View.VISIBLE);
            if (isValidate()) {
                    presenter.sendOtp(phoneNumber.getText().toString().trim());
                    sendPhonenumtoOtpActivity(phoneNumber.getText().toString().trim());
            }
            Log.e(TAG, "afterTextChanged: Valid");
        }


    }
    private boolean isValidate() {
        if (TextUtils.isEmpty(phoneNumber.getText().toString().trim())) {
            showToast("Please enter mobile number");
            return false;
        } else if (!isValidMobile(phoneNumber.getText().toString().trim())) {
            showToast("Please enter valid mobile number");
            return false;
        }
        return true;
    }

    private void sendPhonenumtoOtpActivity(String phonenumb){
        Intent intent= new Intent(context, OTPActivity.class);
        intent.putExtra("Phonenumber",phonenumb);
        startActivity(intent);
    }
    @Override
    public void saveDataToPreferenceAndJumpToHome(String hrid, String token, String phone, int isVerifiedComm, String onBoardingStatus,
                                     String name,String dateOfBirth, String gender, String email) {
    }

}