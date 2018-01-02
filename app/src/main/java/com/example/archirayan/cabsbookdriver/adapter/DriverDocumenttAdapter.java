package com.example.archirayan.cabsbookdriver.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.DriverDocumentClass;
import com.example.archirayan.cabsbookdriver.activity.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by archirayan on 21/12/17.
 */

public class DriverDocumenttAdapter extends RecyclerView.Adapter<DriverDocumenttAdapter.MyViewHolder> {

    Context context;
    ArrayList<DriverDocumentClass> dealArrayList;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, FILTER_IMAGE = 400;
    private String imagePath_1;
    private int SELECT_IMAGE = 200;


    public DriverDocumenttAdapter(Context context, ArrayList<DriverDocumentClass> dealArrayList) {
        this.context = context;
        this.dealArrayList = dealArrayList;
    }

    @Override
    public DriverDocumenttAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_driverdocument, parent, false);
        return new DriverDocumenttAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(DriverDocumenttAdapter.MyViewHolder holder, int position) {
        holder.img_driver_documentype.setImageResource(R.mipmap.ic_warningimg);

        holder.llayout_fashion_main.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("DRIVER LICENSE!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(context);

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



    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) context).startActivityForResult(intent, REQUEST_CAMERA);

    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);


    }


    @Override
    public int getItemCount() {
        return 10;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView img_driver_documentype;
        public TextView txt_driver_doc;
        public LinearLayout llayout_fashion_main;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            img_driver_documentype = (ImageView) itemView.findViewById(R.id.img_driver_documentype);
            llayout_fashion_main = itemView.findViewById(R.id.llayout_fashion_main);

        }
    }

    private void onCaptureImageResult(Intent data)
    {
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
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //img_takeAphoto.setImageBitmap(thumbnail);
    }
}