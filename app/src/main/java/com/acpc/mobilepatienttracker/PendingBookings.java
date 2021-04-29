package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class PendingBookings extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PendingBookings() {
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
    public static PendingBookings newInstance(String param1, String param2) {
        PendingBookings fragment = new PendingBookings();
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
        View rootView = inflater.inflate(R.layout.activity_pending_bookings, container, false);

//        BottomNavigationView bottomNavigationView = findViewById(R.id.d_nav_bar);
//
//        bottomNavigationView.setSelectedItemId(R.id.patient_list);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//
//                    case R.id.d_home:
//                        startActivity(new Intent(getApplicationContext()
//                                ,DHomePage.class));
//                        overridePendingTransition(0 , 0);
//                        return true;
//                    case R.id.d_details:
//                        startActivity(new Intent(getApplicationContext()
//                                ,DoctorDetails.class));
//                        overridePendingTransition(0 , 0);
//                        return true;
//                    case R.id.patient_list:
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

        return rootView;
    }
}