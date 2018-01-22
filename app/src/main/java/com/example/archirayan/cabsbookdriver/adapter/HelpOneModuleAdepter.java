package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.DriverHelpOneModule;
import com.example.archirayan.cabsbookdriver.activity.DriverHelpTwoModule;
import com.example.archirayan.cabsbookdriver.model.HelpMainList;
import com.example.archirayan.cabsbookdriver.model.HelpOneModule;

import java.util.ArrayList;

/**
 * Created by archirayan on 15/1/18.
 */

public class HelpOneModuleAdepter extends RecyclerView.Adapter<HelpOneModuleAdepter.ViewHolder>{

    private Context context;
    private ArrayList<HelpOneModule> helpOneModules;

    public HelpOneModuleAdepter(Context context, ArrayList<HelpOneModule> helpOneModules) {
        this.context = context;
        this.helpOneModules = helpOneModules;
    }

    @Override
    public HelpOneModuleAdepter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_list_item, parent, false);
        return new ViewHolder(viewtype);
    }

    @Override
    public void onBindViewHolder(HelpOneModuleAdepter.ViewHolder holder, final int position) {
        holder.txt_help_module.setText(helpOneModules.get(position).getName());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),DriverHelpTwoModule.class);
                intent.putExtra("id",helpOneModules.get(position).getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return helpOneModules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView card_view;
        private TextView txt_help_module;
        public ViewHolder(View itemView) {
            super(itemView);

            txt_help_module = (TextView) itemView.findViewById(R.id.txt_help_module);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
