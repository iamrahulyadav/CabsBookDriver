package com.example.archirayan.cabsbookdriver.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.UberdocumentdriverActivity;
import com.example.archirayan.cabsbookdriver.Utils.GetFilePathFromDevice;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class DriverDocumentsActivity extends AppCompatActivity implements View.OnClickListener {
    public static String str_Doc_Id;
    public static String str_Doc_IdMAin;
    public ImageView img_back_acceptance_tital;
    public LinearLayout llayout_driverlicense, llayout_vehicalinsurance, llayout_vehicalpermit, llayout_drivinglicensebackside;
    public LinearLayout llayout_photo_bgc, llayout_photo_policeverficertificat;
    public TextView txt_doc_vehicalloanemi, txt_doc_pancard, txt_doc_mandateform, txt_doc_noc, txt_doc_bgc, txt_doc_type, txt_doc_licensebackside, txt_doc_vehicaleinsurance, txt_doc_vehicalpermit, txt_doc_vehicalregistartion, txt_doc_policeverficerti;
    public ImageView img_doc_vehicalloanemi, img_doc_pancard, img_doc_mandateform, img_doc_noc, img_doc_bgc, img_doc_licensetype, img_doc_backsidelicense, img_doc_vehicalinsurance, img_doc_vehicalregistartion, img_doc_vehicalpermit, img_doc_policeverficerti;
    public String str_DriverId;
    public ProgressDialog pd;
    public String userChoosenTask, userChoosenTask1;
    public String imagePath_1;
    public String str_doc_typeDrivelicense, str_doc_typeDrivelicenseBackside;
    public String str_DriverImagePath;
    LinearLayout llayout_noc, llayout_mandateform, llayout__doc_pancard, llayout__doc_loanemi, llayout_vehicleregistartion;
    File photoFile;
    private int requestCodeCamera = 5;
    private Bitmap bitmap;
    private ImageChooserManager imageChooserManager;
    private String TAG = "DriverDocumentsActivity";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, FILTER_IMAGE = 400;
    private int REQUEST_CAMERA_Driver = 3, SELECT_FILE_Driver = 2;
    private String imgDecodableString;
    private String filePath;
    private int chooserType;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private String str_ResulFInal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_documents);
        llayout_driverlicense = findViewById(R.id.llayout_driverlicense);
        llayout_vehicalinsurance = findViewById(R.id.llayout_vehicalinsurance);
        img_back_acceptance_tital = findViewById(R.id.img_back_acceptance_tital);
        llayout_vehicalpermit = findViewById(R.id.llayout_vehicalpermit);
        llayout_drivinglicensebackside = findViewById(R.id.llayout_drivinglicensebackside);
        txt_doc_type = findViewById(R.id.txt_doc_licensetype);
        txt_doc_licensebackside = findViewById(R.id.txt_doc_licensebackside);
        txt_doc_bgc = findViewById(R.id.txt_doc_bgc);
        txt_doc_vehicalloanemi = findViewById(R.id.txt_doc_vehicalloanemi);
        img_doc_vehicalloanemi = findViewById(R.id.img_doc_vehicalloanemi);
        img_doc_bgc = findViewById(R.id.img_doc_bgc);
        img_doc_licensetype = findViewById(R.id.img_doc_licensetype);
        img_doc_backsidelicense = findViewById(R.id.img_doc_backsidelicense);
        img_doc_vehicalinsurance = findViewById(R.id.img_doc_vehicalinsurance);
        img_doc_vehicalregistartion = findViewById(R.id.img_doc_vehicalregistartion);
        img_doc_vehicalpermit = findViewById(R.id.img_doc_vehicalpermit);
        img_doc_policeverficerti = findViewById(R.id.img_doc_policeverficerti);
        img_doc_noc = findViewById(R.id.img_doc_noc);
        img_doc_mandateform = findViewById(R.id.img_doc_mandateform);
        img_doc_pancard = findViewById(R.id.img_doc_pancard);
        llayout_vehicleregistartion = findViewById(R.id.llayout_vehicleregistartion);
        llayout_noc = findViewById(R.id.llayout_noc);
        txt_doc_pancard = findViewById(R.id.txt_doc_pancard);
        txt_doc_mandateform = findViewById(R.id.txt_doc_mandateform);
        txt_doc_noc = findViewById(R.id.txt_doc_noc);
        txt_doc_policeverficerti = findViewById(R.id.txt_doc_policeverficerti);
        txt_doc_vehicaleinsurance = findViewById(R.id.txt_doc_vehicaleinsurance);
        txt_doc_vehicalpermit = findViewById(R.id.txt_doc_vehicalpermit);
        txt_doc_vehicalregistartion = findViewById(R.id.txt_doc_vehicalregistartion);
        llayout_photo_policeverficertificat = findViewById(R.id.llayout_photo_policeverficertificat);
        llayout_photo_bgc = findViewById(R.id.llayout_photo_bgc);
        llayout__doc_loanemi = findViewById(R.id.llayout__doc_loanemi);
        llayout_mandateform = findViewById(R.id.llayout_mandateform);
        llayout__doc_pancard = findViewById(R.id.llayout__doc_pancard);


        img_back_acceptance_tital.setOnClickListener(this);
        llayout_driverlicense.setOnClickListener(this);
        llayout_vehicalinsurance.setOnClickListener(this);
        llayout_vehicalpermit.setOnClickListener(this);
        llayout_drivinglicensebackside.setOnClickListener(this);
        llayout_photo_policeverficertificat.setOnClickListener(this);
        llayout_photo_bgc.setOnClickListener(this);
        llayout_mandateform.setOnClickListener(this);
        llayout_noc.setOnClickListener(this);
        llayout__doc_loanemi.setOnClickListener(this);

        llayout__doc_pancard.setOnClickListener(this);
        llayout_vehicleregistartion.setOnClickListener(this);
        if (Utils.isConnectingToInternet(DriverDocumentsActivity.this))
        {
            str_DriverId = String.valueOf(Utils.ReadSharePrefrence(DriverDocumentsActivity.this, Constant.DRIVERID));
            Log.d("str_DriverId", "====== DriverId ======" + str_DriverId);
            new getallDriverDocument().execute();
        } else {
            Toast.makeText(this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_acceptance_tital:
                onBackPressed();
                break;
            case R.id.llayout_driverlicense:
                str_Doc_IdMAin = String.valueOf(1);
                selectImage();
                break;

            case R.id.llayout_vehicalinsurance:
                str_Doc_IdMAin = String.valueOf(3);
                selectImage();
                break;

            case R.id.llayout_vehicalpermit:
                str_Doc_IdMAin = String.valueOf(4);
                selectImage();
                break;

            case R.id.llayout_drivinglicensebackside:
                str_Doc_IdMAin = String.valueOf(2);
                selectImage();
                break;

            case R.id.llayout_vehicleregistartion:
                str_Doc_IdMAin = String.valueOf(5);
                DriverUploadImage();
                break;

            case R.id.llayout_photo_policeverficertificat:
                DriverUploadImage();
                str_Doc_IdMAin = String.valueOf(6);
                break;

            case R.id.llayout_photo_bgc:
                DriverUploadImage();
                str_Doc_IdMAin = String.valueOf(7);
                break;

            case R.id.llayout_noc:
                DriverUploadImage();
                str_Doc_IdMAin = String.valueOf(8);
                break;

            case R.id.llayout_mandateform:
                DriverUploadImage();
                str_Doc_IdMAin = String.valueOf(9);
                break;

            case R.id.llayout__doc_pancard:
                DriverUploadImage();
                str_Doc_IdMAin = String.valueOf(10);
                break;

            case R.id.llayout__doc_loanemi:
                DriverUploadImage();
                str_Doc_IdMAin = String.valueOf(11);
                break;
        }

    }

    private void DriverUploadImage() {
        DriverselectTakImage();
    }

    private void DriverselectTakImage() {
        final CharSequence[] items =
                {"Take Photo", "Choose from Gallery",
                        "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DriverDocumentsActivity.this);
        builder.setTitle("DRIVER LICENSE!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(DriverDocumentsActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask1 = "Take Photo";
                    if (result)
                        cameraIntentDriverImage();
                }
                if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask1 = "Choose from Gallery";
                    if (result)
                        galleryIntentDriverImage();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntentDriverImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA_Driver);

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Log.d("mCurrentPhotoPath", "====== Image Path ====" + mCurrentPhotoPath);
        return image;
    }

    private void galleryIntentDriverImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_Driver);
    }


    private void selectImage() {
        final CharSequence[] items =
                {"Take Photo", "Choose from Gallery",
                        "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DriverDocumentsActivity.this);
        builder.setTitle("DRIVER LICENSE!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(DriverDocumentsActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void cameraIntent()
    {
        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoCaptureIntent, requestCodeCamera);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    if (userChoosenTask.equals("Choose from Gallery"))
                        galleryIntent();


                    if (userChoosenTask1.equalsIgnoreCase("Take Photo")) {
                        cameraIntentDriverImage();
                    } else if (userChoosenTask1.equalsIgnoreCase("Choose from Gallery")) {
                        galleryIntentDriverImage();
                    }
                    break;
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("data()", "====== Data =======" + data.getData());
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_FILE)
            {
                if (data.getData() != null)
                {
                    imagePath_1 = GetFilePathFromDevice.getPath(DriverDocumentsActivity.this, data.getData());
                    Log.d("imagePath_1()", "===== ImagePath ======" + imagePath_1);
                    Intent intent = new Intent(DriverDocumentsActivity.this, UberdocumentdriverActivity.class);
                    intent.putExtra("img1", imagePath_1);
                    setResult(RESULT_OK, intent);
                    startActivity(intent);
                }

            } else if (requestCode == requestCodeCamera)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                String storeFilename = "photo_" + partFilename + ".jpg";
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                Log.d("storeFilename", "===== ImagePath =====" + storeFilename);
                imagePath_1 = "";
                imagePath_1 = "/storage/sdcard1/DCIM/Camera/" + storeFilename;
                Log.d("imagePath_1", "==== Path ====" + imagePath_1);
                Intent intent = new Intent(DriverDocumentsActivity.this, UberdocumentdriverActivity.class);
                intent.putExtra("img1", imagePath_1);
                setResult(RESULT_OK, intent);
                startActivity(intent);
            }


            if (requestCode == SELECT_FILE_Driver) {
                if (data.getData() != null) {
                    imagePath_1 = GetFilePathFromDevice.getPath(DriverDocumentsActivity.this, data.getData());
                    Log.d("imagePath_1()", "===== ImagePath ======" + imagePath_1);
                    AddDriverImageUpload();
                    setResult(RESULT_OK);

                }

            } else if (requestCode == REQUEST_CAMERA_Driver) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                // display the image from SD Card to ImageView Control
                String storeFilename = "photo_" + partFilename + ".jpg";
                Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                Log.d("storeFilename", "===== ImagePath =====" + storeFilename);
                imagePath_1 = "";
                imagePath_1 = "/storage/sdcard1/DCIM/Camera/" + storeFilename;
                Log.d("imagePath_1", "==== Path ====" + imagePath_1);
                AddDriverImageUpload();
            }
        }/* else {
            // Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);

        }*/
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // TODO: 25/12/17    Camera Code
    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate) {
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageFileFromSDCard(String filename) {
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    // // TODO: 22/12/17 Get Path from Camera...
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void AddDriverImageUpload() {
        // str_ResulFInal = "/storage/sdcard1/DCIM/Camera/" + imagePath_1;
      /*  pd = new ProgressDialog(DriverDocumentsActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Ion.with(DriverDocumentsActivity.this)
                .load(Constant.BASE_URL + "driver_upload_image.php?")
                .setMultipartParameter("driver_id", String.valueOf(str_DriverId))
                .setMultipartParameter("id", str_Doc_IdMAin)
                .setMultipartFile("file", new File(imagePath_1))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.e("Response()", "===== Result =======" + result);
                        pd.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                                Toast.makeText(DriverDocumentsActivity.this, "File Upload Successfully.", Toast.LENGTH_SHORT).show();
                                if (Utils.isConnectingToInternet(DriverDocumentsActivity.this)) {
                                    str_DriverId = Utils.ReadSharePrefrence(DriverDocumentsActivity.this, Constant.DRIVERID);
                                    Log.d("str_DriverId", "====== DriverId ======" + str_DriverId);
                                    new getallDriverDocument().execute();
                                } else {
                                    Toast.makeText(DriverDocumentsActivity.this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(DriverDocumentsActivity.this, "File Not Uploaded.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });*/


        pd = new ProgressDialog(DriverDocumentsActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_id", str_DriverId);
        params.put("id", str_Doc_IdMAin);
        params.put("file", imagePath_1);
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
                        Toast.makeText(DriverDocumentsActivity.this, "File Upload Successfully.", Toast.LENGTH_SHORT).show();
                        if (Utils.isConnectingToInternet(DriverDocumentsActivity.this)) {
                            str_DriverId = String.valueOf(Utils.ReadSharePrefrence(DriverDocumentsActivity.this, Constant.DRIVERID));
                            Log.d("str_DriverId", "====== DriverId ======" + str_DriverId);
                            new getallDriverDocument().execute();
                        } else {
                            Toast.makeText(DriverDocumentsActivity.this, R.string.erronointernet, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DriverDocumentsActivity.this, "File Not Uploaded.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e1)
                {
                    e1.printStackTrace();
                }
            }
        });


    }

    // // TODO: 26/12/17  Get all The Driver Document ...
    private class getallDriverDocument extends AsyncTask<String, String, String> {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DriverDocumentsActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String str_Response = Utils.getResponseofGet(Constant.BASE_URL + "get_driver_document.php?driver_id=" + str_DriverId);
            Log.d("str_Response()", "======= Result =====" + str_Response);
            return str_Response;
        }


        @SuppressLint("ResourceAsColor")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // First Object Driving License ...
                        JSONObject object = jsonArray.getJSONObject(0);
                        str_Doc_Id = object.getString("id");
                        JSONObject obj = jsonArray.getJSONObject(i);
                        str_Doc_IdMAin = obj.getString("id");
                        if (object.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_type.setText(object.getString("status"));
                            txt_doc_type.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_licensetype.setImageResource(R.drawable.ic_action_click);
                        } else if (object.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_type.setText(object.getString("status"));
                            txt_doc_type.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_licensetype.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_type.setText(object.getString("status"));
                            txt_doc_type.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_licensetype.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object Driving License BackSide ...
                        JSONObject object1 = jsonArray.getJSONObject(1);
                        str_Doc_Id = object.getString("id");
                        if (object1.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_licensebackside.setText(object1.getString("status"));
                            txt_doc_licensebackside.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_backsidelicense.setImageResource(R.drawable.ic_action_click);
                        } else if (object1.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_licensebackside.setText(object1.getString("status"));
                            txt_doc_licensebackside.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_backsidelicense.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object1.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_licensebackside.setText(object1.getString("status"));
                            txt_doc_licensebackside.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_backsidelicense.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object Vehical Insurance  ...
                        JSONObject object2 = jsonArray.getJSONObject(2);
                        str_Doc_Id = object.getString("id");
                        if (object2.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_vehicaleinsurance.setText(object2.getString("status"));
                            txt_doc_vehicaleinsurance.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_vehicalinsurance.setImageResource(R.drawable.ic_action_click);
                        } else if (object2.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_vehicaleinsurance.setText(object2.getString("status"));
                            txt_doc_vehicaleinsurance.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_vehicalinsurance.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object2.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_vehicaleinsurance.setText(object2.getString("status"));
                            txt_doc_vehicaleinsurance.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_vehicalinsurance.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object Vehical Permit  ...
                        JSONObject object3 = jsonArray.getJSONObject(3);
                        str_Doc_Id = object.getString("id");
                        if (object3.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_vehicalpermit.setText(object3.getString("status"));
                            txt_doc_vehicalpermit.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_vehicalpermit.setImageResource(R.drawable.ic_action_click);
                        } else if (object3.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_vehicalpermit.setText(object3.getString("status"));
                            txt_doc_vehicalpermit.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_vehicalpermit.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object3.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_vehicalpermit.setText(object3.getString("status"));
                            txt_doc_vehicalpermit.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_vehicalpermit.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object Vehical Registration  ...
                        JSONObject object4 = jsonArray.getJSONObject(4);
                        str_Doc_Id = object.getString("id");
                        if (object4.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_vehicalregistartion.setText(object4.getString("status"));
                            txt_doc_vehicalregistartion.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_vehicalregistartion.setImageResource(R.drawable.ic_action_click);
                        } else if (object4.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_vehicalregistartion.setText(object4.getString("status"));
                            txt_doc_vehicalregistartion.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_vehicalregistartion.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object4.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_vehicalregistartion.setText(object4.getString("status"));
                            txt_doc_vehicalregistartion.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_vehicalregistartion.setImageResource(R.mipmap.ic_warningimg);
                        }


                        // Second Object Police Verification Certificate  ...
                        JSONObject object5 = jsonArray.getJSONObject(5);
                        str_Doc_Id = object.getString("id");
                        if (object5.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_policeverficerti.setText(object5.getString("status"));
                            txt_doc_policeverficerti.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_policeverficerti.setImageResource(R.drawable.ic_action_click);
                        } else if (object5.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_policeverficerti.setText(object5.getString("status"));
                            txt_doc_policeverficerti.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_policeverficerti.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object5.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_policeverficerti.setText(object5.getString("status"));
                            txt_doc_policeverficerti.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_policeverficerti.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object BGC  ...
                        JSONObject object6 = jsonArray.getJSONObject(6);
                        str_Doc_Id = object.getString("id");
                        if (object6.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_bgc.setText(object6.getString("status"));
                            txt_doc_bgc.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_bgc.setImageResource(R.drawable.ic_action_click);
                        } else if (object6.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_bgc.setText(object6.getString("status"));
                            txt_doc_bgc.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_bgc.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object6.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_bgc.setText(object6.getString("status"));
                            txt_doc_bgc.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_bgc.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object NOC  ...
                        JSONObject object8 = jsonArray.getJSONObject(7);
                        str_Doc_Id = object.getString("id");
                        if (object8.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_noc.setText(object8.getString("status"));
                            txt_doc_noc.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_noc.setImageResource(R.drawable.ic_action_click);
                        } else if (object8.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_noc.setText(object8.getString("status"));
                            txt_doc_noc.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_noc.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object8.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_noc.setText(object8.getString("status"));
                            txt_doc_noc.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_noc.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object Mandare Form  ...
                        JSONObject object9 = jsonArray.getJSONObject(8);
                        str_Doc_Id = object.getString("id");
                        if (object9.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_mandateform.setText(object9.getString("status"));
                            txt_doc_mandateform.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_mandateform.setImageResource(R.drawable.ic_action_click);
                        } else if (object9.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_mandateform.setText(object9.getString("status"));
                            txt_doc_mandateform.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_mandateform.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object9.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_mandateform.setText(object9.getString("status"));
                            txt_doc_mandateform.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_mandateform.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object Pan Card  ...
                        JSONObject object10 = jsonArray.getJSONObject(9);
                        str_Doc_Id = object.getString("id");
                        if (object10.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_pancard.setText(object10.getString("status"));
                            txt_doc_pancard.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_pancard.setImageResource(R.drawable.ic_action_click);
                        } else if (object10.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_pancard.setText(object10.getString("status"));
                            txt_doc_pancard.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_pancard.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object10.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_pancard.setText(object10.getString("status"));
                            txt_doc_pancard.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_pancard.setImageResource(R.mipmap.ic_warningimg);
                        }

                        // Second Object Vehical Loan Emi  ...
                        JSONObject object11 = jsonArray.getJSONObject(10);
                        str_Doc_Id = object.getString("id");
                        if (object11.getString("status").equalsIgnoreCase("Pending approval")) {
                            txt_doc_vehicalloanemi.setText(object11.getString("status"));
                            txt_doc_vehicalloanemi.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.green));
                            img_doc_vehicalloanemi.setImageResource(R.drawable.ic_action_click);
                        } else if (object11.getString("status").equalsIgnoreCase("Rejected")) {
                            txt_doc_vehicalloanemi.setText(object11.getString("status"));
                            txt_doc_vehicalloanemi.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_vehicalloanemi.setImageResource(R.drawable.ic_cancle_blue);
                        } else if (object11.getString("status").equalsIgnoreCase("Missing")) {
                            txt_doc_vehicalloanemi.setText(object11.getString("status"));
                            txt_doc_vehicalloanemi.setTextColor(DriverDocumentsActivity.this.getResources().getColor(R.color.red));
                            img_doc_vehicalloanemi.setImageResource(R.mipmap.ic_warningimg);
                        }
                    }
                } else {
                    Toast.makeText(DriverDocumentsActivity.this, R.string.no_result_found, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}