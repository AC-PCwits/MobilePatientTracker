package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class PatientBookings extends Fragment {

    private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout
    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */
    private DocTypeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager; //This will allow single items in our list to alligned correctly

    private ArrayList<DType> mDocTypeList;

    private Button logoutBut;


//    private DocumentReference noteRef = database.collection("doctor-data").document();
    private DType dtype = new DType();
    private TextView testView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientBookings() {
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
    public static PatientBookings newInstance(String param1, String param2) {
        PatientBookings fragment = new PatientBookings();
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

        //  testView = (TextView) findViewById(R.id.testView);
        //  mDocTypeList = new ArrayList<>();
        //  buildExampleList();
        //  buildRecyclerView();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_patient_bookings, container, false);

        testView = (TextView) rootView.findViewById(R.id.testView);

        mDocTypeList = new ArrayList<>();
        // getDocData();
        //To populate the list with actual data use the below function:
//        buildExampleList();
//        buildRecyclerView(rootView);

        getDocTypes(rootView);


        return rootView;
    }

    public void getDocTypes(final View view)
    {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("doctor-data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    ArrayList<Doctor> doctor = new ArrayList<>();
                    ArrayList<String> list = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        doctor.add(doc.toObject(Doctor.class));
                    }

                    for(Doctor doc : doctor)
                    {
                        list.add(doc.doc_type);
                    }

                    list = removeDuplicates(list);

                    for(String str : list)
                    {
                        mDocTypeList.add(new DType(str));
                    }

                    buildRecyclerView(view);

                }
            }
        });
    }

    public void buildExampleList()
    {
        mDocTypeList.add(new DType("Oncologist"));
        mDocTypeList.add(new DType("General Surgeon"));
        mDocTypeList.add(new DType("GP"));
        mDocTypeList.add(new DType("Neurologist"));
        mDocTypeList.add(new DType("Pediatrician"));
        mDocTypeList.add(new DType("Gynaecologist"));



    }

    //This function creates the recylerview object
    public void buildRecyclerView(View rootView)
    {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        /*
        This makes sure that the recycler view will not change size no matter how many items are in the list, which
        also will increase the performance of the app
        */
        mRecyclerView.setHasFixedSize(true); /////////////
        //This sets the layout the user will view
        mLayoutManager = new LinearLayoutManager(getContext());
        //This line is where information about the patient will be parsed to create the list
        mAdapter = new DocTypeAdapter(mDocTypeList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //This function will allow click events to be referenced to the interface in the adapter class
        mAdapter.setOnItemClickListener(new DocTypeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position)
            {
//                changeItem(position, "Clicked");
                Intent intent = new Intent(getContext(), DocTypeList.class);
                Bundle bundle = new Bundle();

                bundle.putString("DOC_TYPE", mDocTypeList.get(position).type);



                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                onPause();
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public ArrayList<String> removeDuplicates(ArrayList<String> list)
    {
        ArrayList<String> newList = new ArrayList<>();

        for(String element : list)
        {
            if(!newList.contains(element))
            {
                newList.add(element);
            }
        }

        return newList;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}