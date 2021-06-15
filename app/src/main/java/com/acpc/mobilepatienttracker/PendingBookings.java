package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PendingBookings extends Fragment {

    private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout
    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */
    private View rootView;

    private BookingsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager; //This will allow single items in our list to alligned correctly

    private ArrayList<Bookings> mBookingsList;

    private TextView noitems;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private TextView testView;

    private Doctor doc = new Doctor();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PendingBookings() {
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
    public static PendingBookings newInstance(String param1, String param2) {
        PendingBookings fragment = new PendingBookings();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.activity_pending_bookings, container, false);

        noitems = rootView.findViewById(R.id.dcl_noitems);
        noitems.setVisibility(View.INVISIBLE);

        testView = (TextView) rootView.findViewById(R.id.testView);

        mBookingsList = new ArrayList<>();
        // getDocData();
        BuildBookingsList();

        return rootView;
    }

    public void BuildBookingsList() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database.collection("doctor-data").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    Log.d("PENDING-BOOKINGS:", "pulled doctor count: " + task.getResult().size());
                    for (QueryDocumentSnapshot d : task.getResult())
                    {
                        doc = d.toObject(Doctor.class);

                        Log.d("PENDING-BOOKINGS:", "Name: " + doc.fname + " " + doc.lname);
                        Log.d("PENDING-BOOKINGS:", "ID: " + doc.ID);
                        Log.d("PENDING-BOOKINGS:", "Patient ID: " + doc.patient_ID);
                        Log.d("PENDING-BOOKINGS:", "Email: " + doc.email);
                        Log.d("PENDING-BOOKINGS:", "Date of birth: " + doc.dob);
                        Log.d("PENDING-BOOKINGS:", "Type: " + doc.doc_type);
                        Log.d("PENDING-BOOKINGS:", "P length: " + doc.p_length);
                        Log.d("PENDING-BOOKINGS:", "P no: " + doc.p_no);

                        database.collection("pending-booking-data").whereEqualTo("doc_id", doc.p_no)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    Log.d("PENDING-BOOKINGS:", "pulled bookings count: " + task.getResult().size());

                                    for (QueryDocumentSnapshot b : task.getResult())
                                    {
                                        Bookings booking = b.toObject(Bookings.class);
                                        booking.path = b.getReference().getPath();
                                        mBookingsList.add(booking);
                                    }

                                    buildRecyclerView(rootView);
                                }
                                else
                                {
                                    testView.setText("Error: get bookings was not successful: " + task.getException().getMessage());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("PENDING-BOOKINGS:", "GET BOOKINGS FAILED: ", task.getException());
                            }
                        });
                    }
                }
                else
                {
                    testView.setText("Error: get doctor was not successful: " + task.getException().getMessage());
                }
            }
        });

        if (mBookingsList.isEmpty())
        {
            noitems.setVisibility(View.VISIBLE);
            return;
        }
    }

    public void buildRecyclerView(View rootView)
    {

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        /*
        This makes sure that the recycler view will not change size no matter how many items are in the list, which
        also will increase the performance of the app
        */
        mRecyclerView.setHasFixedSize(true);
        //This sets the layout the user will view
        mLayoutManager = new LinearLayoutManager(getContext());
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
                Intent intent = new Intent(getContext(), DBookingDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("PATIENT_NAME", mBookingsList.get(position).pname);
                bundle.putString("PATIENT_ID", mBookingsList.get(position).id);
                bundle.putString("BOOKING_DATE", mBookingsList.get(position).bookingdate);
                bundle.putString("BOOKING_TIME", mBookingsList.get(position).time);
                bundle.putString("PATH", mBookingsList.get(position).path);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}