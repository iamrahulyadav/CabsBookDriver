package com.example.archirayan.cabsbookdriver.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.StarttoPickupPointActivity;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class CurrentTripforshowlocation extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,RoutingListener {

    private static final String TAG = "CurrentTripforshowlocation";
    private GoogleMap mMap;
    private Location mLastLocation;
    private double latitude;
    private double longitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private List<Polyline> polylines;
    private LatLng latLng,latLng1;
    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light};
    private ImageView img_userpic,img_cancle,img_back_trip_close;
    private String getImageStr;
    private TextView txt_username,txt_waybill;
    private LinearLayout linear_user_details;
    private CircleImageView img_start_car,img_pickup_car,img_dropoff_car;
    private String str_Address,str_Address_two;
    private double latitude1;
    private double longitude1;
    private String str_detination_address;
    private double tolatitude;
    private double tolongitude;
    private LatLng destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        str_detination_address = Utils.ReadSharePrefrence(CurrentTripforshowlocation.this, com.example.archirayan.cabsbookdriver.model.Constant.USER_DETINATION).toString();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        linear_user_details = (LinearLayout) findViewById(R.id.linear_user_details);

        img_cancle = (ImageView) findViewById(R.id.img_cancle);
        img_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_user_details.setVisibility(View.GONE);

            }
        });

        img_back_trip_close = (ImageView) findViewById(R.id.img_back_trip_close);

        img_back_trip_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_start_car = (CircleImageView) findViewById(R.id.img_start_car);
        img_pickup_car = (CircleImageView) findViewById(R.id.img_pickup_car);
        img_dropoff_car = (CircleImageView) findViewById(R.id.img_dropoff_car);

        img_start_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurrentTripforshowlocation.this,StarttoPickupPointActivity.class));
            }
        });

        img_userpic = (ImageView) findViewById(R.id.img_userpic);
        getImageStr = Utils.ReadSharePrefrence(CurrentTripforshowlocation.this,Constant.IMAGE).toString();
        if (getImageStr.toString().isEmpty()) {
            Picasso.with(CurrentTripforshowlocation.this).load(R.drawable.ic_profile);
        }
        else
        {
            Picasso.with(CurrentTripforshowlocation.this).load(getImageStr).placeholder(R.drawable.ic_profile).into(img_userpic);

        }

        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_username.setText(Utils.ReadSharePrefrence(CurrentTripforshowlocation.this, Constant.NAME));

        txt_waybill = (TextView) findViewById(R.id.txt_waybill);
        txt_waybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurrentTripforshowlocation.this,WaybillActivity.class));
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
        getAddressLocation();


    }

    @SuppressLint("LongLogTag")
    private void getAddressLocation() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Log.e(TAG, params.toString());
        client.get(CurrentTripforshowlocation.this, "https://maps.googleapis.com/maps/api/geocode/json?address="+str_detination_address+"&key=AIzaSyDP_V1qgIXwLN-mZ_BVIt4BnN4z8aVY198", new JsonHttpResponseHandler() {
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
                Log.e(TAG, "RESPONSE-" + response);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    JSONObject jsonArray1 = jsonObj.getJSONObject("geometry");
                    JSONObject jsonObject1 = jsonArray1.getJSONObject("location");
                    tolatitude = jsonObject1.getDouble("lat");
                    Log.e(TAG,"LATITUDE" +tolatitude);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    JSONObject jsonArray1 = jsonObj.getJSONObject("geometry");
                    JSONObject jsonObject1 = jsonArray1.getJSONObject("location");
                    tolongitude = jsonObject1.getDouble("lng");
                    Log.e(TAG,"LATITUDE" +tolongitude);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                destination = new LatLng(tolatitude,tolongitude);
                Log.e(TAG,"LATLNG_detination" +destination);
                route();





            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");
        mLastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        latLng = new LatLng(latitude,longitude);
        new getAddressStr1().execute();
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        mMap.addMarker(options);

        latitude1 = Double.parseDouble(Utils.ReadSharePrefrence(CurrentTripforshowlocation.this, Constant.USER_LATITUDE));
        longitude1 = Double.parseDouble(Utils.ReadSharePrefrence(CurrentTripforshowlocation.this, Constant.USER_LONGITUTED));;
       // double userlat = Double.parseDouble(Utils.ReadSharePrefrence(CurrentTripforshowlocation.this, Constant.USER_LATITUDE).toString());
        //double userlng = Double.parseDouble(Utils.ReadSharePrefrence(CurrentTripforshowlocation.this, Constant.USER_LONGITUTED).toString());

        latLng1 = new LatLng(latitude1,longitude1);
        new getAddressStr2().execute();
        CameraUpdate centertwo = CameraUpdateFactory.newLatLng(latLng1);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12.5f);




        mMap.moveCamera(centertwo);
        mMap.animateCamera(zoom);


        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f",latitude,latitude));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");



    }

    private class getAddressStr1 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String Response = Utils.getResponseofGet("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true");
            return Response;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("INVALID_REQUEST"))
                {
                    Toast.makeText(CurrentTripforshowlocation.this, jsonObject.getString("error_message"), Toast.LENGTH_SHORT).show();
                } else {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    str_Address = jsonObj.getString("formatted_address");
                    Log.d("str_Address()","====== Address ======"+str_Address);
                    Log.e(TAG, "address" + str_Address);
                    Utils.WriteSharePrefrence(CurrentTripforshowlocation.this,Constant.SOURCEADDTESS,str_Address);
                    String str = Utils.ReadSharePrefrence(CurrentTripforshowlocation.this,Constant.SOURCEADDTESS);
                    //jsonArray.getJSONObject(0).getString("formatted_address");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class getAddressStr2 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String Response = Utils.getResponseofGet("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude1 + "," + longitude1 + "&sensor=true");
            return Response;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("INVALID_REQUEST"))
                {
                    Toast.makeText(CurrentTripforshowlocation.this, jsonObject.getString("error_message"), Toast.LENGTH_SHORT).show();
                } else {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    str_Address_two = jsonObj.getString("formatted_address");
                    Log.d("str_Address()","====== Address ======"+str_Address_two);
                    Log.e(TAG, "address_two" + str_Address_two);
                    Utils.WriteSharePrefrence(CurrentTripforshowlocation.this,Constant.DETINATIONADDTESS,str_Address_two);
                    String str2 = Utils.ReadSharePrefrence(CurrentTripforshowlocation.this,Constant.DETINATIONADDTESS);
                    //jsonArray.getJSONObject(0).getString("formatted_address");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private void route() {
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(latLng,latLng1,destination)
                .build();
        routing.execute();


    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int ii) {
        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(8 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            polyOptions.jointType(ROUND);
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            //Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.icon(BitmapDescriptorFactory.defaultMarker());
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(latLng1);
        options.icon(BitmapDescriptorFactory.defaultMarker());
        mMap.addMarker(options);

        options = new MarkerOptions();
        options.position(destination);
        options.icon(BitmapDescriptorFactory.defaultMarker()).title(str_detination_address);
        mMap.addMarker(options);


        CameraUpdate centertwo = CameraUpdateFactory.newLatLng(latLng1);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12.5f);


        mMap.moveCamera(centertwo);
        mMap.animateCamera(zoom);


    }

    @SuppressLint("LongLogTag")
    @Override
    public void onRoutingCancelled() {
        Log.i(TAG, "Routing was cancelled.");
    }
}
