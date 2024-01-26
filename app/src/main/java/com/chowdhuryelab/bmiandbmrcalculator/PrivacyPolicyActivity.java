package com.chowdhuryelab.bmiandbmrcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class PrivacyPolicyActivity extends Activity {
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        web =(WebView)findViewById(R.id.webView);
        web.loadUrl("https://sites.google.com/view/bmiandbmrcalculator/home");

    }

}