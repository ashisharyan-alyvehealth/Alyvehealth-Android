package com.health.immunity.insight.model;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.immunity.R;

import java.util.List;

public class FiltercommunityAdapter extends RecyclerView.Adapter<FiltercommunityAdapter.NoticeHolder> {

    private Context context;
    private List<MyCommunitiesResponse.All> myCommunitiesResponses;
    //  private int row_index=-1;
    private userClickInterface userClickInterface;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public FiltercommunityAdapter(Context context, List<MyCommunitiesResponse.All> myCommunitiesResponses, userClickInterface userClickInterface) {
        this.context = context;
        this.myCommunitiesResponses = myCommunitiesResponses;
        this.userClickInterface=userClickInterface;
    }



    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.your_filter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        if (myCommunitiesResponses.get(position) != null) {
            MyCommunitiesResponse.All healthCondition = myCommunitiesResponses.get(position);
            holder.name.setText(healthCondition.getCommunityName());
            holder.name.setChecked(healthCondition.isChecked());
            holder.name.setTag(myCommunitiesResponses.get(position));
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                /*    String ss= String.valueOf(myCommunitiesResponses.get(position).getCommunityId());
                    userClickInterface.setClick(myCommunitiesResponses.get(position).getCommunityId());
                    CommonUtils.communityuser=ss;
                 //   Toast.makeText(context, CommonUtils.communityuser, Toast.LENGTH_SHORT).show();
                 // row_index=position;
                    notifyDataSetChanged();
*/
                    if (selectedItems.get(position, false)) {
                        selectedItems.delete(position);
                        v.setSelected(false);
                        holder.name.setBackground(context.getResources().getDrawable(R.drawable.background_language, null));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            holder.name.setTextColor(context.getResources().getColor(R.color.colorPrimary, null));
                        }
                    } else {
                        selectedItems.put(position, true);
                        v.setSelected(true);
                        holder.name.setBackground(context.getResources().getDrawable(R.drawable.button_background, null));
                        holder.name.setTextColor(Color.WHITE);
                    }
                    String data = "";
                    MyCommunitiesResponse.All Industry1 = (MyCommunitiesResponse.All) holder.name.getTag();

                    Industry1.setChecked(holder.name.isChecked());

                    myCommunitiesResponses.get(position).setChecked(holder.name.isChecked());
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb1 = new StringBuilder();
                    boolean foundOne = false;
                    for (int j = 0; j < myCommunitiesResponses.size(); j++) {

                        if (myCommunitiesResponses.get(j).isChecked() == true) {
                            if (foundOne) {
                                sb.append(", ");
                                sb1.append(", ");
                            }
                            foundOne = true;
                            sb.append(myCommunitiesResponses.get(j).getCommunityId());
                            sb1.append(myCommunitiesResponses.get(j).getCommunityName());
                        }
                    }
                    System.out.println("##################" + sb1.toString());

                    userClickInterface.setClick(sb.toString());
                    userClickInterface.setClickname(sb1.toString());




                }
            });

          /*  if(row_index==position){
                holder.name.setBackground(context.getResources().getDrawable(R.drawable.button_background));
                holder.name.setTextColor(context.getResources().getColor(R.color.white));
            }
            else
            {
                holder.name.setBackground(context.getResources().getDrawable(R.drawable.background_language));
                holder.name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
*/




        }

    }

    @Override
    public int getItemCount() {
        return myCommunitiesResponses.size();
    }



    public class NoticeHolder extends RecyclerView.ViewHolder {
        private CheckBox name;
        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvcomcom);


        }
    }



}
