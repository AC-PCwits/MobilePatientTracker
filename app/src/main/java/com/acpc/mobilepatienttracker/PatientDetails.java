package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private Patient mPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.p_details);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.p_home:
                        startActivity(new Intent(getApplicationContext()
                                ,PHomePage.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.p_details:
                        return true;
                    case R.id.bookings:
                        startActivity(new Intent(getApplicationContext()
                                , PatientBookings.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.booking_history:
                        startActivity(new Intent(getApplicationContext()
                                , PatientBookingHistory.class));
                        overridePendingTransition(0 , 0);
                        return true;
                }
                return false;
            }
        });

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

        //Patient details are pulled from databse and displayed using this method
        getUserData();

    }

    public void getUserData()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            String str = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail()))
                    {
                        str = dataSnapshot.child("id").getValue().toString();
                    }
                }

                getPatientData(str);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPatientData(final String id)
    {
        database.collection("patient-data").whereEqualTo("idno", id)
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

                    for(Patient patient1 : patient)
                    {
                        if(patient1.idno.equals(id))
                        {
                            mPatient = patient1;
                        }
                    }

                    if( mPatient != null){

                        displayPatientData(mPatient);

                    }


                }
            }
        });
    }

    public void displayPatientData(Patient patient)
    {
        //Patient Details are set here
        name = patient.fname + " " + patient.fsurname;
        id = patient.idno;
        cellno = patient.cellno;
        nationality = patient.nationality;
        gender = patient.gender;
        address = patient.address;
        ename = patient.ename;
        econtact = patient.econtact;
        race = patient.race;
        mstatus = patient.mstatus;
        illnesses = patient.cissues;
        medaid = patient.medaid;
        allergies = patient.allergies;

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

        //UI form text is set here
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