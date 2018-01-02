package com.example.archirayan.cabsbookdriver.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.help.EarningsHelp;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.EarningsBalanceResponse;
import com.example.archirayan.cabsbookdriver.model.TotalOnlinetimeandtrip;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class WeeklySummary extends AppCompatActivity  {

    private static final String TAG = "WeeklySummary";
    private ImageView img_backpress;
    private LinearLayout linear_weeklypage,linear_transaction,linear_triponline,linear_trips,linear_earningshelp;
    private TextView txt_weeklydates,txt_weeklytxtamount,txt_min,txt_numberoftrip,txt_amountdeposit,txt_earningsyet;
    private LineChart linechart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_summary);

        img_backpress = (ImageView) findViewById(R.id.img_backpress);
        img_backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklySummary.this,DriverMainPage.class));
            }
        });

        linear_weeklypage = (LinearLayout) findViewById(R.id.linear_weeklypage);
        linear_weeklypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklySummary.this,Pastweeks.class));
            }
        });
        linear_transaction = (LinearLayout) findViewById(R.id.linear_transaction);
        linear_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklySummary.this,TransactionsActivity.class));

            }
        });
        linear_triponline = (LinearLayout) findViewById(R.id.linear_triponline);
        linear_triponline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WeeklySummary.this);
                builder.setTitle("Time online");
                builder.setMessage("This number reflects the total time you had the app set to Online,not the amount of time you spent on trips.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        linear_trips = (LinearLayout) findViewById(R.id.linear_trips);
        linear_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WeeklySummary.this);
                builder.setTitle("Trips");
                builder.setMessage("This nimber includes all trips, some of which may not count toward promotions.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        linear_earningshelp = (LinearLayout) findViewById(R.id.linear_earningshelp);
        linear_earningshelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklySummary.this,EarningsHelp.class));
            }
        });

        getweeklyamount();
        getWeeklyonlinetimeandtrip();

        txt_weeklydates = (TextView) findViewById(R.id.txt_weeklydates);
        txt_weeklytxtamount = (TextView) findViewById(R.id.txt_weeklytxtamount);
        txt_min = (TextView) findViewById(R.id.txt_min);
        txt_numberoftrip = (TextView) findViewById(R.id.txt_numberoftrip);
        txt_amountdeposit = (TextView) findViewById(R.id.txt_amountdeposit);
        txt_earningsyet = (TextView) findViewById(R.id.txt_earningsyet);


        linechart = (LineChart) findViewById(R.id.linechart);

        ArrayList<Entry> entries = new ArrayList<>();

        LineDataSet dataset = new LineDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("M");
        labels.add("TU");
        labels.add("W");
        labels.add("TH");
        labels.add("F");
        labels.add("SA");
        labels.add("SU");

        LineData data = new LineData(labels, dataset);

        linechart.setData(data);



    }

    private void getWeeklyonlinetimeandtrip() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(WeeklySummary.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "total_online_time_trip.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL+"total_online_time_trip.php?",googleparams, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Driver RESPONSE-" + response);
                TotalOnlinetimeandtrip dmodel = new Gson().fromJson(new String(String.valueOf(response)),TotalOnlinetimeandtrip.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_min.setText(dmodel.getData().getTotal_time());
                    txt_numberoftrip.setText(dmodel.getData().getTrip());

                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void getweeklyamount() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(WeeklySummary.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "driver_weeklly_trip.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL+"driver_weeklly_trip.php?",googleparams, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Driver RESPONSE-" + response);
                EarningsBalanceResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)),EarningsBalanceResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_weeklytxtamount.setText(dmodel.getData().getWeek_fare());
                }
                if (dmodel.getMsg().equalsIgnoreCase("Data Found")){
                    txt_earningsyet.setText("Earnings yet this week");
                }else {
                    txt_earningsyet.setText("No Earnings yet this week");
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
