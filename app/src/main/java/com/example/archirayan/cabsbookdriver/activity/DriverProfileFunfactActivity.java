package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.activity.DriverProfile;
import com.example.archirayan.cabsbookdriver.activity.DriverProfileContentPolicyActivity;
import com.example.archirayan.cabsbookdriver.model.Constant;

public class DriverProfileFunfactActivity extends AppCompatActivity implements View.OnClickListener
{
    public ImageView img_back_driver_profile;
    public EditText edit_driverfunfact;
    public Button btn_savefunfact;
    public String str_driverfunfact, str_Driverfunfactset;
    public TextView txt_contentpolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_funfact);
        FindById();
        Click();
        Init();
        str_Driverfunfactset = Utils.ReadSharePrefrence(DriverProfileFunfactActivity.this, Constant.DATA_FUNFACT);
        edit_driverfunfact.setText(str_Driverfunfactset);
    }


    private void FindById() {
        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        btn_savefunfact = findViewById(R.id.btn_savefunfact);
        txt_contentpolicy = findViewById(R.id.txt_contentpolicy);

        edit_driverfunfact = findViewById(R.id.edit_driverfunfact);
    }

    private void Init() {
    }

    private void Click() {
        img_back_driver_profile.setOnClickListener(this);
        btn_savefunfact.setOnClickListener(this);
        txt_contentpolicy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;
            case R.id.btn_savefunfact:
                btnaddCountry();
                break;

            case R.id.txt_contentpolicy:
                startActivity(new Intent(DriverProfileFunfactActivity.this, DriverProfileContentPolicyActivity.class));
                break;

        }
    }

    private void btnaddCountry() {
        str_driverfunfact = edit_driverfunfact.getText().toString();
        Utils.WriteSharePrefrence(DriverProfileFunfactActivity.this, Constant.DATA_FUNFACT, str_driverfunfact);
        Toast.makeText(this, "Profile Update Successfully.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DriverProfileFunfactActivity.this, DriverProfile.class);
        intent.putExtra("str_driverfunfact", str_driverfunfact);
        startActivity(intent);
        finish();
    }
}
