package com.health.immunity.community.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.health.immunity.R;
import com.health.immunity.community.model.Contact;
import com.health.immunity.community.model.ContactsList;

import java.util.ArrayList;
import java.util.Random;

public class ContactsListAdapter extends BaseAdapter {

    Context context;
   public ContactsList contactsList,filteredContactsList,selectedContactsList;
    String filterContactName;
    onClickInterface onClickInterface;
    private int[] mMaterialColors;

    private static final Random RANDOM = new Random();
    public ContactsListAdapter(Context context, ContactsList contactsList, onClickInterface onClickInterface){

        super();
        this.context = context;
        this.contactsList = contactsList;
        this.filteredContactsList=new ContactsList();
        this.selectedContactsList = new ContactsList();
        this.filterContactName = "";
        this.onClickInterface=onClickInterface;

    }

    public void filter(String filterContactName){



        filteredContactsList.contactArrayList.clear();

        if(filterContactName.isEmpty() || filterContactName.length()<1){
            filteredContactsList.contactArrayList.addAll(contactsList.contactArrayList);
            this.filterContactName = "";

        }
        else {
            this.filterContactName = filterContactName.toLowerCase().trim();
            for (int i = 0; i < contactsList.contactArrayList.size(); i++) {

                if (contactsList.contactArrayList.get(i).name.toLowerCase().contains(filterContactName))
                    filteredContactsList.addContact(contactsList.contactArrayList.get(i));
            }
        }
        notifyDataSetChanged();

    }

    public void addContacts(ArrayList<Contact> contacts){
        this.contactsList.contactArrayList.addAll(contacts);
        this.filter(this.filterContactName);

    }

    @Override
    public int getCount() {
        return filteredContactsList.getCount();
    }

    @Override
    public Contact getItem(int position) {
        return filteredContactsList.contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.getItem(position).id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            convertView = inflater.inflate(R.layout.contact_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.chkContact = (CheckBox) convertView.findViewById(R.id.chk_contact);
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.materialLetterIcon= (MaterialLetterIcon) convertView.findViewById(R.id.icont);


            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //   viewHolder.chkContact.setText(this.filteredContactsList.contactArrayList.get(position).toString());
        mMaterialColors = context.getResources().getIntArray(R.array.colors);

        viewHolder.materialLetterIcon.setLetter(this.filteredContactsList.contactArrayList.get(position).getName());
        viewHolder.materialLetterIcon.setInitials(true);
        viewHolder.materialLetterIcon.setInitialsNumber(2);
        viewHolder.materialLetterIcon.setLetterSize(14);
        viewHolder.materialLetterIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);

        viewHolder.chkContact.setText(this.filteredContactsList.contactArrayList.get(position).getPhone());
        viewHolder.number.setText(this.filteredContactsList.contactArrayList.get(position).getName());

        viewHolder.chkContact.setId(Integer.parseInt(this.filteredContactsList.contactArrayList.get(position).id));
        viewHolder.chkContact.setChecked(alreadySelected(filteredContactsList.contactArrayList.get(position)));

        viewHolder.chkContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Contact contact = filteredContactsList.getContact(buttonView.getId());

                if(contact!=null && isChecked && !alreadySelected(contact)){
                    selectedContactsList.addContact(contact);
                }
                else if(contact!=null && !isChecked){
                    selectedContactsList.removeContact(contact);
                }
            }
        });

   /*     viewHolder.chkContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Contact contact = filteredContactsList.getContact(buttonView.getId());

                if (CommonUtils.countsend>=10)
                {
                    viewHolder.chkContact.setChecked(false);
                    Toast.makeText(context, "You can not send more than 10 invites", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    if (contact != null && isChecked && !alreadySelected(contact)&&CommonUtils.countsend + selectedContactsList.getCount()<=9)
                    {
                        selectedContactsList.addContact(contact);
                    }
                    else if (contact != null && !isChecked)
                    {
                        selectedContactsList.removeContact(contact);
                    }
                    else
                    {
                        buttonView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttonView.setChecked(false);
                            }
                        });
                    }

                    onClickInterface.setClick(selectedContactsList.getCount());
                }
            }
        });*/

        return convertView;
    }

    public boolean alreadySelected(Contact contact)
    {
        if(this.selectedContactsList.getContact(Integer.parseInt(contact.id))!=null)
            return true;

        return false;
    }

    public static class ViewHolder{

        CheckBox chkContact;
        TextView number;
        MaterialLetterIcon materialLetterIcon;

    }
}

