package com.health.immunity.community.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.community.model.LeaveCommunityResponse;
import com.health.immunity.community.view.NewCreateActivity;
import com.health.immunity.insight.model.MyCommunitiesResponse;
import com.health.immunity.login.LoginActivity;
import com.health.immunity.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourcommunityAdapter extends RecyclerView.Adapter<YourcommunityAdapter.NoticeHolder> {

    private Context context;
    private List<MyCommunitiesResponse.All> myCommunitiesResponses;
    comClickInterface comClickInterface;
    public static int ustyp= 0;
    private int a=0;


    public YourcommunityAdapter(Context context, List<MyCommunitiesResponse.All> myCommunitiesResponses, comClickInterface comClickInterface) {
        this.context = context;
        this.myCommunitiesResponses = myCommunitiesResponses;
        this.comClickInterface=comClickInterface;

    }



    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.your_community_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        if (myCommunitiesResponses.get(position) != null) {
            holder.name.setText(myCommunitiesResponses.get(position).getCommunityName());
            holder.coiunt.setText(myCommunitiesResponses.get(position).getUserCount() + " " + "member(s) joined");
            if (myCommunitiesResponses.get(position).getCommunityType().equals("1")) {
                holder.imverify.setVisibility(View.VISIBLE);
                holder.imverify.setBackground(context.getResources().getDrawable(R.drawable.shape_619));

            }

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!myCommunitiesResponses.get(position).getCommunityType().equals("1") ) {

                        Intent intent = new Intent(context, NewCreateActivity.class);
                        intent.putExtra("mycomid", myCommunitiesResponses.get(position).getCommunityId().toString());

                        context.startActivity(intent);
                    }


                }
            });
            if (!myCommunitiesResponses.get(position).getCommunityType().equals("1")) {
                holder.imverify.setVisibility(View.GONE);

            }
            if(!myCommunitiesResponses.get(position).getMemberStatus().equals("0")){
                holder.imvsetting.setBackground(context.getResources().getDrawable(R.drawable.ic_more_vert_black_24dp));
                holder.imvdecline.setVisibility(View.GONE);
            }

            if (myCommunitiesResponses.get(position).getMemberStatus().equals("0"))
            {
                System.out.println("ran the tick"+position);
                holder.imvdecline.setVisibility(View.VISIBLE);
                holder.imvsetting.setBackground(context.getResources().getDrawable(R.drawable.shape_22_copy_6));
                holder.imvdecline.setBackground(context.getResources().getDrawable(R.drawable.shape_952));
                System.out.println("ran the tick3"+position);
                holder.imvsetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                      /*  if (myCommunitiesResponses.get(position).getCommunityType().equals("1"))
                        {

                            if (PreferenceHelper.getStringPreference(context, IConstant.GEOLAT).equals("0.0") && PreferenceHelper.getStringPreference(context, IConstant.GEOLATOFF).equals("0.0"))
                            {
                                Toast.makeText(context, "Please update your profile with home and office address to accept this invitation.", Toast.LENGTH_LONG).show();
                                context.startActivity(new Intent(context, ProfileActivity.class));
                            }

                            else
                            {
                                invirepoApilogout(myCommunitiesResponses.get(position).getCommunityId().toString(),"1");

                            }
                         *//*   myCommunitiesResponses.get(position).setMemberStatus("1");
                            myCommunitiesResponses.get(position).setUserCount(myCommunitiesResponses.get(position).getUserCount()+1);
                            holder.imvdecline.setVisibility(View.GONE);
                            holder.imvsetting.setBackground(context.getResources().getDrawable(R.drawable.ic_more_vert_black_24dp));
                            notifyDataSetChanged();
                            comClickInterface.setClick(2);
*//*
                        }*/
                      /*  else
                        {*/
                        invirepoApi(myCommunitiesResponses.get(position).getCommunityId().toString(),"1");
                        myCommunitiesResponses.get(position).setMemberStatus("1");
                        myCommunitiesResponses.get(position).setUserCount(myCommunitiesResponses.get(position).getUserCount()+1);
                        holder.imvdecline.setVisibility(View.GONE);
                        holder.imvsetting.setBackground(context.getResources().getDrawable(R.drawable.ic_more_vert_black_24dp));
//                             myCommunitiesResponses.clear();
//                             myCommunitiesResponses.addAll(myCommunitiesResponses);

                        //    notifyItemChanged(position);

                        notifyDataSetChanged();
                        comClickInterface.setClick(2);

                        System.out.println("ran it");


                        // }

                    }
                });

                holder.imvdecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        invirepoApi(myCommunitiesResponses.get(position).getCommunityId().toString(),"2");

                        myCommunitiesResponses.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, myCommunitiesResponses.size());


                      /*  myCommunitiesResponses.remove(position);
                        notifyDataSetChanged();
*/

                    }
                });

            }
            else
            {

                holder.imvsetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(context, holder.imvsetting);
                        if (myCommunitiesResponses.get(position).getCommunityType().equals("1") ) {
                            popup.getMenuInflater().inflate(R.menu.product_menu, popup.getMenu());

                        } else if (myCommunitiesResponses.get(position).getCommunityType().equals("2") && myCommunitiesResponses.get(position).getUser_type().equals("2")) {
                            ustyp=2;
                            System.out.println(" Ustyp"+YourcommunityAdapter.ustyp);
                            popup.getMenuInflater().inflate(R.menu.product_list_menu, popup.getMenu());


                        } else if (myCommunitiesResponses.get(position).getUser_type().equals("3")) {
                            ustyp=3;
                            System.out.println(" Ustyp"+YourcommunityAdapter.ustyp);
                            popup.getMenuInflater().inflate(R.menu.product_list_menu, popup.getMenu());

                        } else {
                            ustyp=2;
                            System.out.println(" Ustyp"+YourcommunityAdapter.ustyp);
                            popup.getMenuInflater().inflate(R.menu.product_list_menu, popup.getMenu());

                        }

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.action_leave) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("Do you want to leave this community ?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    LeaveApi(myCommunitiesResponses.get(position).getCommunityId().toString(), "3");
                                                    myCommunitiesResponses.remove(position);
                                                    // notifyItemChanged(position);
                                                    // notify();
                                                    notifyItemRemoved(position);
                                                    //  notifyDataSetChanged();
                                                    comClickInterface.setClick(1);
                                                    dialog.cancel();
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();

                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.setTitle("Alert");
                                    alert.show();


                                } else if (item.getItemId() == R.id.action_delete) {

                                    Intent intent = new Intent(context, NewCreateActivity.class);
                                    intent.putExtra("mycomid", myCommunitiesResponses.get(position).getCommunityId().toString());
                                    intent.putExtra("position",position);


                                    context.startActivity(intent);

                                }
                                else if (item.getItemId() == R.id.action_compare) {

                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.putExtra("homeactcompare", myCommunitiesResponses.get(position).getCommunityId().toString());
                                    intent.putExtra("homeactcomparename", myCommunitiesResponses.get(position).getCommunityName().toString());
                                    context.startActivity(intent);

                                }
                           /* else if (item.getItemId() == R.id.action_solution) {

                                Intent intent = new Intent(context, Webview.class);
                                intent.putExtra("content_link", "https://protect.immunityhealth.me/login");
                                context.startActivity(intent);

                            }*/
                                else if (item.getItemId() == R.id.action_edit) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("Do you want to leave this community ?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                    builder.setMessage("Do you want to close this community ?")
                                                            .setCancelable(false)
                                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    LeaveApi(myCommunitiesResponses.get(position).getCommunityId().toString(), "3");
                                                                    myCommunitiesResponses.remove(position);
                                                                    notifyItemRemoved(position);
                                                                    //  notifyDataSetChanged();
                                                                    dialog.cancel();
                                                                }
                                                            })
                                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();

                                                                }
                                                            });
                                                    AlertDialog alert = builder.create();
                                                    alert.setTitle("Confirm");
                                                    alert.show();

                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();

                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.setTitle("Alert");
                                    alert.show();

                                }/* else if (item.getItemId() == R.id.action_manage) {

                                Intent intent = new Intent(context, Webview.class);
                                intent.putExtra("content_link", myCommunitiesResponses.get(position).getRedirect_url());
                                context.startActivity(intent);
                            }*/
                                return true;
                            }
                        });

                        popup.show();

                    }
                });
            }

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


                            //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();


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
    private void invirepoApilogout(String id, String status) {
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
                            PreferenceHelper.clearAllPreferences(context);
                            PreferenceHelper.setBooleanPreference(context, IConstant.IS_LOGIN, false);
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);


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

