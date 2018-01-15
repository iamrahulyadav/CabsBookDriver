package com.example.archirayan.cabsbookdriver.activity;

import android.Manifest;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverRegisterMainResponse;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class DriverSignup extends AppCompatActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "DriverSignup";
    private ImageView img_back_title_Dsignup;
    private TextView txt_title_Dsignup,txt_havenotaccount,txt_Login;
    private EditText edit_DEmail,edit_DFirstname,edit_DLastName,edit_Phone,edit_Pass,edit_City;
    private Button btn_Signup;
    private LinearLayout linear_title_Dsignup;
    private String dFirst,dLast,dEmail,dpass,dphone,dcity;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Location mLastLocation;
    private double latitude;
    private double longitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);

        img_back_title_Dsignup = (ImageView) findViewById(R.id.img_back_title_Dsignup);

        img_back_title_Dsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverSignup.this,MainActivity.class));
            }
        });

        txt_title_Dsignup = (TextView) findViewById(R.id.txt_title_Dsignup);
        txt_havenotaccount = (TextView) findViewById(R.id.txt_havenotaccount);
        txt_Login = (TextView) findViewById(R.id.txt_Login);

        edit_DEmail = (EditText) findViewById(R.id.edit_DEmail);
        edit_DFirstname = (EditText) findViewById(R.id.edit_DFirstname);
        edit_DLastName = (EditText) findViewById(R.id.edit_DLastName);
        edit_Phone = (EditText) findViewById(R.id.edit_Phone);
        edit_Pass = (EditText) findViewById(R.id.edit_Pass);
        edit_City = (EditText) findViewById(R.id.edit_City);

        btn_Signup = (Button) findViewById(R.id.btn_Signup);

        linear_title_Dsignup = (LinearLayout) findViewById(R.id.linear_title_Dsignup);

        txt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverSignup.this,DriverSignIn.class));
            }
        });

        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dFirst = edit_DFirstname.getText().toString();
                dLast = edit_DLastName.getText().toString();
                dEmail = edit_DEmail.getText().toString();
                dphone = edit_Phone.getText().toString();
                dpass = edit_Pass.getText().toString();
                dcity = edit_City.getText().toString();

                if (edit_DFirstname.getText().toString().trim().equalsIgnoreCase("")){
                    edit_DFirstname.setError("Please Enter Your First Name");
                }else if (edit_DLastName.getText().toString().trim().equalsIgnoreCase("")){
                    edit_DLastName.setError("Please Enter Your Last Name");
                }else if (edit_Phone.getText().toString().equalsIgnoreCase("")){
                    edit_Phone.setError("Please Enter Your Mobile Number");
                }else if (edit_DEmail.getText().length()==0){
                    edit_DEmail.setError("Please Enter Your Email Address ");
                }/*else if (edit_DEmail.getText().toString().matches(emailPattern)){
                    edit_DEmail.setError("Please Insert Valid Email");
                }*/else if (edit_Pass.getText().length() == 0){
                    edit_Pass.setError("Must Insert Password");
                }else if (validatePassword(edit_Pass.getText().toString())){
                    edit_Pass.setError("Please Enter atleast one number");
                }else if (edit_City.getText().toString().trim().equalsIgnoreCase("")){
                    edit_City.setError("Enter your city");
                }
                else {
                    DriverSignUp();
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

    private boolean validatePassword(String s) {
        //check that there are letters
        if (!s.matches("[a-zA-Z]+")) {
            return false;
        } //nope no letters, stop checking and fail!
        //check if there are any numbers
        if (!s.matches("(?=.*?[0-9])")) {
            return false;
        }//nope no numbers, stop checking and fail!
        //check any valid special characters
        //everything has passed so far, lets return valid
        return true;

    }

    public void DriverSignUp() {
        pd = new ProgressDialog(DriverSignup.this);
        pd.setMessage("Please Wait Send OTP On Your Email Address...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("fname", edit_DFirstname.getText().toString());
        googleparams.put("lname", edit_DLastName.getText().toString());
        googleparams.put("email",edit_DEmail.getText().toString());
        googleparams.put("mobile_no", edit_Phone.getText().toString());
        googleparams.put("password", edit_Pass.getText().toString());
        googleparams.put("city", edit_City.getText().toString());
        googleparams.put("latitude", Utils.ReadSharePrefrence(DriverSignup.this,Constant.LATITUDE));
        googleparams.put("longitude",  Utils.ReadSharePrefrence(DriverSignup.this,Constant.LONGITUDE));

        Log.e(TAG, "URL:" + Constant.BASE_URL + "driver_register.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL+"driver_register.php?",googleparams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "LOGIN RESPONSE-" + response);
                DriverRegisterMainResponse model =new Gson().fromJson(new String(String.valueOf(response)),DriverRegisterMainResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    Utils.WriteSharePrefrence(DriverSignup.this, Constant.OTP,model.getData().getOtp());
                    String otp = Utils.ReadSharePrefrence(DriverSignup.this,Constant.OTP);
                    Log.e(TAG,"READ SHARED==>"+otp);
                    startActivity(new Intent(DriverSignup.this, OTPActivity.class));
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DriverSignup.this,MainActivity.class));
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

        Utils.WriteSharePrefrence(DriverSignup.this,Constant.LATITUDE, String.valueOf(latitude));
        String latitude = Utils.ReadSharePrefrence(DriverSignup.this,Constant.LATITUDE);
        Log.e(TAG,"READ SHARED==>"+latitude);

        Utils.WriteSharePrefrence(DriverSignup.this,Constant.LONGITUDE, String.valueOf(longitude));
        String longitude = Utils.ReadSharePrefrence(DriverSignup.this,Constant.LONGITUDE);
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
}
