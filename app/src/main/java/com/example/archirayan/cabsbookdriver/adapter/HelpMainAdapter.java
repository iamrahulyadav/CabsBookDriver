package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.HelpMainList;

import java.util.ArrayList;

/**
 * Created by archirayan on 15/1/18.
 */

public class HelpMainAdapter extends RecyclerView.Adapter<HelpMainAdapter.ViewHolder>{

    private Context context;
    private ArrayList<HelpMainList> helpMainLists;

    @Override
    public HelpMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_list_item, parent, false);
        return new ViewHolder(viewtype);
    }


    @Override
    public void onBindViewHolder(HelpMainAdapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
