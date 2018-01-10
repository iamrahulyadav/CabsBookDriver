package com.example.archirayan.cabsbookdriver.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.GetAllTrips;

import java.util.ArrayList;

/**
 * Created by archirayan on 7/12/17.
 */

public class AllTripAdepter extends RecyclerView.Adapter<AllTripAdepter.ViewHolder> {

    private Context context;
    private ArrayList<GetAllTrips> getAllTrips;

    public AllTripAdepter(Context context, ArrayList<GetAllTrips> getAllTrips) {
        this.context = context;
        this.getAllTrips = getAllTrips;
    }

    @Override
    public AllTripAdepter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false);
        return new ViewHolder(viewtype);
    }


    @Override
    public void onBindViewHolder(AllTripAdepter.ViewHolder holder, int position) {
        holder.txt_notes_type.setText(getAllTrips.get(position).getAddress2());
        holder.txt_notes_detail.setText(getAllTrips.get(position).getFare());
        holder.txt_trip_date.setText(getAllTrips.get(position).getTrip_date());
    }


    @Override
    public int getItemCount() {
        return getAllTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_notes_type,txt_notes_detail,txt_trip_date;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_notes_type = (TextView) itemView.findViewById(R.id.txt_notes_type);
            txt_notes_detail = (TextView) itemView.findViewById(R.id.txt_notes_detail);
            txt_trip_date = (TextView) itemView.findViewById(R.id.txt_trip_date);
        }
    }
}
