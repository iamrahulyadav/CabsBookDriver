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
import com.example.archirayan.cabsbookdriver.model.HelpMainList;

import java.util.ArrayList;

/**
 * Created by archirayan on 15/1/18.
 */

public class HelpMainAdapter extends RecyclerView.Adapter<HelpMainAdapter.ViewHolder>{

    private Context context;
    private ArrayList<HelpMainList> helpMainLists;

    public HelpMainAdapter(Context context, ArrayList<HelpMainList> helpMainLists) {
        this.context = context;
        this.helpMainLists = helpMainLists;
    }

    @Override
    public HelpMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_list_item, parent, false);
        return new ViewHolder(viewtype);
    }

    @Override
    public void onBindViewHolder(HelpMainAdapter.ViewHolder holder, final int position) {
        holder.txt_help_module.setText(helpMainLists.get(position).getName());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),DriverHelpOneModule.class);
                intent.putExtra("id",helpMainLists.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return helpMainLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView card_view;
        private TextView txt_help_module;
        public ViewHolder(View itemView) {
            super(itemView);

            txt_help_module = (TextView) itemView.findViewById(R.id.txt_help_module);
            card_view = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
}
