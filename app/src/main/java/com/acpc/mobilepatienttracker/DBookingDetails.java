package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DBookingDetails extends AppCompatActivity {

    String name;
    String id;
    String date;
    String time;

    private TextView nameT;
    private TextView idT;
    private TextView dateT;
    private TextView timeT;
    private Button accept;
    private Button reject;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_booking_details);

        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        nameT = (TextView)findViewById(R.id.name);
        dateT = (TextView)findViewById(R.id.date);
        timeT = (TextView)findViewById(R.id.time);
        idT = (TextView)findViewById(R.id.id);
        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);

        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            name = intent.getExtras().getString("PATIENT_NAME");
            id = intent.getExtras().getString("PATIENT_ID");
            date = intent.getExtras().getString("BOOKING_DATE");
            time = intent.getExtras().getString("BOOKING_TIME");

        }

        nameT.setText(name);
        idT.setText(id);
        dateT.setText(date);
        timeT.setText(time);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//must add patient-ID to to doctor's list of patients associated with them
//patients must receive some sort of booking confirmation ?notification maybe

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//patients must receive some sort of notice that booking must be rescheduled ?notification maybe

            }
        });

    }
}