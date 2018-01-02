package com.example.archirayan.cabsbookdriver.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;

public class UserContectActivity extends AppCompatActivity {

    private TextView txt_user_con_name,txt_user_num;
    private LinearLayout linear_msg,linear_call;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private String phoneNo;
    private String message,email_str;
    private Dialog dialog;
    private EditText editText;
    private String numberToDial;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contect);

        txt_user_con_name = (TextView) findViewById(R.id.txt_user_con_name);
        txt_user_num = (TextView) findViewById(R.id.txt_user_num);

        txt_user_num.setText(Utils.ReadSharePrefrence(UserContectActivity.this,Constant.USER_mobile_num));
        txt_user_con_name.setText(Utils.ReadSharePrefrence(UserContectActivity.this,Constant.NAME));

        linear_msg = (LinearLayout) findViewById(R.id.linear_msg);
        linear_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(UserContectActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.send_msg_dialog);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                editText = (EditText) dialog.findViewById(R.id.edit_msg);
                Button button = (Button) dialog.findViewById(R.id.btn_msg_send);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        email_str = editText.getText().toString();
                        if (email_str.isEmpty()){
                            editText.setError("Enter Your Message");
                        }else {
                            sendSMSMessage();
                        }
                    }
                });
                dialog.show();

            }
        });

        linear_call = (LinearLayout) findViewById(R.id.linear_call);
        linear_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = txt_user_num.getText().toString();

                if (!TextUtils.isEmpty(phoneNumber)) {
                    if (checkPermission(android.Manifest.permission.CALL_PHONE)) {
                        String dial = "tel:" + phoneNumber;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    } else {
                        Toast.makeText(UserContectActivity.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserContectActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (checkPermission(android.Manifest.permission.CALL_PHONE)) {
            linear_call.setEnabled(true);
        } else {
            linear_call.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }

    }



    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }



    private void sendSMSMessage() {
        phoneNo = txt_user_num.getText().toString();
        message = email_str.toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        switch(requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE :
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    linear_call.setEnabled(true);
                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }

    }
}
