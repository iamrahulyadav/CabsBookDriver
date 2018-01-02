package com.example.archirayan.cabsbookdriver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class DriverSoftwerLicensnceViewActivity extends AppCompatActivity implements View.OnClickListener
{
    public ImageView img_back_acceptance_tital;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_softwer_licensnce_view);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        img_back_acceptance_tital.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;
        }
    }
}