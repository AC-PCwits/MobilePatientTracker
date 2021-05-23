package com.acpc.mobilepatienttracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collections;
import java.util.List;

public class PastConsults extends AppCompatActivity {

    public PastConsults activity;

    private RecyclerView mRecyclerView;

    private ConsultAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_consults);

        Intent intentRecieved = getIntent();
        Bundle data = intentRecieved.getExtras();

        GetPastConsults(data.getString("PATIENT_ID"));
    }

    public void GetPastConsults(final String patientID)
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database.collection("doctor-data").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    Doctor doctor = task.getResult().toObjects(Doctor.class).get(0);

                    database.collection("booking-history-data").whereEqualTo("doc_id", doctor.p_no).whereEqualTo("id", patientID)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                ArrayList<AccOrRej> pastConsults = (ArrayList<AccOrRej>) task.getResult().toObjects(AccOrRej.class);

                                Log.d("PAST CONSULTS", "Past consults size: " + pastConsults.size());

                                buildRecyclerView(pastConsults);
                            }
                            else
                            {
                                Log.w("PAST CONSULTS", "Could not get past consults: ", task.getException());
                            }
                        }
                    });

                }
                else
                {
                    Log.w("PAST CONSULTS", "Could not get doctor info: ", task.getException());
                }
            }
        });
    }

    public void buildRecyclerView(final ArrayList<AccOrRej> list)
    {
        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(PastConsults.this);
        mAdapter = new ConsultAdapter(list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ConsultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent start = new Intent( PastConsults.this, PastConsultDetailed.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

                Bundle bundle = new Bundle();

                bundle.putString("PATIENT_ID", list.get(position).id);
                bundle.putString("PATIENT_NAME", list.get(position).pname);
                bundle.putString("DATE", list.get(position).bookingdate);
                bundle.putString("TIME", list.get(position).time);

                start.putExtras(bundle);
                startActivity(start);
            }
        });
    }
}