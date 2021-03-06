package com.example.archirayan.cabsbookdriver.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.ImageFilePath;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.Utils.Utils;

import java.io.IOException;

public class VehicleInsurance extends AppCompatActivity {

    private ImageView img_insurance,img_takeAphoto_insurance,img_photocamera_insurance;
    private TextView txt_insurance_help,txt_takeyour_insurance,txt_insurance,txt_intractiontake_insurance,txt_taptoadd_insurance;
    private LinearLayout linear_insurance,linear_takeAphoto_insurance;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String imagePath_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_insurance);

        img_takeAphoto_insurance = (ImageView) findViewById(R.id.img_takeAphoto_insurance);
        img_photocamera_insurance = (ImageView) findViewById(R.id.img_photocamera_insurance);


        txt_takeyour_insurance = (TextView) findViewById(R.id.txt_takeyour_insurance);
        txt_insurance = (TextView) findViewById(R.id.txt_insurance);
        txt_intractiontake_insurance = (TextView) findViewById(R.id.txt_intractiontake_insurance);
        txt_taptoadd_insurance = (TextView) findViewById(R.id.txt_taptoadd_insurance);

        linear_takeAphoto_insurance = (LinearLayout) findViewById(R.id.linear_takeAphoto_insurance);

        linear_takeAphoto_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result= Utility.checkPermission(VehicleInsurance.this);
                if(result) {
                    galleryIntent();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_out, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Utils.WriteSharePrefrence(VehicleInsurance.this, Constant.DRIVERID,"");
                startActivity(new Intent(VehicleInsurance.this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Gallery",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(VehicleInsurance.this);
        builder.setTitle("VEHICLE INSURANCE!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(VehicleInsurance.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask ="Choose from Gallery";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }*/

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

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

   /* private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
                if (data != null) {
                    Uri imageUri = data.getData();
                    imagePath_3 = ImageFilePath.getPath(VehicleInsurance.this, data.getData());
                    Intent intent = new Intent(this, Documents.class);
                    intent.putExtra("img3", imagePath_3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
            /*else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }*/
        }
    }

    /*private void onCaptureImageResult(Intent data) {
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

        img_takeAphoto_insurance.setImageBitmap(thumbnail);
    }*/

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        img_takeAphoto_insurance.setImageBitmap(bm);
    }
}
