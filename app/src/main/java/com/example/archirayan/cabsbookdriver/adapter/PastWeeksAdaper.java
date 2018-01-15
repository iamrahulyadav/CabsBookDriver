package com.example.archirayan.cabsbookdriver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.WeeklySummary;
import com.example.archirayan.cabsbookdriver.model.Weeklydata;

import java.util.ArrayList;

/**
 * Created by archirayan on 1/12/17.
 */

public class PastWeeksAdaper extends RecyclerView.Adapter<PastWeeksAdaper.ViewHolder> {

    public static String str_StartDate, str_EndDate;
    private Context context;
    private ArrayList<Weeklydata> weeklydatas;

    public PastWeeksAdaper(Context context, ArrayList<Weeklydata> weeklydatas) {
        this.context = context;
        this.weeklydatas = weeklydatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.pastweek_row, parent, false);
        return new ViewHolder(viewtype);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.txt_time_and_date.setText(weeklydatas.get(position).getDate());
        holder.txt_trip_fare.setText(weeklydatas.get(position).getFare());
        holder.linear_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_StartDate = weeklydatas.get(position).getS_date();
                str_EndDate = weeklydatas.get(position).getE_date();
                context.startActivity(new Intent(context,WeeklySummary.class));
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return weeklydatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linear_row;
        private TextView txt_time_and_date, txt_trip_fare;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_time_and_date = (TextView) itemView.findViewById(R.id.txt_time_and_date);
            txt_trip_fare = (TextView) itemView.findViewById(R.id.txt_trip_fare);
            linear_row = itemView.findViewById(R.id.linear_row);
        }
    }
}
