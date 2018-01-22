package com.example.archirayan.cabsbookdriver.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;

public class HelpDiscription extends AppCompatActivity {

    private ImageView img_back_help_two;
    private TextView txt_help_tital,txt_help_discription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_discription);

        img_back_help_two = (ImageView) findViewById(R.id.img_back_help_two);

        img_back_help_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_help_tital = (TextView) findViewById(R.id.txt_help_tital);
        txt_help_tital.setText(Utils.ReadSharePrefrence(HelpDiscription.this, Constant.HELP_ITEM));

        txt_help_discription = (TextView) findViewById(R.id.txt_help_discription);

        txt_help_discription.setText(Utils.ReadSharePrefrence(HelpDiscription.this, Constant.Discription));
    }
}
