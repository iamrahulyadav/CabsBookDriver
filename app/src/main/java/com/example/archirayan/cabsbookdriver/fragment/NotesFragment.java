package com.example.archirayan.cabsbookdriver.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.adapter.NotesAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.FiveStarComents;
import com.example.archirayan.cabsbookdriver.model.GetFivestarCommentsResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    private static final String TAG = "NotesFragment";
    private LinearLayout linear_no_comments;
    private RecyclerView recycler_view_notes;
    private NotesAdapter notesAdapter;
    private ArrayList<FiveStarComents> fiveStarComents;


    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notes, container, false);

        linear_no_comments = (LinearLayout) v.findViewById(R.id.linear_no_comments);

        recycler_view_notes = (RecyclerView) v.findViewById(R.id.recycler_view_notes);
        recycler_view_notes.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view_notes.setLayoutManager(layoutManager);
        getFiveStarComments();
        return v;
    }

    private void getFiveStarComments() {
        fiveStarComents = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(getActivity(), Constant.DRIVERID));
        Log.e(TAG, "URL:" + Constant.BASE_URL + "five_star_comment.php?" + params);
        Log.e(TAG, params.toString());
        client.post(getActivity(), Constant.BASE_URL+"five_star_comment.php?",params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onFinish() {

                super.onFinish();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "LOGIN RESPONSE-" + response);
                GetFivestarCommentsResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetFivestarCommentsResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    fiveStarComents = model.getData();
                    notesAdapter = new NotesAdapter(getActivity(), fiveStarComents);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recycler_view_notes.setLayoutManager(mLayoutManager);
                    recycler_view_notes.setItemAnimator(new DefaultItemAnimator());
                    recycler_view_notes.setAdapter(notesAdapter);
                    notesAdapter.notifyDataSetChanged();
                }else {
                    linear_no_comments.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

}
