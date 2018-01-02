package com.example.archirayan.cabsbookdriver.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static com.example.archirayan.cabsbookdriver.activity.DriverDocumentsActivity.str_Doc_IdMAin;


public class UberdocumentdriverActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btn_driverlicenesubmit;
    public LinearLayout llayout_softwerlicense;
    public ImageView img_back_ratings_tital;
    public TextView startDateTv;
    public ProgressDialog pd;
    private DatePickerDialog fromDatePickerDialog;
    private Calendar cal;
    private SimpleDateFormat dateFormatter;
    private String elapsedDays;
    private String str_startdate;
    private String str_DriverId;
    private String str_ImagePath;
    private String str_RealPath;
    private File file;
    private String str_ResulFInal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uberdocumentdriver);
        btn_driverlicenesubmit = findViewById(R.id.btn_driverlicenesubmit);
        llayout_softwerlicense = findViewById(R.id.llayout_softwerlicense);
        img_back_ratings_tital = findViewById(R.id.img_back_ratings_tital);
        startDateTv = findViewById(R.id.startDateTv);
        llayout_softwerlicense.setOnClickListener(this);
        btn_driverlicenesubmit.setOnClickListener(this);
        img_back_ratings_tital.setOnClickListener(this);
        cal = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
        setDateTimeField();
        str_DriverId = Utils.ReadSharePrefrence(UberdocumentdriverActivity.this, Constant.DRIVERID);
        Log.d("str_DriverId", "====== DriverId ======" + str_DriverId);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            str_ImagePath = (String) bd.getString("img1");
            Log.d("str_ImagePath()", "======= ImagePath ========" + str_ImagePath);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_driverlicenesubmit:
                if (Utils.isConnectingToInternet(UberdocumentdriverActivity.this)) {
                    str_startdate = startDateTv.getText().toString().trim();
                    Log.d("date()", "==== Date ====" + str_startdate);
                    adddriverDocumentData();
                } else {
                    Toast.makeText(this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.llayout_softwerlicense:
                fromDatePickerDialog.show();
                break;
            case R.id.img_back_ratings_tital:
                onBackPressed();
                break;
        }
    }

    private void adddriverDocumentData() {
       /* pd = new ProgressDialog(UberdocumentdriverActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Ion.with(UberdocumentdriverActivity.this)
                // .load(Constant.BASE_URL + "driver_upload_image.php?")
                .load(Constant.BASE_URL + "driver_upload_image.php?")
                .setMultipartParameter("driver_id", String.valueOf(str_DriverId))
                .setMultipartParameter("id", str_Doc_IdMAin)
                .setMultipartParameter("date", str_startdate)
                .setMultipartFile("file", new File(str_ResulFInal))
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        Log.e("Response()", "===== Result =======" + result);
                        pd.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getString("status").equalsIgnoreCase("true"))
                            {
                                Toast.makeText(UberdocumentdriverActivity.this, "File Upload Successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(UberdocumentdriverActivity.this, "File Not Uploaded.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });*/

        pd = new ProgressDialog(UberdocumentdriverActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", str_DriverId);
        params.put("id", str_Doc_IdMAin);
        params.put("date", str_startdate);
        params.put("file", str_ImagePath);

        //  Log.e(TAG, "USERURL:" + Constant.BASE_URL + "driver_upload_image.php?" + params);
        client.post(this, Constant.BASE_URL + "driver_upload_image.php?", params, new JsonHttpResponseHandler() {
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
                pd.dismiss();
                Log.e("Response()", "===== Result =======" + response);
                pd.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        Toast.makeText(UberdocumentdriverActivity.this, "File Upload Successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UberdocumentdriverActivity.this, "File Not Uploaded.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDateTv.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}

/*

                ForgotpasswordOTPResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)),ForgotpasswordOTPResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")){
                    Utils.WriteSharePrefrence(DriverSignIn.this,Constant.FORGOT_OTP,dmodel.getVerified_code());

    }



   }
*/
