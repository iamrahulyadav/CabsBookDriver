package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.adapter.TabAdapter;

public class RiderCompliments extends AppCompatActivity {

    private ImageView img_back_ridercomoliments_tital;
    private TabLayout sliding_tabs;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_compliments);

        img_back_ridercomoliments_tital = (ImageView) findViewById(R.id.img_back_ridercomoliments_tital);
        img_back_ridercomoliments_tital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RiderCompliments.this,DriverMainPage.class));
            }
        });

        viewpager = (ViewPager) findViewById(R.id.viewpager);

        sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);

        TabAdapter adapter = new TabAdapter(this, getSupportFragmentManager());

        viewpager.setAdapter(adapter);

        sliding_tabs.setupWithViewPager(viewpager);

        sliding_tabs.getTabAt(0).setText("BADGES");
        sliding_tabs.getTabAt(1).setText("NOTES");
        sliding_tabs.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        sliding_tabs.setSelectedTabIndicatorColor(Color.parseColor("#3A7EFD"));

    }
}
