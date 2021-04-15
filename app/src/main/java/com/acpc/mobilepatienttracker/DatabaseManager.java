package com.acpc.mobilepatienttracker;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseManager {

    public static String TAG = "DATABASE MANAGER";

    public DatabaseManager()
    {

    }

    public static Boolean RegisterNewDoctor(Doctor doctor)
    {
        final Boolean[] IsSuccessful = {false};

        FirebaseFirestore database= FirebaseFirestore.getInstance();

        database.collection("doctor-data")
                .add(doctor)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        IsSuccessful[0] = true;
                        Log.d(TAG, "SUCCESS: Added registered new doctor with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "FAILURE: Failed to register new doctor", e);
                    }
                });

        return IsSuccessful[0];
    }

    public static Boolean RegisterPatient(String name, int ID)
    {
        // TODO...
        return false;
    }

    public static Boolean IsSignedIn()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            return true;
        }

        return false;
    }

    public static String CurrentUserID()
    {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static Boolean IsRegistered()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {

        }

        return false;
    }
}
