package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firestore.v1.WriteResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class
DBookingDetails extends AppCompatActivity {

    String name;
    String id;
    String date;
    String time;
    String path;
    String doc_id;
    String aOrR;
    //  String pId;

    private TextView nameT;
    private TextView idT;
    private TextView dateT;
    private TextView timeT;
    private Button accept;
    private Button reject;


    FirebaseAuth mAuth;
    final FirebaseFirestore database = FirebaseFirestore.getInstance();

    private Doctor doc = new Doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_booking_details);

        nameT = (TextView) findViewById(R.id.name);
        dateT = (TextView) findViewById(R.id.date);
        timeT = (TextView) findViewById(R.id.time);
        idT = (TextView) findViewById(R.id.id);
        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);


        aOrR = "";

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            name = intent.getExtras().getString("PATIENT_NAME");
            id = intent.getExtras().getString("PATIENT_ID");
            date = intent.getExtras().getString("BOOKING_DATE");
            time = intent.getExtras().getString("BOOKING_TIME");
            path = intent.getExtras().getString("PATH");

        }

        nameT.setText(name);
        idT.setText(id);
        dateT.setText(date);
        timeT.setText(time);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();
                        doc_id = ID;
                        // Toast.makeText(DBookingDetails.this, "Hi", Toast.LENGTH_SHORT).show();

                       // final Bookings booking = new Bookings(name, id, date, time, doc_id);
                        //final AcceptReject s = new AcceptReject();


                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                addPatient();

                            aOrR = "Accepted";
                                final AccOrRej s = new AccOrRej(name,id,date,time,doc_id, aOrR);

                                //      Toast.makeText(DBookingDetails.this, "Processing booking", LENGTH_LONG).show();


                                database.collection("acc-rej-data")
                                        .add(s)
                                        // Add a success listener so we can be notified if the operation was successfuly.

                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(DBookingDetails.this, "Booking Accepted", Toast.LENGTH_SHORT).show();
                                                DeleteBooking(path);
                                                Intent intent = new Intent(DBookingDetails.this, DoctorFragActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(DBookingDetails.this, "Error", Toast.LENGTH_SHORT).show();

                                            }
                                        });


//must add patient-ID to to doctor's list of patients associated with them
//patients must receive some sort of booking confirmation ?notification maybe
//info must be removed from doctor booking list

                            }
                        });

                        reject.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //  Toast.makeText(DBookingDetails.this, "Declining booking", LENGTH_LONG).show();

                                aOrR = "Rejected";
                                final AccOrRej s = new AccOrRej(name,id,date,time,doc_id, aOrR);

                                //  UpdateDPatientList(doc_id, id);

                                database.collection("acc-rej-data")
                                        .add(s)
                                        // Add a success listener so we can be notified if the operation was successfuly.

                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(DBookingDetails.this, "Booking Rejected", Toast.LENGTH_SHORT).show();
                                                DeleteBooking(path);
                                                Intent intent = new Intent(DBookingDetails.this, DoctorFragActivity.class);
                                                startActivity(intent);
                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(DBookingDetails.this, "Error", Toast.LENGTH_SHORT).show();

                                            }
                                        });


//patients must receive some sort of notice that booking must be rescheduled ?notification maybe
//info must be removed from doctor booking list

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

    public void DeleteBooking(String path)
    {
        database.document(path)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("PENDING-BOOKING DELETE", "SUCCESS: Deleted document.");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("PENDING-BOOKING DELETE", "ERROR: Could not delete document. Here is what went wrong: \n", e);

                    }
                });
    }

    public void addPatient(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database.collection("doctor-data").whereEqualTo("email",user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //   ArrayList<String> patIDs = new ArrayList<>();

                            String documentID = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentID = document.getId();
                            }
                            if(documentID != ""){
                                final DocumentReference Ref = database.collection("doctor-data").document(documentID);
                                Ref.update("patient_ID", FieldValue.arrayUnion(id))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("PD", "SUCCESS: Updated field: ");
                                                //Toast.makeText(DBookingDetails.this, "Could not save: failed to update details", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // failed to update the given field for some reason
                                                Log.w("PD", "ERROR: Could not update field: ", e);
                                                // Toast.makeText(DBookingDetails.this, "Could not save: failed to update details", Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }

                            //  }

                        }

                    }
                });
    }



}



