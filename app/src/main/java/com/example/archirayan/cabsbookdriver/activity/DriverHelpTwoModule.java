package com.example.archirayan.cabsbookdriver.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.adapter.HelpOneModuleAdepter;
import com.example.archirayan.cabsbookdriver.adapter.HelpTwoModuleAdepter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.GetHelpOneResponse;
import com.example.archirayan.cabsbookdriver.model.GetHelpTwoResponse;
import com.example.archirayan.cabsbookdriver.model.HelptwoModule;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DriverHelpTwoModule extends AppCompatActivity {

    private static final String TAG = "DriverHelpTwoModule";
    private ImageView img_back_help_two;
    private RecyclerView recycler_view_help_two;
    private HelpTwoModuleAdepter helpTwoModuleAdepter;
    private ArrayList<HelptwoModule> helptwoModules;
    private String help_id;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_help_two_module);

        recycler_view_help_two = (RecyclerView) findViewById(R.id.recycler_view_help_two);
        recycler_view_help_two.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DriverHelpTwoModule.this);
        recycler_view_help_two.setLayoutManager(layoutManager);

        img_back_help_two = (ImageView) findViewById(R.id.img_back_help_two);

        img_back_help_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        help_id = getIntent().getStringExtra("id");

        getHelpModuleTwo();
    }

    private void getHelpModuleTwo() {
        pd = new ProgressDialog(DriverHelpTwoModule.this);
        pd.setMessage("loading...");
        pd.setCancelable(false);
        pd.show();
        helptwoModules = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("help_sub_catagory_id",help_id);
        Log.e(TAG, "URL:" + Constant.BASE_URL + "driver_hepl_catagory_list.php?" + params);
        Log.e(TAG, params.toString());
        client.post(DriverHelpTwoModule.this, Constant.BASE_URL+"driver_hepl_catagory_list.php?",params, new JsonHttpResponseHandler() {
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
                pd.dismiss();
                GetHelpTwoResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetHelpTwoResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    helptwoModules = model.getData();
                    helpTwoModuleAdepter = new HelpTwoModuleAdepter(DriverHelpTwoModule.this, helptwoModules);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DriverHelpTwoModule.this);
                    recycler_view_help_two.setLayoutManager(mLayoutManager);
                    recycler_view_help_two.setItemAnimator(new DefaultItemAnimator());
                    recycler_view_help_two.setAdapter(helpTwoModuleAdepter);
                    helpTwoModuleAdepter.notifyDataSetChanged();

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
