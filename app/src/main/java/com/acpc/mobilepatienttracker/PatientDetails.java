package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientDetails extends Fragment {

    private String name;
    private String id;
    private String cellno;
    private String nationality;
    private String gender;
    private String address;
    private String ename;
    private String econtact;
    private String race;
    private String mstatus;
    private String medaid;
    private String allergies;
    private ArrayList<String> illnesses;

    private TextView nameText;
    private TextView idText;
    private TextView illText;
    private TextView cellText;
    private TextView nationalityText;
    private TextView genderText;
    private TextView addressText;
    private TextView enameText;
    private TextView econtactText;
    private TextView raceText;
    private TextView mstatusText;
    private TextView medaidText;
    private TextView allergiesText;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private Patient mPatient;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientDetails newInstance(String param1, String param2) {
        PatientDetails fragment = new PatientDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_d_patient_details, container, false);

        nameText = (TextView)rootView.findViewById(R.id.nameText);
        idText = (TextView)rootView.findViewById(R.id.idText);
        illText = (TextView)rootView.findViewById(R.id.illnessText);
        cellText = (TextView)rootView.findViewById(R.id.cellText);
        nationalityText = (TextView)rootView.findViewById(R.id.natText);
        genderText = (TextView)rootView.findViewById(R.id.genderText);
        addressText = (TextView)rootView.findViewById(R.id.addressText);
        enameText = (TextView)rootView.findViewById(R.id.enameText);
        econtactText = (TextView)rootView.findViewById(R.id.ecellText);
        raceText = (TextView)rootView.findViewById(R.id.raceText);
        mstatusText = (TextView)rootView.findViewById(R.id.mstatusText);
        medaidText = (TextView)rootView.findViewById(R.id.medaidText);
        allergiesText = (TextView)rootView.findViewById(R.id.allergiesText);

        //Patient details are pulled from databse and displayed using this method
        getUserData();

        return rootView;

//        BottomNavigationView bottomNavigationView = findViewById(R.id.d_nav_bar);
//
//        bottomNavigationView.setSelectedItemId(R.id.d_home);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//
//                    case R.id.d_home:
//                        return true;
//                    case R.id.d_details:
//                        startActivity(new Intent(getApplicationContext()
//                                ,DoctorDetails.class));
//                        overridePendingTransition(0 , 0);
//                        return true;
//                    case R.id.patient_list:
//                        startActivity(new Intent(getApplicationContext()
//                                ,DoctorPatientList.class));
//                        overridePendingTransition(0 , 0);
//                        return true;
//                    case R.id.pending_bookings:
//                        startActivity(new Intent(getApplicationContext()
//                                ,PendingBookings.class));
//                        overridePendingTransition(0 , 0);
//                        return true;
//
//                }
//
//                return false;
//            }
//        });

    }

    public void getUserData()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            String str = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail()))
                    {
                        str = dataSnapshot.child("id").getValue().toString();
                    }
                }

                getPatientData(str);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPatientData(final String id)
    {
        database.collection("patient-data").whereEqualTo("idno", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Patient> patient = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        patient.add(doc.toObject(Patient.class));
                    }

                    for(Patient patient1 : patient)
                    {
                        if(patient1.idno.equals(id))
                        {
                            mPatient = patient1;
                        }
                    }

                    if( mPatient != null){

                        displayPatientData(mPatient);

                    }


                }
            }
        });
    }

    public void displayPatientData(Patient patient)
    {
        //Patient Details are set here
        name = patient.fname + " " + patient.fsurname;
        id = patient.idno;
        cellno = patient.cellno;
        nationality = patient.nationality;
        gender = patient.gender;
        address = patient.address;
        ename = patient.ename;
        econtact = patient.econtact;
        race = patient.race;
        mstatus = patient.mstatus;
        illnesses = patient.cissues;
        medaid = patient.medaid;
        allergies = patient.allergies;

        String ill = "";

        for(String text: illnesses)
        {
            if(ill.equals(""))
            {
                ill = text;
            }else
            {
                ill = ill + "\n" + text;
            }
        }

        //UI form text is set here
        nameText.setText(name);
        idText.setText(id);
        cellText.setText(cellno);
        nationalityText.setText(nationality);
        genderText.setText(gender);
        addressText.setText(address);
        enameText.setText(ename);
        econtactText.setText(econtact);
        raceText.setText(race);
        mstatusText.setText(mstatus);
        illText.setText(ill);
        medaidText.setText(medaid);
        allergiesText.setText(allergies);
    }
}