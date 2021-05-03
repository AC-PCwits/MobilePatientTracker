package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class DoctorConsultForm extends AppCompatActivity {
    private RadioGroup radioCase;
    private RadioButton case_button;
    private EditText symptoms, diagnosis, patientid,doctorid,date;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_consult_form);

        final FirebaseFirestore database= FirebaseFirestore.getInstance();

        save = findViewById(R.id.buttonSave);
        //save=findViewById(R.id.testbutton);

        radioCase=findViewById(R.id.radioGroup4);
        symptoms=findViewById(R.id.pConsultSymptoms);
        diagnosis=findViewById(R.id.pConsultDiagnosis);
        patientid=findViewById(R.id.pConsultID);
        doctorid=findViewById(R.id.dConsultID);
        date=findViewById(R.id.editTextTextPersonName10);

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //submit.setOnClickListener(new View.OnClickListener() { //when you click the submit button it adds to the patients data in the database
                //  @Override
                //public void onClick(View v) {

                int selected_case = radioCase.getCheckedRadioButtonId();
               case_button = findViewById(selected_case);

                if (symptoms.getText().toString().equals("")) {
                    symptoms.setError("Symptoms are empty");
                    return;
                } else if (diagnosis.getText().toString().equals("")) {
                    diagnosis.setError("Diagnosis is empty");
                    return;
                }
                else {

                    Bundle extras = getIntent().getExtras();

                    final String psymptoms = symptoms.getText().toString();
                    final String pdiagnosis = diagnosis.getText().toString();
                    final String ppatientid = patientid.getText().toString();
                    final String pdoctorid = doctorid.getText().toString();
                    final String pdate = date.getText().toString();
                    final String pcase = getCase(v);

                    Consultation consultation = new Consultation(psymptoms, pdiagnosis, pcase,pdate,ppatientid,pdoctorid);

                    database.collection("consultation-data") // data gets added to a collection called patient-data
                            .add(consultation)
                            // Add a success listener so we can be notified if the operation was successfuly.

                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    // If we are here, the app successfully connected to Firestore and added a new entry
                                    makeText(DoctorConsultForm.this, "Data successfully added", LENGTH_LONG).show();
                                    Intent start = new Intent(DoctorConsultForm.this, DHomePage.class);
                                    startActivity(start);
                                }
                            })
                            // Add a failure listener so we can be notified if something does wrong
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // If we are here, the entry could not be added for some reason (e.g no internet connection)
                                    makeText(DoctorConsultForm.this, "Data was unable added", LENGTH_LONG).show();
                                }
                            });
                }

            }
        });

    }

    public String getCase (View v){            ////function to help convert radiogroup selections to string, this allows us to store it as a string in the DB

        int radioID = radioCase.getCheckedRadioButtonId();

        RadioButton singleButton = (RadioButton) findViewById(radioID);
        String out = singleButton.getText().toString();
        return out;

    }
}