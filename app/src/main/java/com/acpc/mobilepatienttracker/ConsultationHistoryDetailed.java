package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ConsultationHistoryDetailed extends AppCompatActivity {

   private TextView txtDocName;
   private TextView txtDate;
    private TextView txtTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_history_detailed);

        Intent intent = getIntent();

        String docName = "";
        String date="";
        String time ="";

        txtDocName = findViewById(R.id.txtDocName);
        txtDate= findViewById(R.id.txtDate);
        txtTime= findViewById(R.id.txtTime);

        if(intent.getExtras() != null ){

                docName = intent.getExtras().getString("doc_name");
                date = intent.getExtras().getString("date");
                time = intent.getExtras().getString("time");


        }

        txtDocName.setText(docName);
        txtDate.setText(date);
        txtTime.setText(time);

    }
}