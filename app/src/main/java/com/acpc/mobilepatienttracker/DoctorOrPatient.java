package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorOrPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_or_patientt);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();



        Button doctor = findViewById(R.id.doctor);
        Button patient = findViewById(R.id.patient);

        doctor.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                Intent start = new Intent( DoctorOrPatient.this, LoginDoctor.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

            }
        });

        patient.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                Intent start = new Intent(DoctorOrPatient.this, LoginPatient.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

            }
        });
    }
}