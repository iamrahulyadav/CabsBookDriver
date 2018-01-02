package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class VehicleInformation extends AppCompatActivity {

    private ImageView img_back_vehicle_info;

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
    }
}
