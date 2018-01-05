package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.NoticeItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by archirayan on 5/1/18.
 */

public class NoticeAdepter extends RecyclerView.Adapter<NoticeAdepter.MyViewHolder>{

    private Context context;
    private ArrayList<NoticeItem> noticeItems;

    public NoticeAdepter(Context context, ArrayList<NoticeItem> noticeItems) {
        this.context = context;
        this.noticeItems = noticeItems;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewtype = LayoutInflater.from(parent.getContext()).inflate(R.layout.nitify_item, parent, false);
        return new MyViewHolder(viewtype);
    }


    @Override
    public void onBindViewHolder(NoticeAdepter.MyViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView card_view;
        private TextView txt_notice_type,txt_notice_detail;
        private CircleImageView img_notice_pic;
        public MyViewHolder(View itemView) {
            super(itemView);

            card_view = (CardView) itemView.findViewById(R.id.card_view);

            txt_notice_type =(TextView) itemView.findViewById(R.id.txt_notice_type);
            txt_notice_detail = (TextView) itemView.findViewById(R.id.txt_notice_detail);

            img_notice_pic = (CircleImageView) itemView.findViewById(R.id.img_notice_pic);

        }
    }
}
