package com.health.immunity.insight;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.health.immunity.BaseFragment;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.databinding.FragmentActivityBinding;
import com.health.immunity.insight.model.GetKpiCall;
import com.health.immunity.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends BaseFragment {
    private String title;
    private int page;


    FragmentActivityBinding binding;
    private ActivityAdapter adapter;
    private List<GetKpiCall.Activity> mykpiResponses = new ArrayList<>();


    public static ActivityFragment newInstance(int page, String title) {
        ActivityFragment fragmentFirst = new ActivityFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity, container, false);
        View view = binding.getRoot();
        initViews();


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initViews() {

        if (CommonUtils.compareset)
        {
            binding.myrange.setVisibility(View.VISIBLE);
            binding.rectangleAtTheTop.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.myrange.setVisibility(View.INVISIBLE);
            binding.rectangleAtTheTop.setVisibility(View.INVISIBLE);
        }


        adapter = new ActivityAdapter(context, mykpiResponses);
        binding.rvactivity.setLayoutManager(new LinearLayoutManager(context));
        binding.rvactivity.setAdapter(adapter);
        getKpiApi();



    }

    private void getKpiApi() {
        mykpiResponses.clear();
        adapter.notifyDataSetChanged();
        //    CommonUtils.showSpotoProgressDialog(context);
        Call<GetKpiCall> call = RetrofitClient.getUniqInstance().getApi()
                .GetKpiCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN),"0",CommonUtils.communityuser,CommonUtils.communityage,CommonUtils.communitygender,CommonUtils.communitycomm);
        call.enqueue(new Callback<GetKpiCall>() {
            @Override
            public void onResponse(Call<GetKpiCall> call, Response<GetKpiCall> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        if (response.body().getJsonData().getActivity().size() > 0)
                        {

                            mykpiResponses.clear();
                            mykpiResponses.addAll(response.body().getJsonData().getActivity());
                            adapter.notifyDataSetChanged();
                            System.out.println("#########################fitsAT"+PreferenceHelper.getStringPreference(context,FIT_STATUS_BACK));
                            if (response.body().getActivitystatus().equals("false"))
                            {
                                if (PreferenceHelper.getStringPreference(context,FIT_STATUS_BACK).equals("1"))
                                {


                                }
                                else
                                {
                                    //     Toast.makeText(context, "Please provide google fit permission", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }

                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
                //     CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<GetKpiCall> call, Throwable t) {
                t.printStackTrace();
                //  CommonUtils.dismissSpotoProgressDialog();
            }
        });

    }


}
