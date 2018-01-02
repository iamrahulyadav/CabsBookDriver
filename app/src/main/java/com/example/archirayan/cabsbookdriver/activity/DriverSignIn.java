package com.example.archirayan.cabsbookdriver.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverLoginResponse;
import com.example.archirayan.cabsbookdriver.model.ForgotpasswordOTPResponse;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DriverSignIn extends AppCompatActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "DriverSignIn";
    private TextView txt_title_Dsignin;
    private LinearLayout linear_title_Dsignin;
    private EditText edit_Demail,edit_Dpass;
    private Button btn_Driver_Dsignin,btn_Driver_Dforgot_pass;
    private ImageView img_back_title_Dsignin;
    private ProgressDialog pd;
    private Location mLastLocation;
    private double latitude;
    private double longitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Dialog dialog;
    private String email_str,otp_str,pass_str;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_in);
        txt_title_Dsignin = (TextView) findViewById(R.id.txt_title_Dsignin);
        linear_title_Dsignin = (LinearLayout) findViewById(R.id.linear_title_Dsignin);
        edit_Demail = (EditText) findViewById(R.id.edit_Demail);
        edit_Dpass = (EditText) findViewById(R.id.edit_Dpass);

        btn_Driver_Dsignin = (Button) findViewById(R.id.btn_Driver_Dsignin);
        btn_Driver_Dforgot_pass = (Button) findViewById(R.id.btn_Driver_Dforgot_pass);
        btn_Driver_Dforgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(DriverSignIn.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forgot_password_email_dialog);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                final EditText editText = (EditText) dialog.findViewById(R.id.edit_forgot_email);
                Button button = (Button) dialog.findViewById(R.id.btn_next_forgot_pass);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        email_str = editText.getText().toString();
                        if (email_str.isEmpty()){
                            editText.setError("Enter Your number");
                        }else {
                            getOTP();
                        }
                    }
                });
                dialog.show();
            }
        });

        img_back_title_Dsignin = (ImageView) findViewById(R.id.img_back_title_Dsignin);


        img_back_title_Dsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverSignIn.this,MainActivity.class));
            }
        });

        btn_Driver_Dsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_Demail.getText().toString().isEmpty()){
                    edit_Demail.setError("Enter valid email id");
                    edit_Demail.requestFocus();
                }else if (edit_Dpass.getText().toString().isEmpty()) {
                    edit_Dpass.setError("Enter Password");
                    edit_Dpass.requestFocus();
                }else {
                    driverSignUP();
                }

            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }

    }

    private void getOTP()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(DriverSignIn.this, Constant.DRIVERID));
        params.put("email",email_str);


        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "driver_forgot_password.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL+"driver_forgot_password.php?",params, new JsonHttpResponseHandler() {
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
                ForgotpasswordOTPResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)),ForgotpasswordOTPResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")){
                    Utils.WriteSharePrefrence(DriverSignIn.this,Constant.FORGOT_OTP,dmodel.getVerified_code());
                    String forgot_otp = Utils.ReadSharePrefrence(DriverSignIn.this,Constant.FORGOT_OTP);
                    Log.e(TAG,"otpresponse"+forgot_otp);
                    dialog.dismiss();
                    setOtp();



                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void setOtp() {
        dialog = new Dialog(DriverSignIn.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.forgot_pass_otp_code);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText editText = (EditText) dialog.findViewById(R.id.edit_otp);
        Button button = (Button) dialog.findViewById(R.id.btn_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_str = editText.getText().toString();
                if (otp_str.isEmpty()){
                    editText.setError("Enter Otp Code");
                }else if (!otp_str.equals(Utils.ReadSharePrefrence(DriverSignIn.this,Constant.FORGOT_OTP))){
                    editText.setError("Your Otp code is incorrect");
                }else {
                    dialog.dismiss();
                    ResetPassword();
                }
            }
        });
        dialog.show();
    }

    private void ResetPassword() {
        dialog = new Dialog(DriverSignIn.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_password_dialog);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText editText = (EditText) dialog.findViewById(R.id.edit_enter_new_pass);
        Button button = (Button) dialog.findViewById(R.id.btn_save_pass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_str = editText.getText().toString();
                if (pass_str.isEmpty()){
                    editText.setError("Enter your password");
                }else {
                    getChangePassword();

                }
            }
        });
        dialog.show();

    }

    private void getChangePassword()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(DriverSignIn.this, Constant.DRIVERID));
        params.put("email",email_str);
        params.put("password",pass_str);


        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "driver_forgot_password.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL+"driver_forgot_password.php?",params, new JsonHttpResponseHandler() {
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
                dialog.dismiss();
                /*ForgotpasswordOTPResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)),ForgotpasswordOTPResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")){
                    Utils.WriteSharePrefrence(DriverSignIn.this,Constant.FORGOT_OTP,dmodel.getVerified_code());
                    String forgot_otp = Utils.ReadSharePrefrence(DriverSignIn.this,Constant.FORGOT_OTP);
                    Log.e(TAG,"otpresponse"+forgot_otp);
                    dialog.dismiss();
                    setOtp();*/




            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    public void driverSignUP()
    {
        pd = new ProgressDialog(DriverSignIn.this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("email_or_mobile_no",edit_Demail.getText().toString());
        googleparams.put("password",edit_Dpass.getText().toString());
        googleparams.put("device_token_android", FirebaseInstanceId.getInstance().getToken());
        googleparams.put("latitude",Utils.ReadSharePrefrence(DriverSignIn.this,Constant.LOGIN_LATITUDE));
        googleparams.put("longitude",Utils.ReadSharePrefrence(DriverSignIn.this,Constant.LOGIN_LONGITUDE));

        Log.e(TAG, "DriverURL:" + Constant.BASE_URL + "driver_login.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL+"driver_login.php?",googleparams, new JsonHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
            }
            @Override
            public void onFinish()
            {
                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "LOGIN DriverRESPONSE-" + response);
                DriverLoginResponse model = new Gson().fromJson(new String(String.valueOf(response)),DriverLoginResponse.class);
                pd.dismiss();
                if (model.getStatus().equalsIgnoreCase("true"))
                {
                    Utils.WriteSharePrefrence(DriverSignIn.this, Constant.DRIVERID,model.getData().getDriver_id());
                    Utils.WriteSharePrefrence(DriverSignIn.this, Constant.USERNAME_DRIVER,model.getData().getFirst_name());
                    Utils.WriteSharePrefrence(DriverSignIn.this, Constant.IMAGE_DRIVER,model.getData().getImage());
                    String Userid = Utils.ReadSharePrefrence(DriverSignIn.this,Constant.DRIVERID);
                    Log.e(TAG,"READ SHARED==>"+Userid);
                    startActivity(new Intent(DriverSignIn.this, Documents.class));
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("onLocationChanged", "entered");
        mLastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f",latitude,latitude));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");

        Utils.WriteSharePrefrence(DriverSignIn.this,Constant.LOGIN_LATITUDE, String.valueOf(latitude));
        String latitude = Utils.ReadSharePrefrence(DriverSignIn.this,Constant.LOGIN_LATITUDE);
        Log.e(TAG,"READ SHARED==>"+latitude);

        Utils.WriteSharePrefrence(DriverSignIn.this,Constant.LOGIN_LONGITUDE, String.valueOf(longitude));
        String longitude = Utils.ReadSharePrefrence(DriverSignIn.this,Constant.LOGIN_LONGITUDE);
        Log.e(TAG,"READ SHARED==>"+longitude);


    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();

                        }

                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
}
