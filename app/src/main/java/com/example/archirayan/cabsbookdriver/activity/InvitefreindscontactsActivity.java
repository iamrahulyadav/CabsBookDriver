package com.example.archirayan.cabsbookdriver.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class InvitefreindscontactsActivity extends AppCompatActivity implements View.OnClickListener {
    public static String star_CodeCopy;
    public ImageView img_back_waybill;
    public TextView txt_invitecode;
    public LinearLayout llayout_copyinvitecode;
    public Button btn_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitefreindscontacts);
        img_back_waybill = findViewById(R.id.img_back_waybill);
        txt_invitecode = findViewById(R.id.txt_invitecode);
        llayout_copyinvitecode = findViewById(R.id.llayout_copyinvitecode);
        btn_contact = findViewById(R.id.btn_contact);
        img_back_waybill.setOnClickListener(this);
        llayout_copyinvitecode.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        getInvitecode();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_waybill:
                onBackPressed();
                break;

            case R.id.llayout_copyinvitecode:
                getInviteCodeCopy();
                break;
            case R.id.btn_contact:
                startActivity(new Intent(InvitefreindscontactsActivity.this, InviteFriendSelectContactsActivity.class));
                break;
        }
    }

    private void getInviteCodeCopy() {
        Log.d("star_CodeCopy", "====== Copy Text ======" + star_CodeCopy);
        Toast.makeText(InvitefreindscontactsActivity.this, "Your Invite Code has been copied.", Toast.LENGTH_SHORT).show();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", star_CodeCopy);
        clipboard.setPrimaryClip(clip);
    }

    private void getInvitecode() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(InvitefreindscontactsActivity.this, Constant.DRIVERID));
        client.post(this, Constant.BASE_URL + "get_invite_code.php?", googleparams, new JsonHttpResponseHandler() {
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
                GetInvitecodeResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetInvitecodeResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_invitecode.setText(dmodel.getData().getInvite_code());
                    star_CodeCopy = dmodel.getData().getInvite_code();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                //  Log.e(TAG, throwable.getMessage());
            }
        });
    }
}
