package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.DriverLogin;
import com.example.archirayan.cabsbookdriver.model.GetOTPResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class OTPActivity extends AppCompatActivity {

    private static final String TAG = "OTPActivity";
    private EditText edit_otp;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        edit_otp = (EditText) findViewById(R.id.edit_otp);

        edit_otp.addTextChangedListener(new MyTextWatcher(edit_otp));

        btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_otp.getText().toString().isEmpty()){
                    edit_otp.setError("Pleas Enter Your OTP here");
                }else {
                    getOTP();
                }
            }
        });
    }

    private void getOTP() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driver_otp",edit_otp.getText().toString());

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "otp_match.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL+"otp_match.php?",params, new JsonHttpResponseHandler() {
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
                GetOTPResponse model = new Gson().fromJson(new String(String.valueOf(response)),GetOTPResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")){
                    startActivity(new Intent(OTPActivity.this,DriverSignIn.class));
                }else {
                    Toast.makeText(OTPActivity.this, "Your OTP Is not Valid...", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edit_otp:
                    break;

            }
        }
    }
}
