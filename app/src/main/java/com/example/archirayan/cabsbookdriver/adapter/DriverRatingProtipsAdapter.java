package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.DriverRatingProtipsDetailActivity;

import java.util.ArrayList;

/**
 * Created by archirayan on 13/12/17.
 */

public class DriverRatingProtipsAdapter extends RecyclerView.Adapter<DriverRatingProtipsAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> dealArrayList;


    public DriverRatingProtipsAdapter(Context context, ArrayList dealArrayList) {
        this.context = context;
        this.dealArrayList = dealArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ratingprotips, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageIv.setImageResource(R.drawable.images);

        holder.main_llayoutprotips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, DriverRatingProtipsDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageIv;
        public LinearLayout main_llayoutprotips;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageIv = (ImageView) itemView.findViewById(R.id.img_deals);
            main_llayoutprotips = itemView.findViewById(R.id.main_llayoutprotips);
        }
    }
}