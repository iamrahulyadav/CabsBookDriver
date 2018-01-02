package com.example.archirayan.cabsbookdriver.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.adapter.DriverprofileLanguageAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverprofileLanuage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.archirayan.cabsbookdriver.adapter.DriverprofileLanguageAdapter.strCreditId;

public class DriverProfileRecommandedCityActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_driver_profile;

    public EditText edit_drivercity;
    public Button btn_savecity;
    public String str_NewLan;
    public String str_DriverId;
    public String STR_ALL_ID;
    public TextView txt_contentpolicy;
    RecyclerView recyclerView;
    DriverprofileLanguageAdapter multiSelectAdapter;
    boolean isMultiSelect = false;
    ArrayList<DriverprofileLanuage> user_list = new ArrayList<>();
    ArrayList<String> multiselect_list = new ArrayList<String>();
    DriverprofileLanuage driverprofileLanuage;
    String[] strValues;
    CoordinatorLayout coordinatorLayout;
    private String Str_Response, Str_Response1;
    private String str_CityData,str_CityDataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_recommanded_city);
        FindById();
        Click();
        Init();
        if(str_CityDataset.equalsIgnoreCase(""))
        {
            edit_drivercity.setText("");
        }
        else
        {
            edit_drivercity.setText(str_CityDataset);
        }

        if (Utils.isConnectingToInternet(DriverProfileRecommandedCityActivity.this))
        {
            new getAllDriverCity().execute();
        }
        else
        {
            Toast.makeText(this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
        }
    }


    // // TODO: 28/12/17 Initailize the variable  (Memory Allocation)...
    private void Init()
    {
        str_DriverId = Utils.ReadSharePrefrence(DriverProfileRecommandedCityActivity.this, Constant.DRIVERID);
       str_CityDataset= Utils.ReadSharePrefrence(DriverProfileRecommandedCityActivity.this, Constant.DATA_CITY_EDIT);
    }


    // // TODO: 28/12/17 Click the All Componant  (setOnClickListener)...
    private void Click()
    {
        img_back_driver_profile.setOnClickListener(this);
        btn_savecity.setOnClickListener(this);
        txt_contentpolicy.setOnClickListener(this);
    }

    // // TODO: 28/12/17   Find The All Componant Id  (findViewById)...
    private void FindById() {
        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        btn_savecity = findViewById(R.id.btn_savecity);
        edit_drivercity = findViewById(R.id.edit_drivercity);
        txt_contentpolicy = findViewById(R.id.txt_contentpolicy);
    }

    public void refreshAdapter() {
        multiSelectAdapter.selected_usersList = multiselect_list;
        multiSelectAdapter.usersList = user_list;
        multiSelectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;

            case R.id.btn_savecity:
                if (Utils.isConnectingToInternet(DriverProfileRecommandedCityActivity.this))
                {
                    str_NewLan = edit_drivercity.getText().toString();
                    HashSet<String> listToSet = new HashSet<String>(strCreditId);
                    ArrayList<String> listWithoutDuplicates = new ArrayList<String>(listToSet);
                    STR_ALL_ID = TextUtils.join(",", listWithoutDuplicates);
                    Log.d("strValues", "====== Answer ======" + STR_ALL_ID);
                    new AdddriverselectedCity().execute();
                } else {
                    Toast.makeText(this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txt_contentpolicy:
                startActivity(new Intent(DriverProfileRecommandedCityActivity.this, DriverProfileContentPolicyActivity.class));
                break;
        }
    }

    private class AdddriverselectedCity extends AsyncTask<String, String, String> {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DriverProfileRecommandedCityActivity.this);
            pd.setCancelable(false);
            pd.setMessage("Loading...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            Str_Response1 = Utils.getResponseofGet(Constant.BASE_URL + "get_selected_recommandedcity.php?ids=" + STR_ALL_ID + "&driver_id=" + str_DriverId + "&new=" + str_NewLan);
            return Str_Response1;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("true"))
                {
                    pd.dismiss();
                    str_CityData = jsonObject.getString("data");
                    strCreditId.clear();
                    Toast.makeText(DriverProfileRecommandedCityActivity.this, "Profile Update Successfully.", Toast.LENGTH_SHORT).show();
                    Utils.WriteSharePrefrence(DriverProfileRecommandedCityActivity.this, Constant.DATA_CITY, str_CityData);
                    Utils.WriteSharePrefrence(DriverProfileRecommandedCityActivity.this, Constant.DATA_CITY_EDIT, edit_drivercity.getText().toString());
                    Intent intent = new Intent(DriverProfileRecommandedCityActivity.this, DriverProfile.class);
                    intent.putExtra("str_language1", str_CityData);
                    startActivity(intent);
                    finish();
                } else {
                    pd.dismiss();
                    Toast.makeText(DriverProfileRecommandedCityActivity.this, R.string.no_result_found, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // // TODO: 1/1/18  Get all the City Data ...
    private class getAllDriverCity extends AsyncTask<String, String, String> {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            user_list = new ArrayList<>();
            pd = new ProgressDialog(DriverProfileRecommandedCityActivity.this);
            pd.setCancelable(false);
            pd.setMessage("Loading...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Str_Response = Utils.getResponseofGet(Constant.BASE_URL + "recommandedcity.php?driver_id=" + str_DriverId);
            return Str_Response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        driverprofileLanuage = new DriverprofileLanuage();
                        driverprofileLanuage.setLan_Id(object.getString("id"));
                        driverprofileLanuage.setLan_Name(object.getString("name"));
                        driverprofileLanuage.setLan_Status(object.getString("check_status"));
                        user_list.add(driverprofileLanuage);
                    }
                }
                else
                {
                    Toast.makeText(DriverProfileRecommandedCityActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (user_list != null)
            {
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                multiSelectAdapter = new DriverprofileLanguageAdapter(DriverProfileRecommandedCityActivity.this, user_list, multiselect_list);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(multiSelectAdapter);
                refreshAdapter();
            }
        }
    }
}
