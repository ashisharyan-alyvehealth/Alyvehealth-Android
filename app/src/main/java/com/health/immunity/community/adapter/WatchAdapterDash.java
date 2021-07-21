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

import com.bumptech.glide.Glide;
import com.health.immunity.R;
import com.health.immunity.community.model.DashboardData;
import com.health.immunity.community.utility.Youtube;

import java.util.List;

public class WatchAdapterDash extends RecyclerView.Adapter<WatchAdapterDash.NoticeHolder> {

    private Context context;
    private List<DashboardData.Watch> noticeActions;

    public WatchAdapterDash(Context context, List<DashboardData.Watch> noticeActions) {
        this.context = context;
        this.noticeActions = noticeActions;
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.my_watch_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        if (noticeActions.get(position).getLongDescription() != null) {
            holder.name.setText(noticeActions.get(position).getShortDescription());
        }
       /* if (noticeActions.get(position).getValidFrom() != null) {
            String givenDateString = noticeActions.get(position).getValidFrom().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date mDate = sdf.parse(givenDateString);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

                holder.time.setText(sdf2.format(mDate)+" | "+noticeActions.get(position).getSource());


            } catch (ParseException e) {
                e.printStackTrace();

            }


        }*/
        holder.imshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                // String shareBody = "Here is the share content body";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, noticeActions.get(position).getSource());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, noticeActions.get(position).getContentLink());
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
       /* holder.imshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text");
                Uri imageUri;
                imageUri = Uri.parse("android.resource://" + context.getPackageName()+ "/drawable/" + "corna");

                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });*/
        holder.time.setText(noticeActions.get(position).getWatchtime()+" min."+" | "+noticeActions.get(position).getSource());

        // String url = "https://www.youtube.com/watch?v=tResEeK6P5I";
        String url = noticeActions.get(position).getContentLink();
        System.out.println("connect"+url);
        String videoId = url.split("v=")[1]; //you will get this video id "tResEeK6P5I"

        // Glide
        String thumbnailHq = "https://img.youtube.com/vi/"+videoId+"/hqdefault.jpg";
        //  String thumbnailHq = "https://i3.ytimg.com/vi/"+videoId+"/default.jpg";
        // RequestOptions options = new RequestOptions().frame(thumb);
        Glide.with(context)
                .load(thumbnailHq)
                .into( holder.imvideo);

        holder.imviply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Youtube.class);
                intent.putExtra("content_link",videoId);


                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return noticeActions.size();
    }



    public class NoticeHolder extends RecyclerView.ViewHolder {
        private TextView time,name;
        private ImageView imvideo,imviply,imshare;

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            imvideo = itemView.findViewById(R.id.imvideo);
            imviply = itemView.findViewById(R.id.imviply);

            imshare = itemView.findViewById(R.id.imshare);

        }
    }
}

