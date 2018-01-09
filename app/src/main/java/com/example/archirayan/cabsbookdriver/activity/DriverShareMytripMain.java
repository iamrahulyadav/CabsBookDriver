package com.example.archirayan.cabsbookdriver.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.adapter.AllSelectContactsAdapter;

import static com.example.archirayan.cabsbookdriver.adapter.AllSelectContactsAdapter.strCreditId;


public class DriverShareMytripMain extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_acceptance_tital;
    public LinearLayout llayout_addressbook;
    public LinearLayout llayout_main, layoutshow_hide;
    public Switch switch1;
    public TextView txt_onoff;
    public RecyclerView recyclerview;
    public CheckBox chk_status;
    public TextView txt_moreconatcts;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_share_mytrip_main);
        txt_moreconatcts = findViewById(R.id.txt_moreconatcts);
        recyclerview = findViewById(R.id.recyclerview);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        llayout_addressbook = findViewById(R.id.llayout_addressbook);
        switch1 = findViewById(R.id.switch1);
        chk_status = findViewById(R.id.chk_status);
        txt_onoff = findViewById(R.id.txt_onoff);
        llayout_main = findViewById(R.id.llayout_main);
        layoutshow_hide = findViewById(R.id.layoutshow_hide);
        llayout_addressbook.setOnClickListener(this);
        llayout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch1.isChecked()) {
                    layoutshow_hide.setVisibility(View.GONE);
                } else {
                    layoutshow_hide.setVisibility(View.VISIBLE);
                }
            }
        });
        img_back_acceptance_tital.setOnClickListener(this);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch1.isChecked()) {
                    layoutshow_hide.setVisibility(View.GONE);
                    txt_onoff.setText("On");
                } else {
                    layoutshow_hide.setVisibility(View.VISIBLE);
                    txt_onoff.setText("Off");
                }
            }
        });

        txt_moreconatcts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverShareMytripMain.this, DriverShareMytripgetAllContacts.class));
            }
        });

        if (strCreditId.size() > 0) {
            chk_status.setChecked(true);
            AllSelectContactsAdapter contactAdapter = new
                    AllSelectContactsAdapter(strCreditId, getApplicationContext());
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            recyclerview.setAdapter(contactAdapter);
        } else {
            chk_status.setChecked(false);
            Toast.makeText(DriverShareMytripMain.this, R.string.no_result_found, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;
            case R.id.llayout_addressbook:
                OpenDialog();
                break;
        }
    }

    private void OpenDialog() {
        dialog = new Dialog(DriverShareMytripMain.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addressbook);
        Button button = (Button) dialog.findViewById(R.id.btn_nexttrip);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(DriverShareMytripMain.this, DriverShareMytripgetAllContacts.class));
            }
        });
    }
}