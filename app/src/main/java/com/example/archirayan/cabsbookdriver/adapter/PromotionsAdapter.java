package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.PromotionsList;

import java.util.ArrayList;

/**
 * Created by archirayan on 9/1/18.
 */

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<PromotionsList> promotionsLists;

    public PromotionsAdapter(Context context, ArrayList<PromotionsList> promotionsLists) {
        this.context = context;
        this.promotionsLists = promotionsLists;
    }

    @Override
    public PromotionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_list_item, parent, false);
        return new MyViewHolder(viewtype);
    }


    @Override
    public void onBindViewHolder(PromotionsAdapter.MyViewHolder holder, int position) {
        holder.txt_get_promotion_name.setText(promotionsLists.get(position).getPromotions_name());
        holder.text_get_promotion_description.setText(promotionsLists.get(position).getPromotions_discription());
    }


    @Override
    public int getItemCount() {
        return promotionsLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_get_promotion_name,text_get_promotion_description;
        public MyViewHolder(View itemView) {
            super(itemView);

            txt_get_promotion_name = (TextView) itemView.findViewById(R.id.txt_get_promotion_name);
            text_get_promotion_description = (TextView) itemView.findViewById(R.id.text_get_promotion_description);

        }
    }
}
