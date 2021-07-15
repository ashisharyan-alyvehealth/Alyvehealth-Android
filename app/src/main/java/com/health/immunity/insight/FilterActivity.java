package com.health.immunity.insight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.health.immunity.BaseActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.databinding.ActivityFilterBinding;
import com.health.immunity.insight.model.FiltercommunityAdapter;
import com.health.immunity.insight.model.MyCommunitiesResponse;
import com.health.immunity.insight.model.userClickInterface;
import com.health.immunity.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends BaseActivity implements View.OnClickListener {
    private ActivityFilterBinding binding;
    private FiltercommunityAdapter yourcommuntiyAdapter;
    private List<MyCommunitiesResponse.All> myCommunitiesResponses = new ArrayList<>();
    private String userstr="",agestr="",genderstr="",communitystr="",communitystrname="",finalStatus = "",timestr = "";
    private com.health.immunity.insight.model.userClickInterface userClickInterface;
    private List<String> genderConditions = new ArrayList<>();
    private List<String> genderConditionsname = new ArrayList<>();
    private List<String> timeConditions = new ArrayList<>();
    private List<String> agesConditions = new ArrayList<>();
    private List<String> agesConditionsname = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(activity, R.layout.activity_filter);

        userClickInterface=new userClickInterface() {
            @Override
            public void setClick(String abc) {

                // Toast.makeText(context,String.valueOf(abc), Toast.LENGTH_SHORT).show();

                communitystr= String.valueOf(abc);
                binding.tvalluser.setChecked(false);


            }

            @Override
            public void setClickname(String abcname) {

                communitystrname=abcname;

            }
        };

        initViews();



    }

    private void initViews() {
        binding.tvalluser.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                binding.tvalluser.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvalluser.setTextColor(getResources().getColor(R.color.white));
                userstr="all";


             /*   myCommunitiesResponses.clear();
                yourcommuntiyAdapter.notifyDataSetChanged();
             */   mycommunityuser();

            } else {
                userstr="";
                binding.tvalluser.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvalluser.setBackground(getResources().getDrawable(R.drawable.background_language));


            }
        });
        binding.ivcreate.setOnClickListener(this);

/*
        binding.tvlastweek.setOnClickListener(this);
        binding.tvyesterday.setOnClickListener(this);
        binding.tvallages.setOnClickListener(this);
        binding.tvunder25.setOnClickListener(this);
        binding.tv25above.setOnClickListener(this);
        binding.tv35above.setOnClickListener(this);
        binding.tv40above.setOnClickListener(this);
        binding.tv50above.setOnClickListener(this);
        binding.tv60above.setOnClickListener(this);
        binding.tvallgender.setOnClickListener(this);
        binding.tvmale.setOnClickListener(this);
        binding.tvFemale.setOnClickListener(this);*/

        binding.ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mycommunityuser();




        binding.tvallgender.setOnCheckedChangeListener((buttonView, isChecked) -> {
            genderstr="";
            if (isChecked)
            {
                finalStatus = "";
                genderConditions.add(genderstr);
                genderConditionsname.add("");
                binding.tvallgender.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvallgender.setTextColor(getResources().getColor(R.color.white));
                binding.tvmale.setChecked(false);
                binding.tvFemale.setChecked(false);

            } else
            {

                genderConditions.remove(genderstr);
                genderConditionsname.remove("");
                binding.tvallgender.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallgender.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });
        binding.tvmale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            genderstr="1";
            if (isChecked) {
                finalStatus = "";
                genderConditions.add(genderstr);
                genderConditionsname.add("Male");
                binding.tvmale.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvmale.setTextColor(getResources().getColor(R.color.white));

                binding.tvallgender.setChecked(false);

            } else {
                genderConditions.remove(genderstr);
                genderConditionsname.remove("Male");
                binding.tvmale.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvmale.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });
        binding.tvFemale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            genderstr="0";
            if (isChecked) {
                finalStatus = "";
                genderConditions.add(genderstr);
                genderConditionsname.add("Female");
                binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvFemale.setTextColor(getResources().getColor(R.color.white));
                binding.tvallgender.setChecked(false);


            } else {
                genderConditions.remove(genderstr);
                genderConditionsname.remove("Female");
                binding.tvFemale.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });

        binding.tvlastweek.setOnCheckedChangeListener((buttonView, isChecked) -> {
            timestr="0";
            if (isChecked) {
                finalStatus = "";
                timeConditions.add(timestr);
                binding.tvlastweek.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvlastweek.setTextColor(getResources().getColor(R.color.white));

            } else {
                timeConditions.remove(timestr);
                binding.tvlastweek.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvlastweek.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });
        binding.tvyesterday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            timestr="1";
            if (isChecked) {
                finalStatus = "";
                timeConditions.add(timestr);
                binding.tvyesterday.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvyesterday.setTextColor(getResources().getColor(R.color.white));

            } else {
                timeConditions.remove(timestr);
                binding.tvyesterday.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvyesterday.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });
