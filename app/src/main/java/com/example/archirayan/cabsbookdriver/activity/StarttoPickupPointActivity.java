package com.example.archirayan.cabsbookdriver.activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.SeekBar;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.archirayan.cabsbookdriver.R;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class StarttoPickupPointActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,RoutingListener {

    private static final String TAG = "StarttoPickupPointActivity";
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
    private String str_Address,str_Address_two;
    private List<LatLng> polyLineList;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyLine;
    private Handler handler;
    private Marker marker;
    private int index, next;
    private float v;
    private double lat, lng;
    private static final int[] COLORS = new int[]{com.example.archirayan.cabsbookdriver.R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startto_pickup_point);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        str_Address = Utils.ReadSharePrefrence(StarttoPickupPointActivity.this,Constant.SOURCEADDTESS);
        str_Address_two = Utils.ReadSharePrefrence(StarttoPickupPointActivity.this,Constant.DETINATIONADDTESS);

        img_start_car = (CircleImageView) findViewById(R.id.img_start_car);
        img_pickup_car = (CircleImageView) findViewById(R.id.img_pickup_car);

        img_pickup_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StarttoPickupPointActivity.this,PickuoPointToDropoffPoint.class));
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
        //getDirections();
    }

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
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");
        Currentlocation = location;
        latitud = location.getLatitude();
        longitud = location.getLongitude();
        latLng = new LatLng(latitud,longitud);
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        mMap.addMarker(options);

        latitude1 = Double.parseDouble(Utils.ReadSharePrefrence(StarttoPickupPointActivity.this, Constant.USER_LATITUDE));;
        longitude1 = Double.parseDouble(Utils.ReadSharePrefrence(StarttoPickupPointActivity.this, Constant.USER_LONGITUTED));;

        latLng1 = new LatLng(latitude1,longitude1);
        route();



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
        options.icon(BitmapDescriptorFactory.defaultMarker()).title(str_Address);
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(latLng1);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).draggable(true);
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
                .waypoints(latLng,latLng1)
                .build();
        routing.execute();


    }


   /* private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }*/





    /*@SuppressLint("LongLogTag")
    private void getDirections() {
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Log.e(TAG, params.toString());
        Log.e(TAG, "URL:" + "https://maps.googleapis.com/maps/api/directions/json?mode=driving&transit_routing_preference=less_driving&origin=Sheetal Varsha Complex&destination=Kalupur, Ahmedabad, Gujarat&key=AIzaSyCA9DKNgPQ7AGKZTNH1tt7m4qStFkzj8UM");
        client.get(StarttoPickupPointActivity.this, "https://maps.googleapis.com/maps/api/directions/json?mode=driving&transit_routing_preference=less_driving&origin=Sheetal Varsha Complex&destination=Kalupur, Ahmedabad, Gujarat&key=AIzaSyCA9DKNgPQ7AGKZTNH1tt7m4qStFkzj8UM", new JsonHttpResponseHandler() {
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
                latLng = new LatLng(23.0252372,72.5273988);
                latLng1 = new LatLng(23.0288156,72.5907148);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray jsonArray = jsonObject.getJSONArray("routes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject route = jsonArray.getJSONObject(i);
                        JSONObject poly = route.getJSONObject("overview_polyline");
                        String polyline = poly.getString("points");
                        polyLineList = decodePoly(polyline);
                        Log.d(TAG, polyLineList + "");
                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng latLng : polyLineList) {
                        builder.include(latLng);
                    }
                    LatLngBounds bounds = builder.build();
                    CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                    mMap.animateCamera(mCameraUpdate);

                    polylineOptions = new PolylineOptions();
                    polylineOptions.color(Color.GRAY);
                    polylineOptions.width(10);
                    polylineOptions.startCap(new SquareCap());
                    polylineOptions.endCap(new SquareCap());
                    polylineOptions.jointType(ROUND);
                    polylineOptions.addAll(polyLineList);
                    greyPolyLine = mMap.addPolyline(polylineOptions);

                    blackPolylineOptions = new PolylineOptions();
                    blackPolylineOptions.width(10);
                    blackPolylineOptions.color(Color.BLACK);
                    blackPolylineOptions.startCap(new SquareCap());
                    blackPolylineOptions.endCap(new SquareCap());
                    blackPolylineOptions.jointType(ROUND);
                    blackPolyline = mMap.addPolyline(blackPolylineOptions);

                    mMap.addMarker(new MarkerOptions()
                            .position(polyLineList.get(polyLineList.size() - 1)));

                    ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
                    polylineAnimator.setDuration(2000);
                    polylineAnimator.setInterpolator(new LinearInterpolator());
                    polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            List<LatLng> points = greyPolyLine.getPoints();
                            int percentValue = (int) valueAnimator.getAnimatedValue();
                            int size = points.size();
                            int newPoints = (int) (size * (percentValue / 100.0f));
                            List<LatLng> p = points.subList(0, newPoints);
                            blackPolyline.setPoints(p);
                        }
                    });
                    polylineAnimator.start();
                    marker = mMap.addMarker(new MarkerOptions().position(latLng)
                            .flat(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));
                    handler = new Handler();
                    index = -1;
                    next = 1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (index < polyLineList.size() - 1) {
                                index++;
                                next = index + 1;
                            }
                            if (index < polyLineList.size() - 1) {
                                latLng = polyLineList.get(index);
                                latLng1 = polyLineList.get(next);
                            }
                            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                            valueAnimator.setDuration(3000);
                            valueAnimator.setInterpolator(new LinearInterpolator());
                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    v = valueAnimator.getAnimatedFraction();
                                    lat = v * latLng1.longitude + (1 - v)
                                            * latLng.latitude;
                                    lng = v * latLng1.longitude + (1 - v)
                                            * latLng.longitude;
                                    LatLng newPos = new LatLng(lat, lng);
                                    marker.setPosition(newPos);
                                    marker.setAnchor(0.5f, 0.5f);
                                    marker.setRotation(getBearing(latLng, newPos));
                                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(newPos).zoom(5).build()));
                                }
                            });
                            valueAnimator.start();
                            handler.postDelayed(this, 3000);
                        }
                    }, 3000);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());


            }
        });
    }*/












}
