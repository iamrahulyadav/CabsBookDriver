package com.example.archirayan.cabsbookdriver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;

public class WaybillActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_waybill;
    private TextView txt_no_waybill,txt_driver_name,txt_driver_license,txt_license_plate,txt_passed_time,txt_passed_rate,txt_passengers_number,txt_passengers_name,txt_from_address,txt_to_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybill);
        img_back_waybill = findViewById(R.id.img_back_waybill);
        img_back_waybill.setOnClickListener(this);

        txt_no_waybill = (TextView) findViewById(R.id.txt_no_waybill);
        txt_driver_name = (TextView) findViewById(R.id.txt_driver_name);
        txt_driver_license = (TextView) findViewById(R.id.txt_driver_license);
        txt_license_plate = (TextView) findViewById(R.id.txt_license_plate);
        txt_passed_time = (TextView) findViewById(R.id.txt_passed_time);
        txt_passed_rate = (TextView) findViewById(R.id.txt_passed_rate);
        txt_passengers_number = (TextView) findViewById(R.id.txt_passengers_number);
        txt_passengers_name = (TextView) findViewById(R.id.txt_passengers_name);
        txt_from_address = (TextView) findViewById(R.id.txt_from_address);
        txt_to_address = (TextView) findViewById(R.id.txt_to_address);

        txt_driver_name.setText(Utils.ReadSharePrefrence(WaybillActivity.this, Constant.DriverName));
        txt_license_plate.setText(Utils.ReadSharePrefrence(WaybillActivity.this,Constant.PlateNumber));
        txt_to_address.setText(Utils.ReadSharePrefrence(WaybillActivity.this,Constant.USER_DETINATION));
        txt_from_address.setText(Utils.ReadSharePrefrence(WaybillActivity.this,Constant.DETINATIONADDTESS));
        txt_passengers_name.setText(Utils.ReadSharePrefrence(WaybillActivity.this,Constant.NAME));

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
