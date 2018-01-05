package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;

public class DriverShareMytrripcontactDetail extends AppCompatActivity implements View.OnClickListener
{
    public ImageView img_back_acceptance_tital;
    public Button btn_selectcontacts;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_share_mytrripcontact_detail);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        img_back_acceptance_tital.setOnClickListener(this);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        btn_selectcontacts=findViewById(R.id.btn_selectcontacts);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radio_addressbook:
                        btn_selectcontacts.setVisibility(View.VISIBLE);
                        btn_selectcontacts.setText("SELECT CONTACTS");
                        btn_selectcontacts.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                startActivity(new Intent(DriverShareMytrripcontactDetail.this,DriverShareMytripgetAllContacts.class));
                            }
                        });
                        break;
                    case R.id.radio_anothrapp:
                        btn_selectcontacts.setVisibility(View.VISIBLE);
                        btn_selectcontacts.setText("NEXT");
                        btn_selectcontacts.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                startActivity(new Intent(DriverShareMytrripcontactDetail.this,DriverShareMytripMain.class));
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;
        }
    }
}
