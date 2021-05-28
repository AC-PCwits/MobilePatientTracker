package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class DoctorConsultForm extends AppCompatActivity {
    private RadioGroup radioCase;
    private RadioButton case_button;
    private EditText symptoms, diagnosis, patientid,doctorid,date,dname,dsurname,pname,pcell,psurname,dtype;
    private Button save;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private Doctor doc = new Doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_consult_form);

        //populate date field
        date=findViewById(R.id.editTextTextPersonName10);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
        Date currentdate = new Date();
        date.setText(formatter.format(currentdate));

        save = findViewById(R.id.buttonSave);

        radioCase=findViewById(R.id.radioGroup4);
        symptoms=findViewById(R.id.pConsultSymptoms);
        diagnosis=findViewById(R.id.pConsultDiagnosis);

        patientid=findViewById(R.id.pConsultID);
        pname=findViewById(R.id.pConsultName);
        pcell=findViewById(R.id.pConsultCell);
        psurname=findViewById(R.id.pConsultLName);

        doctorid=findViewById(R.id.dConsultID);
        dname=findViewById(R.id.dConsultName);
        dsurname=findViewById(R.id.dConsultLName);
        dtype=findViewById(R.id.dType);


        String[] splitter= (DPatientDetails.clickedname).split(" ", 2);
        pname.setText(splitter[0]);
        psurname.setText(splitter[1]);
      
        pcell.setText((DPatientDetails.clickedcell));
        patientid.setText(DPatientDetails.clickedID);

        getDocDet();

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

                    UpdateLastVisited(ppatientid);

                    Consultation consultation = new Consultation(psymptoms, pdiagnosis, pcase,pdate,ppatientid,pdoctorid);

                    database.collection("consultation-data") // data gets added to a collection called patient-data
                            .add(consultation)
                            // Add a success listener so we can be notified if the operation was successfuly.

                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    // If we are here, the app successfully connected to Firestore and added a new entry
                                    makeText(DoctorConsultForm.this, "Data successfully added", LENGTH_LONG).show();
                                    Intent start = new Intent(DoctorConsultForm.this, DoctorFragActivity.class);
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

    public void getDocDet()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database.collection("doctor-data").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Doctor> doctor1 = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        doctor1.add(doc.toObject(Doctor.class));
                    }

                    for(Doctor doctor2 : doctor1)
                    {
                        if(doctor2.email.equals(user.getEmail()))
                        {
                            doc = doctor2;
                        }

                    }
                    doctorid.setText(doc.p_no);
                    dname.setText(doc.fname);
                    dsurname.setText(doc.lname);
                    dtype.setText(doc.doc_type);
                }
            }
        });
    }

    public void UpdateLastVisited(final String ppatientid) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date currentdate = new Date();
        final String lastVisited = formatter.format(currentdate);

        database.collection("patient-data").whereEqualTo("idno",ppatientid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //   ArrayList<String> patIDs = new ArrayList<>();

                            String documentID = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentID = document.getId();
                            }
                            if(documentID != ""){
                                final DocumentReference Ref = database.collection("patient-data").document(documentID);

                                Ref.update("lastVisited", lastVisited )
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("PD", "SUCCESS: Updated field: ");
                                                //Toast.makeText(DBookingDetails.this, "Could not save: failed to update details", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // failed to update the given field for some reason
                                                Log.w("PD", "ERROR: Could not update field: ", e);
                                                // Toast.makeText(DBookingDetails.this, "Could not save: failed to update details", Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }

                            //  }

                        }

                    }
                });

    }
}