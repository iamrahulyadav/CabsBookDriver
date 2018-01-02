package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.adapter.SpinnerAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.EditProfileResponse;
import com.example.archirayan.cabsbookdriver.model.GetVehicleType;
import com.example.archirayan.cabsbookdriver.model.GetVehicleTypeResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class VehicleInformation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "VehicleInformation";
    private ImageView img_back_vehicle_info;
    private Spinner spinner_vehicle_type;
    private ArrayList<GetVehicleType> getVehicleTypes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information);

        img_back_vehicle_info = (ImageView) findViewById(R.id.img_back_vehicle_info);

        img_back_vehicle_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VehicleInformation.this,DriverMainPage.class));
            }
        });

        spinner_vehicle_type = (Spinner) findViewById(R.id.spinner_vehicle_type);

        spinner_vehicle_type.setOnItemSelectedListener(this);

        getVehicleType();
    }

    private void getVehicleType() {
        getVehicleTypes = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_vehicle_type.php");
        Log.e(TAG, params.toString());
        client.get(this, Constant.BASE_URL + "get_vehicle_type.php",  new JsonHttpResponseHandler() {
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
                GetVehicleTypeResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetVehicleTypeResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
