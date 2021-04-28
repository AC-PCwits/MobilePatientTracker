package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PatientConsultationHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_consultation_history);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.consultation_history);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.p_home:
                        startActivity(new Intent(getApplicationContext()
                                ,PHomePage.class));
                        overridePendingTransition(0 , 0);
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
                        return true;
                }
                return false;
            }
        });
    }
}