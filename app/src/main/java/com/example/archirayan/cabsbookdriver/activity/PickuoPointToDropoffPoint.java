package com.example.archirayan.cabsbookdriver.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Constant;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.CompleteTripResponse;
import com.example.archirayan.cabsbookdriver.model.FirebaseNotificationResponse;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class PickuoPointToDropoffPoint extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,RoutingListener {

    private static final String TAG = "PickuoPointToDropoffPoint";
    private GoogleMap mMap;
    private CircleImageView img_start_car,img_pickup_car;
    private Location Currentlocation;
    private double latitud;
    private double longitud;
    private double latitude1;
    private double longitude1;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private List<Polyline> polylines;
    private LatLng latLng,latLng1;
    private List<LatLng> polyLineList;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyLine;
    private Handler handler;
    private Marker marker;
    private int index, next;
    private float v;
    private double lat, lng;
    private String str_detination_address;
    private double tolatitude;
    private double tolongitude;
    private LatLng destination;
    private LinearLayout linear_seekvar_route, linear_completed_trip;
    private Button btn_completed_trip;
    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickuo_point_to_dropoff_point);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        str_detination_address = Utils.ReadSharePrefrence(PickuoPointToDropoffPoint.this, com.example.archirayan.cabsbookdriver.model.Constant.USER_DETINATION).toString();

        img_start_car = (CircleImageView) findViewById(R.id.img_start_car);
        img_pickup_car = (CircleImageView) findViewById(R.id.img_pickup_car);

        img_pickup_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PickuoPointToDropoffPoint.this,PickuoPointToDropoffPoint.class));
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

        linear_seekvar_route = (LinearLayout) findViewById(R.id.linear_seekvar_route);
        linear_completed_trip = (LinearLayout) findViewById(R.id.linear_completed_trip);

        btn_completed_trip = (Button) findViewById(R.id.btn_completed_trip);

        btn_completed_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTripCompleted();

            }
        });

        getAddressLocation();
    }

    @SuppressLint("LongLogTag")
    private void getTripCompleted() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("trip_id",Utils.ReadSharePrefrence(PickuoPointToDropoffPoint.this, com.example.archirayan.cabsbookdriver.model.Constant.TRIP_ID));

        Log.e(TAG, "USERURL:" + com.example.archirayan.cabsbookdriver.model.Constant.BASE_URL + "success_trip.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, com.example.archirayan.cabsbookdriver.model.Constant.BASE_URL+"success_trip.php?",googleparams, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Notify RESPONSE-" + response);
                CompleteTripResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)),CompleteTripResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")){
                    startActivity(new Intent(PickuoPointToDropoffPoint.this,DriverMainPage.class));
                }



            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void getAddressLocation() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Log.e(TAG, params.toString());
        client.get(PickuoPointToDropoffPoint.this, "https://maps.googleapis.com/maps/api/geocode/json?address="+str_detination_address+"&key=AIzaSyDP_V1qgIXwLN-mZ_BVIt4BnN4z8aVY198", new JsonHttpResponseHandler() {
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

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker in Home and move the camera
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
        options.position(latLng1);
        options.icon(BitmapDescriptorFactory.defaultMarker());
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(destination);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).draggable(true);
        mMap.addMarker(options);


        CameraUpdate centertwo = CameraUpdateFactory.newLatLng(destination);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12.5f);


        mMap.moveCamera(centertwo);
        mMap.animateCamera(zoom);

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onRoutingCancelled() {
        Log.i(TAG, "Routing was cancelled.");

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
        Currentlocation = location;
        latitud = location.getLatitude();
        longitud = location.getLongitude();
        latLng = new LatLng(latitud,longitud);
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        mMap.addMarker(options);

        latitude1 = Double.parseDouble(Utils.ReadSharePrefrence(this, com.example.archirayan.cabsbookdriver.model.Constant.USER_LATITUDE));
        longitude1 = Double.parseDouble(Utils.ReadSharePrefrence(this, com.example.archirayan.cabsbookdriver.model.Constant.USER_LONGITUTED));

        latLng1 = new LatLng(latitude1,longitude1);




        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f",latitud,longitud));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");

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
                .waypoints(latLng1,destination)
                .build();
        routing.execute();


    }


}
