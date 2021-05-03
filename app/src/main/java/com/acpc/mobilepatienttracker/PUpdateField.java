package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PUpdateField extends AppCompatActivity {

    TextView pd_newfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_update_field);

        pd_newfield = findViewById(R.id.pd_newfield);

        Patient patient = null;
        PatientField field = null;

        Bundle b = getIntent().getExtras();
        if(b == null)
        {
            return;
        }

        patient = (Patient) b.getSerializable("patient");
        field = (PatientField) b.getSerializable("field");

        switch(field)
        {
            case FIRST_NAME:
                pd_newfield.setText(patient.fname);
                break;
            case LAST_NAME:
                pd_newfield.setText(patient.fsurname);
                break;
            case ID:
                pd_newfield.setText(patient.idno);
                break;
            case CELLPHONE:
                pd_newfield.setText(patient.cellno);
                break;
            case NATIONALITY:
                pd_newfield.setText(patient.nationality);
                break;
            case GENDER:
                pd_newfield.setText(patient.gender);
                break;
            case RACE:
                pd_newfield.setText(patient.race);
                break;
            case ADDRESS:
                pd_newfield.setText(patient.address);
                break;
            case MARITAL_STATUS:
                pd_newfield.setText(patient.mstatus);
                break;
            case ILLNESSES:
                pd_newfield.setText(patient.cissues.toString());
                break;
            case ALLERGIES:
                pd_newfield.setText(patient.allergies);
                break;
            case MEDICAL_AID:
                pd_newfield.setText(patient.medaid);
                break;
            case ECONTACT_NAME:
                pd_newfield.setText(patient.ename);
                break;
            case ECONTACT_CELLPHONE:
                pd_newfield.setText(patient.econtact);
                break;
        }
    }
}