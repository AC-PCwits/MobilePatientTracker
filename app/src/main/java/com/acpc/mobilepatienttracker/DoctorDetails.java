package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoctorDetails extends AppCompatActivity {

    private String name;
    private String id;
    private String cellno;
    private String dob;
    private String gender;
    private String email;
    private String prac_length;
    private String prac_num;
    private String uni;
    private String spec;

    private TextView nameText;
    private TextView idText;
    private TextView dobText;
    private TextView cellText;
    private TextView emailText;
    private TextView genderText;
    private TextView pracLengthText;
    private TextView pracNumText;
    private TextView uniText;
    private TextView specText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        nameText = (TextView)findViewById(R.id.nameText);
        idText = (TextView)findViewById(R.id.idText);
        dobText = (TextView)findViewById(R.id.dobText);
        cellText = (TextView)findViewById(R.id.cellText);
        emailText = (TextView)findViewById(R.id.emailText);
        genderText = (TextView)findViewById(R.id.genderText);
        pracLengthText = (TextView)findViewById(R.id.pLengthText);
        pracNumText = (TextView)findViewById(R.id.pNumText);
        uniText = (TextView)findViewById(R.id.uniText);
        specText = (TextView)findViewById(R.id.specText);


        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            name = bundle.getString("DOC_NAME");
            id = bundle.getString("DOC_ID");
            dob = bundle.getString("DOC_DOB");
            gender = bundle.getString("DOC_GENDER");
            email = bundle.getString("DOC_EMAIL");
            prac_length = bundle.getString("DOC_PRACYEARS");
            prac_num = bundle.getString("DOC_PRACNUM");
            uni = bundle.getString("DOC_UNI");
            spec = bundle.getString("DOC_SPEC");
            cellno = bundle.getString("DOC_CELL");

            nameText.setText(name);
            idText.setText(id);
            dobText.setText(dob);
            genderText.setText(gender);
            emailText.setText(email);
            pracLengthText.setText(prac_length);
            pracNumText.setText(prac_num);
            uniText.setText(uni);
            specText.setText(spec);
            cellText.setText(cellno);
        }
        else
        {
            nameText.setText("ERROR: NULL VALUE PASSED");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.d_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.d_details);

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