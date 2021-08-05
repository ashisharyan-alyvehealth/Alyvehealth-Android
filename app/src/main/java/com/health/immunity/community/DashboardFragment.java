package com.health.immunity.community;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.health.immunity.BaseFragment;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.community.adapter.CommunityAdapter;
import com.health.immunity.community.adapter.MyCommunityUser;
import com.health.immunity.community.adapter.NoticeAdapter;
import com.health.immunity.community.adapter.PendingAdapter;
import com.health.immunity.community.adapter.WatchAdapterDash;
import com.health.immunity.community.adapter.YourcommunityAdapter;
import com.health.immunity.community.adapter.comClickInterface;
import com.health.immunity.community.model.DashboardData;
import com.health.immunity.community.model.DashboardResponse;
import com.health.immunity.community.model.InsightResponse;
import com.health.immunity.community.model.NoticeAction;
import com.health.immunity.community.model.NoticeResponse;
import com.health.immunity.community.model.ZoneResponse;
import com.health.immunity.community.view.CreateCommunity;
import com.health.immunity.databinding.FragmentDashboardBinding;
import com.health.immunity.insight.model.MyCommunitiesResponse;
import com.health.immunity.profile.model.GetProfileResponse;
import com.health.immunity.retrofit.RetrofitClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends BaseFragment implements View.OnClickListener {

    private FragmentDashboardBinding binding;
    com.health.immunity.community.adapter.comClickInterface comClickInterface;
    private NoticeAdapter adapter;
    private List<NoticeAction> noticeActions = new ArrayList<>();


    private CommunityAdapter communityAdapter;
    public static List<MyCommunityUser> myCommunitylist = new ArrayList<>();

    String yesterdaystring="";
    private String latitude, longitude, city, strCurrentLatitude, strCurrentLongitude;

    String yesterday="0";
    String lastweek="0";

    private YourcommunityAdapter yourcommuntiyAdapter;
    private List<MyCommunitiesResponse.All> myCommunitiesResponses = new ArrayList<>();
    private PendingAdapter pendingAdapter;
    private List<MyCommunitiesResponse.Pending> pendinglist = new ArrayList<>();
    boolean showcomm=false;

    int countcom=0;

    private List<DashboardData.Watch> watchActions = new ArrayList<>();
    private WatchAdapterDash watchAdapter;


    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        View view = binding.getRoot();
        binding.imvv22.setVisibility(View.GONE);

        comClickInterface = new comClickInterface() {
            @Override
            public void setClick(int abc) {

                if (abc==2)
                {
                    countcom=countcom+1;
                }
                else
                {
                    countcom=countcom-abc;
                }



                if (countcom == 0) {

                    binding.tvpart.setText("You are not a part of any community");
                    binding.imvv22.setVisibility(View.INVISIBLE);
                }
                else if (countcom == 1)
                {
                    binding.imvv22.setVisibility(View.INVISIBLE);
                    SpannableString content = new SpannableString(Html.fromHtml("You are part of"+" "+"<font color='#8775ed'><b>" + countcom+" Community" + "</b></font>"));
                    //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    binding.tvpart.setText(content);
                }
                else if (countcom >1 && countcom <4)
                {
                    binding.imvv22.setVisibility(View.INVISIBLE);
                    SpannableString content = new SpannableString(Html.fromHtml("You are part of"+" "+"<font color='#8775ed'><b>" + countcom+" Communities" + "</b></font>"));
                    //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    binding.tvpart.setText(content);
                }
                else
                {
                  //  binding.imvv22.setVisibility(View.VISIBLE);
                    SpannableString content = new SpannableString(Html.fromHtml("You are part of"+" "+"<font color='#8775ed'><b>" + countcom+" Communities" + "</b></font>"));
                    //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    binding.tvpart.setText(content);

                }
            }
        };
        initViews();
        // using method from above
        System.out.println("device"+   Build.MANUFACTURER);
// Using https://github.com/jaredrummler/AndroidDeviceNames
        //System.out.println(DeviceName.getDeviceName());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userDashboardApi();
        //getProfileApi();
        mycommunityrApiAgain();
        binding.imvv22.setVisibility(View.GONE);
        //  getInsightApi();
        if (CommonUtils.isInternetAvail(context)) {
            //  binding.tvMandate.setVisibility(View.GONE);
            noticeApi();
            //  mycommunityrApi();
        }
    }
    private void noticeApi() {
        //   CommonUtils.showSpotoProgressDialog(context);
        Call<NoticeResponse> call = RetrofitClient.getUniqInstance().getApi()
                .noticeCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<NoticeResponse>() {
            @Override
            public void onResponse(Call<NoticeResponse> call, Response<NoticeResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        if (response.body().getJsonData().getAction().size() > 0) {

                            noticeActions.clear();
                            noticeActions.addAll(response.body().getJsonData().getAction());
                            adapter.notifyDataSetChanged();
                        } else {

                            //   binding.tvMandate.setVisibility(View.GONE);
                            // binding.rvNotice.setVisibility(View.GONE);
                        }
                      /*  if (response.body().getJsonData().getGuidance().size() > 0) {
                            binding.tvNoGuidance.setVisibility(View.GONE);
                            binding.rvGuidance.setBackground(getResources().getDrawable(R.drawable.background_layout, null));
                            guidances.clear();
                            guidances.addAll(response.body().getJsonData().getGuidance());
                            guidanceAdapter.notifyDataSetChanged();
                        } else {
                            binding.tvNoGuidance.setVisibility(View.VISIBLE);
                        }*/
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
                //  CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<NoticeResponse> call, Throwable t) {
                t.printStackTrace();
                // CommonUtils.dismissSpotoProgressDialog();
            }
        });

    }
    private void getInsightApi() {
        Call<InsightResponse> call = RetrofitClient.getUniqInstance().getApi()
                .insightCall("Bearer "+PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<InsightResponse>() {
            @Override
            public void onResponse(Call<InsightResponse> call, Response<InsightResponse> response) {
                if (response.body() != null){
                    if (response.code() == 200){
                    /*    binding.tvOneValue.setText(response.body().getJsonData().getHomelocation()+"&#37");
                        binding.tvTwoValue.setText(response.body().getJsonData().getOfficelocation()+"");
                        binding.tvThreeValue.setText(response.body().getJsonData().getSymptomatic()+"");
                   */     yesterday= String.valueOf(response.body().getJsonData().getSymptomatic());
                        lastweek= String.valueOf(response.body().getJsonData().getSymptomatic_in_week());
                    } else {
                        Toast.makeText(context, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<InsightResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void initViews() {
        CommonUtils.compareset=false;
        //requestPermission();
        yesterdaystring=getYesterdayDateString();
     /*   binding.viewSymptom.setOnClickListener(this);
        binding.ivTotalEdit.setOnClickListener(this);
        binding.tvsearchcheck.setOnClickListener(this);
      //  binding.imvv11.setOnClickListener(this);
        binding.infoview1.setOnClickListener(this);
        binding.infoview2.setOnClickListener(this);
        binding.tvyester.setOnClickListener(this);
        binding.tvlastw.setOnClickListener(this);
        binding.tvlastsearch.setText("View your last 5 searches");
*/
        communityAdapter = new CommunityAdapter(context, myCommunitylist);
      /*  binding.rvGuidance.setLayoutManager(new LinearLayoutManager(context));
        binding.rvGuidance.setAdapter(communityAdapter);
*/
        //
        showcomm=true;
        binding.imvv22.setVisibility(View.GONE);
        mycommunityrApiAgain();
        adapter = new NoticeAdapter(context, noticeActions);
    /*    binding.rvNotice.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        binding.rvNotice.setAdapter(adapter);
    */    //
        watchAdapter = new WatchAdapterDash(context, watchActions);
        binding.rvGuidancewatch.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        binding.rvGuidancewatch.setAdapter(watchAdapter);

        yourcommuntiyAdapter = new YourcommunityAdapter(context, myCommunitiesResponses,comClickInterface);
        binding.rvshowcommunity.setLayoutManager(new LinearLayoutManager(context));
        binding.rvshowcommunity.setAdapter(yourcommuntiyAdapter);

        pendingAdapter = new PendingAdapter(context, pendinglist);
        binding.rvpendingcommunity.setLayoutManager(new LinearLayoutManager(context));
        binding.rvpendingcommunity.setAdapter(pendingAdapter);

        binding.tvcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentContactPick = new Intent(context, CreateCommunity.class);
                //  intentContactPick.putExtra("invit_count", 0);
                startActivity(intentContactPick);
            }
        });


        binding.imvv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!showcomm)
                {
                    showcomm=true;
                  //  binding.imvv22.setBackground(context.getDrawable(R.drawable.copy_71));
                    mycommunityrApiAgain();


                }
                else
                {
                    showcomm=false;
                 //   binding.imvv22.setBackground(context.getDrawable(R.drawable.copy_7));
                    mycommunityrApi();



                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.infoview1:
                CommonUtils.showCustomDialog(context, "Are you maintaining social distance? There occurs a ‘Proximity Violation’ if you come within 6 feet of another person who is ‘bluetooth discoverable’ for a duration of 30 seconds.", IConstant.OKAY);
                break;
            case R.id.infoview2:
                CommonUtils.showCustomDialog(context, "You can view the %ge members reporting symptoms in your verified community.", IConstant.OKAY);
                break;
*/

           /* case R.id.imvv11:
                if (!showcomm)
                {
                    showcomm=true;
                    binding.imvv11.setBackground(getResources().getDrawable(R.drawable.group_8));
                    userDashboardApiagain();

                }
                else
                {
                    showcomm=false;
                    binding.imvv11.setBackground(getResources().getDrawable(R.drawable.group_7));

                    userDashboardApiag();

                }
                break;*/
            case R.id.tvyester:
             /*   binding.tvThreeValue.setText(yesterday);

                binding.tvyester.setBackground(context.getDrawable(R.drawable.background_lastweek));
                binding.tvyester.setTextColor(getResources().getColor(R.color.colorPrimary));

                binding.tvlastw.setTextColor(getResources().getColor(R.color.white));
                binding.tvlastw.setBackground(context.getDrawable(R.drawable.background_yesterday));
*/
                break;
            case R.id.tvlastw:
            /*    binding.tvThreeValue.setText(lastweek);

                binding.tvlastw.setBackground(context.getDrawable(R.drawable.background_lastweek));
                binding.tvlastw.setTextColor(getResources().getColor(R.color.colorPrimary));

                binding.tvyester.setTextColor(getResources().getColor(R.color.white));
                binding.tvyester.setBackground(context.getDrawable(R.drawable.background_yesterday));
*/

                break;
           /* case R.id.tvlastsearch:

                if (binding.tvlastsearch.getText().equals("View your last 5 searches"))
                {
                    binding.tvlastsearch.setText("Hide your last 5 searches");
                    binding.viewadd4.setVisibility(View.VISIBLE);
                    binding.viewadd3.setVisibility(View.VISIBLE);
                    binding.viewadd2.setVisibility(View.VISIBLE);
                    binding.tvadd5.setVisibility(View.VISIBLE);
                    binding.tvadd4.setVisibility(View.VISIBLE);
                    binding.tvadd3.setVisibility(View.VISIBLE);
                    binding.tvsub5.setVisibility(View.VISIBLE);
                    binding.tvsub4.setVisibility(View.VISIBLE);
                    binding.tvsub3.setVisibility(View.VISIBLE);
                }

            else
                {
                    binding.tvlastsearch.setText("View your last 5 searches");
                    binding.viewadd4.setVisibility(View.GONE);
                    binding.viewadd3.setVisibility(View.GONE);
                    binding.viewadd2.setVisibility(View.GONE);
                    binding.tvadd5.setVisibility(View.GONE);
                    binding.tvadd4.setVisibility(View.GONE);
                    binding.tvadd3.setVisibility(View.GONE);
                    binding.tvsub5.setVisibility(View.GONE);
                    binding.tvsub4.setVisibility(View.GONE);
                    binding.tvsub3.setVisibility(View.GONE);
                }



                break;*/
           /* case R.id.ivTotalEdit:
                startActivity(new Intent(context, HomeActivity.class));
                break;*/
     /*       case R.id.tvsearchcheck:
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
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
                    dialogBuilder.show();
                }
                else
                {
                    Intent mapIntent = new Intent(context, MapActivity.class);
                    startActivityForResult(mapIntent, ADDRESS_CAPTURE);
                }

                break;*/
         /*   case R.id.tvcheckPlace:

                System.out.println("##################"+latitude);
                System.out.println("##################"+longitude);

                if (TextUtils.isEmpty(latitude))
                {
                    Toast.makeText(context, R.string.pleasesearch, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    newClicked(latitude,longitude);
                }




                break;*/
        }
    }

    private void userDashboardApi() {
        Call<DashboardResponse> call = RetrofitClient.getUniqInstance().getApi()
                .dashboardCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        //  binding.tvName.setText(response.body().getJsonData().getName());

                        if (!TextUtils.isEmpty(response.body().getJsonData().getApiKeys().get(0).getApiKey()))
                        {
                            System.out.println("#####################ooookey"+response.body().getJsonData().getApiKeys().get(0).getApiKey());

                            PreferenceHelper.setStringPreference(context, IConstant.API_KEY_WEATHER,response.body().getJsonData().getApiKeys().get(0).getApiKey());
                        }

                        if (!TextUtils.isEmpty(response.body().getJsonData().getApiKeys().get(2).getApiKey()))
                        {

                            //     binding.tvCheck.setText(response.body().getJsonData().getApiKeys().get(2).getApiKey().toString());

                        }

                        if (!TextUtils.isEmpty(response.body().getJsonData().getGoogle_healthkit_status()))
                        {
                            PreferenceHelper.setStringPreference(context, IConstant.FIT_STATUS_BACK,response.body().getJsonData().getGoogle_healthkit_status());
                            System.out.println("#####################ooookey"+response.body().getJsonData().getGoogle_healthkit_status());

                        }


                        if (PreferenceHelper.getIntPreference(context,CommonUtils.IS_VERIFIEDUSER)==1)
                        {
                            if (response.body().getJsonData().getSymptom_display().equals("1"))
                            {
                                // binding.symcc.setVisibility(View.VISIBLE);
                            }





                        }
                        else  if ( PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
                        {
                            if (response.body().getJsonData().getSymptom_display().equals("1"))
                            {
                                //   binding.symcc.setVisibility(View.VISIBLE);
                            }



                            // binding.recordcc.setVisibility(View.VISIBLE);
                        }


                        yesterday= String.valueOf(response.body().getJsonData().getYesterdayproximity());
                        lastweek= String.valueOf(response.body().getJsonData().getLastweekproximity());



                        if (response.body().getJsonData().getManage() != null)
                        {
                            if (response.body().getJsonData().getManage().equalsIgnoreCase("true"))
                            {

                            }
                        }



                        if (response.body().getJsonData().getAuto_check_in() != null)
                        {
                            PreferenceHelper.setStringPreference(context, IConstant.AUTOCHECKIN, response.body().getJsonData().getAuto_check_in());
                        }
                        if (response.body().getJsonData().getAccess_control() != null) {
                            PreferenceHelper.setStringPreference(context, IConstant.ACCESSCONTROL, response.body().getJsonData().getAccess_control());

                        }
                        if (response.body().getJsonData().getAccess_control() != null) {
                            if (response.body().getJsonData().getAccess_control().equalsIgnoreCase("1"))
                            {

                                //      binding.tvThreeDetails.setText(getResources().getString(R.string.proximity_violations_by_you_on)+" "+yesterdaystring);



                     /*           binding.tvThreeDetails.setVisibility(View.VISIBLE);
                                binding.tvThreeValue.setVisibility(View.VISIBLE);
                                binding.viewInsight3.setVisibility(View.VISIBLE);
                 */           } else {

                    /*            binding.tvThreeDetails.setVisibility(View.GONE);
                                binding.tvThreeValue.setVisibility(View.GONE);
                                binding.viewInsight3.setVisibility(View.GONE);
                    */        }
                        }
                        if (response.body().getJsonData().getUserToday() != null) {
                         /*   binding.tvTravelData.setText(response.body().getJsonData().getUserToday().getHaveYouTraveled());
                            binding.tvSymptomData.setText(response.body().getJsonData().getUserToday().getReportSymptoms());
                            binding.tvWorkPlace.setText(response.body().getJsonData().getUserToday().getCheckIn());
                   */     } else {
                   /*         binding.tvTravelData.setText("N/A");
                            binding.tvWorkPlace.setText("N/A");
                            binding.tvSymptomData.setText("N/A");
                    */    }
                        if (!TextUtils.isEmpty(response.body().getJsonData().getUserToday().getDoctorConsult()))
                        {
                            //   binding.tvConsultData.setText(response.body().getJsonData().getUserToday().getDoctorConsult());

                        }

                        else
                        {
                    /*        binding.tvConsultData.setVisibility(View.GONE);
                            binding.tvConsult.setVisibility(View.GONE);
                    */    }


                        if (response.body().getJsonData().getContents().getWatch().size() > 0) {
                            watchActions.clear();
                            watchActions.addAll(response.body().getJsonData().getContents().getWatch());
                            watchAdapter.notifyDataSetChanged();
                        }



                    } else {
                        PreferenceHelper.setBooleanPreference(context, IConstant.IS_LOGIN, false);
                    }
                }else {
                    Toast.makeText(context, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getProfileApi() {

        Call<GetProfileResponse> call = RetrofitClient.getUniqInstance().getApi()
                .getProfileCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        if (response.body().getJsonData().getUserImages() != null) {
                            //    Glide.with(context).load(IMAGE_BASE_URL + response.body().getJsonData().getUserImages()).into(binding.circleImage);
                        }
                    } else {
                        Toast.makeText(context, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, SERVER_ERROR, Toast.LENGTH_SHORT).show();
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

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_CAPTURE) {
            if (resultCode == RESULT_OK) {
                String address = data.getStringExtra("address");
                latitude = data.getStringExtra("latitude");
                longitude = data.getStringExtra("longitude");
                Log.e(TAG, "onActivityResult: Home Latitue " + latitude);
                Log.e(TAG, "onActivityResult: " + address);
                if (latitude != null) {
                    //   binding.tvsearchcheck.setText(address);
                    System.out.println("##################"+latitude);
                    System.out.println("##################"+longitude);

                    if (TextUtils.isEmpty(latitude))
                    {
                        Toast.makeText(context, R.string.pleasesearch, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        newClicked(latitude,longitude,address);
                    }

                }
                else {
                    Log.e(TAG, "onActivityResult: City -- = " + city);
                    //    binding.tvsearchcheck.setText(city);
                    latitude = strCurrentLatitude;
                    longitude = strCurrentLongitude;
                }
            }
        }
    }






    public void newClicked(String lat,String lon,String address) {
        CommonUtils.showSpotoProgressDialog(context);
        Call<ZoneResponse> call = RetrofitClient.getUniqInstance().getApi()
                .searchZoneCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), lat, lon,address);
        call.enqueue(new Callback<ZoneResponse>() {
            @Override
            public void onResponse(Call<ZoneResponse> call, Response<ZoneResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            try {
                             /*   binding.tvcheckSafe.setVisibility(View.VISIBLE);
                                binding.tvcheckPlace.setVisibility(View.VISIBLE);
                             */   userDashboardApi();

                                if (response.body().getJsonData().get(0).getContainmentZone()) {
                             /*       binding.tvcheckSafe.setText(R.string.care);
                                    binding.tvcheckPlace.setBackground(context.getDrawable(R.drawable.circledotred));
                                    binding.tvcheckSafe.setTextColor(ContextCompat.getColor(context, R.color.orange));
                             */   } else {
                              /*      binding.tvcheckSafe.setText(R.string.safe);
                                    binding.tvcheckPlace.setBackground(context.getDrawable(R.drawable.circledot));

                                    binding.tvcheckSafe.setTextColor(ContextCompat.getColor(context, R.color.green));
*/

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                CommonUtils.dismissSpotoProgressDialog();

            }

            @Override
            public void onFailure(Call<ZoneResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();

            }
        });


    }

    private void userDashboardApiagain() {
        CommonUtils.showSpotoProgressDialog(context);

        Call<DashboardResponse> call = RetrofitClient.getUniqInstance().getApi()
                .dashboardCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {

                        if (response.body().getJsonData().getMyCommunityUsers().size() > 0) {
                            myCommunitylist.clear();
                            myCommunitylist.addAll(response.body().getJsonData().getMyCommunityUsers());
                            communityAdapter.notifyDataSetChanged();
                        }

                    }
                }
                CommonUtils.dismissSpotoProgressDialog();

            }


            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void userDashboardApiag() {
        CommonUtils.showSpotoProgressDialog(context);
        Call<DashboardResponse> call = RetrofitClient.getUniqInstance().getApi()
                .dashboardCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {

                        if (response.body().getJsonData().getMyCommunityUsers().size() > 0) {
                            myCommunitylist.clear();
                            myCommunitylist.add(response.body().getJsonData().getMyCommunityUsers().get(0));
                            communityAdapter.notifyDataSetChanged();
                        }

                    }
                }
                CommonUtils.dismissSpotoProgressDialog();

            }


            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mycommunityrApi() {
        //CommonUtils.showSpotoProgressDialog(context);
        Call<MyCommunitiesResponse> call = RetrofitClient.getUniqInstance().getApi()
                .myCommunitiesCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<MyCommunitiesResponse>() {
            @Override
            public void onResponse(Call<MyCommunitiesResponse> call, Response<MyCommunitiesResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        if (response.body().getJsonData().getAll().size() > 0) {

//                        System.out.println("User_type"+response.body().getJsonData().getAll().get(3).getUser_type());


                            countcom=response.body().getJsonData().getAll().size()-response.body().getJsonData().getPending().size();

//                            if (countcom > 3)
//                            {
//                                binding.imvv22.setVisibility(View.VISIBLE);
//                            }
                            if (countcom>1)
                            {
                                SpannableString content = new SpannableString(Html.fromHtml("You are part of"+" "+"<font color='#8775ed'><b>" + countcom+" Communities" + "</b></font>"));
                                //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                binding.tvpart.setText(content);
                            }
                            else
                            {
                                SpannableString content = new SpannableString(Html.fromHtml("You are part of"+" "+"<font color='#8775ed'><b>" + countcom+" Community" + "</b></font>"));
                                //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                binding.tvpart.setText(content);
                            }





                            //  binding.tvpart.setText();

                            myCommunitiesResponses.clear();
                            if (response.body().getJsonData().getAll().size()==1)
                            {
                                myCommunitiesResponses.add(response.body().getJsonData().getAll().get(0));

                            }
                            else if (response.body().getJsonData().getAll().size()==2)
                            {
                                myCommunitiesResponses.add(response.body().getJsonData().getAll().get(0));
                                myCommunitiesResponses.add(response.body().getJsonData().getAll().get(1));

                            }
                            else
                            {
                                myCommunitiesResponses.add(response.body().getJsonData().getAll().get(0));
                                myCommunitiesResponses.add(response.body().getJsonData().getAll().get(1));
                                myCommunitiesResponses.add(response.body().getJsonData().getAll().get(2));

                            }
                        /*  else
                            {
                                myCommunitiesResponses.addAll(response.body().getJsonData().getAll());

                            }*/
                            yourcommuntiyAdapter.notifyDataSetChanged();
                            //  yourcommuntiyAdapter.notifyItemRangeChanged(0,response.body().getJsonData().getAll().size());


                        }
                        else
                        {
                            binding.tvpart.setText("You are not a part of any community");
                        }
                     /*   if (response.body().getJsonData().getPending().size() > 0) {

                            binding.imvv22.setVisibility(View.VISIBLE);


                            pendinglist.clear();
                            pendinglist.addAll(response.body().getJsonData().getPending());
                            pendingAdapter.notifyDataSetChanged();
                        }*/



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

    private void mycommunityrApiAgain() {
        //CommonUtils.showSpotoProgressDialog(context);
        Call<MyCommunitiesResponse> call = RetrofitClient.getUniqInstance().getApi()
                .myCommunitiesCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN));
        call.enqueue(new Callback<MyCommunitiesResponse>() {
            @Override
            public void onResponse(Call<MyCommunitiesResponse> call, Response<MyCommunitiesResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        if (response.body().getJsonData().getAll().size() > 0) {




                            countcom=response.body().getJsonData().getAll().size()-response.body().getJsonData().getPending().size();
                            if (countcom > 3)
                            {
                              //  binding.imvv22.setVisibility(View.VISIBLE);
                            }
                            //   countcom=response.body().getJsonData().getAll().size();
                            if (countcom>1)
                            {
                                SpannableString content = new SpannableString(Html.fromHtml("You are part of"+" "+"<font color='#8775ed'><b>" + countcom+" Communities" + "</b></font>"));
                                //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                binding.tvpart.setText(content);
                            }
                            else
                            {
                                SpannableString content = new SpannableString(Html.fromHtml("You are part of"+" "+"<font color='#8775ed'><b>" + countcom+" Community" + "</b></font>"));
                                //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                binding.tvpart.setText(content);

                            }




                            myCommunitiesResponses.clear();
                            myCommunitiesResponses.addAll(response.body().getJsonData().getAll());

                            //     yourcommuntiyAdapter.notifyDataSetChanged();
                            yourcommuntiyAdapter.notifyItemRangeChanged(0,response.body().getJsonData().getAll().size());
                        }
                        else
                        {
                            binding.tvpart.setText("You are not a part of any community");

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

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }


    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }
}
