package com.example.archirayan.cabsbookdriver.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;

public class DriverShareMytripMain extends AppCompatActivity implements View.OnClickListener
{
    public ImageView img_back_acceptance_tital;
    public LinearLayout llayout_addressbook;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_share_mytrip_main);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        llayout_addressbook=findViewById(R.id.llayout_addressbook);
        llayout_addressbook.setOnClickListener(this);
        img_back_acceptance_tital.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;
            case R.id.llayout_addressbook:
                OpenDialog();
                break;
        }
    }

    private void OpenDialog()
    {
        dialog = new Dialog(DriverShareMytripMain.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addressbook);
        Button button = (Button) dialog.findViewById(R.id.btn_nexttrip);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                startActivity(new Intent(DriverShareMytripMain.this,DriverShareMytripgetAllContacts.class));
            }
        });
    }
}