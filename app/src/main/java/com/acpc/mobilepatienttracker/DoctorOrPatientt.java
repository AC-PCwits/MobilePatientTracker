package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoctorOrPatientt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_or_patientt);

        Button doctor = findViewById(R.id.doctor);
        Button patient = findViewById(R.id.patient);

        doctor.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                Intent start = new Intent( DoctorOrPatientt.this,DRegistration.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

            }
        });

        patient.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                Intent start = new Intent(DoctorOrPatientt.this,PRegistration.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

            }
        });
    }
}