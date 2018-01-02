package com.example.archirayan.cabsbookdriver.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverDocumentsReaponse;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class Documents extends AppCompatActivity {

    private static final String TAG = "UploadDriverDocuments";
    private ImageView img_driverlicense, img_licensebackside, img_vehicleinsur, img_vehiclepermit, img_vehicleregi, img_driverlicense_click, img_licensebackside_click, img_vehicleinsur_click, img_vehiclepermit_click, img_vehicleregi_click,img_option;
    private TextView txt_driverlicense, txt_licensebackside, txt_vehicleinsur, txt_vehiclepermit, txt_vehicleregi,txt_title_Doc,txt_documents;
    private View view_driver_1, view_driver_2, view_driver_3, view_driver_4, view_driver_5, view_driver_6;
    private Button btn_driver_continue;
    private LinearLayout linear_driverlicense, linear_licensebackside, linear_vehicleinsur, linear_vehiclepermit, linear_vehicleregi;
    private int REQUEST_CODE = 10, SELECT_FILE = 11;
    public static final int PICK_IMAGE_license = 1;
    public static final int PICK_IMAGE_licenseback = 2;
    public static final int PICK_IMAGE_vehicleinsurense = 3;
    public static final int PICK_IMAGE_vehiclepermit = 4;
    public static final int PICK_IMAGE_vehicleregis = 5;
    private String getlicense, getlicenseback, getinsurance, getpermit, getregi;
    String driverId;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        driverId = Utils.ReadSharePrefrence(Documents.this, Constant.DRIVERID);
        Log.e(TAG, "GET DRIVER ID==>" + driverId);

        img_driverlicense = (ImageView) findViewById(R.id.img_driverlicense);
        img_licensebackside = (ImageView) findViewById(R.id.img_licensebackside);
        img_vehicleinsur = (ImageView) findViewById(R.id.img_vehicleinsur);
        img_vehiclepermit = (ImageView) findViewById(R.id.img_vehiclepermit);
        img_vehicleregi = (ImageView) findViewById(R.id.img_vehicleregi);
        img_driverlicense_click = (ImageView) findViewById(R.id.img_driverlicense_click);
        img_licensebackside_click = (ImageView) findViewById(R.id.img_licensebackside_click);
        img_vehicleinsur_click = (ImageView) findViewById(R.id.img_vehicleinsur_click);
        img_vehiclepermit_click = (ImageView) findViewById(R.id.img_vehiclepermit_click);
        img_vehicleregi_click = (ImageView) findViewById(R.id.img_vehicleregi_click);
        /*img_option = (ImageView) findViewById(R.id.img_option);*/

        txt_driverlicense = (TextView) findViewById(R.id.txt_driverlicense);
        txt_licensebackside = (TextView) findViewById(R.id.txt_licensebackside);
        txt_vehicleinsur = (TextView) findViewById(R.id.txt_vehicleinsur);
        txt_vehiclepermit = (TextView) findViewById(R.id.txt_vehiclepermit);
        txt_vehicleregi = (TextView) findViewById(R.id.txt_vehicleregi);
        /*txt_title_Doc = (TextView) findViewById(R.id.txt_title_Doc);*/
        txt_documents = (TextView) findViewById(R.id.txt_documents);

        view_driver_1 = (View) findViewById(R.id.view_driver_1);
        view_driver_2 = (View) findViewById(R.id.view_driver_2);
        view_driver_3 = (View) findViewById(R.id.view_driver_3);
        view_driver_4 = (View) findViewById(R.id.view_driver_4);
        view_driver_5 = (View) findViewById(R.id.view_driver_5);
        view_driver_6 = (View) findViewById(R.id.view_driver_6);

        btn_driver_continue = (Button) findViewById(R.id.btn_driver_continue);

        linear_driverlicense = (LinearLayout) findViewById(R.id.linear_driverlicense);
        linear_licensebackside = (LinearLayout) findViewById(R.id.linear_licensebackside);
        linear_vehicleinsur = (LinearLayout) findViewById(R.id.linear_vehicleinsur);
        linear_vehiclepermit = (LinearLayout) findViewById(R.id.linear_vehiclepermit);
        linear_vehicleregi = (LinearLayout) findViewById(R.id.linear_vehicleregi);


        linear_driverlicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Documents.this, DriverLicense.class);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivityForResult(intent, PICK_IMAGE_license);

            }
        });

        linear_licensebackside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Documents.this, DriverLicenseBackside.class);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivityForResult(intent, PICK_IMAGE_licenseback);

            }
        });

        linear_vehicleinsur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Documents.this, VehicleInsurance.class);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivityForResult(intent, PICK_IMAGE_vehicleinsurense);


            }
        });

        linear_vehiclepermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Documents.this, VehiclePermit.class);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivityForResult(intent, PICK_IMAGE_vehiclepermit);

            }
        });

        linear_vehicleregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Documents.this, VehicleRegistration.class);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivityForResult(intent, PICK_IMAGE_vehicleregis);

            }
        });

        btn_driver_continue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (getlicense == null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Documents.this);
                    builder.setTitle("Missing Documents");
                    builder.setMessage("You have 5 missing documents.");
                    builder.setMessage("Please Upload the required documents.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int item)
                        {
                                dialog.dismiss();
                        }
                    });
                    builder.show();
                } else if (getlicenseback == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Documents.this);
                    builder.setTitle("Missing Documents");
                    builder.setMessage("You have 4 missing documents.");
                    builder.setMessage("Please Upload the required documents.");
                   builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });
                    builder.show();
                } else if (getinsurance == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Documents.this);
                    builder.setTitle("Missing Documents");
                    builder.setMessage("You have 3 missing documents.");
                    builder.setMessage("Please Upload the required documents.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                                dialog.dismiss();

                        }
                    });
                    builder.show();
                } else if (getpermit == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Documents.this);
                    builder.setTitle("Missing Documents");
                    builder.setMessage("You have 2 missing documents.");
                    builder.setMessage("Please Upload the required documents.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            dialog.dismiss();

                        }
                    });
                    builder.show();
                } else if (getregi == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Documents.this);
                    builder.setTitle("Missing Documents");
                    builder.setMessage("You have 1 missing documents.");
                    builder.setMessage("Please Upload the required documents.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item)
                        {
                            dialog.dismiss();

                        }
                    });
                    builder.show();
                }
                else
                {
                    getDocumentsTOCantinue();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_out, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.item1:
                    Utils.WriteSharePrefrence(Documents.this,Constant.DRIVERID,"");
                    startActivity(new Intent(Documents.this, MainActivity.class));
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    public void getDocumentsTOCantinue()
    {
        pd = new ProgressDialog(Documents.this);
        pd.setMessage("loading...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        File file1 = new File(getlicense);
        File file2 = new File(getlicenseback);
        File file3 = new File(getinsurance);
        File file4 = new File(getpermit);
        File file5 = new File(getregi);

        params.put("driver_id", driverId);
        try {
            params.put("driver_license", file1);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try {
            params.put("driver_license_backside", file2);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try {
            params.put("vehicle_insurance", file3);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try {
            params.put("vehicle_permit", file4);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try {
            params.put("vehicle_registration", file5);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        Log.e(TAG, "NAMEURL:" + Constant.BASE_URL + "document_upload.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL + "document_upload.php?", params, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "NAME RESPONSE-" + response);
                DriverDocumentsReaponse model =new Gson().fromJson(new String(String.valueOf(response)),DriverDocumentsReaponse.class);
                pd.dismiss();
                if (model.getStatus().equalsIgnoreCase("true")){
                    startActivity(new Intent(Documents.this,DriverMainPage.class));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_license) {
                getlicense = data.getStringExtra("img1");
                img_driverlicense_click.setVisibility(View.VISIBLE);
                img_driverlicense.setVisibility(View.GONE);
            } else if (requestCode == PICK_IMAGE_licenseback) {
                getlicenseback = data.getStringExtra("img2");
                img_licensebackside_click.setVisibility(View.VISIBLE);
                img_licensebackside.setVisibility(View.GONE);

            } else if (requestCode == PICK_IMAGE_vehicleinsurense) {
                getinsurance = data.getStringExtra("img3");
                img_vehicleinsur_click.setVisibility(View.VISIBLE);
                img_vehicleinsur.setVisibility(View.GONE);


            } else if (requestCode == PICK_IMAGE_vehiclepermit) {
                getpermit = data.getStringExtra("img4");
                img_vehiclepermit_click.setVisibility(View.VISIBLE);
                img_vehiclepermit.setVisibility(View.GONE);


            } else if (requestCode == PICK_IMAGE_vehicleregis)
            {
                getregi = data.getStringExtra("img5");
                img_vehicleregi_click.setVisibility(View.VISIBLE);
                img_vehicleregi.setVisibility(View.GONE);
            }
        }
    }
}