//

        binding.tvallages.setOnCheckedChangeListener((buttonView, isChecked) -> {
            agestr="";
            if (isChecked) {
                finalStatus = "";
                agesConditions.add(agestr);
                agesConditionsname.add("");

                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvallages.setTextColor(getResources().getColor(R.color.white));

                binding.tvunder25.setChecked(false);
                binding.tv25above.setChecked(false);
                binding.tv35above.setChecked(false);
                binding.tv40above.setChecked(false);
                binding.tv50above.setChecked(false);
                binding.tv60above.setChecked(false);

            } else {
                agesConditions.remove(agestr);
                agesConditionsname.remove("");
                binding.tvallages.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });
        binding.tvunder25.setOnCheckedChangeListener((buttonView, isChecked) -> {
            agestr="0-301";
            if (isChecked) {
                finalStatus = "";
                agesConditions.add(agestr);
                agesConditionsname.add("Under 25");
                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvunder25.setTextColor(getResources().getColor(R.color.white));

                binding.tvallages.setChecked(false);

            } else {
                agesConditions.remove(agestr);
                agesConditionsname.remove("Under 25");
                binding.tvunder25.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });
        binding.tv25above.setOnCheckedChangeListener((buttonView, isChecked) -> {
            agestr="299-421";
            if (isChecked) {
                finalStatus = "";
                agesConditions.add(agestr);
                agesConditionsname.add("25-35");
                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv25above.setTextColor(getResources().getColor(R.color.white));

                binding.tvallages.setChecked(false);


            } else {
                agesConditions.remove(agestr);
                agesConditionsname.remove("25-35");
                binding.tv25above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });
        binding.tv35above.setOnCheckedChangeListener((buttonView, isChecked) -> {
            agestr="419-481";

            if (isChecked) {
                finalStatus = "";
                agesConditions.add(agestr);
                agesConditionsname.add("35-40");
                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv35above.setTextColor(getResources().getColor(R.color.white));

                binding.tvallages.setChecked(false);


            } else {
                agesConditions.remove(agestr);
                agesConditionsname.remove("35-40");
                binding.tv35above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });

        binding.tv40above.setOnCheckedChangeListener((buttonView, isChecked) -> {
            agestr="479-601";
            if (isChecked) {
                finalStatus = "";
                agesConditions.add(agestr);
                agesConditionsname.add("40-50");
                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv40above.setTextColor(getResources().getColor(R.color.white));

                binding.tvallages.setChecked(false);


            } else {
                agesConditions.remove(agestr);
                agesConditionsname.remove("40-50");
                binding.tv40above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });

        binding.tv50above.setOnCheckedChangeListener((buttonView, isChecked) -> {
            agestr="599-721";
            if (isChecked) {
                finalStatus = "";
                agesConditions.add(agestr);
                agesConditionsname.add("50-60");
                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv50above.setTextColor(getResources().getColor(R.color.white));

                binding.tvallages.setChecked(false);


            } else {
                agesConditions.remove(agestr);
                agesConditionsname.remove("50-60");
                binding.tv50above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });

        binding.tv60above.setOnCheckedChangeListener((buttonView, isChecked) -> {
            agestr="719-1201";
            if (isChecked) {
                finalStatus = "";
                agesConditions.add(agestr);
                agesConditionsname.add("60+");
                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv60above.setTextColor(getResources().getColor(R.color.white));

                binding.tvallages.setChecked(false);


            } else {
                agesConditions.remove(agestr);
                agesConditionsname.remove("60+");
                binding.tv60above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.background_language));

            }
        });


        if (TextUtils.isEmpty(agestr)&&TextUtils.isEmpty(genderstr)&&TextUtils.isEmpty(userstr))
        {
            binding.tvallages.setChecked(true);
            binding.tvallgender.setChecked(true);
            binding.tvalluser.setChecked(true);



        }


    }


    private void mycommunityuser() {
        //CommonUtils.showSpotoProgressDialog(context);
        Call<MyCommunitiesResponse> call = RetrofitClient.getUniqInstance().getApi()
                .myCommunitiesCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<MyCommunitiesResponse>() {
            @Override
            public void onResponse(Call<MyCommunitiesResponse> call, Response<MyCommunitiesResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        if (response.body().getJsonData().getAll().size() > 0) {

                            myCommunitiesResponses.clear();
                            myCommunitiesResponses.addAll(response.body().getJsonData().getAll());
                            yourcommuntiyAdapter = new FiltercommunityAdapter(context, myCommunitiesResponses,userClickInterface);
                            binding.rvGuidance.setLayoutManager(new LinearLayoutManager(context));
                            binding.rvGuidance.setAdapter(yourcommuntiyAdapter);
                            yourcommuntiyAdapter.notifyDataSetChanged();



                        }




                    }
                }
                //   CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<MyCommunitiesResponse> call, Throwable t) {
                t.printStackTrace();
                // CommonUtils.dismissSpotoProgressDialog();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvlastweek:


                binding.tvlastweek.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvlastweek.setTextColor(getResources().getColor(R.color.white));

                binding.tvyesterday.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvyesterday.setBackground(getResources().getDrawable(R.drawable.background_language));


                break;
            case R.id.tvyesterday:


                binding.tvyesterday.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvyesterday.setTextColor(getResources().getColor(R.color.white));

                binding.tvlastweek.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvlastweek.setBackground(getResources().getDrawable(R.drawable.background_language));


                break;

            //

            case R.id.tvallages:

                //     agestr="300";


                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvallages.setTextColor(getResources().getColor(R.color.white));

                binding.tvunder25.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv25above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv35above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv40above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv50above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv60above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.background_language));

                break;
            case R.id.tvunder25:

                //  agestr="300";


                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvunder25.setTextColor(getResources().getColor(R.color.white));

                binding.tvallages.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv25above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv35above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv40above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv50above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv60above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.background_language));
                break;
            case R.id.tv25above:

                agestr="300-420";



                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv25above.setTextColor(getResources().getColor(R.color.white));

                binding.tvunder25.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvallages.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv35above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv40above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv50above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv60above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.background_language));

                break;
            case R.id.tv35above:

                agestr="420-480";

                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv35above.setTextColor(getResources().getColor(R.color.white));

                binding.tvunder25.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv25above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvallages.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv40above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv50above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv60above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.background_language));
                break;
            case R.id.tv40above:

                agestr="480-600";



                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv40above.setTextColor(getResources().getColor(R.color.white));

                binding.tvunder25.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv25above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv35above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvallages.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv50above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv60above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.background_language));
                break;
            case R.id.tv50above:
                agestr="600-720";


                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv50above.setTextColor(getResources().getColor(R.color.white));

                binding.tv60above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvunder25.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv25above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv35above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv40above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvallages.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.background_language));


                break;

            case R.id.tv60above:
                //    agestr="720";


                binding.tv60above.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tv60above.setTextColor(getResources().getColor(R.color.white));

                binding.tv50above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv50above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvunder25.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvunder25.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv25above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv25above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv35above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv35above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tv40above.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tv40above.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvallages.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallages.setBackground(getResources().getDrawable(R.drawable.background_language));


                break;
            //
            case R.id.tvallgender:

                genderstr="0";
                binding.tvallgender.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvallgender.setTextColor(getResources().getColor(R.color.white));

                binding.tvmale.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvmale.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvFemale.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.background_language));

                break;
            case R.id.tvmale:

                genderstr="0";


                binding.tvmale.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvmale.setTextColor(getResources().getColor(R.color.white));

                binding.tvFemale.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.background_language));
                binding.tvallgender.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallgender.setBackground(getResources().getDrawable(R.drawable.background_language));


                break;
            case R.id.tvFemale:

                genderstr="1";


                binding.tvFemale.setBackground(getResources().getDrawable(R.drawable.button_background));
                binding.tvFemale.setTextColor(getResources().getColor(R.color.white));

                binding.tvallgender.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvallgender.setBackground(getResources().getDrawable(R.drawable.background_language));

                binding.tvmale.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvmale.setBackground(getResources().getDrawable(R.drawable.background_language));

                break;

            case R.id.ivcreate:
                StringBuilder travelId = new StringBuilder();
                for (int i = 0; i < genderConditions.size(); i++) {
                    travelId.append(genderConditions.get(i));
                    if (!(i == genderConditions.size() - 1)) {
                        travelId.append(",");
                    }
                }
                genderstr = travelId.toString();
                Log.e(TAG, "onEditorAction: Health Condition " + genderstr);

                //
                StringBuilder travelIdGN = new StringBuilder();
                for (int i = 0; i < genderConditionsname.size(); i++) {
                    travelIdGN.append(genderConditionsname.get(i));
                    if (!(i == genderConditionsname.size() - 1)) {
                        travelIdGN.append(",");
                    }
                }
                String genderstrname = travelIdGN.toString();

                //

                StringBuilder travelId1 = new StringBuilder();
                for (int i = 0; i < agesConditions.size(); i++) {
                    travelId1.append(agesConditions.get(i));
                    if (!(i == agesConditions.size() - 1)) {
                        travelId1.append(",");
                    }
                }
                agestr = travelId1.toString();
                Log.e(TAG, "onEditorAction: Health Condition " + agestr);

                //

                StringBuilder travelIdAN = new StringBuilder();
                for (int i = 0; i < agesConditionsname.size(); i++) {
                    travelIdAN.append(agesConditionsname.get(i));
                    if (!(i == agesConditionsname.size() - 1)) {
                        travelIdAN.append(",");
                    }
                }
                String agestrname = travelIdAN.toString();
                //


                Intent returnIntent = new Intent();
                returnIntent.putExtra("user",userstr);
                returnIntent.putExtra("age",agestr);
                returnIntent.putExtra("agename",agestrname);
                returnIntent.putExtra("gender",genderstr);
                returnIntent.putExtra("gendername",genderstrname);
                returnIntent.putExtra("commun",communitystr);
                returnIntent.putExtra("communname",communitystrname);
                returnIntent.putExtra("kpi_id",getIntent().getStringExtra("kpi_id"));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

                break;


        }
    }

}
