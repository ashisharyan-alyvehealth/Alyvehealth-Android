package com.health.immunity.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivityProfileBinding;
import com.health.immunity.profile.model.GetProfileResponse;
import com.health.immunity.profile.model.ProfileResponse;
import com.health.immunity.profile.model.WorkEmailResponse;
import com.health.immunity.profile.model.updateZohoIdResponse;
import com.health.immunity.retrofit.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.health.immunity.CommonUtils.profilelong;
import static com.health.immunity.IConstant.SERVER_ERROR;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final int CAMERA_PIC_REQUEST = 121;
    private static final int PICK_IMAGE = 122;
    private static final String IMAGE_DIRECTORY = "/Immunity";
    private String imagePath;
    private File file;
    Calendar myCalendar = Calendar.getInstance();
    private MultipartBody.Part body;
    private ActivityProfileBinding binding;
    private String strDOB;

    private boolean isClickable = false;
    private String genderStatus, strLongitude, strLatitude, city, strCurrentLatitude, strCurrentLongitude;
    private String homlatstr="",homlonstr="",offclatstr="",offclonstr="",homeadd="",offcadd="";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;
    FitnessOptions fitnessOptions;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final int RC_SIGN_IN = 111;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    BluetoothAdapter BA;
    int LAUNCH_BLUE_ACTIVITY = 0;
    String workemail="";
    private String currntDate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_profile);
        BA = BluetoothAdapter.getDefaultAdapter();

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
//                .addDataType(DataType.TYPE_HEART_RATE_BPM)
//                .addDataType(DataType.TYPE_BODY_FAT_PERCENTAGE)
//                .addDataType(DataType.TYPE_WORKOUT_EXERCISE)
//                .addDataType(DataType.TYPE_ACTIVITY_SEGMENT)
                .build();

        //  requestPermission();`
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView backiv=findViewById(R.id.backiv);
        backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initViews();
        if (CommonUtils.isInternetAvail(context)) {
            getProfileApi();
        }
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        System.out.println("####################"+ PreferenceHelper.getStringPreference(context, IConstant.GEOLAT));
        if (!PreferenceHelper.getStringPreference(context, IConstant.GEOLAT).equals("0.0") && !PreferenceHelper.getStringPreference(context, IConstant.GEOLONG).equals("0.0"))
        {
            try {
                if (!TextUtils.isEmpty(PreferenceHelper.getStringPreference(context, IConstant.GEOLAT))){
                    CommonUtils.homelat= Double.parseDouble(PreferenceHelper.getStringPreference(context, IConstant.GEOLAT));

                }

                if (!TextUtils.isEmpty(PreferenceHelper.getStringPreference(context, IConstant.GEOLAT))){
                    CommonUtils.homelong=Double.parseDouble(PreferenceHelper.getStringPreference(context, IConstant.GEOLONG));


                }


                android.location.Address address = geocoder.getFromLocation(CommonUtils.homelat, CommonUtils.homelong , 1).get(0);
                String addres = String.valueOf(address);
                addres = addres.substring(addres.lastIndexOf(":") + 1);
                String result = addres.substring(addres.indexOf("[") + 2, addres.indexOf("]") - 1);

                binding.etHome.setText(result);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!PreferenceHelper.getStringPreference(context, IConstant.GEOLATOFF).equals("0.0") && !PreferenceHelper.getStringPreference(context, IConstant.GEOLONGOFF).equals("0.0"))
        {
            try {
            /*    CommonUtils.officelat= Double.parseDouble(PreferenceHelper.getStringPreference(context, IConstant.GEOLATOFF));
                CommonUtils.officelong=Double.parseDouble(PreferenceHelper.getStringPreference(context, IConstant.GEOLONGOFF));

                android.location.Address address = geocoder.getFromLocation(CommonUtils.officelat, CommonUtils.officelong , 1).get(0);
                String addres = String.valueOf(address);
                addres = addres.substring(addres.lastIndexOf(":") + 1);
                String result = addres.substring(addres.indexOf("[") + 2, addres.indexOf("]") - 1);
*/
                binding.etOffice.setText(profilelong);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        /*
         */
                       /* try {
                            android.location.Address address = geocoder.getFromLocation(response.body().getJsonData().getOfficeLocation().getOfficeLat(), response.body().getJsonData().getOfficeLocation().getOfficeLon(), 1).get(0);
                            String addres = String.valueOf(address);
                            addres = addres.substring(addres.lastIndexOf(":") + 1);
                            String result = addres.substring(addres.indexOf("[") + 2, addres.indexOf("]") - 1);

                            binding.etOffice.setText(result);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.FITNESS_ACTIVITY_READ), new Scope(Scopes.FITNESS_BODY_READ), new Scope(Scopes.FITNESS_LOCATION_READ))
                .requestIdToken("303818861029-tkuc5is3lsi2a56l8tk1og5p4hckc10h.apps.googleusercontent.com")
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);

        /*if (PreferenceHelper.getStringPreference(context,FIT_STATUS_BACK).equals("1"))
        {
        }
        else
        {
        }*/
        // System.out.println("####################"+PreferenceHelper.getStringPreference(context, IConstant.FIT_STATUS_BACK));
        if(account != null) {

            // Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
            binding.swtchbtn.setChecked(true);

        }
        else {
            //  Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
            binding.swtchbtn.setChecked(false);
          /*  Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);*/
        }

        if (!BA.isEnabled()) {
            //Toast.makeText(context, "sa", Toast.LENGTH_SHORT).show();
            binding.swtchbtnblue.setChecked(false);
        }
        else
        {
            // Toast.makeText(context, "as", Toast.LENGTH_SHORT).show();
            binding.swtchbtnblue.setChecked(true);
        }
        //requestPermissionnew();
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent= new Intent(this, HomeActivity.class);
//        intent.putExtra("backActivitycount",true);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_error);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnSave);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void getProfileApi() {
       // CommonUtils.showSpotoProgressDialog(context);
        Call<GetProfileResponse> call = RetrofitClient.getUniqInstance().getApi()
                .getProfileCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        /*if (response.body().getJsonData().getUserImages() != null)
                        {
                            Glide.with(context).load(IMAGE_BASE_URL + response.body().getJsonData().getUserImages()).into(binding.circleImage);
                        }*/
                        if (response.body().getJsonData()!=null&&response.body().getJsonData().getGender()!=null&&response.body().getJsonData().getGender().equalsIgnoreCase("Male"))
                        {
                            if (response.body().getJsonData().getUserImages() != null) {
                                Glide.with(context).load(IMAGE_BASE_URL + response.body().getJsonData().getUserImages()).into(binding.circleImage);

                            }
                            else {

                                //Glide.with(context).load(R.drawable.maledp).into(binding.circleImage);
                            }


                        }
                        else if (response.body().getJsonData().getGender().equalsIgnoreCase("Female"))
                        {
                            if (response.body().getJsonData().getUserImages() != null) {

                                Glide.with(context).load(IMAGE_BASE_URL + response.body().getJsonData().getUserImages()).into(binding.circleImage);

                            }
                            else {
                                // Glide.with(context).load(R.drawable.femaledp).into(binding.circleImage);
                            }


                        }
                        else
                        {
                            if (response.body().getJsonData().getUserImages() != null) {
                                Glide.with(context).load(IMAGE_BASE_URL + response.body().getJsonData().getUserImages()).into(binding.circleImage);

                            }
                            else {
                                // Glide.with(context).load(R.drawable.othersdp).into(binding.circleImage);
                            }

                        }
                        binding.tvDesign.setText(response.body().getJsonData().getOrganization());
                        binding.tvUserName.setText(response.body().getJsonData().getName());
                        if (!TextUtils.isEmpty(response.body().getJsonData().getEmail()))
                        {
                            binding.etEmail.setText(response.body().getJsonData().getEmail());
                            workemail=response.body().getJsonData().getEmail();


                        }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getPersonal_email()))
                        {
                            binding.etPersonalEmail.setText(response.body().getJsonData().getPersonal_email());

                        }
//                       if (!TextUtils.isEmpty(PreferenceHelper.getStringPreference(context,PERSONAL_EMAIL_ADD)))
//                        {
//                            binding.etPersonalEmail.setError("Personal email cannot be empty");
//                         //   binding.etPersonalEmail.setText(PreferenceHelper.getStringPreference(context,PERSONAL_EMAIL_ADD));
//
//                        }

                        binding.etMobile.setText(response.body().getJsonData().getPhoneNumber());
                        if (response.body().getJsonData().getHomeLocation()!=null&&!TextUtils.isEmpty(response.body().getJsonData().getHomeLocation().getHomeLat().trim()))
                        {
                            LatLng homeLoc=new LatLng(Double.parseDouble(response.body().getJsonData().getHomeLocation().getHomeLat()),Double.parseDouble(response.body().getJsonData().getHomeLocation().getHomeLon()));
                            binding.etHome.setText(getCompleteAddressString(homeLoc.latitude,homeLoc.longitude));

                        }
                        if (response.body().getJsonData().getHomeLocation()!=null)
                        {
                            LatLng  offLoc=new LatLng(response.body().getJsonData().getOfficeLocation().getOfficeLat(),response.body().getJsonData().getOfficeLocation().getOfficeLon());
                            if (offLoc.latitude!=0)
                                binding.etOffice.setText(getCompleteAddressString(offLoc.latitude,offLoc.longitude));

                        }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getUserAddress().getOfficeLat()))
                        {
                            offclatstr=response.body().getJsonData().getUserAddress().getOfficeLat();

                        }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getUserAddress().getOfficeLon()))
                        {
                            offclonstr=response.body().getJsonData().getUserAddress().getOfficeLon();

                        }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getUserAddress().getHomeLat()))
                        {
                            homlatstr=response.body().getJsonData().getUserAddress().getHomeLat();

                        }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getUserAddress().getHomeLon()))
                        {
                            homlonstr=response.body().getJsonData().getUserAddress().getHomeLon();

                        }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getUserAddress().getOfficeAddress()))
                        {
                            offcadd=response.body().getJsonData().getUserAddress().getOfficeAddress();

                        }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getUserAddress().getHomeAddress()))
                        {
                            homeadd=response.body().getJsonData().getUserAddress().getHomeAddress();

                        }



                        try {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date newDate = format.parse(response.body().getJsonData().getDateOfBirth());
                            currntDate=format.format(newDate);

                            format = new SimpleDateFormat("dd-MMM-yyyy");
                            String date = format.format(newDate);
                            System.out.println("@@@@@@@@@@@   "+date);
                            if(date.equals("01-Jan-1900")){
                                System.out.println("@@@@@@@@@@@executed  "+date);
                                binding.etDOB.setHint("Date of Birth");

                            }else {
                                binding.etDOB.setText(date);
                            }
                            Log.e(TAG, "onBindViewHolder: Date - " + date);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToast(response.body().getMessage());
                    }
                } else {
                    showToast(SERVER_ERROR);
                }
                CommonUtils.dismissSpotoProgressDialog();



            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }

    private void initViews() {


        binding.swtchbtnloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.swtchbtnloc.isChecked())
                {
                    binding.swtchbtnloc.setChecked(true);

                    requestPermissionnew();
                }
                else
                {
                    context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));

                    binding.swtchbtnloc.setChecked(false);

                }
            }
        });

        binding.swtchbtnblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.swtchbtnblue.isChecked())
                {
                    binding.swtchbtnblue.setChecked(true);
                    if (!BA.isEnabled()) {

                        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(turnOn, LAUNCH_BLUE_ACTIVITY);

                    }
                }
                else
                {
                    BA.disable();
                    Toast.makeText(context, "Disable Successfully", Toast.LENGTH_SHORT).show();

                    binding.swtchbtnblue.setChecked(false);

                }
            }
        });

        binding.swtchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.swtchbtn.isChecked())
                {
                    binding.swtchbtn.setChecked(true);
                    if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)) {
                        GoogleSignIn.requestPermissions((Activity) context, REQUEST_OAUTH_REQUEST_CODE,
                                GoogleSignIn.getLastSignedInAccount(context),
                                fitnessOptions);
                    }
                    // Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    //startActivityForResult(signInIntent, RC_SIGN_IN);

                   /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .requestScopes(new Scope(Scopes.FITNESS_ACTIVITY_READ), new Scope(Scopes.FITNESS_BODY_READ), new Scope(Scopes.FITNESS_LOCATION_READ))
                            .requestIdToken("303818861029-tkuc5is3lsi2a56l8tk1og5p4hckc10h.apps.googleusercontent.com")
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
                    account = GoogleSignIn.getLastSignedInAccount(context);
                    if(account != null) {

                    }
                    else {


                              Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                              startActivityForResult(signInIntent, RC_SIGN_IN);


                    }*/

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

     /*   binding.swtchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {

                    CustomTabsIntent customTabsInten = new CustomTabsIntent.Builder().build();
                    CustomTabActivityHelper.openCustomTab(
                            ProfileActivity.this,
                            customTabsInten,
                            Uri.parse(PreferenceHelper.getStringPreference(context,FIT_URL_BACK)+"?auth_token="+PreferenceHelper.getStringPreference(context, IConstant.TOKEN)),
                            new WebviewFallback());


                          binding.swtchbtn.setChecked(true);

                }
                else
                {
                    binding.swtchbtn.setChecked(false);


                    CustomTabsIntent customTabsInten = new CustomTabsIntent.Builder().build();
                    CustomTabActivityHelper.openCustomTab(
                            ProfileActivity.this,
                            customTabsInten,
                            Uri.parse(PreferenceHelper.getStringPreference(context,FIT_URL_BACK)+"?auth_token="+PreferenceHelper.getStringPreference(context, IConstant.TOKEN)+"&task=signOut"),
                            new WebviewFallback());


                }
            }
        });*/
        if (isClickable) {
            binding.circleImage.setOnClickListener(this);
            binding.editImage.setOnClickListener(this);
        }
        binding.btnSave.setOnClickListener(this);
        binding.tvEdit.setOnClickListener(this);
        binding.etDOB.setOnClickListener(this);
        binding.etOffice.setOnClickListener(this);
        binding.etHome.setOnClickListener(this);
        binding.tveditmore.setOnClickListener(this);
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {

                        //   Toast.makeText(context, "Sign out Successfully", Toast.LENGTH_SHORT).show();

                    }


                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circleImage:
            case R.id.editImage:
                if (isStoragePermissionGranted()) {
//                    showPictureDialog();
                    initiateImage();
                } else {
                    showToast("Permission required");
                }
                break;
            case R.id.btnSave:
                if(TextUtils.isEmpty(binding.etPersonalEmail.getText().toString())){
                    binding.etPersonalEmail.setError("Personal email can not be empty");
                    return;
                }
                if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())&&TextUtils.isEmpty(binding.etPersonalEmail.getText().toString().trim())){
                    showDialog(this);
                    return;

                }

                if(!isValid(binding.etEmail.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                }
                else if(!isperValid(binding.etPersonalEmail.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(),"Invalid personal email address",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (CommonUtils.isInternetAvail(context))
                    {
                        if (!TextUtils.isEmpty(binding.etEmail.getText().toString().trim()))
                        {
                            if (!workemail.equalsIgnoreCase(binding.etEmail.getText().toString()))
                            {
                                //  workEmail();
                                profileSetApi();


                            }
                            else
                            {
                                profileSetApi();
                            }

                        }
                        else
                        {
                            profileSetApi();
                        }

                    }

                }

                break;
            case R.id.tvEdit:
                isClickable = true;
                initViews();
                isEnabled();
                break;
            case R.id.etDOB:
                getDatePick();
                break;
            case R.id.tveditmore:
           //     startActivity(new Intent(ProfileActivity.this,EditMoreDetails.class));
                break;
            case R.id.etOffice:
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    askForPermissionnew();
                    /*AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
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
             /*   Intent mapIntent = new Intent(context, MapActivity.class);
//                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent, ADDRESS_CAPTURE);*/
                break;
            case R.id.etHome:

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    askForPermissionnew();
                    /*AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
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
                    Intent mapIntent1 = new Intent(context, MapActivity.class);
                    startActivityForResult(mapIntent1, ADDRESS_CAPTURE2);
                }

               /* Intent mapIntent1 = new Intent(context, MapActivity.class);
//                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent1, ADDRESS_CAPTURE2);*/
                break;
        }
    }

    private void profileSetApi() {
        String datestr="";
        if (imagePath != null) {
            file = new File(imagePath);
            Log.e("---== ", "editProfile: " + file);
            RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
            body = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
            Log.e("---== ", "editProfileBody: " + body.toString());
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            Date newDate = format.parse(binding.etDOB.getText().toString().trim());

            format = new SimpleDateFormat("yyyy-MM-dd");
            datestr = format.format(newDate);
            Log.e(TAG, "onBindViewHolder: Date - " + datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody name= RequestBody.create(binding.tvUserName.getText().toString().trim(),MediaType.parse("multipart/form-data"));
        RequestBody perosnalemails = RequestBody.create(binding.etPersonalEmail.getText().toString().trim(), MediaType.parse("multipart/form-data"));
        RequestBody emails = RequestBody.create(binding.etEmail.getText().toString().trim(), MediaType.parse("multipart/form-data"));
        RequestBody dob = RequestBody.create(datestr, MediaType.parse("multipart/form-data"));
        RequestBody numbers = RequestBody.create(binding.etMobile.getText().toString().trim(), MediaType.parse("multipart/form-data"));
        RequestBody reqoffclan = RequestBody.create(offclatstr, MediaType.parse("multipart/form-data"));
        RequestBody reqoffclon = RequestBody.create(offclonstr, MediaType.parse("multipart/form-data"));
        RequestBody reqhomlat = RequestBody.create(homlatstr, MediaType.parse("multipart/form-data"));
        RequestBody reqhomlon = RequestBody.create(homlonstr, MediaType.parse("multipart/form-data"));
        RequestBody reqhomadd = RequestBody.create(homeadd, MediaType.parse("multipart/form-data"));
        RequestBody reqoffcadd = RequestBody.create(offcadd, MediaType.parse("multipart/form-data"));
//        CommonUtils.showSpotoProgressDialog(context);
        Call<ProfileResponse> call = RetrofitClient.getUniqInstance().getApi()
                .profileCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), name,numbers, dob, emails,perosnalemails,reqoffclan,reqoffclon,reqhomlat,reqhomlon,reqhomadd,reqoffcadd, body);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        finish();
                        startActivity(new Intent(context, ProfileActivity.class));
//                        finish();
                        //  showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }
                } else {
                    showToast(SERVER_ERROR);
                }
              //  CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                t.printStackTrace();
             //   CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }

    public void initiateImage() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, CAMERA_PIC_REQUEST);*/
            showPictureDialog();
        } else {
            askForPermission();
        }
    }

    private boolean askForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, CAMERA_PIC_REQUEST);*/
                showPictureDialog();
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PIC_REQUEST);
                return false;
            }
        } else {
            /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, CAMERA_PIC_REQUEST);*/
            showPictureDialog();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PIC_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("onRequestPermission ", "Permission: " + permissions[0] + "was " + grantResults[0]);
                    showPictureDialog();
                } else {
                    showSettingsDialog();
                }
                break;
            case LOCATION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        binding.swtchbtnloc.setChecked(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    binding.swtchbtnloc.setChecked(false);

                }
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (data != null) {
                if (data.getExtras() != null) {
                    isEnabled();
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    binding.circleImage.setImageBitmap(image);
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                /*Uri tempUri = getImageUri(getApplicationContext(), image);
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));

                Log.e("--== ", "onActivityResult: " + finalFile);*/
                    if (image != null) {
                        saveImage(image);
                    }
                }
            }
        } else if (requestCode == PICK_IMAGE) {
//            binding.circleProfileImage.setImageBitmap();
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    //showToast("Image Saved");
                    binding.circleImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    showToast("Failed");
                }
            }
        }
        else if (requestCode == ADDRESS_CAPTURE2) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String address = data.getStringExtra("address");
                    strLatitude = data.getStringExtra("latitude");
                    strLongitude = data.getStringExtra("longitude");
