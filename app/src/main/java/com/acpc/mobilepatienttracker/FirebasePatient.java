package com.acpc.mobilepatienttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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
    private PatientField field;
    private String query;
    private ArrayList<PatientDetails.DetailView> allDetails;
    private Patient activeUser;
    private Object newValue;
    private FloatingActionButton save;

    public FirebasePatient(Context context, PatientField field, String query,
                           ArrayList<PatientDetails.DetailView> allDetails, Patient activeUser,
                           Object newValue, FloatingActionButton save) {
        this.context = context;
        this.field = field;
        this.query = query;
        this.allDetails = allDetails;
        this.activeUser = activeUser;
        this.newValue = newValue;
        this.save = save;
    }

    public interface FirebaseCallback
    {
        void onResponse(ArrayList<Patient> patients);
    }

    public FirebasePatient(String query)
    {
        this.query = query;
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

    public FirebasePatient() {}

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

    public void getUserData(final FirebaseCallback callback)
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        database = FirebaseFirestore.getInstance();
                        database.collection("patient-data").whereEqualTo("idno", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<Patient> patients = new ArrayList<>();

                                    for (QueryDocumentSnapshot doc : task.getResult())
                                    {
                                        patients.add(doc.toObject(Patient.class));
                                    }
                                    callback.onResponse(patients);
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

    public void getUserData(final FirebaseCallback callback, String ID)
    {
                        database = FirebaseFirestore.getInstance();
                        database.collection("patient-data").whereEqualTo("idno", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<Patient> patients = new ArrayList<>();

                                    for (QueryDocumentSnapshot doc : task.getResult())
                                    {
                                        patients.add(doc.toObject(Patient.class));
                                    }
                                    callback.onResponse(patients);
                                }
                            }
                        });
    }

    public void UpdateField() {
        final String fieldName = Patient.GetFieldName(field);

        database = FirebaseFirestore.getInstance();
        database.collection("patient-data")
                .whereEqualTo("idno", query)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String documentID = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentID = document.getId();
                            }
                            final String finalDocumentID = documentID;

                            if (documentID != "") {
                                DocumentReference patient = database.collection("patient-data").document(documentID);

                                for (PatientDetails.DetailView view : allDetails) {
                                    if (view.content.getText().toString() != activeUser.GetFieldValue(view.type))

                                        patient.update(fieldName, newValue)

                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("PD", "SUCCESS: Updated field: " + fieldName + " for document ID: " + finalDocumentID);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // failed to update the given field for some reason
                                                        Log.w("PD", "ERROR: Could not update field: " + fieldName + "for document ID: " + finalDocumentID + ": ", e);
                                                        Toast.makeText(context, "Could not save: failed to update details", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                }
                                Toast.makeText(context, "Successfully saved details :)", Toast.LENGTH_LONG).show();
                                save.setEnabled(false);
                                save.setVisibility(View.INVISIBLE);


                                for (PatientDetails.DetailView view : allDetails) {
                                    if (view.content.isFocused()) {
                                        view.content.clearFocus();
                                        view.content.setBackgroundColor(Color.TRANSPARENT);
                                    }
                                    view.content.setEnabled(false);
                                    view.originalText = view.content.getText().toString();
                                }
                            } else {
                                // do document was found with the given field
                                Log.w("PD", "QUERY ERROR: No document found with ID number: " + query);
                                Toast.makeText(context, "Could not save: document not found with that ID number???", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            // query did not complete
                            Log.w("PD", "QUERY ERROR: query did not complete: " + task.getException().getMessage());
                            Toast.makeText(context, "Could not save: query do not complete", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
