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

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.ExpertNavigationComliment;
import com.example.archirayan.cabsbookdriver.activity.GreatAmenitiesCompliment;
import com.example.archirayan.cabsbookdriver.activity.GreatAttitudeCompliment;
import com.example.archirayan.cabsbookdriver.activity.GreatConversationCompliments;
import com.example.archirayan.cabsbookdriver.activity.NeatandCleanCompliment;
import com.example.archirayan.cabsbookdriver.activity.StarComplimentsServices;


/**
 * A simple {@link Fragment} subclass.
 */
public class BadgesFragment extends Fragment {

    private LinearLayout linear_star_service,linear_expert_navigation,linear_great_amenities,linear_great_attitude,linear_greate_conver,linear_clean;




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
