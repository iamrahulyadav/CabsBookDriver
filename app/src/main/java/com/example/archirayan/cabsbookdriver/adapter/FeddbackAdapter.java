package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.FeedbackItemList;

import java.util.ArrayList;

/**
 * Created by archirayan on 10/1/18.
 */

public class FeddbackAdapter extends RecyclerView.Adapter<FeddbackAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FeedbackItemList> feedbackItemLists;

    public FeddbackAdapter(Context context, ArrayList<FeedbackItemList> feedbackItemLists) {
        this.context = context;
        this.feedbackItemLists = feedbackItemLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false);
        return new ViewHolder(viewtype);
    }


    @Override
    public void onBindViewHolder(FeddbackAdapter.ViewHolder holder, int position) {
        holder.txt_notes_type.setText(feedbackItemLists.get(position).getComment());
    }


    @Override
    public int getItemCount() {
        return feedbackItemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_notes_type;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_notes_type = (TextView) itemView.findViewById(R.id.txt_notes_type);
        }
    }
}
