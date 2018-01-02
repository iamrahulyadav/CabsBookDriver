package com.example.archirayan.cabsbookdriver.activity.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.WeeklySummary;
import com.example.archirayan.cabsbookdriver.activity.help.Two.EarningsHelpTwo;
import com.example.archirayan.cabsbookdriver.activity.help.one.EarningsHelpOne;

public class EarningsHelp extends AppCompatActivity {

    private ImageView img_back_helpactivity2;
    private LinearLayout linear_module_1,linear_module_2,linear_module_3,linear_module_4,linear_module_5,linear_module_6,linear_module_7,linear_module_8,linear_module_9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings_help);

        img_back_helpactivity2 = (ImageView) findViewById(R.id.img_back_helpactivity2);
        img_back_helpactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,WeeklySummary.class));
            }
        });

        linear_module_1 = (LinearLayout) findViewById(R.id.linear_module_1);
        linear_module_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpOne.class));
            }
        });
        linear_module_2 = (LinearLayout) findViewById(R.id.linear_module_2);
        linear_module_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpTwo.class));
            }
        });
        linear_module_3 = (LinearLayout) findViewById(R.id.linear_module_3);
        linear_module_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpThree.class));

            }
        });
        linear_module_4 = (LinearLayout) findViewById(R.id.linear_module_4);
        linear_module_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpFour.class));
            }
        });
        linear_module_5 = (LinearLayout) findViewById(R.id.linear_module_5);
        linear_module_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpFive.class));
            }
        });
        linear_module_6 = (LinearLayout) findViewById(R.id.linear_module_6);
        linear_module_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpSix.class));
            }
        });
        linear_module_7 = (LinearLayout) findViewById(R.id.linear_module_7);
        linear_module_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpSeven.class));
            }
        });
        linear_module_8 = (LinearLayout) findViewById(R.id.linear_module_8);
        linear_module_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpEight.class));
            }
        });
        linear_module_9 = (LinearLayout) findViewById(R.id.linear_module_9);
        linear_module_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelp.this,EarningsHelpNine.class));
            }
        });

    }
}
