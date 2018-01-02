package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;

public class DriverAboutActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageView img_back_acceptance_tital;
    public LinearLayout llayout_softwerlicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_about);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        img_back_acceptance_tital.setOnClickListener(this);
        llayout_softwerlicense = findViewById(R.id.llayout_softwerlicense);
        llayout_softwerlicense.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;
            case R.id.llayout_softwerlicense:
                startActivity(new Intent(DriverAboutActivity.this, DriverSoftwerLicensnceViewActivity.class));
                break;
        }
    }
}
