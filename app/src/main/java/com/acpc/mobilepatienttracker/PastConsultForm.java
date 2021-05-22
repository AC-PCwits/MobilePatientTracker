package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class PastConsultForm extends AppCompatActivity {


    private String Dlname,Dfname,Cell,Fname,Sname,Symptoms, Diagnosis, Patientid,Doctorid,Date;
    private TextView dlname,dfname,cell,fname,sname,symptoms, diagnosis, patientid,doctorid,date;
    private Button done  = findViewById(R.id.buttonSave);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_consult_form);


        dlname = (TextView) findViewById(R.id.dConsultLName);
        dfname = (TextView) findViewById(R.id.dConsultName);
        cell = (TextView) findViewById(R.id.pConsultCell);
        fname = (TextView) findViewById(R.id.pConsultName);
        sname = (TextView) findViewById(R.id.pConsultLName);
        symptoms=(TextView) findViewById(R.id.pConsultSymptoms);
        diagnosis=(TextView) findViewById(R.id.pConsultDiagnosis);
        patientid=(TextView) findViewById(R.id.pConsultID);
        doctorid=(TextView) findViewById(R.id.dConsultID);
        date=(TextView) findViewById(R.id.editTextTextPersonName10);


        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            Dlname = intent.getExtras().getString("dlname");
            Dfname = intent.getExtras().getString("dfname");
            Cell = intent.getExtras().getString("cell");
            Fname = intent.getExtras().getString("fname");
            Sname = intent.getExtras().getString("sname");
            Symptoms = intent.getExtras().getString("symptoms");
            Diagnosis = intent.getExtras().getString("diagnosis");
            Patientid = intent.getExtras().getString(" patientid");
            Doctorid = intent.getExtras().getString("doctorid");
            Date = intent.getExtras().getString("date");

        }



        dlname.setText(Dlname);
        dfname.setText(Dfname);
        cell.setText(Cell);
        fname.setText(Fname);
        sname.setText(Sname);
        symptoms.setText(Symptoms);
        diagnosis.setText(Diagnosis);
        patientid.setText(Patientid);
        doctorid.setText(Doctorid);
        date.setText(Date);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(PastConsultForm.this, PastConsults.class);
                startActivity(start);
            }
        });



    }
}