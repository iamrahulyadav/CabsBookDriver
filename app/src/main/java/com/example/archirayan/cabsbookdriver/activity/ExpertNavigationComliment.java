package com.example.archirayan.cabsbookdriver.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class ExpertNavigationComliment extends AppCompatActivity {

    private ImageView img_close_star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_navigation_comliment);

        img_close_star = (ImageView) findViewById(R.id.img_close_star);

        img_close_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
