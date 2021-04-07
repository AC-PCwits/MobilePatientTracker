package com.acpc.mobilepatienttracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.TextUtils;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DialogFragment;
import android.app.Dialog;
import android.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.regex.Pattern;


//This code should not be confused with DRegistration.java
//This code is the doctor information form that will be filled out when a doctor registers for the first time.


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

        final FirebaseFirestore database = FirebaseFirestore.getInstance();

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


                ////////ADDING A BRAND NEW ENTRY OF DOCTOR INFORMATION ONCE SIGN UP HAS BEEN SELECTED

               //prac num
               //doctype
                // cell num
                final String fname= first_name.getText().toString();
                final String lname = last_name.getText().toString();
                final String mail= email.getText().toString();
                final String dob = date_of_birth.getText().toString();
                final String id = id_number.getText().toString(); /////double check
                final String prac_years = length_practice.getText().toString(); /////dpuble check
                final int prac_y = Integer.parseInt(prac_years);
                final String uni = institution.getText().toString();

                final String gen = getSelectedRadioButton(v); //calling function below to find selected button


                      //  Doctor doctor = new Doctor(id, fname, lname, doctype, gender, mail, dob, cell_no, p_no, prac_years, uni); //once UI has been fixed this will be the code to use
                Doctor doctor = new Doctor(id, fname, lname, gen, mail, dob ,prac_y, uni);

                        // Now we add it to a specified collection (table) in the database with database.collection().add()
                        // This way will give the new document an auto-generated unique ID as the file name. This can be used like a primary key

                        database.collection("doctor-data") // specify the collection name here
                                .add(doctor)
                                // Add a success listener so we can be notified if the operation was successfuly.

                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        // If we are here, the app successfully connected to Firestore and added a new entry
                                        Toast.makeText(DoctorRegistration.this,"Data successfully added", Toast.LENGTH_LONG).show();
                                    }
                                })
                                // Add a failure listener so we can be notified if something does wrong
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // If we are here, the entry could not be added for some reason (e.g no internet connection)
                                        Toast.makeText(DoctorRegistration.this,"Data was unable to be added. Check connection", Toast.LENGTH_LONG).show();
                                    }
                                });
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



