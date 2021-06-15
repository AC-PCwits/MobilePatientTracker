package com.acpc.mobilepatienttracker;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DatabaseManager {

    public static String TAG = "DATABASE MANAGER";

    private static FirebaseFirestore database;

    public interface ConsultCallback
    {
        void onResponse(Consultation consultation, Doctor doctor, Patient patient);
    }

    public static void GetPastConsult(final String patientID, final String doctorID, final String dateTime, final ConsultCallback callback)
    {
        database = FirebaseFirestore.getInstance();

        database.collection("doctor-data").whereEqualTo("p_no", doctorID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    final Doctor doctor = task.getResult().toObjects(Doctor.class).get(0);

                    database.collection("patient-data").whereEqualTo("idno", patientID)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                final Patient patient = task.getResult().toObjects(Patient.class).get(0);

                                database.collection("consultation-data").whereEqualTo("pdoctorID", doctorID).whereEqualTo("ppatientID", patientID).whereEqualTo("pdate", dateTime)
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful())
                                        {
                                            Consultation consultation = task.getResult().toObjects(Consultation.class).get(0);

                                            callback.onResponse(consultation, doctor, patient);
                                        }
                                        else
                                        {
                                            Log.w("CONSULTATION-DETAILS", "Could not get past consults: ", task.getException());
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Log.w("CONSULTATION-DETAILS", "Could not get patient info: ", task.getException());
                            }
                        }
                    });
                }
                else
                {
                    Log.w("CONSULTATION-DETAILS", "Could not get doctor info: ", task.getException());
                }
            }
        });
    }
}
