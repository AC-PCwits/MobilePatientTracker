package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorDetails extends Fragment {

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorDetails() {
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
    public static DoctorDetails newInstance(String param1, String param2) {
        DoctorDetails fragment = new DoctorDetails();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_doctor_details, container, false);

        nameText = (TextView)rootView.findViewById(R.id.nameText);
        idText = (TextView)rootView.findViewById(R.id.idText);
        dobText = (TextView)rootView.findViewById(R.id.dobText);
        cellText = (TextView)rootView.findViewById(R.id.cellText);
        emailText = (TextView)rootView.findViewById(R.id.emailText);
        genderText = (TextView)rootView.findViewById(R.id.genderText);
        pracLengthText = (TextView)rootView.findViewById(R.id.pLengthText);
        pracNumText = (TextView)rootView.findViewById(R.id.pNumText);
        uniText = (TextView)rootView.findViewById(R.id.uniText);
        specText = (TextView)rootView.findViewById(R.id.specText);

        getDocDet();

        return rootView;
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