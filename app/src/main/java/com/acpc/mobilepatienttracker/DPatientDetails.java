package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DPatientDetails extends AppCompatActivity {

    private String name;
    private String id;
    private String cellno;
    private String nationality;
    private String gender;
    private String address;
    private String ename;
    private String econtact;
    private String race;
    private String mstatus;
    private String medaid;
    private String allergies;
    private ArrayList<String> illnesses;

    private TextView nameText;
    private TextView idText;
    private TextView illText;
    private TextView cellText;
    private TextView nationalityText;
    private TextView genderText;
    private TextView addressText;
    private TextView enameText;
    private TextView econtactText;
    private TextView raceText;
    private TextView mstatusText;
    private TextView medaidText;
    private TextView allergiesText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_patient_details);

        //Instantiation of View Components
        nameText = (TextView)findViewById(R.id.nameText);
        idText = (TextView)findViewById(R.id.idText);
        illText = (TextView)findViewById(R.id.illnessText);
        cellText = (TextView)findViewById(R.id.cellText);
        nationalityText = (TextView)findViewById(R.id.natText);
        genderText = (TextView)findViewById(R.id.genderText);
        addressText = (TextView)findViewById(R.id.addressText);
        enameText = (TextView)findViewById(R.id.enameText);
        econtactText = (TextView)findViewById(R.id.ecellTitleText);
        raceText = (TextView)findViewById(R.id.raceText);
        mstatusText = (TextView)findViewById(R.id.mstatusText);
        medaidText = (TextView)findViewById(R.id.medaidText);
        allergiesText = (TextView)findViewById(R.id.allergiesText);

        //Patient details from the list are sent with the intent used to send the user to this screen
        Intent intent = getIntent();

        //Patient Details are set here
        if(intent.getExtras() != null) {
            name = intent.getExtras().getString("PATIENT_NAME");
            id = intent.getExtras().getString("PATIENT_ID");
            cellno = intent.getExtras().getString("PATIENT_CELL");
            nationality = intent.getExtras().getString("PATIENT_NAT");
            gender = intent.getExtras().getString("PATIENT_GENDER");
            address = intent.getExtras().getString("PATIENT_ADDRESS");
            ename = intent.getExtras().getString("PATIENT_ENAME");
            econtact = intent.getExtras().getString("PATIENT_ECONT");
            race = intent.getExtras().getString("PATIENT_RACE");
            mstatus = intent.getExtras().getString("PATIENT_MARRIED");
            illnesses = intent.getExtras().getStringArrayList("PATIENT_ILLNESS");
            medaid = intent.getExtras().getString("PATIENT_MEDAID");
            allergies = intent.getExtras().getString("PATIENT_ALLERGIES");
        }

        String ill = "";

        for(String text: illnesses)
        {
            if(ill.equals(""))
            {
                ill = text;
            }else
            {
                ill = ill + "\n" + text;
            }
        }

        nameText.setText(name);
        idText.setText(id);
        cellText.setText(cellno);
        nationalityText.setText(nationality);
        genderText.setText(gender);
        addressText.setText(address);
        enameText.setText(ename);
        econtactText.setText(econtact);
        raceText.setText(race);
        mstatusText.setText(mstatus);
        illText.setText(ill);
        medaidText.setText(medaid);
        allergiesText.setText(allergies);
    }
}