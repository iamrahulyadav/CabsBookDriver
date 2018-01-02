package com.example.archirayan.cabsbookdriver.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

public class SetDestinationDriver extends AppCompatActivity {

    private static final String TAG = "SetDestinationDriver";
    private PlacesAutocompleteTextView place_detination_of_driver;
    private String str;
    private ImageView img_back_setdriverdetination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination_driver);

        place_detination_of_driver = (PlacesAutocompleteTextView) findViewById(R.id.place_detination_of_driver);


        place_detination_of_driver.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                place_detination_of_driver.getDetailsFor(place, new DetailsCallback() {
                    @Override
                    public void onSuccess(PlaceDetails placeDetails) {
                        Log.d("test", "details " + placeDetails);
                       /* AlertDialog.Builder builder = new AlertDialog.Builder(SetDestinationDriver.this);
                        builder.setTitle("Go Online");
                        builder.setMessage("Go Online with your destination set?");
                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                        builder.setPositiveButton("GO ONLINE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(SetDestinationDriver.this,VehicleInformation.class));

                            }
                        });
                        builder.show();*/
                        str = place_detination_of_driver.getText().toString();
                        Log.e(TAG,"Address"+str);

                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d("test", "failure " + throwable);
                    }
                });
            }
        });

        img_back_setdriverdetination = (ImageView) findViewById(R.id.img_back_setdriverdetination);

        img_back_setdriverdetination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
