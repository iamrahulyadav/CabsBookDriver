package com.example.archirayan.cabsbookdriver.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

public class DriverProfileSetLanguageActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView img_back_driver_profile;
    public EditText edit_driverlanguage;
    public Button btn_savelanguage;
    public String str_NewLan;
    public String str_DriverId;
    public String STR_ALL_ID;
    RecyclerView recyclerView;
    DriverprofileLanguageAdapter multiSelectAdapter;
    boolean isMultiSelect = false;
    ArrayList<DriverprofileLanuage> user_list = new ArrayList<>();
    ArrayList<String> multiselect_list = new ArrayList<String>();
    DriverprofileLanuage driverprofileLanuage;
    String[] strValues;
    CoordinatorLayout coordinatorLayout;
    private String Str_Response, Str_Response1;
    private String str_LanguageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_set_language);
        FindById();
        Click();
        Init();
        if (Utils.isConnectingToInternet(DriverProfileSetLanguageActivity.this)) {
            new getAllDriverLanguage().execute();
        } else {
            Toast.makeText(this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
        }
    }

    // // TODO: 28/12/17 Initailize the All Componant  (Memory Allocation)...
    private void Init() {
        str_DriverId = Utils.ReadSharePrefrence(DriverProfileSetLanguageActivity.this, Constant.DRIVERID);
    }

    // // TODO: 28/12/17 Click the All Componant  (setOnClickListener)...
    private void Click() {
        img_back_driver_profile.setOnClickListener(this);
        btn_savelanguage.setOnClickListener(this);
    }

    // // TODO: 28/12/17   Find The All Componant Id  (findViewById)...
    private void FindById() {
        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        btn_savelanguage = findViewById(R.id.btn_savelanguage);
        edit_driverlanguage = findViewById(R.id.edit_driverlanguage);
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
            case R.id.btn_savelanguage:
                if (Utils.isConnectingToInternet(DriverProfileSetLanguageActivity.this)) {
                    str_NewLan = edit_driverlanguage.getText().toString();
                    HashSet<String> listToSet = new HashSet<String>(strCreditId);
                    ArrayList<String> listWithoutDuplicates = new ArrayList<String>(listToSet);
                    STR_ALL_ID = TextUtils.join(",", listWithoutDuplicates);
                    Log.d("strValues", "====== Answer ======" + STR_ALL_ID);
                    if (STR_ALL_ID.equalsIgnoreCase("")) {
                        Toast.makeText(DriverProfileSetLanguageActivity.this, "Please Selecte Country", Toast.LENGTH_SHORT).show();
                    } else {
                        new AdddriverselectedLanguage().execute();
                    }
                } else {
                    Toast.makeText(this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getAllDriverLanguage extends AsyncTask<String, String, String> {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            user_list = new ArrayList<>();
            pd = new ProgressDialog(DriverProfileSetLanguageActivity.this);
            pd.setCancelable(false);
            pd.setMessage("Loading...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Str_Response = Utils.getResponseofGet(Constant.BASE_URL + "languages.php?driver_id=" + str_DriverId);
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
                        driverprofileLanuage.setLan_Name(object.getString("language_name"));
                        driverprofileLanuage.setLan_Status(object.getString("check_status"));
                        user_list.add(driverprofileLanuage);
                    }
                } else {
                    Toast.makeText(DriverProfileSetLanguageActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (user_list != null) {
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                multiSelectAdapter = new DriverprofileLanguageAdapter(DriverProfileSetLanguageActivity.this, user_list, multiselect_list);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(multiSelectAdapter);
                refreshAdapter();
            }
        }
    }

    private class AdddriverselectedLanguage extends AsyncTask<String, String, String> {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DriverProfileSetLanguageActivity.this);
            pd.setCancelable(false);
            pd.setMessage("Loading...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Str_Response1 = Utils.getResponseofGet(Constant.BASE_URL + "get_selected_languages.php?ids=" + STR_ALL_ID + "&new=" + str_NewLan + "&driver_id=" + str_DriverId);
            return Str_Response1;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                    str_LanguageData = jsonObject.getString("data");
                    strCreditId.clear();
                   /* Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Language Add Successfully.", Snackbar.LENGTH_LONG);
                    snackbar.show();*/
                    Toast.makeText(DriverProfileSetLanguageActivity.this, "Language Add Successfully.", Toast.LENGTH_SHORT).show();
                    Utils.WriteSharePrefrence(DriverProfileSetLanguageActivity.this, Constant.DATA_LAN, str_LanguageData);
                    Intent intent = new Intent(DriverProfileSetLanguageActivity.this, DriverProfile.class);
                    intent.putExtra("str_language", str_LanguageData);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DriverProfileSetLanguageActivity.this, R.string.no_result_found, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}