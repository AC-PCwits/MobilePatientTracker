package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class PHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.p_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.p_home:
                        return true;
                    case R.id.p_details:
                        startActivity(new Intent(getApplicationContext()
                                ,PatientDetails.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.bookings:
                        startActivity(new Intent(getApplicationContext()
                                , PatientBookings.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.consultation_history:
                        startActivity(new Intent(getApplicationContext()
                                , PatientConsultationHistory.class));
                        overridePendingTransition(0 , 0);
                        return true;
                }
                return false;
            }
        });

        Button log_out = findViewById(R.id.log_out);


        log_out.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(PHomePage.this, "You have successfully logged out", Toast.LENGTH_LONG).show();
                Intent start = new Intent( PHomePage.this, DoctorOrPatient.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);
            }
        });

    }
}