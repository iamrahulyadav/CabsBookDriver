package com.example.archirayan.cabsbookdriver.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.adapter.FeddbackAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.FeedbackItemList;
import com.example.archirayan.cabsbookdriver.model.GetRiderFeedbackResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RiderFeedback extends AppCompatActivity {

    private static final String TAG = "RiderFeedback";
    private ImageView img_back_riderfeedback_tital;
    private LinearLayout linear_no_feedback;
    private RecyclerView recycler_view_feedback;
    private FeddbackAdapter feddbackAdapter;
    private ArrayList<FeedbackItemList> feedbackItemLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_feedback);

        img_back_riderfeedback_tital = (ImageView) findViewById(R.id.img_back_riderfeedback_tital);

        img_back_riderfeedback_tital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linear_no_feedback = (LinearLayout) findViewById(R.id.linear_no_feedback);


        recycler_view_feedback = (RecyclerView) findViewById(R.id.recycler_view_feedback);
        recycler_view_feedback.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RiderFeedback.this);
        recycler_view_feedback.setLayoutManager(layoutManager);
        getFeedbacks();



    }

    private void getFeedbacks() {
        feedbackItemLists = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(RiderFeedback.this, Constant.DRIVERID));
        Log.e(TAG, "URL:" + Constant.BASE_URL + "get_feedback.php?" + params);
        Log.e(TAG, params.toString());
        client.post(RiderFeedback.this, Constant.BASE_URL+"get_feedback.php?",params, new JsonHttpResponseHandler() {
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
                GetRiderFeedbackResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetRiderFeedbackResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    feedbackItemLists = model.getData();
                    feddbackAdapter = new FeddbackAdapter(RiderFeedback.this, feedbackItemLists);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RiderFeedback.this);
                    recycler_view_feedback.setLayoutManager(mLayoutManager);
                    recycler_view_feedback.setItemAnimator(new DefaultItemAnimator());
                    recycler_view_feedback.setAdapter(feddbackAdapter);
                    feddbackAdapter.notifyDataSetChanged();
                }else {
                    linear_no_feedback.setVisibility(View.VISIBLE);
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
