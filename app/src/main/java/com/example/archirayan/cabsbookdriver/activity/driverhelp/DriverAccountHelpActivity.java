package com.example.archirayan.cabsbookdriver.activity.driverhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;

public class DriverAccountHelpActivity extends AppCompatActivity implements View.OnClickListener {
    public LinearLayout llayoutguidedriving_cabs, llayoutguidedriving_cabs2, llayout_accountpayment3, llayout_signingup4, llayout_more5;
    ImageView img_back_driver_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account_help);
        FindviewById();
        Click();
    }

    private void Click() {
        img_back_driver_profile.setOnClickListener(this);
        llayoutguidedriving_cabs.setOnClickListener(this);
        llayoutguidedriving_cabs2.setOnClickListener(this);
        llayout_accountpayment3.setOnClickListener(this);
        llayout_signingup4.setOnClickListener(this);
        llayout_more5.setOnClickListener(this);
    }

    private void FindviewById() {
        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        llayoutguidedriving_cabs = findViewById(R.id.llayoutguidedriving_cabs);
        llayoutguidedriving_cabs2 = findViewById(R.id.llayoutguidedriving_cabs2);
        llayout_accountpayment3 = findViewById(R.id.llayout_accountpayment3);
        llayout_signingup4 = findViewById(R.id.llayout_signingup4);
        llayout_more5 = findViewById(R.id.llayout_more5);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;
            case R.id.llayoutguidedriving_cabs:
                startActivity(new Intent(DriverAccountHelpActivity.this, DriverAccountHelpGuideDrivingCabsActivity.class));
                Utils.WriteSharePrefrence(DriverAccountHelpActivity.this, Constant.HELP_MAINPAGE, "1");
                break;
            case R.id.llayoutguidedriving_cabs2:
                startActivity(new Intent(DriverAccountHelpActivity.this, DriverAccountHelpGuideDrivingCabsActivity.class));
                Utils.WriteSharePrefrence(DriverAccountHelpActivity.this, Constant.HELP_MAINPAGE, "2");
                break;
            case R.id.llayout_accountpayment3:
                startActivity(new Intent(DriverAccountHelpActivity.this, DriverAccountHelpGuideDrivingCabsActivity.class));
                Utils.WriteSharePrefrence(DriverAccountHelpActivity.this, Constant.HELP_MAINPAGE, "3");
                break;
            case R.id.llayout_signingup4:
                startActivity(new Intent(DriverAccountHelpActivity.this, DriverAccountHelpGuideDrivingCabsActivity.class));
                Utils.WriteSharePrefrence(DriverAccountHelpActivity.this, Constant.HELP_MAINPAGE, "4");
                break;
            case R.id.llayout_more5:
                startActivity(new Intent(DriverAccountHelpActivity.this, DriverAccountHelpGuideDrivingCabsActivity.class));
                Utils.WriteSharePrefrence(DriverAccountHelpActivity.this, Constant.HELP_MAINPAGE, "5");
                break;
        }
    }
}