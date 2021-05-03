package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DocTypeList extends AppCompatActivity {

    String t;
    private TextView dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_type_list);

        dt=findViewById(R.id.dt);
        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            t = intent.getExtras().getString("DOC_TYPE");

        }

        dt.setText(t);
    }
}