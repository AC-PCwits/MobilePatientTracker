package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.util.Log;

import java.util.Random;

import java.util.ArrayList;


public class PatientConsultationHistory extends Fragment {


    private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout
    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */

    //variables

    private ConsultationHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView testView;
    private ArrayList<Appointment> mAppointmentList;
    private ArrayList<Doctor> d;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientConsultationHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientConsultationHistory newInstance(String param1, String param2) {
        PatientConsultationHistory fragment = new PatientConsultationHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_patient_consultation_history, container, false);

        testView = (TextView) rootView.findViewById(R.id.testView);

        mAppointmentList = new ArrayList<>();
        d = new ArrayList<>();



       // getBookingData();
        //To populate the list with dummy data use the below function:
          //buildExampleList();
        getUserData(rootView);
        getPendingData(rootView);
        getPastBookings(rootView);

        MovePastBookings(); //moving expired bookings to consultation history

        return rootView;



//        BottomNavigationView bottomNavigationView = findViewById(R.id.d_nav_bar);
//
//        bottomNavigationView.setSelectedItemId(R.id.d_home);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//
//                    case R.id.d_home:
//                        return true;
//                    case R.id.d_details:
//                        startActivity(new Intent(getApplicationContext()
//                                ,DoctorDetails.class));
//                        overridePendingTransition(0 , 0);
//                        return true;
//                    case R.id.patient_list:
//                        startActivity(new Intent(getApplicationContext()
//                                ,DoctorPatientList.class));
//                        overridePendingTransition(0 , 0);
//                        return true;
//                    case R.id.pending_bookings:
//                        startActivity(new Intent(getApplicationContext()
//                                ,PendingBookings.class));
//                        overridePendingTransition(0 , 0);
//                        return true;
//
//                }
//
//                return false;
//            }
//        });

    }

    //// method to move old bookings from acc-rej-data collection to booking-data-history collection
    public void MovePastBookings() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        Log.d("ACC-REJ METHOD", "Running method...");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        Log.d("ACC-REJ METHOD", "Got ID");

                        database.collection("acc-rej-data").whereEqualTo("id", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    if (task.getResult().isEmpty())
                                    {
                                        Log.d("ACC-REJ METHOD", "Snapshot was empty :(");
                                    }

                                    for (QueryDocumentSnapshot doc : task.getResult()) {

                                        final DocumentReference reference = doc.getReference();

                                        AccOrRej accRej = doc.toObject(AccOrRej.class);

                                        Log.d("ACC-REJ METHOD", "AccOrRej data:" + accRej.pname + ", " +  accRej.id + ", " + accRej.bookingdate);

                                        String date = accRej.bookingdate.replace('/', '-');

                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                        try {
                                            Date bookingDate = format.parse(date);
                                            Date today = new Date();
                                            if (today.equals(bookingDate) || today.after(bookingDate))
                                            {
                                                database.collection("booking-history-data") // specify the collection name here
                                                        .add(accRej)
                                                        // Add a success listener so we can be notified if the operation was successfuly.
                                                        // i think success/failure listeners are optional, but if you don't use them you won't know if entry was actually added
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                // If we are here, the app successfully connected to Firestore and added a new entry

                                                                database.document(reference.getPath())
                                                                        .delete()
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                Log.d("ACC-REJ DELETE", "SUCCESS: Deleted document.");

                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Log.w("ACC-REJ DELETE", "ERROR: Could not delete document. Here is what went wrong: \n", e);

                                                                            }
                                                                        });

                                                                Log.d("ACC-REJ ADD", "SUCCESS: Added new document with ID: " + documentReference.getId());
                                                            }
                                                        })
                                                        // Add a failure listener so we can be notified if something does wrong
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                // If we are here, the entry could not be added for some reason (e.g no internet connection)
                                                                Log.w("ACC-REJ ADD", "ERROR: Failed to add document", e);
                                                            }
                                                        });
                                            }
                                            else
                                            {
                                                Log.d("ACC-REJ DATE CONDITION", "Date was not old");
                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                else
                                {
                                    Log.w("ACC-REJ ADD", "ERROR: AccOrRej query failed :(", task.getException());
                                }
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("ACC-REJ GET", "ERROR: Could not get AccOrRej objects of patient: \n", e);
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



  /*  public void buildExampleList()
    {
        mAppointmentList.add(new Appointment("Helen Joseph","12/05/2021","13:00"));
        mAppointmentList.add(new Appointment("Mark James","16/05/2021","14:00"));
        mAppointmentList.add(new Appointment("Helen Joseph","19/05/2021","13:00"));
        mAppointmentList.add(new Appointment("Krithi Pillay","25/05/2021","12:00"));

    } // end of build example */

    public void getUserData(final View view) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();


                        database.collection("acc-rej-data").whereEqualTo("id", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {

                                    ArrayList<AccOrRej> ar1 = new ArrayList<>();

                                    for(QueryDocumentSnapshot doc : task.getResult())
                                    {
                                        ar1.add(doc.toObject(AccOrRej.class));

                                    }



                                    for(final AccOrRej ar2: ar1)
                                    {
                                        database.collection("doctor-data").whereEqualTo("p_no", ar2.doc_id)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful())
                                                {
                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                                        Doctor doctor = doc.toObject(Doctor.class);
                                                        mAppointmentList.add(new Appointment(ar2.pname, ar2.id, ar2.bookingdate, ar2.time, ar2.doc_id, doctor.lname, ar2.accOrRej));
                                                        Collections.sort(mAppointmentList);
                                                        buildRecyclerView(view);
                                                    }
                                                }
                                            }
                                        });
                                    }

                                  //  buildRecyclerView(view);


                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPendingData(final View view){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();


                        database.collection("pending-booking-data").whereEqualTo("id", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {

                                    ArrayList<Bookings> ar1 = new ArrayList<>();

                                    for(QueryDocumentSnapshot doc : task.getResult())
                                    {
                                        ar1.add(doc.toObject(Bookings.class));
                                    }

                                    for(final Bookings ar2: ar1)
                                    {  // String docName= "Eric";



                                        database.collection("doctor-data").whereEqualTo("p_no", ar2.doc_id)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful())
                                                {
                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                                        Doctor doctor = doc.toObject(Doctor.class);
                                                        mAppointmentList.add(new Appointment(ar2.pname, ar2.id, ar2.bookingdate, ar2.time, ar2.doc_id, doctor.lname, "Pending"));
                                                        Collections.sort(mAppointmentList);
                                                        buildRecyclerView(view);
                                                    }
                                                }

                                            }
                                        });

                                      //  mAppointmentList.add(new Appointment(ar2.pname, ar2.id, ar2.bookingdate, ar2.time, ar2.doc_id, docName, "Pending"));
                                        //  Toast.makeText(getContext(), "Added", Toast.LENGTH_LONG).show();
                                    }

                                 //   buildRecyclerView(view);


                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPastBookings(final View view){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();


                        database.collection("booking-history-data").whereEqualTo("id", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    ArrayList<AccOrRej> ar1 = new ArrayList<>();

                                    for(QueryDocumentSnapshot doc : task.getResult())
                                    {
                                        ar1.add(doc.toObject(AccOrRej.class));
                                    }

                                    for(final AccOrRej ar2: ar1)
                                    {  // String docName= "Eric";
                                        database.collection("doctor-data").whereEqualTo("p_no", ar2.doc_id)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful())
                                                {
                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                                        Doctor doctor = doc.toObject(Doctor.class);
                                                        mAppointmentList.add(new Appointment(ar2.pname, ar2.id, ar2.bookingdate, ar2.time, ar2.doc_id, doctor.lname, "Past"));
                                                        Collections.sort(mAppointmentList);
                                                        buildRecyclerView(view);
                                                    }
                                                }
                                            }
                                        });

                                        //  mAppointmentList.add(new Appointment(ar2.pname, ar2.id, ar2.bookingdate, ar2.time, ar2.doc_id, docName, "Pending"));
                                        //  Toast.makeText(getContext(), "Added", Toast.LENGTH_LONG).show();
                                    }

                                    //   buildRecyclerView(view);


                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void buildRecyclerView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        /*
        This makes sure that the recycler view will not change size no matter how many items are in the list, which
        also will increase the performance of the app
        */
        mRecyclerView.setHasFixedSize(true);
        //This sets the layout the user will view
        mLayoutManager = new LinearLayoutManager(getContext());
        //This line is where information about the patient will be parsed to create the list
        mAdapter = new ConsultationHistoryAdapter(mAppointmentList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //This function will allow click events to be referenced to the interface in the adapter class
        mAdapter.setOnItemClickListener(new ConsultationHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                changeItem(position, "Clicked");
                Intent intent = new Intent(getContext(), ConsultationHistoryDetailed.class);
                Bundle bundle = new Bundle();

                if (!mAppointmentList.get(position).status.equals("Rejected"))
                {
                    bundle.putString("doc_name", mAppointmentList.get(position).docName);
                    bundle.putString("date", mAppointmentList.get(position).bookingdate);
                    bundle.putString("time", mAppointmentList.get(position).time);
                    bundle.putString("status", mAppointmentList.get(position).status);
                    bundle.putString("doc_id", mAppointmentList.get(position).doc_id);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

}
