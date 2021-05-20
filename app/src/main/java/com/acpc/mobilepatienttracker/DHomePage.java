package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
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

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

public class DHomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView booking_date, booking_time, booking_patient, patientID;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private ArrayList<AccOrRej> accept_rejects = new ArrayList<>();
    private HorizontalPicker picker;


    public interface DateCallBack
    {
        void onResponse(ArrayList<AccOrRej> list);
    }



    public DHomePage() {
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
    public static DHomePage newInstance(String param1, String param2) {
        DHomePage fragment = new DHomePage();
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
        View rootView = inflater.inflate(R.layout.activity_d_home_page, container, false);


        booking_date = rootView.findViewById(R.id.booking_date);
        booking_time = rootView.findViewById(R.id.booking_time);
        booking_patient = rootView.findViewById(R.id.booking_patient);
        patientID = rootView.findViewById(R.id.patientID);

        picker = rootView.findViewById(R.id.datePicker);


        getUserData(picker, new DHomePage.DateCallBack() {
            @Override
            public void onResponse(final ArrayList<AccOrRej> list) {

                picker.setListener(new DatePickerListener() {
                    @Override
                    public void onDateSelected(DateTime dateSelected)
                    {
                        String [] date = dateSelected.toString().substring(0,10).split("-");
                        String formdate = date[0] + "/" + date[1] + "/" + date[2];

                        booking_date.setText("Date: " + formdate);

                        for(AccOrRej acceptReject : list)
                        {
                            if(acceptReject.bookingdate.equals(formdate))
                            {
                                booking_date.setText("Date: " + acceptReject.bookingdate);
                                booking_time.setText("Time: " + acceptReject.time);
                                patientID.setText("Patient ID: " + acceptReject.id);
                                booking_patient.setText("Patient: " + acceptReject.pname);
                                return;
                            }
                        }

                        booking_date.setText("Date: ");
                        booking_time.setText("Time: ");
                        patientID.setText("Patient ID: ");
                        booking_patient.setText("Patient: ");
                    }
                })
                        .showTodayButton(true)
                        .setOffset(3)
                        .init();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();

                for(AccOrRej acceptReject : list)
                {
                    booking_date.setText("Date: " + acceptReject.bookingdate);
                    booking_time.setText("Time: " + acceptReject.time);
                    patientID.setText("Patient ID: " + acceptReject.id);
                    booking_patient.setText("Patient: " + acceptReject.pname);

                    if(acceptReject.bookingdate.equals(formatter.format(date)))
                    {
                        booking_date.setText("Date: " + acceptReject.bookingdate);
                        booking_time.setText("Time: " + acceptReject.time);
                        patientID.setText("Patient ID: " + acceptReject.id);
                        booking_patient.setText("Patient: " + acceptReject.pname);
                    }
                }

            }
        });

        return rootView;

    }


    public void getUserData(final HorizontalPicker picker, final DHomePage.DateCallBack callBack) {

        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        database.collection("acc-rej-data").whereEqualTo("doc_id", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot booking : task.getResult()) {
                                        accept_rejects.add(booking.toObject(AccOrRej.class));
                                    }

                                    callBack.onResponse(accept_rejects);

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
}