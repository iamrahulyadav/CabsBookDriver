package com.example.archirayan.cabsbookdriver.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.ImageFilePath;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.adapter.MakeModelIdAdepter;
import com.example.archirayan.cabsbookdriver.adapter.ModelNameAdepter;
import com.example.archirayan.cabsbookdriver.adapter.SpinnerAdapter;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverLoginResponse;
import com.example.archirayan.cabsbookdriver.model.EditProfileResponse;
import com.example.archirayan.cabsbookdriver.model.GetMakeModelIdResponse;
import com.example.archirayan.cabsbookdriver.model.GetModelNameResponse;
import com.example.archirayan.cabsbookdriver.model.GetVehicleInformationResponse;
import com.example.archirayan.cabsbookdriver.model.GetVehicleType;
import com.example.archirayan.cabsbookdriver.model.GetVehicleTypeResponse;
import com.example.archirayan.cabsbookdriver.model.MakeModelId;
import com.example.archirayan.cabsbookdriver.model.ModelName;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class VehicleInformation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "VehicleInformation";
    private ImageView img_back_vehicle_info;
    private Spinner spinner_vehicle_type,spinner_selcete_make,spinner_selecete_model;
    private ArrayList<GetVehicleType> getVehicleTypes;
    private ArrayList<MakeModelId> makeModelIds;
    private ArrayList<ModelName> modelNames;
    private ModelNameAdepter modelNameAdepter;
    private MakeModelIdAdepter makeModelIdAdepter;
    private SpinnerAdapter spinnerAdapter;
    private String setVehicaleType,setVehicaleType_Id,setMakeModel_Id,setMakeModelName,setModel_Id,setModelName,imagePath_1;
    private TextView txt_selected_vecle_type,txt_vecle_name,txt_registered_num,txt_edit_vehicle_color,txt_no_info;
    private CircleImageView img_carPorifile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, FILTER_IMAGE = 400;
    private LinearLayout linear_info;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information);

        img_back_vehicle_info = (ImageView) findViewById(R.id.img_back_vehicle_info);
        txt_selected_vecle_type = (TextView) findViewById(R.id.txt_selected_vecle_type);
        txt_vecle_name = (TextView) findViewById(R.id.txt_vecle_name);
        spinner_selcete_make = (Spinner) findViewById(R.id.spinner_selcete_make);
        spinner_selecete_model = (Spinner) findViewById(R.id.spinner_selecete_model);
        txt_registered_num = (TextView) findViewById(R.id.txt_registered_num);
        txt_edit_vehicle_color = (TextView) findViewById(R.id.txt_edit_vehicle_color);
        txt_no_info = (TextView) findViewById(R.id.txt_no_info);

        linear_info = (LinearLayout) findViewById(R.id.linear_info);

        img_carPorifile = (CircleImageView) findViewById(R.id.img_carPorifile);

        img_carPorifile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Utility.checkPermission(VehicleInformation.this);
                if (result) {
                    galleryIntent();

                }
            }
        });


        img_back_vehicle_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VehicleInformation.this,DriverMainPage.class));
            }
        });

        spinner_vehicle_type = (Spinner) findViewById(R.id.spinner_vehicle_type);

        spinner_vehicle_type.setOnItemSelectedListener(this);
        spinner_selcete_make.setOnItemSelectedListener(this);
        spinner_selecete_model.setOnItemSelectedListener(this);

        getVehicleType();
        getMakeId();
        getMakeModelName();
        getVehicleInfo();
        //editVehicleInfo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
                if (data != null) {
                    Uri imageUri = data.getData();
                    imagePath_1 = ImageFilePath.getPath(VehicleInformation.this, data.getData());
                    getDriverVehicalePic();

                }
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        img_carPorifile.setImageBitmap(bm);

    }

    private void getDriverVehicalePic() {
        AsyncHttpClient client = new AsyncHttpClient();
        File file = new File(imagePath_1);
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(VehicleInformation.this, Constant.DRIVERID));
        params.put("driver_texi_id", Utils.ReadSharePrefrence(VehicleInformation.this,Constant.Driver_Trip_Id));
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "PicURL:" + Constant.BASE_URL + "upload_driver_image.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL + "upload_driver_image.php?", params, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Pic~" + response);
                GetVehicleInformationResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetVehicleInformationResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")){
                    if (dmodel.getData().getImage().isEmpty()) {
                        Picasso.with(VehicleInformation.this).load(R.drawable.carprofile);
                    } else {
                        Picasso.with(VehicleInformation.this).load(dmodel.getData().getImage()).placeholder(R.drawable.carprofile).into(img_carPorifile);

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


    private void getVehicleInfo() {
        pd = new ProgressDialog(VehicleInformation.this);
        pd.setCancelable(false);
        pd.setMessage("Pleas Wait...");
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(VehicleInformation.this, Constant.DRIVERID));

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
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "Driver RESPONSE-" + response);
                GetVehicleInformationResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetVehicleInformationResponse.class);
                pd.dismiss();
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_vecle_name.setText((dmodel.getData().getMake())+" "+(dmodel.getData().getModel()));
                    txt_selected_vecle_type.setText(dmodel.getData().getVehicle_type());
                    txt_registered_num.setText(dmodel.getData().getLicense_plate());
                    Utils.WriteSharePrefrence(VehicleInformation.this,Constant.PlateNumber,dmodel.getData().getLicense_plate());
                    String platenumber = Utils.ReadSharePrefrence(VehicleInformation.this,Constant.PlateNumber);
                    txt_edit_vehicle_color.setText(dmodel.getData().getVehicle_color());
                    Utils.WriteSharePrefrence(VehicleInformation.this,Constant.Driver_Trip_Id,dmodel.getData().getId());
                    String driver_Tid = Utils.ReadSharePrefrence(VehicleInformation.this,Constant.Driver_Trip_Id);
                    if (dmodel.getData().getImage().isEmpty()) {
                        Picasso.with(VehicleInformation.this).load(R.drawable.carprofile);
                    } else {
                        Picasso.with(VehicleInformation.this).load(dmodel.getData().getImage()).placeholder(R.drawable.carprofile).into(img_carPorifile);

                    }


                }else {
                    linear_info.setVisibility(View.GONE);
                    txt_no_info.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void getMakeModelName() {
        modelNames = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("make_id",setMakeModel_Id);

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_make_model.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL + "get_make_model.php?",params,  new JsonHttpResponseHandler() {
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
                GetModelNameResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetModelNameResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")){
                    modelNames = dmodel.getData();
                    modelNameAdepter = new ModelNameAdepter(VehicleInformation.this,modelNames);
                    spinner_selecete_model.setAdapter(modelNameAdepter);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }



    private void getMakeId() {
        makeModelIds = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_make_model.php");
        Log.e(TAG, params.toString());
        client.get(this, Constant.BASE_URL + "get_make_model.php",  new JsonHttpResponseHandler() {
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
                GetMakeModelIdResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetMakeModelIdResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    makeModelIds = dmodel.getData();
                    makeModelIdAdepter = new MakeModelIdAdepter(VehicleInformation.this,makeModelIds);
                    spinner_selcete_make.setAdapter(makeModelIdAdepter);


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });

    }


    private void getVehicleType() {
        getVehicleTypes = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_vehicle_type.php");
        Log.e(TAG, params.toString());
        client.get(this, Constant.BASE_URL + "get_vehicle_type.php",  new JsonHttpResponseHandler() {
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
                GetVehicleTypeResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetVehicleTypeResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    getVehicleTypes = dmodel.getData();
                    spinnerAdapter = new SpinnerAdapter(VehicleInformation.this,getVehicleTypes);
                    spinner_vehicle_type.setAdapter(spinnerAdapter);

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.spinner_vehicle_type:
                TextView textView = (TextView) view.findViewById(R.id.textView);
                textView.setText(getVehicleTypes.get(position).getType());
                setVehicaleType_Id = (getVehicleTypes.get(position).getId()).toString();
                setVehicaleType = (getVehicleTypes.get(position).getType()).toString();
                Toast.makeText(this, getVehicleTypes.get(position).getId(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner_selcete_make:
                TextView textView1 = (TextView) view.findViewById(R.id.textView);
                textView1.setText(makeModelIds.get(position).getName());
                setMakeModel_Id = (makeModelIds.get(position).getId()).toString();
                setMakeModelName = (makeModelIds.get(position).getId()).toString();
                Toast.makeText(this, makeModelIds.get(position).getId(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner_selecete_model:
                TextView textView2 = (TextView) view.findViewById(R.id.textView);
                textView2.setText(modelNames.get(position).getName());
                setModel_Id = (modelNames.get(position).getId()).toString();
                setModelName = (modelNames.get(position).getId()).toString();
                Toast.makeText(this, modelNames.get(position).getId(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
