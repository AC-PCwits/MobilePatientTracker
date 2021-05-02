package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PendingBookings extends AppCompatActivity {
    private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout
    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */
    private BookingsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager; //This will allow single items in our list to alligned correctly

    private ArrayList<Bookings> mBookingsList;


  //  DatabaseReference mRef; // requestRef, bookingRef;
  //  private FirebaseAuth mAuth; //using Realtime db
  //  FirebaseUser mUser;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private DocumentReference noteRef = database.collection("booking-data").document();
    private Bookings booking = new Bookings();
    private TextView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_bookings);

        testView = (TextView) findViewById(R.id.testView);

       // mAuth = FirebaseAuth.getInstance();
       // mUser = mAuth.getCurrentUser();
       // mRef = FirebaseDatabase.getInstance().getReference().child("Bookings");

        mBookingsList = new ArrayList<>();
       // getDocData();
        buildExampleList();


        //To populate the list with actual data use the below function:
        //buildPatientList()
//        buildExampleList();
        buildRecyclerView();



        BottomNavigationView bottomNavigationView = findViewById(R.id.d_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.pending_bookings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.d_home:
                        startActivity(new Intent(getApplicationContext()
                                ,DHomePage.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.d_details:
                        startActivity(new Intent(getApplicationContext()
                                ,DoctorDetails.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.patient_list:
                        startActivity(new Intent(getApplicationContext()
                                ,DoctorPatientList.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.pending_bookings:
                        return true;

                }

                return false;
            }
        });
    }



   /* public void getDocData()
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
    } */

    //This function will populate the patient list from the database
  /*  public void buildPatientList(final ArrayList<String> pIDs)
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
    }*/

    public void buildExampleList()
    {

        mBookingsList.add(new Bookings("Theo Jones", "0000000000000", "13/03/2021", "14:00", "0000000"));
        mBookingsList.add(new Bookings("Tam Jones", "0000000000000", "16/08/2021", "09:00","0000000"));
        mBookingsList.add(new Bookings("Tim Jones", "0000000000000", "13/09/2021", "08:00","0000000"));
        mBookingsList.add(new Bookings("Trin Jones", "0000000000000", "13/10/2021","16:00","0000000"));
        mBookingsList.add(new Bookings("Trip Jones", "0000000000000", "13/12/2021", "10:00","0000000"));

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
        mAdapter = new BookingsAdapter(mBookingsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //This function will allow click events to be referenced to the interface in the adapter class
        mAdapter.setOnItemClickListener(new BookingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
//                changeItem(position, "Clicked");
                Intent intent = new Intent(PendingBookings.this, DBookingDetails.class);
                Bundle bundle = new Bundle();

               bundle.putString("PATIENT_NAME", mBookingsList.get(position).pname);
               bundle.putString("PATIENT_ID", mBookingsList.get(position).id);
               bundle.putString("BOOKING_DATE", mBookingsList.get(position).bookingdate);
               bundle.putString("BOOKING_TIME", mBookingsList.get(position).time);


                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


}
