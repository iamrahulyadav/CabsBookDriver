package com.example.archirayan.cabsbookdriver.activity.driverhelp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;

public class DriverAccountHelpGuidDrivingCabs1Activity extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_driver_profile;
    LinearLayout llayout_help_secondhelppage,llayout_help_secondhelppage1,llayout_help_secondhelppage2;
   public String str_HelpSubMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account_help_guid_driving_cabs1);
        FindViewById();
        Click();
        str_HelpSubMain = Utils.ReadSharePrefrence(DriverAccountHelpGuidDrivingCabs1Activity.this, Constant.HELP_FIRST_FIRSTPAGE);
        if (str_HelpSubMain.equalsIgnoreCase("1"))
        {
            llayout_help_secondhelppage.setVisibility(View.VISIBLE);
        }
        else if (str_HelpSubMain.equalsIgnoreCase("2"))
        {
            llayout_help_secondhelppage1.setVisibility(View.VISIBLE);
            llayout_help_secondhelppage.setVisibility(View.GONE);
        }


        else if (str_HelpSubMain.equalsIgnoreCase("2"))
        {
            llayout_help_secondhelppage1.setVisibility(View.VISIBLE);
            llayout_help_secondhelppage2.setVisibility(View.GONE);
        }

    }

    private void Click()
    {
        llayout_help_secondhelppage.setOnClickListener(this);
        img_back_driver_profile.setOnClickListener(this);
        llayout_help_secondhelppage1.setOnClickListener(this);
        llayout_help_secondhelppage2.setOnClickListener(this);
    }

    private void FindViewById()
    {
        llayout_help_secondhelppage = findViewById(R.id.llayout_help_secondhelppage);
        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        llayout_help_secondhelppage1=findViewById(R.id.llayout_help_secondhelppage1);
        llayout_help_secondhelppage2=findViewById(R.id.llayout_help_secondhelppage2);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;

            case R.id.llayout_help_secondhelppage1:
                onBackPressed();
                break;

            case R.id.llayout_help_secondhelppage2:
                onBackPressed();
                break;
        }
    }
}