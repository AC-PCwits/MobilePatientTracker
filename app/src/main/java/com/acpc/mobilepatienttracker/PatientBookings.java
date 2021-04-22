package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PatientBookings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_bookings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.bookings);

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
                        return true;
                    case R.id.booking_history:
                        startActivity(new Intent(getApplicationContext()
                                , PatientBookingHistory.class));
                        overridePendingTransition(0 , 0);
                        return true;
                }
                return false;
            }
        });
    }
}