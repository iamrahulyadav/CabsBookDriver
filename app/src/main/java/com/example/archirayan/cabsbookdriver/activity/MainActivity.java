package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;

import static com.example.archirayan.cabsbookdriver.R.id.text;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linear_drivermain,linear_choosesignup,linear_userapp;
    private Button btn_driver_signin,btn_driver_signup;
    private View view_main_1;
    private TextView titaltxt,txt_userapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear_drivermain = (LinearLayout) findViewById(R.id.linear_drivermain);
        linear_choosesignup = (LinearLayout) findViewById(R.id.linear_choosesignup);
        linear_userapp = (LinearLayout) findViewById(R.id.linear_userapp);

        btn_driver_signin = (Button) findViewById(R.id.btn_driver_signin);
        btn_driver_signup = (Button) findViewById(R.id.btn_driver_signup);

        view_main_1 = (View) findViewById(R.id.view_main_1);

        titaltxt = (TextView) findViewById(R.id.titaltxt);
        txt_userapp = (TextView) findViewById(R.id.txt_userapp);

        btn_driver_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DriverSignIn.class));
                finish();
            }
        });

        btn_driver_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DriverSignup.class));
                finish();
            }
        });

        linear_userapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                Intent chooserIntent = Intent.createChooser(shareIntent, "Share image via...");
                String link = "https://play.google.com/store/apps/details?id=com.picciti.archi1.piccity&hl=en";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    shareIntent.putExtra(Intent.EXTRA_TEXT, text + " " + link);
                    Bundle facebookBundle = new Bundle();
                    facebookBundle.putString(Intent.EXTRA_TEXT, link);
                    Bundle replacement = new Bundle();
                    replacement.putBundle("com.facebook.katana", facebookBundle);
                    chooserIntent.putExtra(Intent.EXTRA_REPLACEMENT_EXTRAS, replacement);
                } else

                chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chooserIntent);*/
                Intent intent = new Intent();
                intent = new Intent(android.content.Intent.ACTION_VIEW);

                //Copy App URL from Google Play Store.
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.picciti.archi1.piccity&hl=en"));

                startActivity(intent);

            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
