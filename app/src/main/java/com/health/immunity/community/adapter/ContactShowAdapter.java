package com.health.immunity.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.health.immunity.R;
import com.health.immunity.community.model.Contact;

import java.util.ArrayList;

public class ContactShowAdapter extends RecyclerView.Adapter<ContactShowAdapter.NoticeHolder> {

    private Context context;
    private ArrayList<Contact> selectedContacts;

    public ContactShowAdapter(Context context,  ArrayList<Contact> selectedContacts) {
        this.context = context;
        this.selectedContacts = selectedContacts;
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.show_contact_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        if (selectedContacts.get(position) != null) {
            holder.name.setText(selectedContacts.get(position).getName());
            holder.number.setText(selectedContacts.get(position).getPhone());
        }

    }

    @Override
    public int getItemCount() {
        //  return noticeActions.size();
        return selectedContacts.size();
    }



    public class NoticeHolder extends RecyclerView.ViewHolder {
        private TextView name,number;

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvadd1);
            number = itemView.findViewById(R.id.tvsub1);

        }
    }
}

