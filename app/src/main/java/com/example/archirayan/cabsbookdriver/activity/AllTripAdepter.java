package com.example.archirayan.cabsbookdriver.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by archirayan on 7/12/17.
 */

public class AllTripAdepter extends RecyclerView.Adapter<AllTripAdepter.ViewHolder> {



    @Override
    public AllTripAdepter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(AllTripAdepter.ViewHolder holder, int position) {

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
