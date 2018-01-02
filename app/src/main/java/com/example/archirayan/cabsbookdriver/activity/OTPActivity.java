package com.example.archirayan.cabsbookdriver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.Utils.Utils;

public class OTPActivity extends AppCompatActivity {

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
                if (edit_otp.getText().toString().equals(Utils.ReadSharePrefrence(OTPActivity.this, Constant.OTP))){
                    startActivity(new Intent(OTPActivity.this,Documents.class));
                }else {
                    Toast.makeText(OTPActivity.this, "This OTP is not Valid...", Toast.LENGTH_SHORT).show();
                }
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
