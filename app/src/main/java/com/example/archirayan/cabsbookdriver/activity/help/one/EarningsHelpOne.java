package com.example.archirayan.cabsbookdriver.activity.help.one;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.help.EarningsHelp;

public class EarningsHelpOne extends AppCompatActivity {

    private ImageView img_back_helpactivity2;
    private LinearLayout linear_module_1,linear_module_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings_help_one);

        img_back_helpactivity2 = (ImageView) findViewById(R.id.img_back_helpactivity2);
        img_back_helpactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelpOne.this,EarningsHelp.class));
            }
        });

        linear_module_1 = (LinearLayout) findViewById(R.id.linear_module_1);
        linear_module_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelpOne.this,HelpOne_Module_1.class));
            }
        });
        linear_module_2 = (LinearLayout) findViewById(R.id.linear_module_2);
        linear_module_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EarningsHelpOne.this,HelpOne_Module_2.class));
            }
        });
    }
}
