package com.example.archirayan.cabsbookdriver.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class InvitefreindssharecontactsActivity extends AppCompatActivity implements View.OnClickListener {

    public static String star_CodeCopy;
    public LinearLayout llayout_main, llayout_email, llayout_main1, llayout_facebook, llayout_gmail, llayout_whatsapp, llayout_messages, llayout_copyinvitecode;
    public TextView txt_invitecode;

    public static final String PACKAGE_NAME = "com.facebook.orca";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitefreindssharecontacts);
        FindviewById();
        Click();
        getInvitecode();

    }


    private void Click() {
        llayout_main.setOnClickListener(this);
        llayout_main1.setOnClickListener(this);
        llayout_facebook.setOnClickListener(this);
        llayout_gmail.setOnClickListener(this);
        llayout_whatsapp.setOnClickListener(this);
        llayout_messages.setOnClickListener(this);
        llayout_copyinvitecode.setOnClickListener(this);
        llayout_email.setOnClickListener(this);
    }

    private void FindviewById() {
        llayout_main = findViewById(R.id.llayout_main);
        llayout_main1 = findViewById(R.id.llayout_main1);

        llayout_facebook = findViewById(R.id.llayout_facebook);
        llayout_gmail = findViewById(R.id.llayout_gmail);
        llayout_email = findViewById(R.id.llayout_email);
        llayout_whatsapp = findViewById(R.id.llayout_whatsapp);
        llayout_messages = findViewById(R.id.llayout_messages);
        llayout_copyinvitecode = findViewById(R.id.llayout_copyinvitecode);

        txt_invitecode = findViewById(R.id.txt_invitecode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llayout_main:
                onBackPressed();
                break;

            case R.id.llayout_main1:
                onBackPressed();
                break;

            case R.id.llayout_facebook:
                getOpenFacebookIntent();
                break;

            case R.id.llayout_gmail:
                getOpenGmailIntent();
                break;

            case R.id.llayout_whatsapp:
                getOpenWhatsppIntent();
                break;

            case R.id.llayout_messages:
                getOpenSMSIntent();
                break;

            case R.id.llayout_copyinvitecode:
                getInviteCodeCopy();
                break;

            case R.id.llayout_email:
                getOpenTwitterIntent();
                break;
        }
    }

    private void getOpenTwitterIntent() {
        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, star_CodeCopy);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Twitter is not installed on this device", Toast.LENGTH_LONG).show();
        }
    }

    private void getInviteCodeCopy() {
        Log.d("star_CodeCopy", "====== Copy Text ======" + star_CodeCopy);
        Toast.makeText(InvitefreindssharecontactsActivity.this, "Your Invite Code has been copied.", Toast.LENGTH_SHORT).show();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", star_CodeCopy);
        clipboard.setPrimaryClip(clip);
    }

    private void getOpenWhatsppIntent() {
        String smsNumber = "7****";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, star_CodeCopy);
        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "Whatsapp is not installed on this device", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(sendIntent);
    }

    private void getOpenGmailIntent() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
         emailIntent.putExtra(Intent.EXTRA_TEXT, star_CodeCopy);
        startActivity(Intent.createChooser(
                emailIntent, "Send mail..."));
    }

    private void getOpenFacebookIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "https://www.facebook.com");
        sendIntent.putExtra(Intent.EXTRA_TEXT, star_CodeCopy);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    private void getOpenSMSIntent()
    {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        //smsIntent.putExtra("address", new String(star_CodeCopy));
        smsIntent.putExtra("sms_body", star_CodeCopy);
        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(InvitefreindssharecontactsActivity.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getInvitecode() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(InvitefreindssharecontactsActivity.this, Constant.DRIVERID));
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
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                GetInvitecodeResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), GetInvitecodeResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_invitecode.setText(dmodel.getData().getInvite_code());
                    star_CodeCopy = dmodel.getData().getInvite_code();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                super.onFailure(statusCode, headers, responseString, throwable);
                //  Log.e(TAG, throwable.getMessage());
            }
        });
    }
}
