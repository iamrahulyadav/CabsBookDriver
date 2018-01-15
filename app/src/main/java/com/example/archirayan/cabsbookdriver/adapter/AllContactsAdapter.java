package com.example.archirayan.cabsbookdriver.adapter;

/**
 * Created by archirayan on 5/1/18.
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.ContactListItem;

import java.util.ArrayList;
import java.util.List;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ContactViewHolder> implements Filterable {

    public static List<ContactListItem> mFilteredList;
    public static ArrayList<String> strCreditId1 = new ArrayList<>();
    public static ArrayList<ContactListItem> strCreditId = new ArrayList<>();
    public static String STR_ConatctName;
    public Context mContext;
    public String str_number;
    ContactListItem contactListItem;
    ;
    ArrayList<String> multiselect_list = new ArrayList<String>();
    private List<ContactListItem> contactList;
    private String str_id, str_id1;


    public AllContactsAdapter(List<ContactListItem> contactList, Context mContext) {
        this.contactList = contactList;
        this.mContext = mContext;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_selectcontactlist, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        contactListItem = contactList.get(position);
        holder.tvContactName.setText(contactListItem.getContactName());
        holder.tvPhoneNumber.setText(contactListItem.getContactNumber());
        /*holder.ivContactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_number = contactList.get(position).getContactNumber();

                Log.d("str_number", "====== str_number======" + str_number);
                onCall();

            }
        });*/

        holder.ischeck_chk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                str_id = contactList.get(position).getContactName();
                str_id1 = contactList.get(position).getContactNumber();

                if (holder.ischeck_chk.isChecked()) {
                    multiselect_list.add(str_id);
                    holder.ischeck_chk.setBackgroundColor(R.drawable.ic_action_click);
                    contactListItem = new ContactListItem();
                    contactListItem.setContactName(str_id);
                    contactListItem.setContactNumber(str_id1);
                    strCreditId.add(contactListItem);
                    STR_ConatctName = TextUtils.join(",", strCreditId);
                    Log.d("strCreditId", "========   Answer =====" + STR_ConatctName);
                } else {
                    contactListItem = new ContactListItem();
                    contactListItem.setContactName(str_id);
                    contactListItem.setContactNumber(str_id1);
                    strCreditId.remove(contactListItem);
                    holder.ischeck_chk.setBackgroundColor(R.color.black);
                    multiselect_list.remove(str_id);
                    STR_ConatctName = TextUtils.join(",", strCreditId);
                    Log.d("strCreditId", "========   Answer =====" + STR_ConatctName);
                }
            }
        });
    }

    private void onCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + str_number));
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        mContext.startActivity(callIntent);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = contactList;
                } else {
                    ArrayList<ContactListItem> filteredList = new ArrayList<>();

                    for (ContactListItem androidVersion : contactList) {

                        if (androidVersion.getContactName().toLowerCase().contains(charString) || androidVersion.getContactNumber().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ContactListItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public CheckBox ischeck_chk;
        //ImageView ivContactImage;
        TextView tvContactName;
        TextView tvPhoneNumber;


        public ContactViewHolder(View itemView) {
            super(itemView);
           // ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            ischeck_chk = (CheckBox) itemView.findViewById(R.id.ischeck_chk);
        }
    }
}