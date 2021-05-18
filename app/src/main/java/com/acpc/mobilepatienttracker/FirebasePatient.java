package com.acpc.mobilepatienttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class FirebasePatient
{

    private FirebaseAuth mAuth;
    private FirebaseFirestore database;
    private User user;
    private Patient patient;
    private String docName;
    private String docName_2;
    private String password;
    private Context context;

    public interface FirebaseCallback
    {
        void onResponse(Patient patient);
    }

    public FirebasePatient(User user, String docName, String password, Context context)
    {
        this.user = user;
        this.docName = docName;
        this.password = password;
        this.context = context;
    }

    public FirebasePatient(Patient patient, String docName, Context context)
    {
        this.patient = patient;
        this.docName = docName;
        this.context = context;
    }

    public void patientRealtimeReg()
    {
        if(password.equals("___NULL_DEV___")) {
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference(docName)

                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(context, "You have successfully been registered as a patient.", Toast.LENGTH_LONG).show();

                                        Intent start = new Intent(context, PatientForm.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("PID", user.id);
                                        bundle.putString("EMAIL", user.email);
                                        start.putExtras(bundle);
                                        context.startActivity(start);
                                    }
                                    else{
                                        Toast.makeText(context, "Patient Registration Unsuccessful, Try Again", Toast.LENGTH_LONG).show();
                                        Log.d("PATIENT", task.getException().getMessage());

                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(context, "Patient Registration Unsuccessful, Try Again", Toast.LENGTH_LONG).show();
                            Log.d("PATIENT", task.getException().getMessage());
                        }
                    }
                });
    }

    public void patientFirestoreReg()
    {
        if(patient.fname.equals("___NULL_DEV___"))
        {
            return;
        }
        database = FirebaseFirestore.getInstance();
        database.collection(docName) // data gets added to a collection called patient-data
                .add(patient)
                // Add a success listener so we can be notified if the operation was successfuly.

                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // If we are here, the app successfully connected to Firestore and added a new entry
                        makeText(context, "Data successfully added", LENGTH_LONG).show();
                        Intent start = new Intent(context, PatientFragActivity.class);
                        context.startActivity(start);
                    }
                })
                // Add a failure listener so we can be notified if something does wrong
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If we are here, the entry could not be added for some reason (e.g no internet connection)
                        makeText(context, "Data was unable added", LENGTH_LONG).show();
                    }
                });
    }

    public void getUserData()
    {
        final FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(docName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        getPatientData(ID);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPatientData(String query)
    {



    }
}
