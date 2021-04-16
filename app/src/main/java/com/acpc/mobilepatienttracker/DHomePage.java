package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DHomePage extends AppCompatActivity
{
    private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout
    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */
    private PatientListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager; //This will allow single items in our list to alligned correctly

    private ArrayList<Patient> mPatientList;

    private Button logoutBut;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = database.collection("doctor-data").document();
    private Doctor doc = new Doctor();
    private TextView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_home_page);

        testView = (TextView) findViewById(R.id.testView);
        logoutBut = (Button) findViewById(R.id.logoutButton);

        mPatientList = new ArrayList<>();
        getDocData();
        //To populate the list with actual data use the below function:
        //buildPatientList()
//        buildExampleList();
        buildRecyclerView();

        logoutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutActivity();
            }
        });

    }


    public void getDocData()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


//        final String[] str = {""};
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
//        reference.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren())
//                {
//                    if(dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail()))
//                    {
////                            str[0] = dataSnapshot.child("IDnum").getValue().toString();
//                        testView.setText(dataSnapshot.child("IDnum").getValue().toString());
//                    }
//                }
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

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

                    String s = "";

                    for(Doctor doctor2 : doctor1)
                    {
                        if(doctor2.email.equals(user.getEmail()))
                        {
                            doc = doctor2;
                        }

                    }

                    if(doc.patient_ID == null)
                    { return;}
                    else
                    {
                        buildPatientList(doc.patient_ID);
                    }
                }
            }
        });
    }

    //This function will populate the patient list from the database
    public void buildPatientList(final ArrayList<String> pIDs)
    {
        database.collection("patient-data")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Patient> patients = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        patients.add(doc.toObject(Patient.class));
                    }

                    String s = "";

                    for(Patient patient : patients)
                    {
//                        s = s + patient.fname + " " + patient.fsurname + " : " + patient.idno + "\n";
                        for(String pID : pIDs)
                        {
                            if(pID.equals(patient.idno))
                            {
                                mPatientList.add(patient);
                                s = s + patient.fname + " " + patient.fsurname + " : " + patient.idno + "\n";
                            }
                        }
                    }
//
                    testView.setText(s);
////                    testView.setText("Successful but list was empty");


                }
                else
                {
                    testView.setText("Error: could not get documents from query");
                }
            }
        });
    }

    public void buildExampleList()
    {
        ArrayList<String> illness = new ArrayList<>();
        illness.add("TB");
        mPatientList.add(new Patient("John", "Smith", "1001", "0600606600", "SA", "Male"
                            ,"1234 street", "Emily Dow", "1110100001", "Caucasian", "Single", illness, "Yes", "None"));
        illness.remove(0);
        illness.add("Diabetes");
        mPatientList.add(new Patient("Emily", "Smith", "1002", "0600606900", "SA", "Female"
                ,"1334 street", "Emily Dow", "1110100001", "Coloured", "Married", illness, "Yes", "None"));
        mPatientList.add(new Patient("Emile", "Jonah", "1003", "0303030253", "SA", "Male"
                ,"1334 Str", "Bob Newman", "7878789226", "Other", "Married", illness, "Yes", "None"));
        illness.add("HIV/AIDS");
        mPatientList.add(new Patient("Dow", "Jones", "1004", "7856452673", "SA", "Male"
                ,"13 Av Blueberry Hill", "Joe Willock", "5264831346", "Indian", "Other", illness, "Yes", "Sulfates"));
        mPatientList.add(new Patient("Deena", "Schmidt", "1005", "6542156482", "SA", "Female"
                ,"1 Victory Lane", "Emile Qura", "6248561279", "Black", "Married", illness, "Yes", "Peanuts"));
    }

    //This function creates the recylerview object
    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
        /*
        This makes sure that the recycler view will not change size no matter how many items are in the list, which
        also will increase the performance of the app
        */
        mRecyclerView.setHasFixedSize(true);
        //This sets the layout the user will view
        mLayoutManager = new LinearLayoutManager(this);
        //This line is where information about the patient will be parsed to create the list
        mAdapter = new PatientListAdapter(mPatientList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //This function will allow click events to be referenced to the interface in the adapter class
        mAdapter.setOnItemClickListener(new PatientListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
//                changeItem(position, "Clicked");
                Intent intent = new Intent(DHomePage.this, DPatientDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("PATIENT_NAME", mPatientList.get(position).fname + " " +
                        mPatientList.get(position).fsurname);
                bundle.putString("PATIENT_ID", mPatientList.get(position).idno);
                bundle.putString("PATIENT_CELL", mPatientList.get(position).cellno);
                bundle.putString("PATIENT_NAT", mPatientList.get(position).nationality);
                bundle.putString("PATIENT_GENDER", mPatientList.get(position).gender);
                bundle.putString("PATIENT_ADDRESS", mPatientList.get(position).address);
                bundle.putString("PATIENT_ENAME", mPatientList.get(position).ename);
                bundle.putString("PATIENT_ECONT", mPatientList.get(position).econtact);
                bundle.putString("PATIENT_RACE", mPatientList.get(position).race);
                bundle.putString("PATIENT_MARRIED", mPatientList.get(position).mstatus);
                bundle.putStringArrayList("PATIENT_ILLNESS", mPatientList.get(position).cissues);
                bundle.putString("PATIENT_MEDAID", mPatientList.get(position).medaid);
                bundle.putString("PATIENT_ALLERGIES", mPatientList.get(position).allergies);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void logoutActivity()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(DHomePage.this, DoctorOrPatient.class);
        startActivity(intent);
        finish();
    }
}