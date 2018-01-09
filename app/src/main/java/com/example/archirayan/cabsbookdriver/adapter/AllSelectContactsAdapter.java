package com.example.archirayan.cabsbookdriver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.ContactListItem;

import java.util.ArrayList;
import java.util.List;

public class AllSelectContactsAdapter extends RecyclerView.Adapter<AllSelectContactsAdapter.ContactViewHolder> {

    public static List<ContactListItem> mFilteredList;
    public static ArrayList<String> strCreditId1 = new ArrayList<>();
    public static ArrayList<ContactListItem> strCreditId = new ArrayList<>();
    public static String STR_ConatctName;
    public Context mContext;
    public String str_number, str_name;
    ContactListItem contactListItem;
    ArrayList<String> multiselect_list = new ArrayList<String>();
    private ArrayList<ContactListItem> contactList;
    private String str_id;


    public AllSelectContactsAdapter(ArrayList<ContactListItem> contactList, Context mContext) {
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
    public void onBindViewHolder(final ContactViewHolder holder, final int position)
    {
        contactListItem = contactList.get(position);
        holder.tvContactName.setText(contactListItem.getContactName());
        holder.tvPhoneNumber.setText(contactListItem.getContactNumber());
        holder.ischeck_chk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                str_number = contactList.get(position).getContactNumber();
                str_name = contactList.get(position).getContactName();
                deleteItem(position);

              /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setMessage("Will be removed from you share trip list ");
                alertDialogBuilder.setTitle("Are you sure?");
                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.setPositiveButton("REMOVE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                deleteItem(position);
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();*/
            }
        });
    }

    private void deleteItem(int position)
    {
        contactList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, contactList.size());
    }

    @Override
    public int getItemCount()
    {
        return contactList.size();
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public Button ischeck_chk;
        ImageView ivContactImage;
        TextView tvContactName;
        TextView tvPhoneNumber;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            ischeck_chk = itemView.findViewById(R.id.ischeck_chk);
        }
    }
}