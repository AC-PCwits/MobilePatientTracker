package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorDetails extends AppCompatActivity {

    private String name;
    private String id;
    private String cellno;
    private String dob;
    private String gender;
    private String email;
    private String prac_length;
    private String prac_num;
    private String uni;
    private String spec;

    private TextView nameText;
    private TextView idText;
    private TextView dobText;
    private TextView cellText;
    private TextView emailText;
    private TextView genderText;
    private TextView pracLengthText;
    private TextView pracNumText;
    private TextView uniText;
    private TextView specText;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private Doctor doc = new Doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        nameText = (TextView)findViewById(R.id.nameText);
        idText = (TextView)findViewById(R.id.idText);
        dobText = (TextView)findViewById(R.id.dobText);
        cellText = (TextView)findViewById(R.id.cellText);
        emailText = (TextView)findViewById(R.id.emailText);
        genderText = (TextView)findViewById(R.id.genderText);
        pracLengthText = (TextView)findViewById(R.id.pLengthText);
        pracNumText = (TextView)findViewById(R.id.pNumText);
        uniText = (TextView)findViewById(R.id.uniText);
        specText = (TextView)findViewById(R.id.specText);


        Bundle bundle = getIntent().getExtras();

//        if(bundle != null)
//        {
//            name = bundle.getString("DOC_NAME");
//            id = bundle.getString("DOC_ID");
//            dob = bundle.getString("DOC_DOB");
//            gender = bundle.getString("DOC_GENDER");
//            email = bundle.getString("DOC_EMAIL");
//            prac_length = bundle.getString("DOC_PRACYEARS");
//            prac_num = bundle.getString("DOC_PRACNUM");
//            uni = bundle.getString("DOC_UNI");
//            spec = bundle.getString("DOC_SPEC");
//            cellno = bundle.getString("DOC_CELL");
//
//            nameText.setText(name);
//            idText.setText(id);
//            dobText.setText(dob);
//            genderText.setText(gender);
//            emailText.setText(email);
//            pracLengthText.setText(prac_length);
//            pracNumText.setText(prac_num);
//            uniText.setText(uni);
//            specText.setText(spec);
//            cellText.setText(cellno);
//        }
//        else
//        {
//            nameText.setText("ERROR: NULL VALUE PASSED");
//        }

        getDocDet();

        BottomNavigationView bottomNavigationView = findViewById(R.id.d_nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.d_details);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.d_home:
                        startActivity(new Intent(getApplicationContext()
                                ,DHomePage.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.d_details:
                        return true;
                    case R.id.patient_list:
                        startActivity(new Intent(getApplicationContext()
                                ,DoctorPatientList.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.pending_bookings:
                        startActivity(new Intent(getApplicationContext()
                                ,PendingBookings.class));
                        overridePendingTransition(0 , 0);
                        return true;

                }

                return false;
            }
        });



    }

    public void getDocDet()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


//        final String[] str = {""};
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
//        reference.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren())
//                {
//                    if(dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail()))
//                    {
////                            str[0] = dataSnapshot.child("IDnum").getValue().toString();
//                        testView.setText(dataSnapshot.child("IDnum").getValue().toString());
//                    }
//                }
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        database.collection("doctor-data").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Doctor> doctor1 = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        doctor1.add(doc.toObject(Doctor.class));
                    }

                    String s = "";

                    for(Doctor doctor2 : doctor1)
                    {
                        if(doctor2.email.equals(user.getEmail()))
                        {
                            doc = doctor2;
                        }

                    }

                    nameText.setText(doc.fname + " " + doc.lname);
                    idText.setText(doc.ID);
                    dobText.setText(doc.dob);
                    genderText.setText(doc.gender);
                    emailText.setText(doc.email);
                    pracLengthText.setText(String.valueOf(doc.p_length));
                    pracNumText.setText(doc.p_no);
                    uniText.setText(doc.uni_name);
                    specText.setText(doc.doc_type);
                    cellText.setText(doc.cell_no);
                }
            }
        });
    }
}