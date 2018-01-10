package com.example.archirayan.cabsbookdriver.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.adapter.PromotionsAdapter;

public class PromotionsActivity extends AppCompatActivity {

    private LinearLayout linear_no_upcomings,linear_promotions_list;
    private ImageView img_back_promotions_tital;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recycler_view_promotions;
    private PromotionsAdapter promotionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);

        img_back_promotions_tital = (ImageView) findViewById(R.id.img_back_promotions_tital);

        img_back_promotions_tital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linear_no_upcomings = (LinearLayout) findViewById(R.id.linear_no_upcomings);
        linear_no_upcomings.setVisibility(View.VISIBLE);

        linear_promotions_list = (LinearLayout) findViewById(R.id.linear_promotions_list);

        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        recycler_view_promotions = (RecyclerView) findViewById(R.id.recycler_view_promotions);
        recycler_view_promotions.setAdapter(promotionsAdapter);

    }
}
