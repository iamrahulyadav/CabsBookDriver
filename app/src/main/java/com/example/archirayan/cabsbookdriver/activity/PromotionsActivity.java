package com.example.archirayan.cabsbookdriver.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.adapter.PromotionsAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.GetPromotinsListResponse;
import com.example.archirayan.cabsbookdriver.model.PromotionsList;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PromotionsActivity extends AppCompatActivity {

    private static final String TAG = "PromotionsActivity";
    private LinearLayout linear_no_upcomings,linear_promotions_list;
    private ImageView img_back_promotions_tital;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recycler_view_promotions;
    private PromotionsAdapter promotionsAdapter;
    private ArrayList<PromotionsList> promotionsLists;


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


        linear_promotions_list = (LinearLayout) findViewById(R.id.linear_promotions_list);

        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        recycler_view_promotions = (RecyclerView) findViewById(R.id.recycler_view_promotions);
        recycler_view_promotions.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PromotionsActivity.this);
        recycler_view_promotions.setLayoutManager(layoutManager);

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
            void refreshItems() {
                onItemsLoadComplete();
            }

            void onItemsLoadComplete() {
                swipe_refresh.setRefreshing(false);
            }
        });

        getPromotions();


    }

    private void getPromotions() {
        promotionsLists = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Log.e(TAG, "URL:" + Constant.BASE_URL + "promotion.php");
        Log.e(TAG, params.toString());
        client.get(PromotionsActivity.this, Constant.BASE_URL+"promotion.php", new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onFinish() {

                super.onFinish();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "LOGIN RESPONSE-" + response);
                GetPromotinsListResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetPromotinsListResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    promotionsLists = model.getData();
                    promotionsAdapter = new PromotionsAdapter(PromotionsActivity.this, promotionsLists);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PromotionsActivity.this);
                    recycler_view_promotions.setLayoutManager(mLayoutManager);
                    recycler_view_promotions.setItemAnimator(new DefaultItemAnimator());
                    recycler_view_promotions.setAdapter(promotionsAdapter);
                    promotionsAdapter.notifyDataSetChanged();
                }else {
                    linear_no_upcomings.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }
}
