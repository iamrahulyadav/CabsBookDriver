package com.example.archirayan.cabsbookdriver.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.ExpertNavigationComliment;
import com.example.archirayan.cabsbookdriver.activity.GreatAmenitiesCompliment;
import com.example.archirayan.cabsbookdriver.activity.GreatAttitudeCompliment;
import com.example.archirayan.cabsbookdriver.activity.GreatConversationCompliments;
import com.example.archirayan.cabsbookdriver.activity.NeatandCleanCompliment;
import com.example.archirayan.cabsbookdriver.activity.StarComplimentsServices;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BadgesFragment extends Fragment {

    private LinearLayout linear_star_service,linear_expert_navigation,linear_great_amenities,linear_great_attitude,linear_greate_conver,linear_clean;
    private RelativeLayout relative_img_one,relative_img_two,relative_img_three,relative_img_four,relative_img_five,relative_img_six;
    private CircleImageView img_star_service,img_expert_navigation,img_great_amenities,img_gteat_attitude,img_greate_conver,img_clean;


    public BadgesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_badges, container, false);

        linear_star_service = (LinearLayout) v.findViewById(R.id.linear_star_service);
        linear_expert_navigation = (LinearLayout) v.findViewById(R.id.linear_expert_navigation);
        linear_great_amenities = (LinearLayout) v.findViewById(R.id.linear_great_amenities);
        linear_great_attitude = (LinearLayout) v.findViewById(R.id.linear_great_attitude);
        linear_greate_conver = (LinearLayout) v.findViewById(R.id.linear_greate_conver);
        linear_clean = (LinearLayout) v.findViewById(R.id.linear_clean);

        relative_img_one = (RelativeLayout) v.findViewById(R.id.relative_img_one);
        relative_img_two = (RelativeLayout) v.findViewById(R.id.relative_img_two);
        relative_img_three = (RelativeLayout) v.findViewById(R.id.relative_img_three);
        relative_img_four = (RelativeLayout) v.findViewById(R.id.relative_img_four);
        relative_img_five = (RelativeLayout) v.findViewById(R.id.relative_img_five);
        relative_img_six = (RelativeLayout) v.findViewById(R.id.relative_img_six);

        img_star_service =(CircleImageView) v.findViewById(R.id.img_star_service);
        img_expert_navigation =(CircleImageView) v.findViewById(R.id.img_expert_navigation);
        img_great_amenities =(CircleImageView) v.findViewById(R.id.img_great_amenities);
        img_gteat_attitude =(CircleImageView) v.findViewById(R.id.img_gteat_attitude);
        img_greate_conver =(CircleImageView) v.findViewById(R.id.img_greate_conver);
        img_clean =(CircleImageView) v.findViewById(R.id.img_clean);

        img_star_service.setVisibility(View.GONE);
        relative_img_one.setVisibility(View.VISIBLE);

        img_expert_navigation.setVisibility(View.GONE);
        relative_img_two.setVisibility(View.VISIBLE);

        img_great_amenities.setVisibility(View.GONE);
        relative_img_three.setVisibility(View.VISIBLE);

        img_gteat_attitude.setVisibility(View.GONE);
        relative_img_four.setVisibility(View.VISIBLE);

        img_greate_conver.setVisibility(View.GONE);
        relative_img_five.setVisibility(View.VISIBLE);

        img_clean.setVisibility(View.GONE);
        relative_img_six.setVisibility(View.VISIBLE);

        linear_star_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StarComplimentsServices.class));
            }
        });

        linear_expert_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ExpertNavigationComliment.class));
            }
        });

        linear_great_amenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GreatAmenitiesCompliment.class));
            }
        });

        linear_great_attitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GreatAttitudeCompliment.class));
            }
        });

        linear_greate_conver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GreatConversationCompliments.class));
            }
        });

        linear_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NeatandCleanCompliment.class));

            }
        });


        return v;
    }

}
