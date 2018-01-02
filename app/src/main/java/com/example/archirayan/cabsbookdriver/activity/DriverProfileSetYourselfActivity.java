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
import com.example.archirayan.cabsbookdriver.model.Constant;

public class DriverProfileSetYourselfActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_driver_profile;
    public EditText edit_driveryourself;
    public Button btn_saveyourself;
    public String str_driveryourself, str_Driveryourself;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_set_yourself);
        FindById();
        Click();
        Init();
        str_Driveryourself = Utils.ReadSharePrefrence(DriverProfileSetYourselfActivity.this, Constant.DATA_YOURSELF);
        edit_driveryourself.setText(str_Driveryourself);
    }

    private void FindById() {
        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        btn_saveyourself = findViewById(R.id.btn_saveyourself);
        edit_driveryourself = findViewById(R.id.edit_driveryourself);
    }

    private void Init() {
    }

    private void Click()
    {
        img_back_driver_profile.setOnClickListener(this);
        btn_saveyourself.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;
            case R.id.btn_saveyourself:
                btnaddCountry();
                break;
        }
    }

    private void btnaddCountry() {
        str_driveryourself = edit_driveryourself.getText().toString();
        Utils.WriteSharePrefrence(DriverProfileSetYourselfActivity.this, Constant.DATA_YOURSELF, str_driveryourself);
        Toast.makeText(this, "Profile Update Successfully.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DriverProfileSetYourselfActivity.this, DriverProfile.class);
        intent.putExtra("str_driveryourself", str_driveryourself);
        startActivity(intent);
        finish();
    }
}