package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.FirebaseNotificationResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CurrentTrips extends AppCompatActivity {

    private static final String TAG = "CurrentTrips";
    private ImageView img_back_currenttrip,img_userpic,img_call_dial;
    private LinearLayout linear_opentrip_settings,linear_find_trip;
    private Button btn_set_detination,btn_confrim,btn_cancle;
    private TextView txt_username,txt_source_of_trip,txt_destination_of_trip,txt_time_and_date,txt_user_contect;
    private String getImageStr;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_trips);

        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_time_and_date = (TextView) findViewById(R.id.txt_time_and_date);
        txt_user_contect = (TextView) findViewById(R.id.txt_user_contect);

        img_call_dial = (ImageView) findViewById(R.id.img_call_dial);
        img_call_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurrentTrips.this,UserContectActivity.class));

            }
        });

        linear_find_trip = (LinearLayout) findViewById(R.id.linear_find_trip);

        linear_find_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurrentTrips.this,SetDestinationDriver.class));
            }
        });

        txt_username.setText(Utils.ReadSharePrefrence(CurrentTrips.this, Constant.NAME));
        txt_user_contect.setText(Utils.ReadSharePrefrence(CurrentTrips.this,Constant.USER_mobile_num));

        img_userpic = (ImageView) findViewById(R.id.img_userpic);
        getImageStr = Utils.ReadSharePrefrence(CurrentTrips.this,Constant.IMAGE).toString();
        if (getImageStr.toString().isEmpty()) {
            Picasso.with(CurrentTrips.this).load(R.drawable.ic_profile);
        }
        else
        {
            Picasso.with(CurrentTrips.this).load(getImageStr).placeholder(R.drawable.ic_profile).into(img_userpic);

        }


        img_back_currenttrip = (ImageView) findViewById(R.id.img_back_currenttrip);
        img_back_currenttrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linear_opentrip_settings = (LinearLayout) findViewById(R.id.linear_opentrip_settings);
        linear_opentrip_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurrentTrips.this,PreferenceTrip.class));
            }
        });
        btn_set_detination = (Button) findViewById(R.id.btn_set_detination);

        btn_confrim = (Button) findViewById(R.id.btn_confrim);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendcancleNotification();
            }
        });

    }

    private void sendcancleNotification() {
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(CurrentTrips.this, Constant.DRIVERID));
        googleparams.put("user_id", Utils.ReadSharePrefrence(CurrentTrips.this,Constant.USERID));
        googleparams.put("trip_id",Utils.ReadSharePrefrence(CurrentTrips.this,Constant.TRIP_ID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "cancel_trip.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL+"cancel_trip.php?",googleparams, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Notify RESPONSE-" + response);


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });

    }

    private void sendNotification() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(CurrentTrips.this, Constant.DRIVERID));
        googleparams.put("user_id", Utils.ReadSharePrefrence(CurrentTrips.this,Constant.USERID));


        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "request_to_user_notification.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL+"request_to_user_notification.php?",googleparams, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Notify RESPONSE-" + response);
                FirebaseNotificationResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)),FirebaseNotificationResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")){
                    startActivity(new Intent(CurrentTrips.this,CurrentTripforshowlocation.class));
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
