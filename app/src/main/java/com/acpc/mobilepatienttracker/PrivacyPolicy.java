package com.acpc.mobilepatienttracker;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicy extends AppCompatActivity {

    private CheckBox checkBox;


    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);


        web =(WebView)findViewById(R.id.webView);
        web.loadUrl("file:///android_asset/Privacy.html");

        /*checkBox = findViewById(R.id.check);



        Button done = findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBox.isChecked()) {
                    checkBox.setError("Policy must be accepted");
                }
                else{
                    finish();
                }
            }
        });*/

    }
}