package com.example.archirayan.cabsbookdriver.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.adapter.HelpMainAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.GetHelpMainListResponse;
import com.example.archirayan.cabsbookdriver.model.HelpMainList;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DriverAccountHelpActivity extends AppCompatActivity {

    private static final String TAG = "DriverAccountHelpActivity";
    private RecyclerView recycler_view_help;
    private ImageView img_back_driver_profile;
    private HelpMainAdapter helpMainAdapter;
    private ArrayList<HelpMainList> helpMainLists;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account_help);

        img_back_driver_profile = (ImageView) findViewById(R.id.img_back_driver_profile);

        recycler_view_help = (RecyclerView) findViewById(R.id.recycler_view_help);
        recycler_view_help.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DriverAccountHelpActivity.this);
        recycler_view_help.setLayoutManager(layoutManager);
        getHelpMainModules();

        img_back_driver_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getHelpMainModules() {
        pd = new ProgressDialog(DriverAccountHelpActivity.this);
        pd.setMessage("loading...");
        pd.setCancelable(false);
        pd.show();
        helpMainLists = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.get(DriverAccountHelpActivity.this, Constant.BASE_URL+"driver_hepl_catagory_list.php?", new JsonHttpResponseHandler() {
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
                GetHelpMainListResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetHelpMainListResponse.class);
                pd.dismiss();
                if (model.getStatus().equalsIgnoreCase("true")) {
                    helpMainLists = model.getData();
                    helpMainAdapter = new HelpMainAdapter(DriverAccountHelpActivity.this, helpMainLists);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DriverAccountHelpActivity.this);
                    recycler_view_help.setLayoutManager(mLayoutManager);
                    recycler_view_help.setItemAnimator(new DefaultItemAnimator());
                    recycler_view_help.setAdapter(helpMainAdapter);
                    helpMainAdapter.notifyDataSetChanged();

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