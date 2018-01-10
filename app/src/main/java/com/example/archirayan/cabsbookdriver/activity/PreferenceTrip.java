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
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.adapter.FeddbackAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.GetAllTrips;
import com.example.archirayan.cabsbookdriver.model.GetAllTripsResponse;
import com.example.archirayan.cabsbookdriver.model.GetRiderFeedbackResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PreferenceTrip extends AppCompatActivity {

    private static final String TAG = "PreferenceTrip";
    private ImageView img_back_preference;
    private SwipeRefreshLayout swipe_trips;
    private RecyclerView recycler_view_preference_trips;
    private AllTripAdepter allTripAdepter;
    private ArrayList<GetAllTrips> getAllTrips;
    private TextView txt_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_trip);

        img_back_preference = (ImageView) findViewById(R.id.img_back_preference);

        txt_reset = (TextView) findViewById(R.id.txt_reset);

        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreferenceTrip.this,SetDestinationDriver.class));
            }
        });

        img_back_preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreferenceTrip.this,CurrentTrips.class));
            }
        });

        swipe_trips = (SwipeRefreshLayout) findViewById(R.id.swipe_trips);

        swipe_trips.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
            void refreshItems() {
                onItemsLoadComplete();
            }

            void onItemsLoadComplete() {
                swipe_trips.setRefreshing(false);
            }
        });

        recycler_view_preference_trips = (RecyclerView) findViewById(R.id.recycler_view_preference_trips);
        recycler_view_preference_trips.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PreferenceTrip.this);
        recycler_view_preference_trips.setLayoutManager(layoutManager);
        getAllYourTrips();

    }

    private void getAllYourTrips() {
        getAllTrips = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(PreferenceTrip.this, Constant.DRIVERID));
        Log.e(TAG, "URL:" + Constant.BASE_URL + "get_success_driver_trip.php?" + params);
        Log.e(TAG, params.toString());
        client.post(PreferenceTrip.this, Constant.BASE_URL+"get_success_driver_trip.php?",params, new JsonHttpResponseHandler() {
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
                GetAllTripsResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetAllTripsResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    getAllTrips = model.getData();
                    allTripAdepter = new AllTripAdepter(PreferenceTrip.this, getAllTrips);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PreferenceTrip.this);
                    recycler_view_preference_trips.setLayoutManager(mLayoutManager);
                    recycler_view_preference_trips.setItemAnimator(new DefaultItemAnimator());
                    recycler_view_preference_trips.setAdapter(allTripAdepter);
                    allTripAdepter.notifyDataSetChanged();
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
