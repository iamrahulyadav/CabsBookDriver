package com.example.archirayan.cabsbookdriver.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverProfile extends AppCompatActivity implements View.OnClickListener {
    public TextView txt_username;
    public CircleImageView img_userimage;
    public LinearLayout llayout_driverprofile_selectlanguage, llayout_driverprofile_recommandedcity, llayout_driverprofile_selectcountry2, llayout_driverprofile_addyourself, llayout_driverprofile_selectfavoritestory, llayout_driverprofile_selectfunfactmessage;
    public TextView txt_language, txt_country, txt_funfact, txt_yourself, txt_city;
    public ImageView img_language, img_country, img_yourself, img_city;
    public ImageView img_back_driver_profile, img_story, img_funfact;
    public String str_Username;
    public String str_UserImage;
    public String str_Data, str_CityData;
    public String str_DataCountry;
    public TextView txt_story;
    public String str_Languagedata, str_Countrydata, str_Driverdata, str_DriverFunfact, str_DriverYourself;
    public TextView txt_driver_month, txt_driver_rating, txt_driver_trip;
    public String str_DriverId;
    public String Str_Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        FindViewById();
        Init();
        Click();
        txt_username.setText(str_Username);
        txt_driver_rating.setText("4");
        if (Utils.isConnectingToInternet(DriverProfile.this)) {
            new getCountTripAndMonth().execute();
        } else {
            Toast.makeText(DriverProfile.this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
        }
        if (str_UserImage.length() > 0) {
            Picasso.with(DriverProfile.this)
                    .load(str_UserImage)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(img_userimage);
        } else {
        }

        if (str_Languagedata.equalsIgnoreCase("")) {
            txt_language.setText("What languages do you know?");
            img_language.setImageResource(R.drawable.ic_plus);
        } else {
            txt_language.setText(str_Languagedata);
            img_language.setImageResource(R.drawable.ic_edit_pencilweb);
        }

        if (str_Countrydata.equalsIgnoreCase("")) {
            txt_country.setText("Where did you grow up?");
            img_country.setImageResource(R.drawable.ic_plus);
        } else {
            txt_country.setText(str_Countrydata);
            img_country.setImageResource(R.drawable.ic_edit_pencilweb);
        }

        if (str_Driverdata.equalsIgnoreCase("")) {
            txt_story.setText("What your favorite Cabs Book story to tell?");
            img_story.setImageResource(R.drawable.ic_plus);
        } else {
            txt_story.setText(str_Driverdata);
            img_story.setImageResource(R.drawable.ic_edit_pencilweb);
        }


        if (str_DriverFunfact.equalsIgnoreCase("")) {
            txt_funfact.setText("What's a fun fact about you?");
            img_funfact.setImageResource(R.drawable.ic_plus);
        } else {
            txt_funfact.setText(str_DriverFunfact);
            img_funfact.setImageResource(R.drawable.ic_edit_pencilweb);
        }

        if (str_DriverYourself.equalsIgnoreCase("")) {
            txt_yourself.setText("What's a fun fact about you?");
            img_yourself.setImageResource(R.drawable.ic_plus);
        } else {
            txt_yourself.setText(str_DriverYourself);
            img_yourself.setImageResource(R.drawable.ic_edit_pencilweb);
        }


        if (str_CityData.equalsIgnoreCase("")) {
            txt_city.setText("What can you recommend in your city?");
            img_city.setImageResource(R.drawable.ic_plus);
        } else {
            txt_city.setText(str_CityData);
            img_city.setImageResource(R.drawable.ic_edit_pencilweb);
        }
    }

    // // TODO: 28/12/17 Initailize the All Componant  (Memory Allocation)...
    private void Init() {
        str_DriverId = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DRIVERID);
        str_Data = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DATA_LAN);
        str_CityData = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DATA_CITY);
        str_DataCountry = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DATA_COUNTRY);
        str_Username = Utils.ReadSharePrefrence(DriverProfile.this, Constant.USERNAME_DRIVER);
        str_UserImage = Utils.ReadSharePrefrence(DriverProfile.this, Constant.IMAGE_DRIVER);
        str_Languagedata = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DATA_LAN);
        str_Countrydata = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DATA_COUNTRY);
        str_Driverdata = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DATA_STORY);
        str_DriverFunfact = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DATA_FUNFACT);
        str_DriverYourself = Utils.ReadSharePrefrence(DriverProfile.this, Constant.DATA_YOURSELF);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            txt_country.setText("Where did you grow up?");
            txt_story.setText("What your favorite Cabs Book story to tell?");
            txt_language.setText("What languages do you know?");
            txt_funfact.setText("What's a fun fact about you?");
            txt_yourself.setText("How would you describe yourself?");
            txt_city.setText("What can you recommend in your city?");
            img_country.setImageResource(R.drawable.ic_plus);
            img_language.setImageResource(R.drawable.ic_plus);
            img_story.setImageResource(R.drawable.ic_plus);
            img_funfact.setImageResource(R.drawable.ic_plus);
            img_yourself.setImageResource(R.drawable.ic_plus);
            img_city.setImageResource(R.drawable.ic_plus);
        } else {
            txt_story.setText(extras.getString("str_driverstory"));
            img_story.setImageResource(R.drawable.ic_edit_pencilweb);
            txt_country.setText(extras.getString("str_country"));
            img_country.setImageResource(R.drawable.ic_edit_pencilweb);
            txt_language.setText(extras.getString("str_language"));
            img_language.setImageResource(R.drawable.ic_edit_pencilweb);
            txt_funfact.setText(extras.getString("str_driverfunfact"));
            img_funfact.setImageResource(R.drawable.ic_edit_pencilweb);
            txt_yourself.setText(extras.getString("str_driveryourself"));
            img_yourself.setImageResource(R.drawable.ic_edit_pencilweb);
            txt_yourself.setText(extras.getString("str_driveryourself"));
            img_yourself.setImageResource(R.drawable.ic_edit_pencilweb);
            txt_city.setText(extras.getString("str_language1"));
            img_city.setImageResource(R.drawable.ic_edit_pencilweb);
        }
    }

    // // TODO: 28/12/17 Click the All Componant  (setOnClickListener)...
    private void Click() {
        llayout_driverprofile_selectcountry2.setOnClickListener(this);
        llayout_driverprofile_selectlanguage.setOnClickListener(this);
        img_back_driver_profile.setOnClickListener(this);
        llayout_driverprofile_selectfavoritestory.setOnClickListener(this);
        llayout_driverprofile_selectfunfactmessage.setOnClickListener(this);
        llayout_driverprofile_addyourself.setOnClickListener(this);
        llayout_driverprofile_recommandedcity.setOnClickListener(this);
    }

    // // TODO: 28/12/17   Find The All Componant Id  (findViewById)...
    private void FindViewById() {
        txt_driver_month = findViewById(R.id.txt_driver_month);
        txt_driver_rating = findViewById(R.id.txt_driver_rating);
        txt_driver_trip = findViewById(R.id.txt_driver_trip);
        txt_language = findViewById(R.id.txt_language);
        txt_country = findViewById(R.id.txt_country);
        img_language = findViewById(R.id.img_language);
        img_story = findViewById(R.id.img_story);
        img_city = findViewById(R.id.img_city);
        txt_city = findViewById(R.id.txt_city);
        llayout_driverprofile_selectcountry2 = findViewById(R.id.llayout_driverprofile_selectcountry2);
        llayout_driverprofile_selectlanguage = findViewById(R.id.llayout_driverprofile_selectlanguage);
        txt_username = findViewById(R.id.txt_username);
        img_back_driver_profile = (ImageView) findViewById(R.id.img_back_driver_profile);
        img_userimage = findViewById(R.id.img_userimage);
        img_country = findViewById(R.id.img_country);
        llayout_driverprofile_selectfavoritestory = findViewById(R.id.llayout_driverprofile_selectfavoritestory);
        txt_story = findViewById(R.id.txt_story);
        llayout_driverprofile_selectfunfactmessage = findViewById(R.id.llayout_driverprofile_selectfunfactmessage);
        txt_funfact = findViewById(R.id.txt_funfact);
        img_funfact = findViewById(R.id.img_funfact);
        llayout_driverprofile_addyourself = findViewById(R.id.llayout_driverprofile_addyourself);
        txt_yourself = findViewById(R.id.txt_yourself);
        img_yourself = findViewById(R.id.img_yourself);
        llayout_driverprofile_recommandedcity = findViewById(R.id.llayout_driverprofile_recommandedcity);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llayout_driverprofile_selectlanguage:
                startActivity(new Intent(DriverProfile.this, DriverProfileSetLanguageActivity.class));
                break;

            case R.id.llayout_driverprofile_selectcountry2:
                startActivity(new Intent(DriverProfile.this, DriverProfileSetCountryActivity.class));
                break;

            case R.id.llayout_driverprofile_selectfavoritestory:
                startActivity(new Intent(DriverProfile.this, DriverProfileSetFavouritStory.class));
                break;

            case R.id.llayout_driverprofile_selectfunfactmessage:
                startActivity(new Intent(DriverProfile.this, DriverProfileFunfactActivity.class));
                break;

            case R.id.llayout_driverprofile_addyourself:
                startActivity(new Intent(DriverProfile.this, DriverProfileSetYourselfActivity.class));
                break;

            case R.id.llayout_driverprofile_recommandedcity:
                startActivity(new Intent(DriverProfile.this, DriverProfileRecommandedCityActivity.class));
                break;

            case R.id.img_back_driver_profile:
                startActivity(new Intent(DriverProfile.this, DriverMainPage.class));
                break;
        }
    }

    private class getCountTripAndMonth extends AsyncTask<String, String, String> {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DriverProfile.this);
            pd.setCancelable(false);
            pd.setMessage("Loading...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Str_Response = Utils.getResponseofGet(Constant.BASE_URL + "count_trips_and_month.php?driver_id=" + str_DriverId);
            return Str_Response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("true"))
                {
                    txt_driver_trip.setText(jsonObject.getJSONObject("data").getString("trip_count"));
                    txt_driver_month.setText(jsonObject.getJSONObject("data").getString("month_count"));
                }
                else
                {
                    Toast.makeText(DriverProfile.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e)
            {
                Log.e("MYAPP", "unexpected JSON exception", e);
            }
        }
    }
}