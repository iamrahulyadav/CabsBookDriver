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
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.adapter.MakeModelIdAdepter;
import com.example.archirayan.cabsbookdriver.adapter.SpinnerAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.EditProfileResponse;
import com.example.archirayan.cabsbookdriver.model.GetMakeModelIdResponse;
import com.example.archirayan.cabsbookdriver.model.GetVehicleType;
import com.example.archirayan.cabsbookdriver.model.GetVehicleTypeResponse;
import com.example.archirayan.cabsbookdriver.model.MakeModelId;
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
    private Spinner spinner_vehicle_type,spinner_selcete_make,spinner_selecete_model;
    private ArrayList<GetVehicleType> getVehicleTypes;
    private ArrayList<MakeModelId> makeModelIds;
    private MakeModelIdAdepter makeModelIdAdepter;
    private SpinnerAdapter spinnerAdapter;
    private String setVehicaleType,setVehicaleType_Id,setMakeModel_Id,setMakeModelName;
    private TextView txt_selected_vecle_type,txt_vecle_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information);

        img_back_vehicle_info = (ImageView) findViewById(R.id.img_back_vehicle_info);
        txt_selected_vecle_type = (TextView) findViewById(R.id.txt_selected_vecle_type);
        txt_vecle_name = (TextView) findViewById(R.id.txt_vecle_name);
        spinner_selcete_make = (Spinner) findViewById(R.id.spinner_selcete_make);
        spinner_selecete_model = (Spinner) findViewById(R.id.spinner_selecete_model);


        img_back_vehicle_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VehicleInformation.this,DriverMainPage.class));
            }
        });

        spinner_vehicle_type = (Spinner) findViewById(R.id.spinner_vehicle_type);

        spinner_vehicle_type.setOnItemSelectedListener(this);
        spinner_selcete_make.setOnItemSelectedListener(this);
        spinner_selecete_model.setOnItemSelectedListener(this);

        getVehicleType();
        getMakeId();
       // editVehicleInfo();
    }

    private void getMakeId() {
        makeModelIds = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_make_model.php");
        Log.e(TAG, params.toString());
        client.get(this, Constant.BASE_URL + "get_make_model.php",  new JsonHttpResponseHandler() {
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
                GetMakeModelIdResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetMakeModelIdResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    makeModelIds = dmodel.getData();
                    makeModelIdAdepter = new MakeModelIdAdepter(VehicleInformation.this,makeModelIds);
                    spinner_selcete_make.setAdapter(makeModelIdAdepter);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });

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
                    getVehicleTypes = dmodel.getData();
                    spinnerAdapter = new SpinnerAdapter(VehicleInformation.this,getVehicleTypes);
                    spinner_vehicle_type.setAdapter(spinnerAdapter);

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
        switch (view.getId()) {
            case R.id.spinner_vehicle_type:
                TextView textView = (TextView) view.findViewById(R.id.textView);
                textView.setText(getVehicleTypes.get(position).getType());
                setVehicaleType_Id = (getVehicleTypes.get(position).getId()).toString();
                setVehicaleType = (getVehicleTypes.get(position).getType()).toString();
                Toast.makeText(this, getVehicleTypes.get(position).getId(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner_selcete_make:
                TextView textView1 = (TextView) view.findViewById(R.id.textView);
                textView1.setText(makeModelIds.get(position).getName());
                setMakeModel_Id = (makeModelIds.get(position).getId()).toString();
                setMakeModelName = (makeModelIds.get(position).getId()).toString();
                Toast.makeText(this, makeModelIds.get(position).getId(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner_selecete_model:
                break;

        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void editVehicleInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("vehicle_id",setVehicaleType_Id);
        params.put("make_id",setMakeModel_Id);

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "edit_driver_texi.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL + "edit_driver_texi.php?",params,  new JsonHttpResponseHandler() {
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

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });

    }
}
