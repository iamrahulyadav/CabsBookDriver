package com.example.archirayan.cabsbookdriver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class WaybillActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_waybill;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybill);
        img_back_waybill = findViewById(R.id.img_back_waybill);
        img_back_waybill.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back_waybill:
                onBackPressed();
                break;
        }
    }
}
