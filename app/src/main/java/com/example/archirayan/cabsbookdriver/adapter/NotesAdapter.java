package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.FiveStarComents;

import java.util.ArrayList;

/**
 * Created by archirayan on 8/1/18.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{

    private Context context;
    private ArrayList<FiveStarComents> fiveStarComents;

    public NotesAdapter(Context context, ArrayList<FiveStarComents> fiveStarComents) {
        this.context = context;
        this.fiveStarComents = fiveStarComents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false);
        return new ViewHolder(viewtype);
    }


    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
        holder.txt_notes_type.setText(fiveStarComents.get(position).getFiver_star_comment());

    }


    @Override
    public int getItemCount() {
        return fiveStarComents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_notes_type,txt_notes_detail;
        public ViewHolder(View itemView) {
            super(itemView);

            txt_notes_type = (TextView) itemView.findViewById(R.id.txt_notes_type);
            txt_notes_detail = (TextView) itemView.findViewById(R.id.txt_notes_detail);

        }
    }
}
