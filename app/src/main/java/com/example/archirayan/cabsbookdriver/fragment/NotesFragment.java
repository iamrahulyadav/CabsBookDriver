package com.example.archirayan.cabsbookdriver.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    private LinearLayout linear_no_comments;
    private RecyclerView recycler_view_notes;


    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notes, container, false);

        linear_no_comments = (LinearLayout) v.findViewById(R.id.linear_no_comments);
        linear_no_comments.setVisibility(View.VISIBLE);

        recycler_view_notes = (RecyclerView) v.findViewById(R.id.recycler_view_notes);
        return v;
    }

}
