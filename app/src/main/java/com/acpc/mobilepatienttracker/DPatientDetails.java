package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DPatientDetails extends AppCompatActivity {

    private String name;
    private String id;
    private ArrayList<String> illnesses;

    private TextView nameText;
    private TextView idText;
    private TextView illText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_patient_details);

        //Instantiation of View Components
        nameText = (TextView)findViewById(R.id.nameText);
        idText = (TextView)findViewById(R.id.idText);
        illText = (TextView)findViewById(R.id.illnessText);

        //Patient details from the list are sent with the intent used to send the user to this screen
        Intent intent = getIntent();

        //Patient Details are set here
        if(intent.getExtras() != null) {
            name = intent.getExtras().getString("PATIENT_NAME");
            id = String.valueOf(intent.getExtras().getInt("PATIENT_ID"));
            illnesses = intent.getExtras().getStringArrayList("PATIENT_ILLNESS");
        }

        String ill = "";

        for(String text: illnesses)
        {
            if(ill.equals(""))
            {
                ill = text;
            }else
            {
                ill = ill + " , " + text;
            }
        }

        nameText.setText(name);
        idText.setText(id);
        illText.setText(ill);

    }
}