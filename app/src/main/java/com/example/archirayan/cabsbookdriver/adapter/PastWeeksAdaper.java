package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.Weeklydata;

import java.util.ArrayList;

/**
 * Created by archirayan on 1/12/17.
 */

public class PastWeeksAdaper extends RecyclerView.Adapter<PastWeeksAdaper.ViewHolder>{


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
    public void onBindViewHolder(PastWeeksAdaper.ViewHolder holder, int position) {
        holder.txt_time_and_date.setText(weeklydatas.get(position).getDate());
        holder.txt_trip_fare.setText(weeklydatas.get(position).getFare());

    }


    @Override
    public int getItemCount() {
        return weeklydatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_time_and_date,txt_trip_fare;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_time_and_date = (TextView) itemView.findViewById(R.id.txt_time_and_date);
            txt_trip_fare = (TextView) itemView.findViewById(R.id.txt_trip_fare);

        }
    }
}
