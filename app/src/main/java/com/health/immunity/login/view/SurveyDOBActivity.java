package com.health.immunity.login.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import androidx.databinding.DataBindingUtil;

import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivitySurveyDOBBinding;
import com.health.immunity.login.utility.GooglefitPermission;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SurveyDOBActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySurveyDOBBinding binding;
    Calendar myCalendar = Calendar.getInstance();
    private String strDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_survey_d_o_b);
        initViews();
    }

    private void initViews() {
        //binding.etName.setText(getIntent().getStringExtra("name"));
        binding.tvDOB.setOnClickListener(this);
        binding.ivNext.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.textskip.setOnClickListener(this);
        if (getIntent() != null) {
            strDOB = getIntent().getStringExtra("dob");
            binding.tvDOB.setText(strDOB);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDOB:
                getDatePick();
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivNext:
                //  if (!TextUtils.isEmpty(binding.tvDOB.getText().toString().trim())) {
                Intent intent1 = new Intent(context, GooglefitPermission.class);
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intent1.putExtra("dob", binding.tvDOB.getText().toString());
                intent1.putExtra("name", getIntent().getStringExtra("name"));
                intent1.putExtra("email", getIntent().getStringExtra("email"));
                intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                startActivity(intent1);
               /* }
                else {
                    showToast("Please enter date of birth");
                }*/
                break;
            case R.id.textskip:

                Intent intent11 = new Intent(context, GooglefitPermission.class);
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intent11.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intent11.putExtra("dob", binding.tvDOB.getText().toString());
                intent11.putExtra("name", getIntent().getStringExtra("name"));
                intent11.putExtra("email", getIntent().getStringExtra("email"));
                intent11.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                intent11.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                startActivity(intent11);

                break;

        }
    }

    private void getDatePick() {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(MY_DATE_FORMAT_PROFILE);
        Calendar calendar = Calendar.getInstance();
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Calendar newCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR) - 12, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();

                if (currentYear - year == 12) {
                    if (Calendar.getInstance().get(Calendar.MONTH) > monthOfYear) {
                        newDate.set(year, monthOfYear, dayOfMonth);
                        binding.tvDOB.setText((dateFormatter.format(newDate.getTime())));
                        binding.tvDOBage.setText("You are a "+getAge(year,monthOfYear,dayOfMonth)+" yrs old!");
                    } else if (Calendar.getInstance().get(Calendar.MONTH) == monthOfYear) {
                        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= dayOfMonth) {
                            newDate.set(year, monthOfYear, dayOfMonth);
                            binding.tvDOB.setText((dateFormatter.format(newDate.getTime())));
                            binding.tvDOBage.setText("You are a "+getAge(year,monthOfYear,dayOfMonth)+" yrs old!");

                        } else {
                            CommonUtils.showCustomDialog(context, getString(R.string.not_eleigible_for_app), IConstant.OKAY);
                            binding.tvDOB.setText("");
                        }
                    } else {
                        CommonUtils.showCustomDialog(context, getString(R.string.not_eleigible_for_app), IConstant.OKAY);
                        binding.tvDOB.setText("");
                    }

                } else if (currentYear - year < 12) {
                    CommonUtils.showCustomDialog(context, getString(R.string.not_eleigible_for_app), IConstant.OKAY);
                    binding.tvDOB.setText("");
                } else {
                    newDate.set(year, monthOfYear, dayOfMonth);
                    binding.tvDOB.setText((dateFormatter.format(newDate.getTime())));
                    binding.tvDOBage.setText("You are a "+getAge(year,monthOfYear,dayOfMonth)+" yrs old!");

                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}

