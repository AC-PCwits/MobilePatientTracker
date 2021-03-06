package com.acpc.mobilepatienttracker;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.text.InputFilter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_LONG;


//This code should not be confused with DRegistration.java
//This code is the doctor information form that will be filled out when a doctor registers for the first time.


public class DoctorForm extends AppCompatActivity {


    //from line 37 to 43 we declare all variabls to be used in the following code below

    private TextView date, first_name, last_name, email, password, date_of_birth,id_number,length_practice,institution,doctorSpec,cellNum,practicingNum;
    private CheckBox policy;
    private RadioGroup gender_group;
    private RadioButton gender_button;
    private Button select_date;
    private Calendar c;
    private DatePickerDialog dpd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_form);

        gender_group = findViewById(R.id.genderGroup);
        date = findViewById(R.id.date_of_birth);
        //select_date = findViewById(R.id.select_dob);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        date_of_birth = findViewById(R.id.date_of_birth);
        id_number = findViewById(R.id.IDnu);
        length_practice = findViewById(R.id.LengthOfPractice);
        institution=findViewById(R.id.Institution);
        doctorSpec = findViewById(R.id.Doctor_speciality);
        cellNum = findViewById(R.id.Cellnu);

        first_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        last_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        length_practice.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        institution.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        id_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        cellNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(DoctorForm.this,AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int selected_gender = gender_group.getCheckedRadioButtonId();
                gender_button = findViewById(selected_gender);

                String datePattern = "^\\d{4}/\\d{2}/\\d{2}$";




                if (first_name.getText().toString().equals("")) {
                    first_name.setError("Empty first name");
                    return;
                } else if (last_name.getText().toString().equals("")) {
                    last_name.setError("Empty last name");
                    return;
                } else if (date_of_birth.getText().toString().equals("")) {
                    date_of_birth.setError("Select date of birth");
                    return;
                } else if (!date_of_birth.getText().toString().trim().matches(datePattern)) {
                    date_of_birth.setError("Date format should be 'yyyy/mm/dd");
                    return;
                } else if (id_number.getText().toString().equals("")) {
                    id_number.setError("Empty ID number");
                    return;
                } else if (length_practice.getText().toString().equals("")) {
                    length_practice.setError("Empty length of practice");
                    return;
                } else if (institution.getText().toString().equals("")) {
                    institution.setError("Empty name of institution");
                    return;
                } else if (doctorSpec.getText().toString().equals("")) {
                    doctorSpec.setError("Empty Doctor speciality");
                    return;
                } else if (cellNum.getText().toString().equals("")) {
                    cellNum.setError("Empty cell number");
                    return;
                }
                else if(gender_group.getCheckedRadioButtonId()==-1){
                    Toast.makeText(DoctorForm.this,"Please select your gender", LENGTH_LONG).show();
                }
                else{
                        ////////ADDING A BRAND NEW ENTRY OF DOCTOR INFORMATION ONCE SIGN UP HAS BEEN SELECTED

                        Bundle extras = getIntent().getExtras();

                        final String fname = first_name.getText().toString();
                        final String lname = last_name.getText().toString();
                        final String mail = extras.getString("EMAIL");
                        final String dob = date_of_birth.getText().toString();
                        final String id = id_number.getText().toString(); /////double check
                        final String prac_years = length_practice.getText().toString(); /////dpuble check
                        //final int prac_y = Integer.parseInt(prac_years);
                        final String uni = institution.getText().toString();
                        final String prac_num = extras.getString("PID");
                        final String doc_type = doctorSpec.getText().toString();
                        final String cell = cellNum.getText().toString();

                        final String gen = getSelectedRadioButton(v); //calling function below to find selected button

                        Doctor doctor = new Doctor(id, fname, lname, dob, gen, mail, prac_years, uni, prac_num, doc_type, cell);

                    // Now we add it to a specified collection (table) in the database with database.collection().add()
                    // This way will give the new document an auto-generated unique ID as the file name. This can be used like a primary key

                    FirebaseDoctor firebaseDoctor = new FirebaseDoctor(doctor, "doctor-data", DoctorForm.this);
                    firebaseDoctor.doctorFirestoreReg();
                }
            }
        });

    }

    public String getSelectedRadioButton (View v){

        //checks radio group called gender_group to see what has been selected. Gets text of the gender type selected.
        //Converts the gender type to string and returns it

        int radioID = gender_group.getCheckedRadioButtonId();

        RadioButton singleButton = (RadioButton) findViewById(radioID);
        String gender = singleButton.getText().toString();
        return gender;

    }

}