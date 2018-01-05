package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class DriverShareMytrrip extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_acceptance_tital;
    public Button btn_sharemytrip_learnmore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_share_mytrrip);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        btn_sharemytrip_learnmore = findViewById(R.id.btn_sharemytrip_learnmore);
        img_back_acceptance_tital.setOnClickListener(this);
        btn_sharemytrip_learnmore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;

            case R.id.btn_sharemytrip_learnmore:
                startActivity(new Intent(DriverShareMytrrip.this, DriverShareMytrripSetupDetail.class));
                break;
        }
    }
}