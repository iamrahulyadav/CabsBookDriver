package com.example.archirayan.cabsbookdriver.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;

public class RiderFeedback extends AppCompatActivity {

    private ImageView img_back_riderfeedback_tital;
    private LinearLayout linear_no_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_feedback);

        img_back_riderfeedback_tital = (ImageView) findViewById(R.id.img_back_riderfeedback_tital);

        img_back_riderfeedback_tital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linear_no_feedback = (LinearLayout) findViewById(R.id.linear_no_feedback);
        linear_no_feedback.setVisibility(View.VISIBLE);

    }
}
