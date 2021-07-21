package com.health.immunity.community.adapter;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.immunity.R;
import com.health.immunity.community.model.NoticeAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {

    private Context context;
    private List<NoticeAction> noticeActions;
    private Date date, date1;

    public NoticeAdapter(Context context, List<NoticeAction> noticeActions) {
        this.context = context;
        this.noticeActions = noticeActions;
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.my_notice_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        if (noticeActions.get(position).getActionName() != null) {
            holder.tvWorkFromHome.setText(noticeActions.get(position).getActionName());
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(noticeActions.get(position).getStartDate());
            format = new SimpleDateFormat("dd-MMM-yyyy");
            String date = format.format(newDate);
            Log.e(TAG, "onBindViewHolder: Date - " + date);
            holder.tvWFHDate.setText("From : " + format.format(newDate));

            SimpleDateFormat endFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date newEndDate = endFormat.parse(noticeActions.get(position).getEndDate());
            endFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String endDate = endFormat.format(newEndDate);
            Log.e(TAG, "onBindViewHolder: End Date - " + endDate);
            holder.tvWFHEndDate.setText("To"+"      "+" : " + endFormat.format(newEndDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(noticeActions.get(position).getSession_link())) {

            if (noticeActions.get(position).getSession_link().equals("selfcare")) {
                holder.sessionlink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //   context.startActivity(new Intent(context, SelfCareActivity.class));
//                        Intent browserIntent = new Intent(context, SelfCareActivity.class);
//                        browserIntent.putExtra("orderidlink",noticeActions.get(position).getOrder_id());
//                        browserIntent.putExtra("actionname",noticeActions.get(position).getActionName());
//                        context.startActivity(browserIntent);

                    }
                });

            }
            else
            {

                holder.sessionlink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                   /* Intent browserIntent = new Intent(context, GoogleWebPermission.class);
                    browserIntent.putExtra("content_link",noticeActions.get(position).getSession_link());
                    context.startActivity(browserIntent);*/

                        Dialog dialog;
                        dialog = new Dialog(context);
                        dialog.setContentView(R.layout.custom_dialog_actions);
                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(true);
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();
                        Button tvDialog = dialog.findViewById(R.id.tvDialog);
                        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
      /*  tvDialog.setText(message);
        btnSubmit.setText(btnText);
      */
                        tvDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(noticeActions.get(position).getSession_link()));
                                context.startActivity(browserIntent);
                            }
                        });
                        btnSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // finish();
                                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("label", noticeActions.get(position).getSession_link());
                                clipboard.setPrimaryClip(clip);
                                dialog.dismiss();

                            }
                        });


                    }
                });
            }
        }
     /*   else
        {
            holder.sessionlink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 //   context.startActivity(new Intent(context, SelfCareActivity.class));
                    Intent browserIntent = new Intent(context, SelfCareActivity.class);
                    browserIntent.putExtra("orderidlink",noticeActions.get(position).getOrder_id());
                    context.startActivity(browserIntent);

                }
            });

        }*/



    }

    @Override
    public int getItemCount() {
        return noticeActions.size();
    }

    private String reFormatDate(String dateIn) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(dateIn);
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return simpleDateFormat.format(date);
    }

    public class NoticeHolder extends RecyclerView.ViewHolder {
        private TextView tvWFHDate, tvWorkFromHome, tvWFHEndDate;
        private LinearLayout sessionlink;

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            tvWorkFromHome = itemView.findViewById(R.id.tvWorkFromHome);
            tvWFHDate = itemView.findViewById(R.id.tvWFHDate);
            tvWFHEndDate = itemView.findViewById(R.id.tvWFHEndDate);
            sessionlink = itemView.findViewById(R.id.sessionlink);
        }
    }



}
