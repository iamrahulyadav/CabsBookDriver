package com.example.archirayan.cabsbookdriver.activity.driverhelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class DriverAccountHelpActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView img_back_driver_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account_help);
        FindviewById();
        Click();
    }

    private void Click()
    {
        img_back_driver_profile.setOnClickListener(this);
    }

    private void FindviewById()
    {
        img_back_driver_profile=findViewById(R.id.img_back_driver_profile);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;
        }
    }
}
