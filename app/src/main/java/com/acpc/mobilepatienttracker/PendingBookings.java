package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PendingBookings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_bookings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.d_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.pending_bookings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.d_home:
                        startActivity(new Intent(getApplicationContext()
                                ,DHomePage.class));
                        overridePendingTransition(0 , 0);
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
                        return true;

                }

                return false;
            }
        });
    }
}