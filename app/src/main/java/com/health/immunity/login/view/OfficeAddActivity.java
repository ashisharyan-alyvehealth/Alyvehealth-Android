package com.health.immunity.login.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.health.immunity.BaseActivity;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivityOfficeAddBinding;
import com.health.immunity.profile.MapActivity;

import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class OfficeAddActivity extends BaseActivity implements View.OnClickListener {

    private ActivityOfficeAddBinding binding;
    private String genderStatus, strLongitude, strLatitude, city, strCurrentLatitude, strCurrentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_office_add);

        initViews();
    }

    private void initViews() {
        //    requestPermission();
//        binding.etLocation.setAdapter(new PlaceAutoSuggestAdapter(context, R.layout.list_item));
        binding.ivNext.setOnClickListener(this);
        binding.etLocation.setOnClickListener(this);
        binding.textskip.setOnClickListener(this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
   /*     if (getIntent().getStringExtra("genderStatus").equalsIgnoreCase("female")) {
            binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.english_blur_selected, null));
            binding.tvFemale.setTextColor(Color.WHITE);
//            genderStatus = "female";
        } else if (getIntent().getStringExtra("genderStatus").equalsIgnoreCase("male")) {
            binding.tvMale.setBackground(getResources().getDrawable(R.drawable.male_blur_selected, null));
            binding.tvMale.setTextColor(Color.WHITE);
  //          genderStatus = "male";
        } else if (getIntent().getStringExtra("genderStatus").equalsIgnoreCase("binary")) {
            binding.tvNonBinary.setBackground(getResources().getDrawable(R.drawable.hindi_blur_selected, null));
            binding.tvNonBinary.setTextColor(Color.WHITE);
    //        genderStatus = "binary";
        } else if (getIntent().getStringExtra("genderStatus").equalsIgnoreCase("disclose")) {
            binding.tvDisclose.setBackground(getResources().getDrawable(R.drawable.button_blur_background, null));
            binding.tvDisclose.setTextColor(Color.WHITE);
      //      genderStatus = "disclose";
        }*/

        // binding.layout.setOnClickListener(this);

  /*      binding.etLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                        showToast("Please enter Office Address");
                    } else {
                        Intent intent1 = new Intent(context, HomeAddActivity.class);
                        if (getIntent().getStringExtra("comesFrom") != null) {
                            intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                        }
                        intent1.putExtra("officeLocation", binding.etLocation.getText().toString());
                        intent1.putExtra("genderStatus", genderStatus);
                        intent1.putExtra("name", getIntent().getStringExtra("name"));
                        intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                        startActivity(intent1);
                    }
                }
                return false;
            }
        });*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout:
                finish();
                break;
            case R.id.ivNext:

                //  if (PreferenceHelper.getIntPreference(context, IConstant.IS_VERIFIEDUSER)==1)
                //{
                   /* if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                        showToast("Please enter office address");
                    } else*/
            {
//                    reverseGeocode(binding.etLocation.getText().toString());
                Intent intent1 = new Intent(context, HomeAddActivity.class);
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intent1.putExtra("officeLocation", binding.etLocation.getText().toString());
                intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                intent1.putExtra("name", getIntent().getStringExtra("name"));
                intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                intent1.putExtra("officelatitude", strLatitude);
                intent1.putExtra("officelongitude", strLongitude);
                intent1.putExtra("email", getIntent().getStringExtra("email"));
                intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                startActivity(intent1);
            }
            // }
            /*    else if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
                {
                    if (TextUtils.isEmpty(binding.etLocation.getText().toString())) {
                        showToast("Please enter office address");
                    } else {
//                    reverseGeocode(binding.etLocation.getText().toString());
                        Intent intent1 = new Intent(context, HomeAddActivity.class);
                        if (getIntent().getStringExtra("comesFrom") != null) {
                            intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                        }
                        intent1.putExtra("officeLocation", binding.etLocation.getText().toString());
                        intent1.putExtra("genderStatus", genderStatus);
                        intent1.putExtra("name", getIntent().getStringExtra("name"));
                        intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                        intent1.putExtra("officelatitude", strLatitude);
                        intent1.putExtra("officelongitude", strLongitude);
                        intent1.putExtra("email", getIntent().getStringExtra("email"));
                        intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                        intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                        startActivity(intent1);
                    }
                }
                else
                {
                    Intent intent1 = new Intent(context, HomeAddActivity.class);
                    if (getIntent().getStringExtra("comesFrom") != null) {
                        intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                    }
                    intent1.putExtra("officeLocation", binding.etLocation.getText().toString());
                    intent1.putExtra("genderStatus", genderStatus);
                    intent1.putExtra("name", getIntent().getStringExtra("name"));
                    intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                    intent1.putExtra("officelatitude", strLatitude);
                    intent1.putExtra("officelongitude", strLongitude);
                    intent1.putExtra("email", getIntent().getStringExtra("email"));
                    intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                    intent1.putExtra("accessCode", getIntent().getStringExtra("accessCode"));
                    startActivity(intent1);
                }*/

            break;

            case R.id.textskip:


                Intent intent1 = new Intent(context, HomeAddActivity.class);
                if (getIntent().getStringExtra("comesFrom") != null) {
                    intent1.putExtra("comesFrom", getIntent().getStringExtra("comesFrom"));
                }
                intent1.putExtra("officeLocation", binding.etLocation.getText().toString());
                intent1.putExtra("genderStatus", getIntent().getStringExtra("genderStatus"));
                intent1.putExtra("name", getIntent().getStringExtra("name"));
                intent1.putExtra("dob", getIntent().getStringExtra("dob"));
                intent1.putExtra("officelatitude", strLatitude);
                intent1.putExtra("officelongitude", strLongitude);
                intent1.putExtra("email", getIntent().getStringExtra("email"));
                intent1.putExtra("mobileNumber", getIntent().getStringExtra("mobileNumber"));
                startActivity(intent1);



                break;
            case R.id.etLocation:
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {

                    askForPermissionnew();
                 /* AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_profile, null);
                    Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                    Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
                    TextView textView = (TextView) dialogView.findViewById(R.id.textView);
                    textView.setText("Please provide location access to 'Immunity Health' in settings");

                    button2.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view) {


                            dialogBuilder.dismiss();

                        }
                    });
                    button1.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view) {

                            dialogBuilder.dismiss();

                            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));


                        }
                    });

                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();*/
                }
                else
                {
                    Intent mapIntent = new Intent(context, MapActivity.class);
                    startActivityForResult(mapIntent, ADDRESS_CAPTURE);
                }
/*//                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(context, MapActivity.class);
//                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent, ADDRESS_CAPTURE);*/
                break;
        }
    }
    private void askForPermissionnew() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                Intent mapIntent = new Intent(context, MapActivity.class);
                startActivityForResult(mapIntent, ADDRESS_CAPTURE);
            }

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        7);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String address = data.getStringExtra("address");
                    strLatitude = data.getStringExtra("latitude");
                    strLongitude = data.getStringExtra("longitude");
