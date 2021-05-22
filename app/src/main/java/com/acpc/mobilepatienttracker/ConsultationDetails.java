package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class ConsultationDetails extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private TextView txtPFirstName;
    private TextView txtPSurname;
    private TextView txtPatientID;
    private TextView txtPcell;
    private TextView txtDFirstName;
    private TextView txtDSurname;
    private TextView txtPracticeID;
    private TextView txtpFirstName;
    private TextView txtCaseInfo;
    private TextView txtSymptoms;
    private TextView txtDiagnosis;
    private TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_details);
    }
}