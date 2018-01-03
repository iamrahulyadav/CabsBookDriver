package com.example.archirayan.cabsbookdriver.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.activity.driverhelp.DriverAccountHelpActivity;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverLoginResponse;
import com.example.archirayan.cabsbookdriver.model.EarningsBalanceResponse;
import com.example.archirayan.cabsbookdriver.model.GetVehicleInformationResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class DriverMainPage extends AppCompatActivity
{
    private static final String TAG = "DriverMainPage";
    public LinearLayout linear_setting, linear_about, linear_doc, linear_help;
    private LinearLayout line2, linear_home, linear_earnings, linear_rating, linear_account, line1, linear_activity_account, linear_profilcontent, linear_activity_home, linear_activity_earning, linear_activity_rating, linear_earningweek, linear_starrating, linear_acceptancerate, linear_cancelling, linear_compliments, linear_invites, linear_find_trip, linear_vihical, linear_driverprofile, linear_waybill;
    private ImageView img_home, img_earnings, img_rating, img_account, img_profile;
    private TextView txt_earnings, txt_home, txt_rating, txt_account, txt_signout, txt_person_name, txt_money, txt_trip_num, txt_balance_num;
    private Button btn_goonline, btn_online, btn_notify;
    private CircleImageView img_vihical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main_page);

        linear_about = (LinearLayout) findViewById(R.id.linear_about);

        linear_doc = (LinearLayout) findViewById(R.id.linear_doc);
        linear_help = findViewById(R.id.linear_help);
        linear_account = (LinearLayout) findViewById(R.id.linear_account);
        linear_activity_account = (LinearLayout) findViewById(R.id.linear_activity_account);
        linear_profilcontent = (LinearLayout) findViewById(R.id.linear_profilcontent);
        linear_activity_home = (LinearLayout) findViewById(R.id.linear_activity_home);
        linear_home = (LinearLayout) findViewById(R.id.linear_home);
        linear_earnings = (LinearLayout) findViewById(R.id.linear_earnings);
        linear_activity_earning = (LinearLayout) findViewById(R.id.linear_activity_earning);
        linear_activity_rating = (LinearLayout) findViewById(R.id.linear_activity_rating);
        linear_rating = (LinearLayout) findViewById(R.id.linear_rating);
        line2 = (LinearLayout) findViewById(R.id.line2);
        line1 = (LinearLayout) findViewById(R.id.line1);
        linear_earningweek = (LinearLayout) findViewById(R.id.linear_earningweek);
        linear_starrating = (LinearLayout) findViewById(R.id.linear_starrating);
        linear_acceptancerate = (LinearLayout) findViewById(R.id.linear_acceptancerate);
        linear_cancelling = (LinearLayout) findViewById(R.id.linear_cancelling);
        linear_compliments = (LinearLayout) findViewById(R.id.linear_compliments);
        linear_invites = (LinearLayout) findViewById(R.id.linear_invites);
        linear_find_trip = (LinearLayout) findViewById(R.id.linear_find_trip);
        linear_vihical = (LinearLayout) findViewById(R.id.linear_vihical);
        linear_driverprofile = (LinearLayout) findViewById(R.id.linear_driverprofile);
        linear_waybill = (LinearLayout) findViewById(R.id.linear_waybill);
        linear_setting = (LinearLayout) findViewById(R.id.linear_setting);

        img_vihical = (CircleImageView) findViewById(R.id.img_vihical);

        linear_waybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, WaybillActivity.class));
            }
        });

        linear_driverprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, DriverProfile.class));
            }
        });

        linear_vihical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, VehicleInformation.class));
            }
        });

        linear_find_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, CurrentTrips.class));
            }
        });

        linear_invites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, InvitesActivity.class));
            }
        });

        linear_compliments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, RiderCompliments.class));
            }
        });

        linear_cancelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, CancellationDetailsactivity.class));
            }
        });

        linear_acceptancerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, AcceptanceDetails.class));
            }
        });

        linear_starrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, RatinsDetailsactivity.class));
            }
        });

        linear_earningweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverMainPage.this, WeeklySummary.class));
            }
        });


        // // TODO: 19/12/17  Click Setting menu...Open Driver Profile...

        linear_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverMainPage.this, DriverprofilesettingActivity.class));
            }
        });


        linear_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverMainPage.this, DriverAboutActivity.class));
            }
        });


        linear_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverMainPage.this, DriverDocumentsActivity.class));
            }
        });


        txt_signout = (TextView) findViewById(R.id.txt_signout);
        txt_person_name = (TextView) findViewById(R.id.txt_person_name);
        txt_money = (TextView) findViewById(R.id.txt_money);
        txt_trip_num = (TextView) findViewById(R.id.txt_trip_num);
        txt_balance_num = (TextView) findViewById(R.id.txt_balance_num);
        img_profile = (ImageView) findViewById(R.id.img_profile);

        linear_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_activity_account.setVisibility(View.VISIBLE);
                linear_activity_home.setVisibility(View.GONE);
                linear_activity_earning.setVisibility(View.GONE);
                linear_activity_rating.setVisibility(View.GONE);

            }
        });
        linear_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_activity_home.setVisibility(View.VISIBLE);
                linear_activity_account.setVisibility(View.GONE);
                linear_activity_earning.setVisibility(View.GONE);
                linear_activity_rating.setVisibility(View.GONE);

            }
        });
        linear_earnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_activity_earning.setVisibility(View.VISIBLE);
                linear_activity_home.setVisibility(View.GONE);
                linear_activity_account.setVisibility(View.GONE);
                linear_activity_rating.setVisibility(View.GONE);

            }
        });
        linear_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_activity_rating.setVisibility(View.VISIBLE);
                linear_activity_earning.setVisibility(View.GONE);
                linear_activity_account.setVisibility(View.GONE);
                linear_activity_home.setVisibility(View.GONE);

            }
        });

        linear_profilcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverMainPage.this, EditAccount.class));
            }
        });
        txt_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DriverMainPage.this);
                alertDialogBuilder.setMessage("Are you sure you want to sign out ? ");

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.setPositiveButton("Sign out",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Utils.WriteSharePrefrence(DriverMainPage.this, Constant.DRIVERID, "");
                                startActivity(new Intent(DriverMainPage.this, MainActivity.class));
                                finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        btn_goonline = (Button) findViewById(R.id.btn_goonline);
        btn_goonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DriverMainPage.this);
                builder.setMessage("You'll be notified via text and email once you're approved to drive.");
                builder.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActiveStatus();
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
        btn_online = (Button) findViewById(R.id.btn_online);
        btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DriverMainPage.this);
                builder.setMessage("You'll be notified via text and email once you're approved to offline.");
                builder.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getInActiveStatus();
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });


        linear_help.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(DriverMainPage.this, DriverAccountHelpActivity.class));
            }
        });


        getUserDetails();
        getEnerings();
        getVehicalDetails();


    }

    private void getVehicalDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(DriverMainPage.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_driver_texi.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "get_driver_texi.php?", googleparams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "Driver RESPONSE-" + response);
                GetVehicleInformationResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetVehicleInformationResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    if (dmodel.getData().getImage().isEmpty()) {
                        Picasso.with(DriverMainPage.this).load(R.drawable.carprofile);
                    } else {
                        Picasso.with(DriverMainPage.this).load(dmodel.getData().getImage()).placeholder(R.drawable.carprofile).into(img_vihical);

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }


    private void getInActiveStatus()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(DriverMainPage.this, Constant.DRIVERID));
        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "online_offline.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "online_offline.php?", googleparams, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Driver RESPONSE-" + response);
                OnlineOfflineResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), OnlineOfflineResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    btn_goonline.setVisibility(View.VISIBLE);
                    btn_online.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void getActiveStatus() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(DriverMainPage.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "online_offline.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "online_offline.php?", googleparams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "Driver RESPONSE-" + response);
                OnlineOfflineResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), OnlineOfflineResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    btn_goonline.setVisibility(View.GONE);
                    btn_online.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });

    }

    private void getEnerings() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(DriverMainPage.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "driver_weeklly_trip.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "driver_weeklly_trip.php?", googleparams, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Driver RESPONSE-" + response);
                EarningsBalanceResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), EarningsBalanceResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_money.setText(dmodel.getData().getWeek_fare());
                    txt_trip_num.setText(dmodel.getData().getTotal_trip_count());
                    txt_balance_num.setText(dmodel.getData().getTotal_balance());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void getUserDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(DriverMainPage.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_driver_detail.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "get_driver_detail.php?", googleparams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "Driver RESPONSE-" + response);
                DriverLoginResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), DriverLoginResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_person_name.setText(dmodel.getData().getFirst_name());
                    if (dmodel.getData().getImage().isEmpty()) {
                        Picasso.with(DriverMainPage.this).load(R.drawable.ic_profile);
                    } else {
                        Picasso.with(DriverMainPage.this).load(dmodel.getData().getImage()).placeholder(R.drawable.ic_profile).into(img_profile);

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
