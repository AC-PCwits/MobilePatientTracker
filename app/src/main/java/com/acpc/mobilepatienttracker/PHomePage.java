package com.acpc.mobilepatienttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

public class PHomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView bookingDate, bookingTime, bookingDoc, docID;

    private ArrayList<AccOrRej> acceptRejects = new ArrayList<>();
    private String docName = "John Smith";

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    public PHomePage() {
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
    public static PHomePage newInstance(String param1, String param2) {
        PHomePage fragment = new PHomePage();
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
        View rootView = inflater.inflate(R.layout.activity_p_home_page, container, false);

        bookingDate = rootView.findViewById(R.id.bookingDate);
        bookingTime = rootView.findViewById(R.id.bookingTime);
        bookingDoc = rootView.findViewById(R.id.bookingDoc);
        docID = rootView.findViewById(R.id.docID);

        HorizontalPicker picker = rootView.findViewById(R.id.datePicker);




        getUserData(picker);


        return rootView;

    }

//    @Override
//    public void onDateSelected(DateTime dateSelected)
//    {
//        String [] date = dateSelected.toString().substring(0,10).split("-");
//        String formdate = date[2] + "/" + date[1] + "/" + date[0];
//
//        bookingDate.setText("Date: " + formdate);
//
//        for(AccOrRej acceptReject : acceptRejects)
//        {
//            if(acceptReject.bookingdate.equals(formdate))
//            {
//                bookingDate.setText("Date: " + acceptReject.bookingdate);
//                bookingTime.setText("Time: " + acceptReject.time);
//                docID.setText("Doctor ID: " + acceptReject.doc_id);
//                bookingDoc.setText("Doctor: " + docName);
//                return;
//            }
//        }
//
//        bookingDate.setText("Date: ");
//        bookingTime.setText("Time: ");
//        docID.setText("Doctor ID: ");
//        bookingDoc.setText("Doctor: ");
//
//    }

    public void getUserData(final HorizontalPicker picker) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        database.collection("acc-rej-data").whereEqualTo("id", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot booking : task.getResult()) {
                                        acceptRejects.add(booking.toObject(AccOrRej.class));
                                    }
//                                    for (AccOrRej AorR : bookingData) {
//                                        if (AorR.id.equals(ID)) {
//
//                                            bookingTime.setText(AorR.time);
//                                            docID.setText(AorR.doc_id);
//                                            bookingDate.setText(AorR.bookingdate);
//
//
//                                        }
//                                    }
                                    picker
                                            .setListener(new DatePickerListener() {
                                                @Override
                                                public void onDateSelected(DateTime dateSelected)
                                                {
                                                    String [] date = dateSelected.toString().substring(0,10).split("-");
                                                    String formdate = date[2] + "/" + date[1] + "/" + date[0];

                                                    bookingDate.setText("Date: " + formdate);

                                                    for(AccOrRej acceptReject : acceptRejects)
                                                    {
                                                        if(acceptReject.bookingdate.equals(formdate))
                                                        {
                                                            bookingDate.setText("Date: " + acceptReject.bookingdate);
                                                            bookingTime.setText("Time: " + acceptReject.time);
                                                            docID.setText("Doctor ID: " + acceptReject.doc_id);
                                                            bookingDoc.setText("Doctor: " + docName);
                                                            return;
                                                        }
                                                    }

                                                    bookingDate.setText("Date: ");
                                                    bookingTime.setText("Time: ");
                                                    docID.setText("Doctor ID: ");
                                                    bookingDoc.setText("Doctor: ");
                                                }
                                            })
                                            .showTodayButton(true)
                                            .setOffset(3)
                                            .init();

                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                                    Date date = new Date();

                                    for(AccOrRej acceptReject : acceptRejects)
                                    {
                                        if(acceptReject.bookingdate.equals(formatter.format(date)))
                                        {
                                            bookingDate.setText("Date: " + acceptReject.bookingdate);
                                            bookingTime.setText("Time: " + acceptReject.time);
                                            docID.setText("Doctor ID: " + acceptReject.doc_id);
                                            bookingDoc.setText("Doctor: " + docName);
                                        }
                                    }
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

    /*public void getPatientID(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");

        bookingDoc.setText("Query failed");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bookingDoc.setText("List empty");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        bookingDate.setText(ID);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }*/
}
