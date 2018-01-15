package com.example.archirayan.cabsbookdriver.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.driverhelp.DriverAccountHelpActivity;
import com.example.archirayan.cabsbookdriver.adapter.HelpMainAdapter;
import com.example.archirayan.cabsbookdriver.adapter.HelpOneModuleAdepter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.GetHelpMainListResponse;
import com.example.archirayan.cabsbookdriver.model.GetHelpOneResponse;
import com.example.archirayan.cabsbookdriver.model.HelpOneModule;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DriverHelpOneModule extends AppCompatActivity {

    private static final String TAG = "DriverHelpOneModule";
    private RecyclerView recycler_view_help_one;
    private ImageView img_back_help_one;
    private HelpOneModuleAdepter helpOneModuleAdepter;
    private ArrayList<HelpOneModule> helpOneModules;
    private String help_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_help_one_module);

        recycler_view_help_one = (RecyclerView) findViewById(R.id.recycler_view_help_one);
        recycler_view_help_one.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DriverHelpOneModule.this);
        recycler_view_help_one.setLayoutManager(layoutManager);
        getHelpOneModules();
        img_back_help_one = (ImageView) findViewById(R.id.img_back_help_one);

        img_back_help_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        help_id = getIntent().getStringExtra("id");
    }

    private void getHelpOneModules() {
        helpOneModules = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("hale_catagory_id",help_id);

        Log.e(TAG, "URL:" + Constant.BASE_URL + "driver_hepl_catagory_list.php?" + params);
        Log.e(TAG, params.toString());
        client.post(DriverHelpOneModule.this, Constant.BASE_URL+"driver_hepl_catagory_list.php?",params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onFinish() {

                super.onFinish();
            }
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "LOGIN RESPONSE-" + response);
                GetHelpOneResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetHelpOneResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    helpOneModules = model.getData();
                    helpOneModuleAdepter = new HelpOneModuleAdepter(DriverHelpOneModule.this, helpOneModules);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DriverHelpOneModule.this);
                    recycler_view_help_one.setLayoutManager(mLayoutManager);
                    recycler_view_help_one.setItemAnimator(new DefaultItemAnimator());
                    recycler_view_help_one.setAdapter(helpOneModuleAdepter);
                    helpOneModuleAdepter.notifyDataSetChanged();

                }
            }
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }
}
