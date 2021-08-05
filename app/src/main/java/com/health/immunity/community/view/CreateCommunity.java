package com.health.immunity.community.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.community.adapter.ContactShowAdapter;
import com.health.immunity.community.adapter.NewContactListAdapter;
import com.health.immunity.community.model.CommunityResponse;
import com.health.immunity.community.model.CommunityUserListResponse;
import com.health.immunity.community.model.Contact;
import com.health.immunity.databinding.ActivityCreateCommunityBinding;
import com.health.immunity.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.health.immunity.IConstant.SERVER_ERROR;

public class CreateCommunity extends BaseActivity {

    ActivityCreateCommunityBinding binding;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    final int CONTACT_PICK_REQUEST = 1000;
    private ContactShowAdapter contactShowAdapter;
    private NewContactListAdapter newcontactShowAdapter;
    private List<CommunityUserListResponse.JsonDatum> comlist = new ArrayList<>();

    private int extend_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(activity, R.layout.activity_create_community);

        initViews();

    }

    private void initViews() {
        binding.ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContacts();
            }
        });
        binding.ivcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.etEmail.getText().toString()))
                {
                    showToast("Please enter the name");

                }
                else if (TextUtils.isEmpty(binding.etMobile.getText().toString()))
                {
                    showToast("Please enter the description");

                }
                else
                {
                    createcommunityApi(binding.etEmail.getText().toString().trim(),binding.etMobile.getText().toString().trim());
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            Intent intentContactPick = new Intent(CreateCommunity.this, ContactsPickerActivity.class);
            intentContactPick.putExtra("invit_count", extend_count);
            startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                showContacts();
            } else
            {
                Toast.makeText(this, "Until you grant the permission, we can't display the names", Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK){
            binding.txtSelectedContacts.setVisibility(View.VISIBLE);

            ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");

            String display="";
            for(int i=0;i<selectedContacts.size();i++){

                display += (i+1)+". "+selectedContacts.get(i).toString()+"\n";

            }
        /*    contactShowAdapter = new ContactShowAdapter(context, selectedContacts);
            binding.rvshowcontact.setLayoutManager(new LinearLayoutManager(context));
            binding.rvshowcontact.setAdapter(contactShowAdapter);
*/
            //binding.txtSelectedContacts.setText("Invited people \n\n"+display);
            usercommApi(String.valueOf(extend_count));

        }

    }

    private void createcommunityApi(String sub, String desc) {
        //CommonUtils.showSpotoProgressDialog(context);
        Call<CommunityResponse> call = RetrofitClient.getUniqInstance().getApi()
                .communityCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), sub, desc);
        call.enqueue(new Callback<CommunityResponse>() {
            @Override
            public void onResponse(Call<CommunityResponse> call, Response<CommunityResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            showToast(response.body().getMessage());
                            System.out.println("@@@@@@@@@@@@@@@@@Response"+ response.body().toString());
                            extend_count=response.body().getJsonData().getId();
                            binding.etEmail.setEnabled(false);
                            binding.etMobile.setEnabled(false);
                            binding.ivcreate.setVisibility(View.INVISIBLE);
                            binding.btnSave.setVisibility(View.VISIBLE);
                        } else {
                            showToast(response.body().getMessage());
                        }
                    } else {
                        showToast(SERVER_ERROR);
                    }
                } else {
                    showToast(SERVER_ERROR);
                }
               // CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<CommunityResponse> call, Throwable t) {
                t.printStackTrace();
              //  CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }



    private void usercommApi(String id) {
       // CommonUtils.showSpotoProgressDialog(context);
        Call<CommunityUserListResponse> call = RetrofitClient.getUniqInstance().getApi()
                .communityUserListResponseCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), id);
        call.enqueue(new Callback<CommunityUserListResponse>() {
            @Override
            public void onResponse(Call<CommunityUserListResponse> call, Response<CommunityUserListResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            System.out.println("@@@@@@@@@@@@@@@@@Response"+ response.body().toString());

                            newcontactShowAdapter = new NewContactListAdapter(context, response.body().getJsonData());
                            binding.rvshowcontact.setLayoutManager(new LinearLayoutManager(context));
                            binding.rvshowcontact.setAdapter(newcontactShowAdapter);

                            //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
               // CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<CommunityUserListResponse> call, Throwable t) {
                t.printStackTrace();
              //  CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }
}