//                    LatLng location = new LatLng(Double.parseDouble(strLatitude), Double.parseDouble(strLongitude));
                    Log.e(TAG, "onActivityResult: Office Latitude " + strLatitude);
                    Log.e(TAG, "onActivityResult: " + address);
                    if (strLatitude != null) {
                        binding.etLocation.setText(address);
                    } else {
                        Log.e(TAG, "onActivityResult: City -- = " + city);
                        binding.etLocation.setText(city);
                        strLatitude = strCurrentLatitude;
                        strLongitude = strCurrentLongitude;
                    }
                } else {
                    showToast("No address fetched!");
                }
            }
        }
        else if (requestCode == LOCATION_REQUEST_CODE) {

            Intent mapIntent = new Intent(context, MapActivity.class);
            startActivityForResult(mapIntent, ADDRESS_CAPTURE);


        }
        else if (requestCode == 7) {

            Intent mapIntent = new Intent(context, MapActivity.class);
            startActivityForResult(mapIntent, ADDRESS_CAPTURE);


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case LOCATION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        Intent mapIntent = new Intent(context, MapActivity.class);
                        startActivityForResult(mapIntent, ADDRESS_CAPTURE);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.


                }
                break;


        }
    }

    private void requestPermission() {
        /*if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForPermission();
        } else {
            getDeviceLocation();
        }*/
        ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        //getDeviceLocation();
    }

    private void askForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //  getDeviceLocation();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                //getDeviceLocation();
            }
        } else {
            // getDeviceLocation();
        }
    }



    private void reverseGeocode(String myAddress) {
        Geocoder coder = new Geocoder(context);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(myAddress, 1);
            double longitude = adresses.get(0).getLongitude();
            double latitude = adresses.get(0).getLatitude();
            Log.e(TAG, "reverseGeocode: " + longitude);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}

