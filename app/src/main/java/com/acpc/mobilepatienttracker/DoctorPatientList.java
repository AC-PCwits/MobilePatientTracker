package com.acpc.mobilepatienttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DoctorPatientList extends Fragment
{
    private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout
    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */
    private PatientListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager; //This will allow single items in our list to alligned correctly

    private ArrayList<Patient> mPatientList;

    private Button logoutBut;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = database.collection("doctor-data").document();
    private Doctor doc = new Doctor();
    private TextView testView;

    private TextView searchBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DoctorPatientList() {
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
    public static DoctorPatientList newInstance(String param1, String param2) {
        DoctorPatientList fragment = new DoctorPatientList();
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
        View rootView = inflater.inflate(R.layout.activity_d_patient_list, container, false);

        testView = (TextView) rootView.findViewById(R.id.testView);

        searchBar = rootView.findViewById(R.id.pdl_searchbar);
        TextWatcher watcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String selector = charSequence.toString().toLowerCase();
                if (selector == "")
                {
                    RebuildRecyclerView(mPatientList);
                    return;
                }

                ArrayList<Patient> newPatientList = new ArrayList<>();

                for (Patient patient : mPatientList)
                {
                    if (patient.fname.toLowerCase().contains(selector) || patient.fsurname.toLowerCase().contains(selector))
                    {
                        newPatientList.add(patient);
                    }
                }

                RebuildRecyclerView(newPatientList);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        };
        searchBar.addTextChangedListener(watcher);

        mPatientList = new ArrayList<>();
        getDocData();
        //To populate the list with actual data use the below function:
//        buildExampleList();
        buildRecyclerView(rootView);

        return rootView;
    }

    public void getDocData()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

                    if(doc.patient_ID == null)
                    { return;}
                    else
                    {

                        buildPatientList(doc.patient_ID);
                    }
                }
            }
        });
    }

    //This function will populate the patient list from the database
    public void buildPatientList(final ArrayList<String> pIDs)
    {
        database.collection("patient-data")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Patient> patients = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        patients.add(doc.toObject(Patient.class));
                    }

                    String s = "";

                    for(Patient patient : patients)
                    {
                        for(String pID : pIDs)
                        {
                            if(pID.equals(patient.idno))
                            {
                                Intent intent = new Intent(getContext(),PastConsults.class);
                                intent.putExtra("pID",pID);
                                mPatientList.add(patient);
                                s = s + patient.fname + " " + patient.fsurname + " : " + patient.idno + "\n";
                            }
                        }
                    }
                    testView.setText(s);
////                    testView.setText("Successful but list was empty");
                }
                else
                {
                    testView.setText("Error: could not get documents from query");
                }
            }
        });
    }

  /*  public void buildExampleList()
    {
        ArrayList<String> illness = new ArrayList<>();
        illness.add("TB");
        mPatientList.add(new Patient("John", "Smith", "1001", "0600606600", "SA", "Male"
                            ,"1234 street", "Emily Dow", "1110100001", "Caucasian", "Single", illness, "Yes", "None"));
        illness.remove(0);
        illness.add("Diabetes");
        mPatientList.add(new Patient("Emily", "Smith", "1002", "0600606900", "SA", "Female"
                ,"1334 street", "Emily Dow", "1110100001", "Coloured", "Married", illness, "Yes", "None"));
        mPatientList.add(new Patient("Emile", "Jonah", "1003", "0303030253", "SA", "Male"
                ,"1334 Str", "Bob Newman", "7878789226", "Other", "Married", illness, "Yes", "None"));
        illness.add("HIV/AIDS");
        mPatientList.add(new Patient("Dow", "Jones", "1004", "7856452673", "SA", "Male"
                ,"13 Av Blueberry Hill", "Joe Willock", "5264831346", "Indian", "Other", illness, "Yes", "Sulfates"));
        mPatientList.add(new Patient("Deena", "Schmidt", "1005", "6542156482", "SA", "Female"
                ,"1 Victory Lane", "Emile Qura", "6248561279", "Black", "Married", illness, "Yes", "Peanuts"));
    } */

    //This function creates the recylerview object
    public void buildRecyclerView(View rootView)
    {
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        /*
        This makes sure that the recycler view will not change size no matter how many items are in the list, which
        also will increase the performance of the app
        */
        mRecyclerView.setHasFixedSize(false);
        //This sets the layout the user will view
        mLayoutManager = new LinearLayoutManager(getContext());
        //This line is where information about the patient will be parsed to create the list
        mAdapter = new PatientListAdapter(mPatientList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //This function will allow click events to be referenced to the interface in the adapter class
        mAdapter.setOnItemClickListener(new PatientListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
//                changeItem(position, "Clicked");
                GoToNextActivity(position);
            }
        });
    }

    public void RebuildRecyclerView(ArrayList<Patient> newList)
    {
        if (mRecyclerView == null)
        {
            return;
        }

        mAdapter = new PatientListAdapter(newList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //This function will allow click events to be referenced to the interface in the adapter class
        mAdapter.setOnItemClickListener(new PatientListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
//                changeItem(position, "Clicked");
                GoToNextActivity(position);
            }
        });
    }

    public void GoToNextActivity(int position)
    {
        Intent intent = new Intent(getContext(), DPatientDetails.class);
        Bundle bundle = new Bundle();

        bundle.putString("PATIENT_NAME", mPatientList.get(position).fname + " " +
                mPatientList.get(position).fsurname);
        bundle.putString("PATIENT_ID", mPatientList.get(position).idno);
        bundle.putString("PATIENT_CELL", mPatientList.get(position).cellno);
        bundle.putString("PATIENT_NAT", mPatientList.get(position).nationality);
        bundle.putString("PATIENT_GENDER", mPatientList.get(position).gender);
        bundle.putString("PATIENT_ADDRESS", mPatientList.get(position).address);
        bundle.putString("PATIENT_ENAME", mPatientList.get(position).ename);
        bundle.putString("PATIENT_ECONT", mPatientList.get(position).econtact);
        bundle.putString("PATIENT_RACE", mPatientList.get(position).race);
        bundle.putString("PATIENT_MARRIED", mPatientList.get(position).mstatus);
        bundle.putStringArrayList("PATIENT_ILLNESS", mPatientList.get(position).cissues);
        bundle.putString("PATIENT_MEDAID", mPatientList.get(position).medaid);
        bundle.putString("PATIENT_ALLERGIES", mPatientList.get(position).allergies);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}