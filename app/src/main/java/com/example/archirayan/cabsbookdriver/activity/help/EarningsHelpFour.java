package com.example.archirayan.cabsbookdriver.activity.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class EarningsHelpFour extends AppCompatActivity {

    private ImageView img_back_helpactivity2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings_help_four);

        img_back_helpactivity2 = (ImageView) findViewById(R.id.img_back_helpactivity2);
        img_back_helpactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelpFour.this,EarningsHelp.class));
            }
        });
    }
}
