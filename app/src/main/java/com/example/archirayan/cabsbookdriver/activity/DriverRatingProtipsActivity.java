package com.example.archirayan.cabsbookdriver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.adapter.DriverRatingProtipsAdapter;


import java.util.ArrayList;

public class DriverRatingProtipsActivity extends AppCompatActivity implements View.OnClickListener
{
    public ImageView img_back_waybill;

    LinearLayoutManager layoutManager;
    RecyclerView fashionRecyclerView;
    ArrayList<DriverratingProTripsClass> fashionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_rating_protips);
        img_back_waybill = findViewById(R.id.img_back_waybill);
        img_back_waybill.setOnClickListener(this);
        Init();
    }

    private void Init() {
        fashionRecyclerView = (RecyclerView) findViewById(R.id.ratingprotips_recyclerView);
        fashionArrayList = new ArrayList<DriverratingProTripsClass>();
        layoutManager = new LinearLayoutManager(DriverRatingProtipsActivity.this, LinearLayoutManager.VERTICAL, false);
        fashionRecyclerView.setLayoutManager(layoutManager);
        fashionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DriverRatingProtipsAdapter fashionListAdapter = new DriverRatingProtipsAdapter(DriverRatingProtipsActivity.this, fashionArrayList);
        fashionRecyclerView.setAdapter(fashionListAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_waybill:
                onBackPressed();
                break;
        }
    }


}
