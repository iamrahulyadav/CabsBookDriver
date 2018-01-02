package com.example.archirayan.cabsbookdriver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.example.archirayan.cabsbookdriver.R;

public class DriverProfileContentPolicyActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btn_savecontent;
    public ImageView img_back_driver_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_content_policy);
        FindById();
        Click();
        WebView htmlWebView = (WebView) findViewById(R.id.webView);
        htmlWebView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        htmlWebView.loadUrl("https://www.uber.com/en-IN/");
    }

    private void FindById() {
        img_back_driver_profile = findViewById(R.id.img_back_driver_profile);
        btn_savecontent = findViewById(R.id.btn_contentpolicy_gotit);
    }

    private void Click()
    {
        img_back_driver_profile.setOnClickListener(this);
        btn_savecontent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_driver_profile:
                onBackPressed();
                break;
            case R.id.btn_contentpolicy_gotit:
                finish();
                break;
        }
    }

    private class CustomWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}