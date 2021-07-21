package com.health.immunity.community.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.community.model.CommunityUserListResponse;
import com.health.immunity.community.model.LeaveCommunityResponse;
import com.health.immunity.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewContactListAdapter extends RecyclerView.Adapter<NewContactListAdapter.NoticeHolder> {

    private Context context;
    private List<CommunityUserListResponse.JsonDatum> myCommunitiesResponses;
    int temp=0;
    int i=0;

    public NewContactListAdapter(Context context, List<CommunityUserListResponse.JsonDatum> myCommunitiesResponses) {
        this.context = context;
        this.myCommunitiesResponses = myCommunitiesResponses;
    }


//    public NewContactListAdapter(Context context, List<CommunityUserListResponse.JsonDatum> jsonData, int position) {
//        this.context = context;
//        this.myCommunitiesResponses = jsonData;
//        this.position= position;
//    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.show_contact_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        if (myCommunitiesResponses.get(position) != null) {
            holder.name.setText(myCommunitiesResponses.get(position).getUserName());
            holder.coiunt.setText(myCommunitiesResponses.get(position).getMemberStatus());
            if(YourcommunityAdapter.ustyp!=2){
                holder.remove.setVisibility(View.GONE);
                holder.remove.setEnabled(false);
            }

            if(myCommunitiesResponses.get(position).getUserId().equals(HomeActivity.userid) ){
//                System.out.println("userid "+HomeActivity.userid);
//                holder.remove.setEnabled(false);
//                holder.remove.setVisibility(View.GONE);
                if(!myCommunitiesResponses.get(position).getUserType().equals("2")){
//                    if(i==getItemCount()-1){
//
//                        return;
//                    }
//                    temp=1;
//                    while (i<getItemCount()) {
//                        onBindViewHolder(holder, i);
//                        i++;
//                    }

                }
                holder.remove.setVisibility(View.GONE);
            }
            if(myCommunitiesResponses.get(position).getMemberStatus().equals("Pending") ){
                holder.remove.setEnabled(false);
                holder.remove.setVisibility(View.GONE);

            }
            if(myCommunitiesResponses.get(position).getMemberStatus().equals("Reject") ){
                holder.remove.setEnabled(false);
                holder.remove.setVisibility(View.GONE);

            }





            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("userid"+myCommunitiesResponses.get(position).getMemberStatus());
                    int comid=  myCommunitiesResponses.get(position).getCommunityId();
                    int userid= myCommunitiesResponses.get(position).getUserId();

                    System.out.println("userid"+userid+"   "+comid);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setTitle("Remove member?");
                    builder1.setIcon(R.drawable.ic_launcher);
                    builder1.setMessage("Do you really want to remove "+ myCommunitiesResponses.get(position).getUserName()+" from this community?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    memberstatus(userid,comid);
                                    holder.remove.setEnabled(false);
                                    holder.remove.setVisibility(View.GONE);
                                    holder.name.setVisibility(View.GONE);
                                    holder.coiunt.setVisibility(View.GONE);
                                    //         holder.coiunt.setText(myCommunitiesResponses.get(position).getMemberStatus());

                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();






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
        private TextView remove;
        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvadd1);
            coiunt = itemView.findViewById(R.id.tvsub1);
            remove = itemView.findViewById(R.id.removebtn);

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






//    private void MemberstatusApi(int id,int comid, int status) {
//        CommonUtils.showSpotoProgressDialog(context);
//        Call<String> call = RetrofitClient.getUniqInstance().getApi()
//                .manageMemberStatus(id, comid, status);
//       call.enqueue(new Callback<String>() {
//           @Override
//           public void onResponse(Call<String> call, Response<String> response) {
//              System.out.println("userid"+"Success");
//           }
//
//           @Override
//           public void onFailure(Call<String> call, Throwable t) {
//               System.out.println("userid"+t);
//               t.printStackTrace();
//
//
//           }
//       });
//    }


    private void memberstatus(int id, int comid) {
        Call<JSONObject> call = RetrofitClient.getUniqInstance().getApi()
                .manageMemberStatus("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN),comid, 3, id);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                System.out.println("userid" + response.toString());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                System.out.println("userid" + t);
            }
        });
    }



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

