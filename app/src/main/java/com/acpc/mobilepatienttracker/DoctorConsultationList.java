package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoctorConsultationList extends AppCompatActivity {

    public DoctorConsultationList activity;

    private RecyclerView mRecyclerView;

    private ConsultationsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView noitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_consultation_list);
        setTitle("Past Consultations");
        Intent intent = getIntent();
        if (intent.getExtras() != null)
        {
            noitems = findViewById(R.id.dcl_noitems);
            noitems.setVisibility(View.INVISIBLE);

            Bundle data = intent.getExtras();

            GetConsultList(data.getString("PATIENT_ID"));
        }
    }

    public void GetConsultList(final String patientID)
    {

        if(patientID.equals("1234567891234")){

            return;
        }

        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database.collection("doctor-data").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    Doctor doctor = task.getResult().toObjects(Doctor.class).get(0);

                    database.collection("consultation-data").whereEqualTo("ppatientID", patientID)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                ArrayList<Consultation> pastConsults = (ArrayList<Consultation>) task.getResult().toObjects(Consultation.class);
                                Collections.sort(pastConsults);

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

    public void buildRecyclerView(final ArrayList<Consultation> list)
    {
        if (list.isEmpty())
        {
            noitems.setVisibility(View.VISIBLE);
            return;
        }


        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(DoctorConsultationList.this);
        mAdapter = new ConsultationsAdapter(list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ConsultationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent start = new Intent( DoctorConsultationList.this, ConsultationDetails.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

                Bundle bundle = new Bundle();

                bundle.putString("PATIENT_ID", list.get(position).ppatientID);
                bundle.putString("DOCTOR_ID", list.get(position).pdoctorID);
                bundle.putString("DATETIME", list.get(position).pdate);

                start.putExtras(bundle);
                startActivity(start);
            }
        });
    }

}