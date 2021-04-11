package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientDetails extends AppCompatActivity {

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
    private TextView testView;
    private TextView testView1;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        //Instantiation of View Components
        nameText = (TextView)findViewById(R.id.nameText);
        idText = (TextView)findViewById(R.id.idText);
        illText = (TextView)findViewById(R.id.illnessText);
        cellText = (TextView)findViewById(R.id.cellText);
        nationalityText = (TextView)findViewById(R.id.natText);
        genderText = (TextView)findViewById(R.id.genderText);
        addressText = (TextView)findViewById(R.id.addressText);
        enameText = (TextView)findViewById(R.id.enameText);
        econtactText = (TextView)findViewById(R.id.ecellText);
        raceText = (TextView)findViewById(R.id.raceText);
        mstatusText = (TextView)findViewById(R.id.mstatusText);
        medaidText = (TextView)findViewById(R.id.medaidText);
        allergiesText = (TextView)findViewById(R.id.allergiesText);
        testView = (TextView)findViewById(R.id.testView);
        testView1 = (TextView)findViewById(R.id.testView1);

        //Patient details from the list are sent with the intent used to send the user to this screen
//        Intent intent = getIntent();

        //Patient Details are set here
//        if(intent.getExtras() != null) {
//            name = intent.getExtras().getString("PATIENT_NAME");
//            id = intent.getExtras().getString("PATIENT_ID");
//            cellno = intent.getExtras().getString("PATIENT_CELL");
//            nationality = intent.getExtras().getString("PATIENT_NAT");
//            gender = intent.getExtras().getString("PATIENT_GENDER");
//            address = intent.getExtras().getString("PATIENT_ADDRESS");
//            ename = intent.getExtras().getString("PATIENT_ENAME");
//            econtact = intent.getExtras().getString("PATIENT_ECONT");
//            race = intent.getExtras().getString("PATIENT_RACE");
//            mstatus = intent.getExtras().getString("PATIENT_MARRIED");
//            illnesses = intent.getExtras().getStringArrayList("PATIENT_ILLNESS");
//            medaid = intent.getExtras().getString("PATIENT_MEDAID");
//            allergies = intent.getExtras().getString("PATIENT_ALLERGIES");
//        }

        String ill = "";

//        for(String text: illnesses)
//        {
//            if(ill.equals(""))
//            {
//                ill = text;
//            }else
//            {
//                ill = ill + "\n" + text;
//            }
//        }

        getUserData();
//        getPatientData();

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

    public Doctor getUserData()
    {
        database.collection("Users")//.whereEqualTo("id", "1111111111111")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<User> user = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        user.add(doc.toObject(User.class));
                    }

                    String s = "Users:\n";

                    for(User user1 : user)
                    {
//                        if(doctor1.ID.equals("1111111111111"))
//                        {
//                            doc = doctor1;
//                        }
                        s = s + user1.fname + ";" +user1.email + "\n";
                    }

                    testView.setText(s);
//                    testView.setText("Successful but list was empty");

                }
                else
                {
                    testView.setText("Error: could not get documents from query");
                }
            }
        });

        return null;
    }

    public Doctor getPatientData()
    {
        database.collection("patient-data")//.whereEqualTo("id", "1111111111111")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Patient> patient = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        patient.add(doc.toObject(Patient.class));
                    }

                    String s = "Patients:\n";

                    for(Patient patient1 : patient)
                    {
//                        if(doctor1.ID.equals("1111111111111"))
//                        {
//                            doc = doctor1;
//                        }
                        s = s + patient1.fname + " " + patient1.fsurname + "\n";
                    }

                    testView1.setText(s);
//                    testView.setText("Successful but list was empty");

                }
                else
                {
                    testView1.setText("Error: could not get documents from query");
                }
            }
        });

        return null;
    }
}