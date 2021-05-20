package com.acpc.mobilepatienttracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PastConsults extends AppCompatActivity {

    public PastConsults activity;

    public void onAttach(PastConsults activity){
        this.activity =  activity;
    }

    private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout

    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */
    private ConsultAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager; //This will allow single items in our list to alligned correctly

    private ArrayList<Consultation> mConsultList;
    private ArrayList<Consult> ConsultList;
    private TextView testView;
    private Context Context;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = database.collection("doctor-data").document();
    private Doctor doc = new Doctor();



    public PastConsults() {
        // Required empty public constructor
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_consults);



        mConsultList= new ArrayList<Consultation>();
        ConsultList= new ArrayList<Consult>();

        buildExampleList();
        buildRecyclerView();

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
                        buildConsultList(doc.patient_ID,doc.ID);
                    }
                }
            }
        });
    }
    //This function will populate the patient list from the database
    public void buildConsultList(final ArrayList<String> pIDs, final String ide)
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
                                database.collection("consultation-data")
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful())
                                        {
                                            ArrayList<Consultation> consults = new ArrayList<>();

                                            for(QueryDocumentSnapshot doc : task.getResult())
                                            {
                                                consults.add(doc.toObject(Consultation.class));
                                            }

                                            String s = "";

                                            for(Consultation consultation : consults)
                                            {

                                                if(ide.equals(consultation.pdoctorID))
                                                {
                                                    mConsultList.add(new Consultation(consultation.pcase,consultation.pdate,consultation.pdiagnosis,consultation.pdoctorID,consultation.ppatientID,consultation.psymptoms));

                                                }

                                            }

                                        }

                                    }
                                });
                            }
                        }
                    }
//



                }

            }
        });
    }

    public void buildExampleList()
    {
        ConsultList.add(new Consult("TB","8/04/2021"));
        ConsultList.add(new Consult("Corona","10/04/2021"));

    }

    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView1);
        /*
        This makes sure that the recycler view will not change size no matter how many items are in the list, which
        also will increase the performance of the app
        */
        mRecyclerView.setHasFixedSize(true);
        //This sets the layout the user will view
        mLayoutManager = new LinearLayoutManager(Context);
        //This line is where information about the patient will be parsed to create the list
        mAdapter = new ConsultAdapter(ConsultList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ConsultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {


//                changeItem(position, "Clicked");
                Intent intent = new Intent(activity, PastConsultForm.class);
                Bundle bundle = new Bundle();



                bundle.putString("dfname", "Dr Eric");
                bundle.putString("dlname", "Khan");
                bundle.putString("cell", "0789765454");
                bundle.putString("fname", "alan");
                bundle.putString("sname", "walker");
                bundle.putString("symptoms", "cough");
                bundle.putString("diagnosis", "Corona");
                bundle.putString("patientId", "8736372727");
                bundle.putString("doctorId", "883882282");
                bundle.putString("date", "10/10/2000");


                intent.putExtras(bundle);


                startActivity(intent);




            }
        });





    }
}