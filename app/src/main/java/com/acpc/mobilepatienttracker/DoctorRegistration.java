package com.acpc.mobilepatienttracker;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.text.InputFilter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.AlertDialog;

import java.util.Calendar;

public class DoctorRegistration extends AppCompatActivity {


    //from line 37 to 43 we declare all variabls to be used in the following code below

    private TextView date, first_name, last_name, email, password, date_of_birth,id_number,length_practice,institution;
    private CheckBox policy;
    private RadioGroup gender_group;
    private RadioButton gender_button;
    private Button select_date;
    private Calendar c;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        gender_group = findViewById(R.id.genderGroup);
        date = findViewById(R.id.date_of_birth);
        select_date = findViewById(R.id.select_dob);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        date_of_birth = findViewById(R.id.date_of_birth);
        id_number = findViewById(R.id.IDnu);
        length_practice = findViewById(R.id.LengthOfPractice);
        institution=findViewById(R.id.Institution);
        password = findViewById(R.id.password);
        policy = findViewById(R.id.policy);

        first_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        last_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        length_practice.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        institution.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        id_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});




        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(DoctorRegistration.this,AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        int mnth = month+1;

                        if(dayOfMonth <10 && mnth >=10){
                            date.setText(year + "/" +(month+1) + "/" + "0"+dayOfMonth);
                        }else if (mnth <10 && dayOfMonth>=10){
                            date.setText(year + "/" +"0"+(month+1) + "/" + dayOfMonth);
                        }else if (mnth <10 && dayOfMonth <10){
                            date.setText(year + "/" +"0"+(month+1) + "/" +"0"+dayOfMonth);
                        }else if (mnth >= 10 && dayOfMonth >= 10){
                            date.setText(year + "/" +(month+1) + "/" + dayOfMonth);
                        }
                    }
                }, year,month,day);
                dpd.show();
            }
        });


        Button b = findViewById(R.id.signUpButtonId);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_gender = gender_group.getCheckedRadioButtonId();
                gender_button = findViewById(selected_gender);

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String datePattern ="^\\d{4}/\\d{2}/\\d{2}$";


                if (first_name.getText().toString().equals("")) {
                    first_name.setError("Empty first name");
                }  else if (last_name.getText().toString().equals("")) {
                    last_name.setError("Empty last name");
                } else if (email.getText().toString().equals("")) {
                    email.setError("Empty email address");
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Invalid email address");
                } else if (date_of_birth.getText().toString().equals("")) {
                    date_of_birth.setError("Select date of birth");
                } else if (!date_of_birth.getText().toString().trim().matches(datePattern)) {
                    date_of_birth.setError("Date format should be 'yyyy/mm/dd'");
                } else if (id_number.getText().toString().equals("")) {
                    id_number.setError("Empty ID number");
                } else if (length_practice.getText().toString().equals("")) {
                    length_practice.setError("Empty length of practice");
                } else if (institution.getText().toString().equals("")) {
                    institution.setError("Empty name of institution");
                } else if (password.getText().toString().equals("")) {
                    password.setError("Empty password");
                } else if(!policy.isChecked()) {
                    policy.setError("Policy must be accepted");
                }
            }
        });

    }
}