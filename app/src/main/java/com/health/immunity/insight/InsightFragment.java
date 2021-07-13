package com.health.immunity.insight;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.health.immunity.CommonUtils;
import com.health.immunity.R;
import com.health.immunity.databinding.FragmentInsightBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsightFragment extends Fragment {
    private FragmentInsightBinding binding;
    FragmentPagerAdapter adapterViewPager;
    private String userstr="",agestr="",genderstr="",communitystr="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsightFragment newInstance(String param1, String param2) {
        InsightFragment fragment = new InsightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_insight, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_insight, container, false);
        View view = binding.getRoot();

        initViews();
        return view;
    }

    private void initViews() {


        binding.swtchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    if (TextUtils.isEmpty(agestr)&&TextUtils.isEmpty(genderstr)&&TextUtils.isEmpty(userstr))
                    {

                        binding.tvloren.setText(Html.fromHtml("<font color='#4c4e55'>" + "Your comparision set is "+ "</font>"+"All Ages"+ "<font color='#4c4e55'>"+" & "+"</font>" +"All Genders"+ "<font color='#4c4e55'>"+" & "+"</font>"+"All Immunity Users." ));

                    }
                    else
                    {
                        binding.tvloren.setText("Your comparision set is");
                    }

                    binding.tvloren.setVisibility(View.VISIBLE);
                    CommonUtils.compareset=true;
                    binding.tvfilter.setVisibility(View.VISIBLE);
                    adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
                    binding.vpPager.setAdapter(adapterViewPager);
                    binding.vpPager.setCurrentItem(0);
                    binding.vpPager.setPagingEnabled(false);
                    binding.vpPager.beginFakeDrag();
                    binding.tabs.setViewPager(binding.vpPager);

                    binding.tabs.setTextColor(Color.parseColor("#4c4e55"));
                }
                else
                {
                    CommonUtils.compareset=false;
                    binding.tvloren.setVisibility(View.GONE);
                    binding.tvfilter.setVisibility(View.INVISIBLE);


                    adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
                    binding.vpPager.setAdapter(adapterViewPager);


                    binding.vpPager.setCurrentItem(0);


                    binding.tabs.setViewPager(binding.vpPager);
                    binding.tabs.setTextColor(Color.parseColor("#4c4e55"));
                    CommonUtils.communityage="";
                    CommonUtils.communityuser="";
                    CommonUtils.communitygender="";
                    CommonUtils.communitycomm="";
                    CommonUtils.communityagename="";
                    CommonUtils.communityusername="";
                    CommonUtils.communitygendername="";
                    CommonUtils.communitycommname="";
                    userstr="";
                    agestr="";
                    genderstr="";
                    communitystr="";

                }
            }
        });

        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        binding.vpPager.setAdapter(adapterViewPager);
        if (getArguments()!=null)
        {
            System.out.println("####################"+getArguments().getString("vitalcoming"));
            System.out.println("####################"+getArguments().getString("comparecoming"));

            if (!TextUtils.isEmpty(getArguments().getString("vitalcoming")))
            {
                String getArgument = getArguments().getString("vitalcoming");

                if (getArgument.equalsIgnoreCase("vital"))
                {
                    binding.vpPager.setCurrentItem(1);
                }
            }
            else if (!TextUtils.isEmpty(getArguments().getString("comparecoming")))
            {
                CommonUtils.communitycomm=getArguments().getString("comparecoming");
                String getArgument = getArguments().getString("comparenamecoming");
                binding.swtchbtn.setChecked(true);
                binding.tvloren.setText(Html.fromHtml("<font color='#4c4e55'>" + "Your comparision set is "+ "</font>"+"All Ages"+ "<font color='#4c4e55'>"+" & "+"</font>" +"All Genders"+ "<font color='#4c4e55'>"+" & "+"</font>"+getArgument ));
                binding.tvloren.setVisibility(View.VISIBLE);
                CommonUtils.compareset=true;
                binding.tvfilter.setVisibility(View.VISIBLE);
                adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
                binding.vpPager.setAdapter(adapterViewPager);
                binding.vpPager.setCurrentItem(0);
                binding.vpPager.setNestedScrollingEnabled(true);
                binding.tabs.setViewPager(binding.vpPager);
                binding.tabs.setTextColor(Color.parseColor("#4c4e55"));
            }
        }

        else
        {
            binding.vpPager.setCurrentItem(0);

        }
        binding.tabs.setViewPager(binding.vpPager);
        binding.tabs.setTextColor(Color.parseColor("#4c4e55"));
        binding.tabs.setAllCaps(false);
        binding.tvfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Intent intent=new Intent(context,FilterActivity.class);
                //intent.putExtra("kpi_id",getIntent().getStringExtra("kpi_id"));

              //  startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
            }
        });

    }
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return PedoActivityFragment.newInstance("0", "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return VitalFragment.newInstance("0", "Page # 2");
              //  case 2: // Fragment # 1 - This will show SecondFragment

                   // return MyReportsFragment.newInstance(0,"Page # 3");
                default:
                    return null;
            }
        }


        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "Activity";
                case 1:

                    return "Vitals";
//                case 2:
//
//                    return "Reports";





            }
            return null;
            //return "Page " + position;
        }

    }


}