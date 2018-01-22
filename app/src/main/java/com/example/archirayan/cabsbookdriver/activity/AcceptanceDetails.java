package com.example.archirayan.cabsbookdriver.activity;

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

public class AcceptanceDetails extends AppCompatActivity {

    private static final String TAG = "AcceptanceDetails";
    private ImageView img_back_acceptance_tital;
    private TextView txt_acceptance_rate,txt_description_acceptence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_details);

        img_back_acceptance_tital = (ImageView) findViewById(R.id.img_back_acceptance_tital);
        img_back_acceptance_tital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AcceptanceDetails.this,DriverMainPage.class));
            }
        });

        txt_acceptance_rate = (TextView) findViewById(R.id.txt_acceptance_rate);

        txt_description_acceptence = (TextView) findViewById(R.id.txt_description_acceptence);
        txt_description_acceptence.setVisibility(View.VISIBLE);

        getAcceptetionRate();

    }

    private void getAcceptetionRate() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(AcceptanceDetails.this, Constant.DRIVERID));

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
                    txt_acceptance_rate.setText(dmodel.getData().getAccept());
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
