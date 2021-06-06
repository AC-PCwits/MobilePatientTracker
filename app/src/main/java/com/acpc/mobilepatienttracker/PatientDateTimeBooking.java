package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.acpc.mobilepatienttracker.DoctorField.ID;

public class PatientDateTimeBooking extends AppCompatActivity {

    private DatePickerDialog dpd;
    private TextView date;
    private Calendar c;
    private TextView tvTimer1;
    int t1Hour, t1Minute;
    private String f;
    private TextView name;
    private String s;
    // private TextView sname;
    private TextView qual;
    private String e;
    private TextView exp;
    private String id;
    private Button confirm;
    private String patientname;
    private String patientID;
    private String q;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    //String docID;////
    private Button check;

///



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_date_time_booking);

        name=findViewById(R.id.name);
        //sname=findViewById(R.id.sname);
        exp=findViewById(R.id.exp);
        confirm=findViewById(R.id.confirmbtn);
        qual= findViewById(R.id.textView10);

        check = findViewById(R.id.checkbtn);
        // check.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);


        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            f = intent.getExtras().getString("DOCTOR_FNAME");
            s = intent.getExtras().getString("DOCTOR_SNAME");
            e = intent.getExtras().getString("EXPERIENCE");
            id = intent.getExtras().getString("PID");

        }

        name.setText(f+" "+s);
        //  sname.setText(s);
        exp.setText(e + " Years");



        date = findViewById(R.id.date_of_birth);
        tvTimer1 = findViewById(R.id.tv_timer1);

        //////
        database.collection("doctor-data").whereEqualTo("p_no", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Doctor> d = new ArrayList<>();

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        d.add(doc.toObject(Doctor.class) );
                    }
                    for (Doctor dt : d) {
                        if (dt.p_no.equals(id)) {
                            String uniQ= dt.uni_name;
                            qual.setText(uniQ);

                        }
                    }
                }
            }
        });



        tvTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        PatientDateTimeBooking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                                t1Hour = hourofDay;
                                t1Minute = minute;

                                //Store hour and min in string
                                String time = t1Hour + ":" + t1Minute;



                                //initialize 24 hour time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try{
                                    Date date = f24Hours.parse(time);
                                    //initialize 12 hours time format
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );

                                    //Set selected time in textview
                                    tvTimer1.setText(f12Hours.format(date));

                                }catch (ParseException e){
                                    e.printStackTrace();
                                }

                            }
                        }, 12,0,false
                );
                //Set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //Displayed previous selected time
                timePickerDialog.updateTime(t1Hour,t1Minute);

                //show dialog
                timePickerDialog.show();



            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(PatientDateTimeBooking.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                        int mnth = month+1;

                        if(dayOfMonth <10 && mnth >=10){
                            date.setText(year + "/" +(month+1) + "/" + "0"+dayOfMonth);
                        }else if (mnth <10 && dayOfMonth>=10){
                            date.setText(year + "/" +"0"+(month+1) + "/" + dayOfMonth);
                        }else if (mnth <10 && dayOfMonth <10){
                            date.setText(year + "/" +"0"+(month+1) + "/" +"0"+dayOfMonth);
                        }else if (mnth >= 10 && dayOfMonth >= 10){
                            date.setText(year + "/" +(month+1) + "/" + dayOfMonth);
                        }
                    }
                }, year,month,day);

                dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dpd.show();
            }
        });








        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {

                        final String ID = dataSnapshot.child("id").getValue().toString();
                        final String patName= dataSnapshot.child("fname").getValue().toString();


                        check.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {


                                final String patDate = date.getText().toString();
                                final String patTime = tvTimer1.getText().toString();



                                if(patTime.isEmpty()){

                                    Toast.makeText(PatientDateTimeBooking.this, "Time is Required", Toast.LENGTH_LONG).show();
                                    tvTimer1.requestFocus();

                                    return;
                                }

                                else if(patDate.isEmpty()){
                                    //tvTimer1.setError(" ");
                                    Toast.makeText(PatientDateTimeBooking.this, "Date is Required", Toast.LENGTH_LONG).show();
                                    tvTimer1.requestFocus();

                                    return;
                                }


                                Bookings b = new Bookings(patName, ID, patDate, patTime, id);

                                checkPending(b);




                            }
                        });

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final String patDate = date.getText().toString();
                                final String patTime = tvTimer1.getText().toString();
                                Bookings b = new Bookings(patName, ID, patDate, patTime, id);
                                Add(true,b);

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



    public void checkPending(final Bookings book){

        database.collection("pending-booking-data").whereEqualTo("doc_id", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Bookings> b = new ArrayList<>();

                    for (QueryDocumentSnapshot bk : task.getResult()) {
                        b.add(bk.toObject(Bookings.class));
                    }
                    for (Bookings bking : b) {
                        final String patDate = date.getText().toString();
                        final String patTime = tvTimer1.getText().toString();
                        if (bking.bookingdate.equals(patDate) && bking.time.equals(patTime)) {
                            Toast.makeText(PatientDateTimeBooking.this, "Unavailable time slot. Please select another time", LENGTH_LONG).show();
                            break;

                        } else {

                            checkTest(book);


                        }
                    }

                }
                if (task.getResult().isEmpty()){
                    checkTest(book);
                }

            }
        });



    }



    public void checkTest(final Bookings book){
        database.collection("acc-rej-data").whereEqualTo("doc_id", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<AccOrRej> b = new ArrayList<>();

                    for (QueryDocumentSnapshot bk : task.getResult()) {
                        b.add(bk.toObject(AccOrRej.class));
                    }
                    for (AccOrRej bking : b) {
                        final String patDate = date.getText().toString();
                        final String patTime = tvTimer1.getText().toString();
                        if (bking.bookingdate.equals(patDate) && bking.time.equals(patTime) && bking.accOrRej.equals("Accepted")) {
                            Toast.makeText(PatientDateTimeBooking.this, "Unavailable time slot. Please select another time", LENGTH_LONG).show();
                            break;


                        } else {

                            //Toast.makeText(PatientDateTimeBooking.this, "Checking Availability",LENGTH_LONG).show();

                            // NoBookings();
                            confirm.setVisibility(View.VISIBLE);
                            check.setVisibility(View.GONE);
                            Toast.makeText(PatientDateTimeBooking.this, "Available Time Slot", Toast.LENGTH_SHORT).show();
                            break;

                            // Add(true,book);

                            // btn.setVisibility(View.GONE);

                        }
                    }

                }
                if (task.getResult().isEmpty()){
                    confirm.setVisibility(View.VISIBLE);
                    check.setVisibility(View.GONE);
                    Toast.makeText(PatientDateTimeBooking.this, "Available Time Slot", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }



    public void Add(boolean y, Bookings b){
        if(y==true){
            database.collection("pending-booking-data") // data gets added to a collection called patient-data
                    .add(b)
                    // Add a success listener so we can be notified if the operation was successfuly.

                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Intent start = new Intent(PatientDateTimeBooking.this, PatientFragActivity.class);

                            Toast.makeText(PatientDateTimeBooking.this, "Booking Request Successful", Toast.LENGTH_LONG).show();
                            startActivity(start);


                        }
                    })
                    // Add a failure listener so we can be notified if something does wrong
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // If we are here, the entry could not be added for some reason (e.g no internet connection)
                            makeText(PatientDateTimeBooking.this, "Error Try Again", LENGTH_LONG).show();
                        }
                    });
        }

    }

   /* public void check(){

        database.collection("acc-rej-data").whereEqualTo("doc_id", id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(DBookingDetails.this, "Booking Rejected", Toast.LENGTH_SHORT).show();
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


    } */




}




