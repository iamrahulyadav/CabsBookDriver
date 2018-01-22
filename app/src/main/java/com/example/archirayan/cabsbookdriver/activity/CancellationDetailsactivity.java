package com.example.archirayan.cabsbookdriver.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverTripsRateResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CancellationDetailsactivity extends AppCompatActivity {

    private static final String TAG = "CancellationDetailsactivity";
    private ImageView img_back_cancledetails_tital;
    private TextView txt_canclection_rate,txt_description_cancelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation_detailsactivity);

        img_back_cancledetails_tital = (ImageView) findViewById(R.id.img_back_cancledetails_tital);
        img_back_cancledetails_tital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CancellationDetailsactivity.this,DriverMainPage.class));
            }
        });
        txt_canclection_rate = (TextView) findViewById(R.id.txt_canclection_rate);

        txt_description_cancelation = (TextView) findViewById(R.id.txt_description_cancelation);
        txt_description_cancelation.setVisibility(View.VISIBLE);

        getCancellationRate();
    }

    @SuppressLint("LongLogTag")
    private void getCancellationRate() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(CancellationDetailsactivity.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "trip_accept_cancel_rate.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "trip_accept_cancel_rate.php?", googleparams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "Driver RESPONSE-" + response);
                DriverTripsRateResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), DriverTripsRateResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_canclection_rate.setText(dmodel.getData().getCancel());
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
