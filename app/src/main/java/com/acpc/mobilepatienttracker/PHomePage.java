package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_home_page);

        Button log_out = findViewById(R.id.log_out);
        Button p_details = findViewById(R.id.p_details);

        log_out.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(PHomePage.this, "You have successfully logged out", Toast.LENGTH_LONG).show();
                Intent start = new Intent( PHomePage.this, DoctorOrPatient.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);
            }
        });

        p_details.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {

                Intent start = new Intent( PHomePage.this, PatientDetails.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

            }
        });
    }
}