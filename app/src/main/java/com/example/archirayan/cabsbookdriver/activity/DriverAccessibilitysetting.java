package com.example.archirayan.cabsbookdriver.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;

public class DriverAccessibilitysetting extends AppCompatActivity implements View.OnClickListener {

    public ImageView img_back_acceptance_tital;
    public LinearLayout llayout_vibratornot;
    public Switch switch_vibratenot;
    private boolean mPhoneIsSilent;
    private AudioManager mAudioManager = null;
    private AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_accessibilitysetting);

        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        llayout_vibratornot = findViewById(R.id.llayout_vibratornot);

        switch_vibratenot = findViewById(R.id.switch_vibratenot);
        img_back_acceptance_tital.setOnClickListener(this);
        llayout_vibratornot.setOnClickListener(this);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //checkIfPhoneIsSilent();
        //   setButtonClickListener();
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;

            case R.id.switch_vibratenot:
                if (switch_vibratenot.isChecked())
                {
                    am.setRingerMode(0);
                    Toast.makeText(this, "Silent", Toast.LENGTH_SHORT).show();
                } else
                {
                    am.setRingerMode(1);
                    Toast.makeText(this, "Vibrat", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    /*private void setButtonClickListener() {
        llayout_vibratornot.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                if (mPhoneIsSilent) {
                    //change back to normal mode
                    mAudioManager.setRingerMode(AudioManager.);
                    mPhoneIsSilent = false;
                    switch_vibratenot.setChecked(false);
                } else {
                    mPhoneIsSilent = true;
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    switch_vibratenot.setChecked(true);
                }
            }
        });
    }

    private void checkIfPhoneIsSilent()
    {
        int ringermode = mAudioManager.getRingerMode();
        if (ringermode == AudioManager.RINGER_MODE_SILENT) {
            mPhoneIsSilent = true;
            switch_vibratenot.setChecked(true);
        } else {
            switch_vibratenot.setChecked(false);
            mPhoneIsSilent = false;
        }
    }

    private void toggleUi() {
        if (mPhoneIsSilent)
        {
            switch_vibratenot.setChecked(false);
        } else {
            switch_vibratenot.setChecked(true);
        }
    }

      @Override
        protected void onResume () {
            super.onResume();
            checkIfPhoneIsSilent();
            toggleUi();
        }
    }
    */
    }
}