package com.health.immunity.community.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.community.adapter.ContactsListAdapter;
import com.health.immunity.community.adapter.onClickInterface;
import com.health.immunity.community.model.ContactsList;
import com.health.immunity.community.model.InvitResponse;
import com.health.immunity.community.model.InviteMemberResponse;
import com.health.immunity.community.utility.ContactsLoader;
import com.health.immunity.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsPickerActivity extends BaseActivity {

    ListView contactsChooser;
    ImageView ivHeader;
    Button btnDone;
    EditText txtFilter;
    TextView txtLoadInfo;/*, usercount;*/
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;
    String arr;
    int nucount = 0;
    int sentcount = 0;
    private onClickInterface onclickInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_picker);
        // usercount = findViewById(R.id.usercount);
        nucount = getIntent().getIntExtra("invit_count", 0);
        //  CommonUtils.countsend = nucount;
        // usercount.setText(0 + "/" + (10 - nucount) + " Invite");

        onclickInterface = new onClickInterface() {
            @Override
            public void setClick(int abc) {
               /* if (abc > 10) {
                    Toast.makeText(context, "You can not send more than 10 invites", Toast.LENGTH_SHORT).show();

                } else {
                    sentcount = abc;
                    usercount.setText(abc + "/" + (10 - nucount) + " Invite");
                }*/
            }
        };


        ivHeader = findViewById(R.id.ivHeader);
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        contactsChooser = findViewById(R.id.lst_contacts_chooser);
        btnDone = findViewById(R.id.btn_done);
        txtFilter = findViewById(R.id.txt_filter);
        txtLoadInfo = findViewById(R.id.txt_load_progress);


        contactsListAdapter = new ContactsListAdapter(this, new ContactsList(), onclickInterface);

        contactsChooser.setAdapter(contactsListAdapter);


        loadContacts("");


        txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsListAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contactsListAdapter.selectedContactsList.contactArrayList.isEmpty()){
                    setResult(RESULT_CANCELED);
                    finish();
                }
                else{
                    arr = String.valueOf(contactsListAdapter.selectedContactsList.contactArrayList);
                    arr = arr.replace("[", " ");
                    arr = arr.replace("]", " ");
                    System.out.println(arr);
                    System.out.println("SelectedContacts" + arr);
                    if (nucount==0)
                    {
                        showToast("Create Community to invite the member");
                    }
                    else
                    {
                        inviteApi(String.valueOf(nucount),arr);

                    }

//                    Intent resultIntent = new Intent();
//                    resultIntent.putParcelableArrayListExtra("SelectedContacts", contactsListAdapter.selectedContactsList.contactArrayList);
//                    setResult(RESULT_OK,resultIntent);

                }
                //   finish();
               /* if (contactsListAdapter.selectedContactsList.contactArrayList.isEmpty()) {
                    finish();
                } else {
                    arr = String.valueOf(contactsListAdapter.selectedContactsList.contactArrayList);
                    arr = arr.replace("[", " ");
                    arr = arr.replace("]", " ");
                    System.out.println(arr);
                    System.out.println("SelectedContacts" + arr);
                    qextendedApi();


                }*/

            }
        });
    }
    private void inviteApi(String sub, String desc) {
        CommonUtils.showSpotoProgressDialog(context);
        Call<InviteMemberResponse> call = RetrofitClient.getUniqInstance().getApi()
                .inviteMemberCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), sub, desc);
        call.enqueue(new Callback<InviteMemberResponse>() {
            @Override
            public void onResponse(Call<InviteMemberResponse> call, Response<InviteMemberResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            showToast(response.body().getMessage());
                            Intent resultIntent = new Intent();
                            resultIntent.putParcelableArrayListExtra("SelectedContacts", contactsListAdapter.selectedContactsList.contactArrayList);
                            setResult(RESULT_OK,resultIntent);
                            finish();
                        } else {
                            showToast(response.body().getMessage());
                        }
                    } else {
                        showToast(SERVER_ERROR);
                    }
                } else {
                    showToast(SERVER_ERROR);
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<InviteMemberResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }
    private void qextendedApi() {
        CommonUtils.showSpotoProgressDialog(context);


        Call<InvitResponse> call = RetrofitClient.getUniqInstance().getApi()
                .ExtendedCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), arr);
        call.enqueue(new Callback<InvitResponse>() {
            @Override
            public void onResponse(Call<InvitResponse> call, Response<InvitResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.dialog);
                            TextView txt = dialog.findViewById(R.id.txt);

                            if (response.body().getCount() == 10) {
                                txt.setText(" " + response.body().getMessage());
                            } else {
                                txt.setText(sentcount + " " + "invites successfully sent");
                            }
                            Button dialogButton = dialog.findViewById(R.id.btn_done);
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });

                            dialog.show();
                        } else {
                            showToast(response.body().getMessage());
                        }
                    } else {
                        showToast(SERVER_ERROR);
                    }
                } else {
                    showToast(SERVER_ERROR);
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<InvitResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }


    private void loadContacts(String filter) {

        if (contactsLoader != null && contactsLoader.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                contactsLoader.cancel(true);
            } catch (Exception e) {

            }
        }
        if (filter == null) filter = "";

        try {
            //Running AsyncLoader with adapter and  filter
            contactsLoader = new ContactsLoader(this, contactsListAdapter);
            contactsLoader.txtProgress = txtLoadInfo;
            contactsLoader.execute(filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
