package com.example.archirayan.cabsbookdriver.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class TransactionsActivity extends AppCompatActivity {

    private ImageView img_backpress_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        img_backpress_activity = (ImageView) findViewById(R.id.img_backpress_activity);

        img_backpress_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
