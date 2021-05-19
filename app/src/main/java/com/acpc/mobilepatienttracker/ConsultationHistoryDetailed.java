package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ConsultationHistoryDetailed extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private TextView txtDocName;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtStatus;
    private TextView txtDocExp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_history_detailed);

        final Intent intent = getIntent();

        database.collection("doctor-data").whereEqualTo("p_no", intent.getExtras().getString("doc_id"))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    Log.d("CONSULTATION DETAILS:", "doctor count: " + task.getResult().size());
                    Log.d("CONSULTATION DETAILS:", "doctor ID: " + intent.getExtras().getString("doc_id"));

                    for (QueryDocumentSnapshot d : task.getResult()) {
                        Doctor doctor = d.toObject(Doctor.class);

                        txtDocName = findViewById(R.id.txtDocName);
                        txtDate = findViewById(R.id.txtDate);
                        txtTime = findViewById(R.id.txtTime);
                        txtStatus = findViewById(R.id.statusText);
                        txtDocExp = findViewById(R.id.txtDocExp);

                        String docName = intent.getExtras().getString("doc_name");
                        String date = intent.getExtras().getString("date");
                        String time = intent.getExtras().getString("time");
                        String status = intent.getExtras().getString("status");

                        txtDocName.setText(docName);
                        txtDate.setText(date);
                        txtTime.setText(time);
                        txtStatus.setText(status);
                        txtDocExp.setText(doctor.p_length);

                        if (status.equals("Pending"))
                        {
                            txtStatus.setBackground(ContextCompat.getDrawable(ConsultationHistoryDetailed.this, R.drawable.rounded_corner_pending));
                            txtStatus.setTextColor(Color.parseColor("#C3C3C3"));
                        }
                        if (status.equals("Accepted"))
                        {
                            txtStatus.setBackground(ContextCompat.getDrawable(ConsultationHistoryDetailed.this, R.drawable.rounded_corner_accepted));
                            txtStatus.setTextColor(Color.parseColor("#50C878"));
                        }
                        if (status.equals("Past"))
                        {
                            txtStatus.setBackground(ContextCompat.getDrawable(ConsultationHistoryDetailed.this, R.drawable.rounded_corner_past));
                            txtStatus.setTextColor(Color.parseColor("#0E20C5"));
                        }
                        return;
                    }
                } else {
                    Log.w("CONSULTATION DETAILS: ", "GET DOCTOR FAILED: ", task.getException());
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("CONSULTATION DETAILS: ", "GET DOCTOR FAILED: ", e);
                    }
                });
    }
}