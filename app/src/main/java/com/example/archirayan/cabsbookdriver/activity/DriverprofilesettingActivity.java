package com.example.archirayan.cabsbookdriver.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;

public class DriverprofilesettingActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_acceptance_tital;
    public TextView txt_navigationprofile, txt_contacts, driver_accessibility, driver_sharemytrip;
    public SeekBar notifyvlmseekbar;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);

        setContentView(R.layout.activity_driverprofilesetting);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        txt_navigationprofile = findViewById(R.id.txt_driverprofile_navigationprofile);
        txt_contacts = findViewById(R.id.txt_driverprofile_contacts);
        driver_accessibility = findViewById(R.id.driver_accessibility);
        driver_sharemytrip = findViewById(R.id.driver_sharemytrip);

        img_back_acceptance_tital.setOnClickListener(this);
        txt_navigationprofile.setOnClickListener(this);
        txt_contacts.setOnClickListener(this);
        driver_accessibility.setOnClickListener(this);
        driver_sharemytrip.setOnClickListener(this);

        try
        {
            notifyvlmseekbar = (SeekBar)findViewById(R.id.notifyvlmseekbar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
         /*  notifyvlmseekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            notifyvlmseekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));
*/

            notifyvlmseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {

                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;

            case R.id.txt_driverprofile_navigationprofile:
                startActivity(new Intent(DriverprofilesettingActivity.this, NavigationProvider.class));
                break;

            case R.id.txt_driverprofile_contacts:
                startActivity(new Intent(DriverprofilesettingActivity.this, DriverprofileContacts.class));
                break;

            case R.id.driver_accessibility:
                startActivity(new Intent(DriverprofilesettingActivity.this, DriverAccessibilitysetting.class));
                break;

            case R.id.driver_sharemytrip:
                startActivity(new Intent(DriverprofilesettingActivity.this, DriverShareMytrrip.class));
                break;
        }
    }
}