package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class AcceptanceDetails extends AppCompatActivity {

    private ImageView img_back_acceptance_tital;

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
    }
}
