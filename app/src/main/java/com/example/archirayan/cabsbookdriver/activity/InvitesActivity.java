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

public class InvitesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "InvitesActivity";
    public static String star_CodeCopy;
    public LinearLayout llayout_invitefriendshareapp, llayout_invitefriendcontacts;
    private ImageView img_back_invite_tital;
    private TextView txt_invitecode;
    private Button btn_copycode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites);

        FindviewById();
        Click();
        getInvitecode();

        img_back_invite_tital = (ImageView) findViewById(R.id.img_back_invite_tital);
        img_back_invite_tital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InvitesActivity.this, DriverMainPage.class));
            }
        });
        txt_invitecode = (TextView) findViewById(R.id.txt_invitecode);
        btn_copycode = (Button) findViewById(R.id.btn_copycode);
        btn_copycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InvitesActivity.this, "Your Invite Code has been copied.", Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied", star_CodeCopy);
                clipboard.setPrimaryClip(clip);
            }
        });


    }

    private void Click() {
        llayout_invitefriendshareapp.setOnClickListener(this);
        llayout_invitefriendcontacts.setOnClickListener(this);
    }

    private void FindviewById() {
        llayout_invitefriendshareapp = findViewById(R.id.llayout_invitefriendshareapp);
        llayout_invitefriendcontacts = findViewById(R.id.llayout_invitefriendcontacts);
    }

    private void getInvitecode()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(InvitesActivity.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_invite_code.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "get_invite_code.php?", googleparams, new JsonHttpResponseHandler()
        {
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
                GetInvitecodeResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetInvitecodeResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true"))
                {
                    txt_invitecode.setText(dmodel.getData().getInvite_code());
                    star_CodeCopy = dmodel.getData().getInvite_code();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llayout_invitefriendshareapp:
                sharetheAppDialoge();
                break;

            case R.id.llayout_invitefriendcontacts:
                startActivity(new Intent(InvitesActivity.this, InvitefreindscontactsActivity.class));
                break;
        }
    }

    private void sharetheAppDialoge() {
        startActivity(new Intent(InvitesActivity.this, InvitefreindssharecontactsActivity.class));
    }
}