//                    LatLng location = new LatLng(Double.parseDouble(strLatitude), Double.parseDouble(strLongitude));
                    Log.e(TAG, "onActivityResult: Office Latitude " + strLatitude);
                    Log.e(TAG, "onActivityResult: " + address);
                    if (strLatitude != null) {
                        binding.etHome.setText(address);
                        homlatstr=data.getStringExtra("latitude");
                        homlonstr=data.getStringExtra("longitude");
                        homeadd=data.getStringExtra("address");
                    } else {
                        Log.e(TAG, "onActivityResult: City -- = " + city);
                        binding.etHome.setText(city);
                        strLatitude = strCurrentLatitude;
                        strLongitude = strCurrentLongitude;
                    }
                } else {
                    showToast("Address not found");
                }
            }
        }
        else if (requestCode == ADDRESS_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String address = data.getStringExtra("address");
                    strLatitude = data.getStringExtra("latitude");
                    strLongitude = data.getStringExtra("longitude");
//                    LatLng location = new LatLng(Double.parseDouble(strLatitude), Double.parseDouble(strLongitude));
                    Log.e(TAG, "onActivityResult: Office Latitude " + strLatitude);
                    Log.e(TAG, "onActivityResult: " + address);
                    if (strLatitude != null) {
                        binding.etOffice.setText(address);
                        offclatstr=data.getStringExtra("latitude");
                        offclonstr=data.getStringExtra("longitude");
                        offcadd=data.getStringExtra("address");
                    } else {
                        Log.e(TAG, "onActivityResult: City -- = " + city);
                        binding.etOffice.setText(city);
                        strLatitude = strCurrentLatitude;
                        strLongitude = strCurrentLongitude;
                    }
                } else {
                    showToast("Address not found");
                }
            }
        }
        else if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else if (requestCode == LOCATION_REQUEST_CODE) {

            binding.swtchbtnloc.setChecked(true);


        }
        else if (requestCode == 7) {

            binding.swtchbtnloc.setChecked(true);


        }
        else if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
            com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
        if (requestCode==LAUNCH_BLUE_ACTIVITY)
        {
            if (!BA.isEnabled()) {
                binding.swtchbtnblue.setChecked(false);
                // Toast.makeText(context, "sa", Toast.LENGTH_SHORT).show();

            }
            else
            {
                binding.swtchbtnblue.setChecked(true);

                // Toast.makeText(context, "as", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = null;
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 500, 500, true);
        if (isStoragePermissionGranted()) {
            try {
                path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSettings();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("---", "Permission is granted");
                return true;
            } else {
                Log.e("---", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("--- ", "Permission is granted");
            return true;
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG -- ", "File Saved::---&gt;" + f.getAbsolutePath());
            imagePath = f.getAbsolutePath();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void isEnabled() {
        binding.tvUserName.setEnabled(true);
        binding.etDOB.setEnabled(true);
        binding.etDOB.setTextColor(getResources().getColor(R.color.colorText));
        binding.etMobile.setEnabled(true);
        binding.etEmail.setEnabled(true);
        binding.etPersonalEmail.setEnabled(true);
        binding.etOffice.setEnabled(true);
        binding.etHome.setEnabled(true);
        binding.tvEdit.setVisibility(View.INVISIBLE);
        binding.circleImage.setEnabled(true);
        binding.editImage.setVisibility(View.VISIBLE);
        binding.btnSave.setVisibility(View.VISIBLE);
    }

    private void getDatePick() {
        Calendar newCalendar=null;
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(MY_DATE_FORMAT_PROFILE);
        Calendar calendar = Calendar.getInstance();
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (!TextUtils.isEmpty(currntDate)){
            int yer= (Integer.parseInt(currntDate.split("-")[0]));

            int mon= (Integer.parseInt(currntDate.split("-")[1]));

            int day= (Integer.parseInt(currntDate.split("-")[2]));
            newCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            newCalendar.set(yer, (mon-1), day);


        }
        else
            newCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR) - 25, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();

                if (currentYear - year == 12) {
                    if (Calendar.getInstance().get(Calendar.MONTH) > monthOfYear) {
                        newDate.set(year, monthOfYear, dayOfMonth);
                        binding.etDOB.setText((dateFormatter.format(newDate.getTime())));
                    } else if (Calendar.getInstance().get(Calendar.MONTH) == monthOfYear) {
                        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= dayOfMonth) {
                            newDate.set(year, monthOfYear, dayOfMonth);
                            binding.etDOB.setText((dateFormatter.format(newDate.getTime())));
                        } else {
                            CommonUtils.showCustomDialog(context, getString(R.string.not_eleigible_for_app), IConstant.OKAY);
                            binding.etDOB.setText("");
                        }
                    } else {
                        CommonUtils.showCustomDialog(context, getString(R.string.not_eleigible_for_app), IConstant.OKAY);
                        binding.etDOB.setText("");
                    }

                } else if (currentYear - year < 12) {
                    CommonUtils.showCustomDialog(context, getString(R.string.not_eleigible_for_app), IConstant.OKAY);
                    binding.tvDOB.setText("");
                } else {
                    newDate.set(year, monthOfYear, dayOfMonth);
                    binding.etDOB.setText((dateFormatter.format(newDate.getTime())));
                }
            }
        }, 2000,01, 01);
        String date=binding.etDOB.getText().toString();
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
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
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    private Address getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            if (addresses != null) {
                Address address = addresses.get(0);

                return address;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
         if (requestCode == RC_SIGN_IN) {
             // The Task returned from this call is always completed, no need to attach
             // a listener.
             com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
             handleSignInResult(task);
         }
     }*/
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            if(account != null)
            {
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@sis");
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

    private void requestPermissionnew() {
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            askForPermissionnew();
            binding.swtchbtnloc.setChecked(false);

        }
        else
        {

            binding.swtchbtnloc.setChecked(true);
        }

    }

    private void askForPermissionnew() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                binding.swtchbtnloc.setChecked(true);
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
    public boolean isValid(final String email) {
        if(TextUtils.isEmpty(email)){
            //email is empty and therefore valid
            return true;
        }
        else{
            String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+";

            return binding.etEmail.getText().toString().trim().matches(emailPattern);
        }

    }

    public boolean isperValid(final String email) {
        if(TextUtils.isEmpty(email)){
            //email is empty and therefore valid
            return true;
        }
        else{
            String emailPattern = "[a-zA-Z0-9._-]+@+[a-zA-Z0-9._-]+";


            return binding.etPersonalEmail.getText().toString().trim().matches(emailPattern);
        }

    }


    private void workEmail() {
        Call<WorkEmailResponse> call = RetrofitClient.getUniqInstance().getApi()
                .workEmailCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), binding.etEmail.getText().toString(), "0");
        call.enqueue(new Callback<WorkEmailResponse>() {
            @Override
            public void onResponse(Call<WorkEmailResponse> call, Response<WorkEmailResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {

                        if (response.body().getStatus().equalsIgnoreCase("true"))
                        {
                            profileSetApi();
                        }
                        else
                        {
                            profileSetApi();
                        }
                    } else {
                        showToast(SERVER_ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkEmailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}