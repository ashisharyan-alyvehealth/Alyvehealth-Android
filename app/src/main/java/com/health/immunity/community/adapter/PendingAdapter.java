package com.health.immunity.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.community.model.LeaveCommunityResponse;
import com.health.immunity.insight.model.MyCommunitiesResponse;
import com.health.immunity.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.NoticeHolder> {

    private Context context;
    private List<MyCommunitiesResponse.Pending> myCommunitiesResponses;

    public PendingAdapter(Context context, List<MyCommunitiesResponse.Pending> myCommunitiesResponses) {
        this.context = context;
        this.myCommunitiesResponses = myCommunitiesResponses;
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.pending_community_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        if (myCommunitiesResponses.get(position) != null) {
            holder.name.setText(myCommunitiesResponses.get(position).getCommunityName());
            holder.coiunt.setText(myCommunitiesResponses.get(position).getUserCount()+" "+"member(s) joined");






            holder.imvsetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    invirepoApi(myCommunitiesResponses.get(position).getCommunityId().toString(),"1");

                }
            });

            holder.imvdecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    invirepoApi(myCommunitiesResponses.get(position).getCommunityId().toString(),"2");

                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return myCommunitiesResponses.size();
    }



    public class NoticeHolder extends RecyclerView.ViewHolder {
        private TextView name,coiunt;
        private ImageView imverify,imvdecline,imvsetting;
        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvadd1);
            coiunt = itemView.findViewById(R.id.tvsub1);
            imverify = itemView.findViewById(R.id.imverify);
            imvsetting = itemView.findViewById(R.id.imvsetting);
            imvdecline = itemView.findViewById(R.id.imvdecline);

        }
    }


    //
    private void invirepoApi(String id, String status) {
        CommonUtils.showSpotoProgressDialog(context);
        Call<LeaveCommunityResponse> call = RetrofitClient.getUniqInstance().getApi()
                .invitationResponseCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), id, status);
        call.enqueue(new Callback<LeaveCommunityResponse>() {
            @Override
            public void onResponse(Call<LeaveCommunityResponse> call, Response<LeaveCommunityResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {


                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<LeaveCommunityResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }
    //

    private void LeaveApi(String id, String status) {
        CommonUtils.showSpotoProgressDialog(context);
        Call<LeaveCommunityResponse> call = RetrofitClient.getUniqInstance().getApi()
                .leaveCommunityResponseCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), id, status);
        call.enqueue(new Callback<LeaveCommunityResponse>() {
            @Override
            public void onResponse(Call<LeaveCommunityResponse> call, Response<LeaveCommunityResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {


                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<LeaveCommunityResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }
}

