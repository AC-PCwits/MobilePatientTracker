package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class PatientForm extends AppCompatActivity  {
    private EditText pname, psurname, pid, pcell, pNationality, pAddress, pemname, pemcell, Allergies;
    private RadioGroup radioRace;  //// race
    private RadioGroup radioMarital;  /// marital status
    private RadioGroup radioGender; /// gender
    private RadioButton gender_button;
    private CheckBox chkHIV;
    private CheckBox chkTB;
    private CheckBox chKDiabetes;
    private CheckBox chkHyp;
    private CheckBox chkMedaid;
    private CheckBox chkNone;
  //  private Button btnSubmit;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FirebaseFirestore database= FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);
       // btnSubmit= findViewById(R.id.btnSubmit);
        submit = findViewById(R.id.submit);

        radioRace= findViewById(R.id.radioGroup);
        radioMarital= findViewById(R.id.radioGroup2);
        radioGender= findViewById(R.id.rgpGender);

        pname= findViewById(R.id.pname);
        psurname= findViewById(R.id.psurname);
//        pid= findViewById(R.id.pid);
        pcell= findViewById(R.id.pCell);
        pNationality= findViewById(R.id.pNationality);
        pAddress= findViewById(R.id.pAddress);
        pemname= findViewById(R.id.pemname);
        pemcell= findViewById(R.id.pemcell);
        Allergies= findViewById(R.id.Allergies);
        chkMedaid= findViewById(R.id.chkMedaid);
        chkHIV= findViewById(R.id.chkHIV);
        chkTB= findViewById(R.id.chkTB);
        chKDiabetes= findViewById(R.id.chkDiabetes);
        chkHyp= findViewById(R.id.chkHyp);
        chkNone= findViewById(R.id.chkNone);

//        pid.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        pcell.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        pemcell.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
        //submit.setOnClickListener(new View.OnClickListener() { //when you click the submit button it adds to the patients data in the database
          //  @Override
            //public void onClick(View v) {

                int selected_gender = radioGender.getCheckedRadioButtonId();
                gender_button= findViewById(selected_gender);

                if (pname.getText().toString().equals("")) {
                    pname.setError("Empty first name");
                    return;
                } else if (psurname.getText().toString().equals("")) {
                    psurname.setError("Empty last name");
                    return;
//                } else if (pid.getText().toString().equals("")) {
//                    pid.setError("Enter ID");
//                    return;
                } else if (pcell.getText().toString().equals("")) {
                    pcell.setError("Enter cell number");
                    return;
                } else if (pNationality.getText().toString().equals("")) {
                    pNationality.setError("Enter Nationality");
                    return;
                } else if (pAddress.getText().toString().equals("")) {
                    pAddress.setError("Enter Address");
                    return;
                } else if (pemname.getText().toString().equals("")) {
                    pemname.setError("Emergency Contact is required");
                    return;
                } else if (pemcell.getText().toString().equals("")) {
                    pemcell.setError("Emergency Contact Number is required");
                    return;
                } else if(chkHIV.isChecked()==false && chkTB.isChecked()==false && chkHyp.isChecked()==false && chKDiabetes.isChecked()==false && chkNone.isChecked()==false){
                    Toast.makeText(PatientForm.this, "Select a Common Issue option!", LENGTH_LONG).show(); //error message for common issues
                }//common issues validation

                else {

                    Bundle extras = getIntent().getExtras();

                    final String p_name = pname.getText().toString();
                    final String p_surname = psurname.getText().toString();
                    final String p_id = extras.getString("PID");
                    final String p_cell = pcell.getText().toString();
                    final String p_Nationality = pNationality.getText().toString();
                    final String p_Address = pAddress.getText().toString();
                    final String p_emname = pemname.getText().toString();
                    final String p_emcell = pemcell.getText().toString();
                    final String allergies = Allergies.getText().toString();
                    final String m_status = getMarriage_Status(v);
                    final String p_race = getRace(v);
                    final ArrayList<String> cissues = ailments(v);
                    final String gender = getGender(v); //changed to radiobuttons
                    final String medaid = checkAid(v);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date currentdate = new Date();
                    final String lastVisited = formatter.format(currentdate);

                    Patient patient = new Patient(p_name, p_surname, p_id, p_cell, p_Nationality, gender, p_Address, p_emname, p_emcell, p_race, m_status, cissues, medaid, allergies,lastVisited);

                    database.collection("patient-data") // data gets added to a collection called patient-data
                            .add(patient)
                            // Add a success listener so we can be notified if the operation was successfuly.

                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    // If we are here, the app successfully connected to Firestore and added a new entry
                                    makeText(PatientForm.this, "Data successfully added", LENGTH_LONG).show();
                                    Intent start = new Intent(PatientForm.this, PatientFragActivity.class);
                                    startActivity(start);
                                }
                            })
                            // Add a failure listener so we can be notified if something does wrong
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // If we are here, the entry could not be added for some reason (e.g no internet connection)
                                    makeText(PatientForm.this, "Data was unable added", LENGTH_LONG).show();
                                }
                            });
                }

            }
        });

    }


    public String getMarriage_Status (View v){            ////function to help convert radiogroup selections to string, this allows us to store it as a string in the DB

        int radioID = radioMarital.getCheckedRadioButtonId();

        RadioButton singleButton = (RadioButton) findViewById(radioID);
        String out = singleButton.getText().toString();
        return out;

    }


    public String getRace (View v){    ////function to help convert radiogroup selections to string, this allows us to store it as a string in the DB


        int radioID = radioRace.getCheckedRadioButtonId();

        RadioButton singleButton = (RadioButton) findViewById(radioID);
        String out = singleButton.getText().toString();
        return out;

    }

    public String getGender(View v) {      ///// function to help convert radiogroup selections to string, this allows us to store it as a string in the DBra

        int radioID = radioGender.getCheckedRadioButtonId();

        RadioButton singleButton = (RadioButton) findViewById(radioID);
        String out = singleButton.getText().toString();
        return out;

    }


    public ArrayList<String> ailments(View v){         ////function to collect options selected from the check box, stores in a string arrayList, this is stored in the DB
        ArrayList<String> sickness= new ArrayList<String>();

        if(chkHIV.isChecked()){
            sickness.add("HIV/AIDS");
        }
        if(chkTB.isChecked()){
            sickness.add("TB");
        }

        if(chKDiabetes.isChecked()){
            sickness.add("Diabetes");
        }

        if(chkHyp.isChecked()){
            sickness.add("Hypertension");
        }

        if(chkNone.isChecked()){
            sickness.add("None");
        }

        return sickness;

    }


    public String checkAid(View v){    ///// function that checks check box, stores option as a string, this can be stored in the DB
        String aid= "";
        chkMedaid= findViewById(R.id.chkMedaid);

        if(chkMedaid.isChecked()){
            aid="Yes";
        }
        else{
            aid="No";
        }
        return aid;

    }

}