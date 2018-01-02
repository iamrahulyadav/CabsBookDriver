package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.activity.DriverProfileSetCountryActivity;
import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;

public class DriverProfileSetFavouritStory extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_driver_profile;
    public EditText edit_driverstory;
    public Button btn_savestory;
    public String str_driverstory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_set_favourit_story);
        FindById();
        Click();
        Init();
    }

    private void FindById() {

        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        btn_savestory = findViewById(R.id.btn_savestory);
        edit_driverstory = findViewById(R.id.edit_driverstory);

    }

    private void Init() {
    }

    private void Click() {
        img_back_driver_profile.setOnClickListener(this);
        btn_savestory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;
            case R.id.btn_savestory:
                btnaddCountry();
                break;
        }
    }

    private void btnaddCountry()
    {
        str_driverstory= edit_driverstory.getText().toString();
        Utils.WriteSharePrefrence(DriverProfileSetFavouritStory.this, Constant.DATA_STORY, str_driverstory);
        Toast.makeText(this, "Profile Update Successfully.", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(DriverProfileSetFavouritStory.this,DriverProfile.class);
        intent.putExtra("str_driverstory",str_driverstory);
        startActivity(intent);
        finish();
    }
}

