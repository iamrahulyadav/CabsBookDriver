package com.example.archirayan.cabsbookdriver.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.ImageFilePath;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.DriverLoginResponse;
import com.example.archirayan.cabsbookdriver.model.EditProfileResponse;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class EditAccount extends AppCompatActivity {
    private static final String TAG = "EditAccount";
    public ImageView img_back_waybill;
    private ImageView img_edit_phone, ing_editprofile, img_edit_email, img_edit_address;
    private TextView txt_first_name, txt_first, txt_last_name, txt_last, txt_phone, txt_edit_phone, txt_email, txt_edit_email, txt_address, txt_edit_address, txt_edit_city, txt_edit_postal, txt_edit_state;
    private LinearLayout linear_phone, linear_email, linear_adress;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, FILTER_IMAGE = 400;
    private String imagePath_1;
    private Dialog dialog;
    private String edit_number, edit_email, edit_str_address, edit_str_city, edit_str_state, edit_str_pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        img_back_waybill = findViewById(R.id.img_back_waybill);
        img_edit_phone = (ImageView) findViewById(R.id.img_edit_phone);
        ing_editprofile = (ImageView) findViewById(R.id.ing_editprofile);
        img_edit_email = (ImageView) findViewById(R.id.img_edit_email);
        img_edit_address = (ImageView) findViewById(R.id.img_edit_address);
        txt_first_name = (TextView) findViewById(R.id.txt_first_name);
        txt_first = (TextView) findViewById(R.id.txt_first);
        txt_last_name = (TextView) findViewById(R.id.txt_last_name);
        txt_last = (TextView) findViewById(R.id.txt_last);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txt_edit_phone = (TextView) findViewById(R.id.txt_edit_phone);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_edit_email = (TextView) findViewById(R.id.txt_edit_email);
        txt_address = (TextView) findViewById(R.id.txt_address);
        txt_edit_address = (TextView) findViewById(R.id.txt_edit_address);
        txt_edit_city = (TextView) findViewById(R.id.txt_edit_city);
        txt_edit_postal = (TextView) findViewById(R.id.txt_edit_postal);
        txt_edit_state = (TextView) findViewById(R.id.txt_edit_state);

        linear_phone = (LinearLayout) findViewById(R.id.linear_phone);
        linear_email = (LinearLayout) findViewById(R.id.linear_email);
        linear_adress = (LinearLayout) findViewById(R.id.linear_adress);

        getUserProfile();

        linear_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditAccount.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_address_dialog);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                final EditText edit_address = (EditText) dialog.findViewById(R.id.edit_address);
                final EditText edit_city = (EditText) dialog.findViewById(R.id.edit_city);
                final EditText edit_state = (EditText) dialog.findViewById(R.id.edit_state);
                final EditText edit_pincode = (EditText) dialog.findViewById(R.id.edit_pincode);
                Button button = (Button) dialog.findViewById(R.id.btn_save_address);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit_str_address = edit_address.getText().toString();
                        edit_str_city = edit_city.getText().toString();
                        edit_str_state = edit_state.getText().toString();
                        edit_str_pincode = edit_pincode.getText().toString();
                        if (edit_str_address.isEmpty()) {
                            edit_address.setError("Enter Your address");
                        } else if (edit_str_city.isEmpty()) {
                            edit_city.setError("Enter Your City");
                        } else if (edit_str_state.isEmpty()) {
                            edit_state.setError("Enter Your State");
                        } else if (edit_str_pincode.isEmpty()) {
                            edit_pincode.setError("Enter Your Pincode");
                        } else {
                            editaddress();
                        }
                    }
                });
                dialog.show();
            }
        });

        linear_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditAccount.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_email_dialog);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                TextView textView = (TextView) dialog.findViewById(R.id.txt_edit_email);
                final EditText editText = (EditText) dialog.findViewById(R.id.edit_email);
                Button button = (Button) dialog.findViewById(R.id.btn_save_email);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit_email = editText.getText().toString();
                        if (edit_email.isEmpty()) {
                            editText.setError("Enter Your number");
                        } else {
                            editemail();
                        }
                    }
                });
                dialog.show();
            }
        });

        linear_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditAccount.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_phone_number);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                TextView textView = (TextView) dialog.findViewById(R.id.txt_tital_phone);
                final EditText editText = (EditText) dialog.findViewById(R.id.edit_number);
                Button button = (Button) dialog.findViewById(R.id.btn_save_phone_num);
                CountryCodePicker countryCodePicker = (CountryCodePicker) dialog.findViewById(R.id.code_picker_edit);
                countryCodePicker.resetToDefaultCountry();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit_number = editText.getText().toString();
                        if (edit_number.isEmpty()) {
                            editText.setError("Enter Your number");
                        } else {
                            editphoneNumber();
                        }
                    }
                });
                dialog.show();
            }
        });

        ing_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean result = Utility.checkPermission(EditAccount.this);
                if (result) {
                    galleryIntent();

                }
            }
        });


        img_back_waybill.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });
    }

    private void editaddress() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(EditAccount.this, Constant.DRIVERID));
        params.put("email", Utils.ReadSharePrefrence(EditAccount.this, Constant.EMAIL));
        params.put("mobile_no", Utils.ReadSharePrefrence(EditAccount.this, Constant.PHONE_NUMBER));
        params.put("address", edit_str_address);
        params.put("city", edit_str_city);
        params.put("state", edit_str_state);
        params.put("pincode", edit_str_pincode);

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "edit_driver_profile.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL + "edit_driver_profile.php?", params, new JsonHttpResponseHandler() {
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
                EditProfileResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), EditProfileResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_edit_address.setText(dmodel.getData().getAddress().getAddress());
                    txt_edit_city.setText(dmodel.getData().getAddress().getCity());
                    txt_edit_state.setText(dmodel.getData().getAddress().getState());
                    txt_edit_postal.setText(dmodel.getData().getAddress().getPincode());


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void editemail() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(EditAccount.this, Constant.DRIVERID));
        params.put("email", edit_email);
        params.put("mobile_no", Utils.ReadSharePrefrence(EditAccount.this, Constant.PHONE_NUMBER));
        params.put("address", Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS));
        params.put("city", Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS));
        params.put("state", Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS));
        params.put("pincode", Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "edit_driver_profile.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL + "edit_driver_profile.php?", params, new JsonHttpResponseHandler() {
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
                EditProfileResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), EditProfileResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_edit_email.setText(dmodel.getData().getEmail());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }


    private void editphoneNumber() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(EditAccount.this, Constant.DRIVERID));
        params.put("email", Utils.ReadSharePrefrence(EditAccount.this, Constant.EMAIL));
        params.put("mobile_no", edit_number);
        params.put("address", Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS));
        params.put("city", Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS));
        params.put("state", Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS));
        params.put("pincode", Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "edit_driver_profile.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL + "edit_driver_profile.php?", params, new JsonHttpResponseHandler() {
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
                EditProfileResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), EditProfileResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_edit_phone.setText(dmodel.getData().getMobile_no());

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
                    imagePath_1 = ImageFilePath.getPath(EditAccount.this, data.getData());
                    getDriverProfilePic();

                }
            }
        }
    }

    private void getDriverProfilePic() {
        AsyncHttpClient client = new AsyncHttpClient();
        File file = new File(imagePath_1);
        RequestParams params = new RequestParams();
        params.put("driver_id", Utils.ReadSharePrefrence(EditAccount.this, Constant.DRIVERID));
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
                DriverLoginResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), DriverLoginResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")){
                    if (dmodel.getData().getImage().isEmpty()) {
                        Picasso.with(EditAccount.this).load(R.drawable.ic_profile);
                    } else {
                        Picasso.with(EditAccount.this).load(dmodel.getData().getImage()).placeholder(R.drawable.ic_profile).into(ing_editprofile);

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

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ing_editprofile.setImageBitmap(bm);

    }


    public void getUserProfile() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(EditAccount.this, Constant.DRIVERID));

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
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "Driver RESPONSE-" + response);
                DriverLoginResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), DriverLoginResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_first.setText(dmodel.getData().getFirst_name());
                    txt_last.setText(dmodel.getData().getLast_name());
                    Utils.WriteSharePrefrence(EditAccount.this,Constant.DriverName,dmodel.getData().getFirst_name()+" "+dmodel.getData().getLast_name());
                    String drivername = Utils.ReadSharePrefrence(EditAccount.this,Constant.DriverName);
                    txt_edit_phone.setText(dmodel.getData().getMobile_no());
                    Utils.WriteSharePrefrence(EditAccount.this, Constant.PHONE_NUMBER, dmodel.getData().getMobile_no());
                    String phone_num = Utils.ReadSharePrefrence(EditAccount.this, Constant.PHONE_NUMBER);
                    txt_edit_email.setText(dmodel.getData().getEmail());
                    Utils.WriteSharePrefrence(EditAccount.this, Constant.EMAIL, dmodel.getData().getEmail());
                    String email = Utils.ReadSharePrefrence(EditAccount.this, Constant.EMAIL);
                    txt_edit_address.setText(dmodel.getData().getCity());
                    Utils.WriteSharePrefrence(EditAccount.this, Constant.ADDRESS, dmodel.getData().getCity());
                    String address = Utils.ReadSharePrefrence(EditAccount.this, Constant.ADDRESS);
                    if (dmodel.getData().getImage().isEmpty()) {
                        Picasso.with(EditAccount.this).load(R.drawable.ic_profile);
                    } else {
                        Picasso.with(EditAccount.this).load(dmodel.getData().getImage()).placeholder(R.drawable.ic_profile).into(ing_editprofile);

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
}
