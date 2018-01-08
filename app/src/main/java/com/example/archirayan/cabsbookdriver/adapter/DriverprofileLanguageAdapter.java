package com.example.archirayan.cabsbookdriver.adapter;

/**
 * Created by archirayan on 26/12/17.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverprofileLanuage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jaison on 08/10/16.
 */
public class DriverprofileLanguageAdapter extends RecyclerView.Adapter<DriverprofileLanguageAdapter.MyViewHolder>
{
    public static String STR_CREDITTYPEID, STR_TYPEID, STR_TYPE2;
    public static String STR_CREDITTYPEID2;
    public static ArrayList<String> strCreditId=new ArrayList<>();

    public static ArrayList<String>  strCreditId1 = new ArrayList<>();;
    public ArrayList<DriverprofileLanuage> usersList = new ArrayList<>();
    public ArrayList<String> selected_usersList = new ArrayList<>();
    public String str_Language_Data1;
    Context mContext;
    ArrayList<String> multiselect_list = new ArrayList<String>();
    boolean isMultiSelect = false;
    DriverprofileLanuage movie;
    private String str_id, strLan_Status;

   public static ArrayList<String> myList;
    public DriverprofileLanguageAdapter(Context context, ArrayList<DriverprofileLanuage> userList, ArrayList<String> selectedList) {
        this.mContext = context;
        this.usersList = userList;
        this.selected_usersList = selectedList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_languagelist, parent, false);
        return new MyViewHolder(itemView);
    }


    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        str_Language_Data1 = Utils.ReadSharePrefrence(mContext, Constant.SELECTED_DATA_LANGUAGE);
        movie = usersList.get(position);
        str_id = movie.getLan_Id();
        holder.name.setText(movie.getLan_Name());
        strLan_Status = movie.getLan_Status();

        if (strLan_Status.length() > 0)
        {
            if (strLan_Status.equalsIgnoreCase("unchecked"))
            {
                holder.ll_listitem.setImageResource(0);
                strCreditId.remove(str_id);
                multiselect_list.remove(str_id);
                STR_TYPEID = TextUtils.join(",", strCreditId);
                myList = new ArrayList<String>(Arrays.asList(STR_TYPEID.split(",")));
                Log.d("strCreditId", "========= Api Selection ======= " + myList);
            }
            else
            {
                multiselect_list.add(str_id);
                holder.ll_listitem.setImageResource(R.drawable.ic_action_click);
                strCreditId.add(str_id);
                STR_TYPEID = TextUtils.join(",", strCreditId);
                myList = new ArrayList<String>(Arrays.asList(STR_TYPEID.split(",")));
                Log.d("strCreditId", "========= Api Selection ======= " + myList);
            }
        }
        else
        {

        }

        holder.llayout_main.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                str_id = usersList.get(position).getLan_Id();
                if (!isMultiSelect)
                {
                    isMultiSelect = true;
                }

                if (multiselect_list.contains(str_id))
                {
                    multiselect_list.remove(str_id);
                    holder.ll_listitem.setImageResource(0);
                    strCreditId.remove(str_id);
                }
                else
                {
                    multiselect_list.add(str_id);
                    strCreditId.add(str_id);
                    holder.ll_listitem.setImageResource(R.drawable.ic_action_click);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public LinearLayout llayout_main;
        public ImageView ll_listitem;

        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_user_name);
            ll_listitem = (ImageView) view.findViewById(R.id.ll_listitem);
            llayout_main = itemView.findViewById(R.id.llayout_main);
        }
    }
}