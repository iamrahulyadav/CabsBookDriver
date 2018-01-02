package com.example.archirayan.cabsbookdriver.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;

public class NavigationProvider extends AppCompatActivity implements OnClickListener {
    public ImageView img_back_acceptance_tital;
    public LinearLayout llayout_ifinstallornot;
    public RadioButton rad_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_provider);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        llayout_ifinstallornot = findViewById(R.id.llayout_ifinstallornot);
        rad_status = findViewById(R.id.rad_status);
        llayout_ifinstallornot.setOnClickListener(this);
        img_back_acceptance_tital.setOnClickListener(this);
        setUpMapIfNeeded();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;

            case R.id.llayout_ifinstallornot:
                setUpMapIfNeeded();
                break;
        }
    }

    // // TODO: 19/12/17 Permission for Open map in playstore if not installed ...
    private void setUpMapIfNeeded()
    {
        if (isGoogleMapsInstalled()) {
            Toast.makeText(this, "Map is Already exists", Toast.LENGTH_SHORT).show();
            rad_status.setChecked(true);
        } else {
            rad_status.setChecked(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please install Google Maps");
            builder.setCancelable(false);
            builder.setPositiveButton("Install", (DialogInterface.OnClickListener) getGoogleMapsListener());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public boolean isGoogleMapsInstalled()
    {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    public OnClickListener getGoogleMapsListener()
    {
        return new OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
                startActivity(intent);
                finish();
            }
        };
    }
}