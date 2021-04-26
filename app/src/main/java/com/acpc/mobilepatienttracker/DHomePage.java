package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.d_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.d_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.d_home:
                        return true;
                    case R.id.d_details:
                        startActivity(new Intent(getApplicationContext()
                                ,DoctorDetails.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.patient_list:
                        startActivity(new Intent(getApplicationContext()
                                ,DoctorPatientList.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.pending_bookings:
                        startActivity(new Intent(getApplicationContext()
                                ,PendingBookings.class));
                        overridePendingTransition(0 , 0);
                        return true;

                }

                return false;
            }
        });
    }
}