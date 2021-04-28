package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

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

        Button btn_logout = findViewById(R.id.btn_logout_doc);


        btn_logout.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DHomePage.this, "You have successfully logged out", Toast.LENGTH_LONG).show();
                Intent start = new Intent( DHomePage.this, DoctorOrPatient.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);
            }
        });
    }
}