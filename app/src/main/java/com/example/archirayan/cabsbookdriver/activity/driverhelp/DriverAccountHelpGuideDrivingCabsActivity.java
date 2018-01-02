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

public class DriverAccountHelpGuideDrivingCabsActivity extends AppCompatActivity implements View.OnClickListener
{
    public LinearLayout llayout_help_secondhelppage, llayout_help_secondhelppage2, llayout_help_secondhelppage3, llayout_help_secondhelppage4,llayout_help_secondhelppage5;
    public ImageView img_back_driver_profile;
    public LinearLayout llayoutguidedriving_help1,llayoutguidedriving_help2;
    private String str_HelpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account_help_guide_driving_cabs);
        FindviewById();
        Click();
        str_HelpMain = Utils.ReadSharePrefrence(DriverAccountHelpGuideDrivingCabsActivity.this, Constant.HELP_MAINPAGE);
        if (str_HelpMain.equalsIgnoreCase("1")) {
            llayout_help_secondhelppage.setVisibility(View.VISIBLE);
            llayout_help_secondhelppage2.setVisibility(View.GONE);
        } else if (str_HelpMain.equalsIgnoreCase("2")) {
            llayout_help_secondhelppage2.setVisibility(View.VISIBLE);
            llayout_help_secondhelppage.setVisibility(View.GONE);
        } else if (str_HelpMain.equalsIgnoreCase("3")) {
            llayout_help_secondhelppage3.setVisibility(View.VISIBLE);
            llayout_help_secondhelppage2.setVisibility(View.GONE);
            llayout_help_secondhelppage.setVisibility(View.GONE);
        } else if (str_HelpMain.equalsIgnoreCase("4")) {
            llayout_help_secondhelppage4.setVisibility(View.VISIBLE);
            llayout_help_secondhelppage2.setVisibility(View.GONE);
            llayout_help_secondhelppage.setVisibility(View.GONE);
            llayout_help_secondhelppage3.setVisibility(View.GONE);
        } else if (str_HelpMain.equalsIgnoreCase("5"))
        {
            llayout_help_secondhelppage5.setVisibility(View.VISIBLE);
            llayout_help_secondhelppage4.setVisibility(View.GONE);
            llayout_help_secondhelppage2.setVisibility(View.GONE);
            llayout_help_secondhelppage.setVisibility(View.GONE);
            llayout_help_secondhelppage3.setVisibility(View.GONE);
        }
    }

    private void Click() {
        img_back_driver_profile.setOnClickListener(this);
        llayoutguidedriving_help1.setOnClickListener(this);
        llayoutguidedriving_help2.setOnClickListener(this);
    }

    private void FindviewById() {
        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        llayout_help_secondhelppage = findViewById(R.id.llayout_help_secondhelppage);
        llayout_help_secondhelppage2 = findViewById(R.id.llayout_help_secondhelppage2);
        llayout_help_secondhelppage3 = findViewById(R.id.llayout_help_secondhelppage3);
        llayout_help_secondhelppage4 = findViewById(R.id.llayout_help_secondhelppage4);
        llayout_help_secondhelppage5=findViewById(R.id.llayout_help_secondhelppage5);

        // first menu under submenu...
        llayoutguidedriving_help1=findViewById(R.id.llayoutguidedriving_help1);
        llayoutguidedriving_help2=findViewById(R.id.llayoutguidedriving_help2);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;

            case R.id.llayoutguidedriving_help1:
                startActivity(new Intent(DriverAccountHelpGuideDrivingCabsActivity.this,DriverAccountHelpGuidDrivingCabs1Activity.class));
                Utils.WriteSharePrefrence(DriverAccountHelpGuideDrivingCabsActivity.this, Constant.HELP_FIRST_FIRSTPAGE, "1");
                break;

            case R.id.llayoutguidedriving_help2:
                startActivity(new Intent(DriverAccountHelpGuideDrivingCabsActivity.this,DriverAccountHelpGuidDrivingCabs1Activity.class));
                Utils.WriteSharePrefrence(DriverAccountHelpGuideDrivingCabsActivity.this, Constant.HELP_FIRST_FIRSTPAGE, "2");
                break;
        }
    }
}