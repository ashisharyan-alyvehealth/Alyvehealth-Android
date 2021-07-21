package com.health.immunity.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.immunity.R;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.NoticeHolder> {

    private Context context;
    private List<MyCommunityUser> noticeActions;

    public CommunityAdapter(Context contextList,List<MyCommunityUser> noticeActions) {
        this.context = contextList;
        this.noticeActions = noticeActions;
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.my_community_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        if (noticeActions.get(position) != null) {
            holder.name.setText(noticeActions.get(position).getCommunityName());
            holder.count.setText(noticeActions.get(position).getUserCount()+" "+"members joined");
            holder.imvsetting.setText(noticeActions.get(position).getAlloversympton());
            if (noticeActions.get(position).getCommunity_type().equals("1"))
            {
                holder.imverify.setVisibility(View.VISIBLE);
                holder.imverify.setBackground(context.getResources().getDrawable(R.drawable.shape_619));

            }

        }

    }

    @Override
    public int getItemCount() {
        return noticeActions.size();
        //  return 5;
    }



    public class NoticeHolder extends RecyclerView.ViewHolder {
        private TextView name,count,imvsetting;
        private ImageView imverify;

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvadd1);
            count = itemView.findViewById(R.id.tvsub1);
            imvsetting = itemView.findViewById(R.id.imvsetting);
            imverify = itemView.findViewById(R.id.imverify);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  context.startActivity(new Intent(context, VerifyComunityActivity.class));
                }
            });

        }
    }
}

