package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class PreferenceTrip extends AppCompatActivity {

    private ImageView img_back_preference;
    private SwipeRefreshLayout swipe_trips;
    private RecyclerView recycler_view_preference_trips;
    private AllTripAdepter allTripAdepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_trip);

        img_back_preference = (ImageView) findViewById(R.id.img_back_preference);

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
        recycler_view_preference_trips.setAdapter(allTripAdepter);

    }
}
