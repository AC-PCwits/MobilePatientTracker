package com.acpc.mobilepatienttracker;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PatientConsultationHistory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientConsultationHistory() {
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
    public static PatientConsultationHistory newInstance(String param1, String param2) {
        PatientConsultationHistory fragment = new PatientConsultationHistory();
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
        View rootView = inflater.inflate(R.layout.activity_patient_consultation_history, container, false);

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
}