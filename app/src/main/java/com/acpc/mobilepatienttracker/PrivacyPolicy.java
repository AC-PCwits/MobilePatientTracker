package com.acpc.mobilepatienttracker;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicy extends AppCompatActivity {

   // private CheckBox acceptpolicy;
    private Button accept;


    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);


        web =(WebView)findViewById(R.id.webView);
        web.loadUrl("file:///android_asset/Privacy.html");

      //  acceptpolicy = findViewById(R.id.chkAcceptPolicy);
        accept = findViewById(R.id.accept);

        /////working code for privacy policy with accept checkbox //////
     /*   done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (acceptpolicy.isChecked()==true){
                   // setContentView(R.layout.activity_doctor_form);
                    finish();

                }//if
                else {
                    acceptpolicy.setError("Policy must be accepted");
                }//else

            }
        });*/

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        ////////old code////////////////
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