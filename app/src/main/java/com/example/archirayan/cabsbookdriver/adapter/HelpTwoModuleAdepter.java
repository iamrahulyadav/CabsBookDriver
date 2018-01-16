package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.HelptwoModule;

import java.util.ArrayList;

/**
 * Created by archirayan on 15/1/18.
 */

public class HelpTwoModuleAdepter extends RecyclerView.Adapter<HelpTwoModuleAdepter.ViewHolder>{

    private Context context;
    private ArrayList<HelptwoModule> helptwoModules;

    public HelpTwoModuleAdepter(Context context, ArrayList<HelptwoModule> helptwoModules) {
        this.context = context;
        this.helptwoModules = helptwoModules;
    }

    @Override
    public HelpTwoModuleAdepter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_list_item, parent, false);
        return new ViewHolder(viewtype);
    }


    @Override
    public void onBindViewHolder(HelpTwoModuleAdepter.ViewHolder holder, int position) {
        holder.txt_help_module.setText(helptwoModules.get(position).getName());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return helptwoModules.size();
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
