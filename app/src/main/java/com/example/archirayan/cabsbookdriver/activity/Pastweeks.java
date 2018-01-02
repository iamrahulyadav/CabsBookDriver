package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.adapter.PastWeeksAdaper;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Weeklydata;
import com.example.archirayan.cabsbookdriver.model.WeeklydataResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Pastweeks extends AppCompatActivity {
    private static final String TAG = "Pastweeks";
    private ImageView img_backpress;
    private RecyclerView recycleview_pastweel;
    private PastWeeksAdaper pastWeeksAdaper;
    private ArrayList<Weeklydata> weeklydatas;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastweeks);

        img_backpress = (ImageView) findViewById(R.id.img_backpress);
        img_backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pastweeks.this,WeeklySummary.class));
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recycleview_pastweel = (RecyclerView) findViewById(R.id.recycleview_pastweel);
        recycleview_pastweel.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Pastweeks.this);
        recycleview_pastweel.setLayoutManager(layoutManager);
        getPastweekdata();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
            void refreshItems() {
                onItemsLoadComplete();
            }

            void onItemsLoadComplete() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void getPastweekdata() {
        weeklydatas = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(Pastweeks.this, Constant.DRIVERID));
        Log.e(TAG, "URL:" + Constant.BASE_URL + "driver_weeklly_trip_list.php?" + params);
        Log.e(TAG, params.toString());
        client.post(Pastweeks.this, Constant.BASE_URL+"driver_weeklly_trip_list.php?",params, new JsonHttpResponseHandler() {
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
                WeeklydataResponse model =new Gson().fromJson(new String(String.valueOf(response)),WeeklydataResponse.class);
                weeklydatas = model.getData();
                pastWeeksAdaper=new PastWeeksAdaper(Pastweeks.this, weeklydatas);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Pastweeks.this);
                recycleview_pastweel.setLayoutManager(mLayoutManager);
                recycleview_pastweel.setItemAnimator(new DefaultItemAnimator());
                recycleview_pastweel.setAdapter(pastWeeksAdaper);
                pastWeeksAdaper.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }
}
