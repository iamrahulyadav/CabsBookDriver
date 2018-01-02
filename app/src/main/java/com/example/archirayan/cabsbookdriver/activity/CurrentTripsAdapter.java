package com.example.archirayan.cabsbookdriver.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;

/**
 * Created by archirayan on 7/12/17.
 */

public class CurrentTripsAdapter extends RecyclerView.Adapter<CurrentTripsAdapter.ViewHolder> {

    public CurrentTripsAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public CurrentTripsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.tripdetails_row, parent, false);
        return new ViewHolder(viewtype);
    }


    @Override
    public void onBindViewHolder(CurrentTripsAdapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_source, txt_destination, txt_date_time, txt_fare;
        private Button btn_accept, btn_cancle;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_source = (TextView) itemView.findViewById(R.id.txt_source);
            txt_destination = (TextView) itemView.findViewById(R.id.txt_destination);
            txt_date_time = (TextView) itemView.findViewById(R.id.txt_date_time);
            txt_fare = (TextView) itemView.findViewById(R.id.txt_fare);

            btn_accept = (Button) itemView.findViewById(R.id.btn_accept);
            btn_cancle = (Button) itemView.findViewById(R.id.btn_cancle);


        }
    }
}
