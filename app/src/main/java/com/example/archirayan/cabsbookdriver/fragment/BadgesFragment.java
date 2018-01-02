package com.example.archirayan.cabsbookdriver.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.archirayan.cabsbookdriver.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BadgesFragment extends Fragment {



    public BadgesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_badges, container, false);


        return v;
    }

}
