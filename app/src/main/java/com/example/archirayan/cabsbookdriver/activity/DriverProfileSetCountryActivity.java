package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.activity.DriverProfile;
import com.example.archirayan.cabsbookdriver.activity.DriverProfileSetFavouritStory;
import com.example.archirayan.cabsbookdriver.activity.DriverProfileSetLanguageActivity;
import com.example.archirayan.cabsbookdriver.model.Constant;

public class DriverProfileSetCountryActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_driver_profile;
    public EditText edit_drivercountry;
    public Button btn_savecountry;
    public String str_driverCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_set_country);
        FindById();
        Click();
        Init();
    }

    private void FindById() {

        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        btn_savecountry = findViewById(R.id.btn_savecountry);
        edit_drivercountry = findViewById(R.id.edit_drivercountry);

    }

    private void Init() {
    }

    private void Click() {
        img_back_driver_profile.setOnClickListener(this);
        btn_savecountry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;
            case R.id.btn_savecountry:
                btnaddCountry();
                break;
        }
    }

    private void btnaddCountry()
    {
        str_driverCountry = edit_drivercountry.getText().toString();
        Utils.WriteSharePrefrence(DriverProfileSetCountryActivity.this, Constant.DATA_COUNTRY, str_driverCountry);
        Toast.makeText(this, "Profile Update Successfully.", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(DriverProfileSetCountryActivity.this,DriverProfile.class);
        intent.putExtra("str_country",str_driverCountry);
        startActivity(intent);
        finish();
    }
}