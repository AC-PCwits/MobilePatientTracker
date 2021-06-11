package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DocTypeList extends AppCompatActivity {

    private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout
    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */
    private DocInfoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager; //This will allow single items in our list to alligned correctly

    private ArrayList<DInfo> mdinfoList;

    private DInfo info = new DInfo();
    private TextView testView;

    String t;
    private TextView dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_type_list);

        testView = (TextView) findViewById(R.id.testView);

        // mAuth = FirebaseAuth.getInstance();
        // mUser = mAuth.getCurrentUser();
        // mRef = FirebaseDatabase.getInstance().getReference().child("Bookings");

        mdinfoList = new ArrayList<>();
        // getDocData();
//        buildExampleList();


        //To populate the list with actual data use the below function:
        //buildPatientList()
//        buildExampleList();
//        buildRecyclerView();

        dt=findViewById(R.id.dt);
        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            t = intent.getExtras().getString("DOC_TYPE");

        }

        if(t.equals("__EXAMPLE_BUILD___"))
        {
            buildExampleList();
            buildRecyclerView();
        }
        else {
            dt.setText(t);
            getDocData(t);
        }
    }

    public void getDocData(String type)
    {
//        database.collection("doctor-data").whereEqualTo("doc_type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful())
//                {
//                    ArrayList<Doctor> doctors = new ArrayList<>();
//
//                    for(QueryDocumentSnapshot doc : task.getResult())
//                    {
//                        doctors.add(doc.toObject(Doctor.class));
//                    }
//
//                    for(Doctor doc: doctors)
//                    {
//                        mdinfoList.add(new DInfo(doc.fname, doc.lname, doc.p_length, doc.p_no));
//                    }
//
//                    buildRecyclerView();
//                }
//            }
//        });
        FirebaseDoctor firebaseDoctor = new FirebaseDoctor();
        firebaseDoctor.getDInfoData("doc_type", type, new FirebaseDoctor.DInfoCallback() {
            @Override
            public void onResponse(ArrayList<DInfo> dInfos) {
                mdinfoList.addAll(dInfos);
                buildRecyclerView();
            }
        });
    }
    public void buildExampleList()
    {
        mdinfoList.add(new DInfo("Sally","Parker","12 Years","12"));
        mdinfoList.add(new DInfo("Paul","Parker","2 Years", "123"));
        mdinfoList.add(new DInfo("Alex","Ferguson","27 Years", "123"));
        mdinfoList.add(new DInfo("Eric","Cantona","7 Years", "1234"));
        mdinfoList.add(new DInfo("Krithi","Pillay","30 Years", "2"));

    }

    //This function creates the recylerview object
    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
        /*
        This makes sure that the recycler view will not change size no matter how many items are in the list, which
        also will increase the performance of the app
        */
        mRecyclerView.setHasFixedSize(true);
        //This sets the layout the user will view
        mLayoutManager = new LinearLayoutManager(this);
        //This line is where information about the patient will be parsed to create the list
        mAdapter = new DocInfoAdapter(mdinfoList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //This function will allow click events to be referenced to the interface in the adapter class
        mAdapter.setOnItemClickListener(new DocInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
//                changeItem(position, "Clicked");
                Intent intent = new Intent(DocTypeList.this, PatientDateTimeBooking.class);
                Bundle bundle = new Bundle();


                bundle.putString("DOCTOR_FNAME", mdinfoList.get(position).fname);
                bundle.putString("DOCTOR_SNAME", mdinfoList.get(position).sname);
                bundle.putString("EXPERIENCE", mdinfoList.get(position).exp);
                bundle.putString("PID", mdinfoList.get(position).doc_ID);



                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
