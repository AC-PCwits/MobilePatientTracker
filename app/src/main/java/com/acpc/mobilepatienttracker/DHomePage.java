package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DHomePage extends AppCompatActivity
{
  /*  private RecyclerView mRecyclerView; //This variable will contain the recycler view created in the XML layout
    /*
    This is the bridge between our data and recycler view. We have to use the PatientListAdapter
    instead of RecylcerView.Adapter as the class contains custom functions for the Recycler View
     */
  /*  private PatientListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager; //This will allow single items in our list to alligned correctly

    private ArrayList<Patient> mPatientList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_home_page);

        mPatientList = new ArrayList<>();
        //Creating an example list to test
        buildExampleList();
        //To populate the list with actual data use the below function:
        //buildPatientList()
        buildRecyclerView();

    }

    public void buildExampleList()
    {
        ArrayList<String> ill = new ArrayList<>();
        ill.add("Flu");
        ill.add("TB");
        ill.add("High Blood Pressure");
        mPatientList.add(new Patient(1234, "Patient 1", ill));
        mPatientList.add(new Patient(1235, "Patient 2", ill));
        mPatientList.add(new Patient(1236, "Patient 3", ill));
        mPatientList.add(new Patient(1237, "Patient 4", ill));
        mPatientList.add(new Patient(1238, "Patient 5", ill));
        mPatientList.add(new Patient(1239, "Patient 6", ill));
        mPatientList.add(new Patient(1240, "Patient 7", ill));
        mPatientList.add(new Patient(1241, "Patient 8", ill));
        mPatientList.add(new Patient(1242, "Patient 9", ill));
        mPatientList.add(new Patient(1243, "Patient 10", ill));
        mPatientList.add(new Patient(1244, "Patient 11", ill));
        mPatientList.add(new Patient(1245, "Patient 12", ill));
    }

    //This function will populate the patient list from the database
    //TODO: Populate patient list from database within this method
    public void buildPatientList()
    {

    }

    //This function creates the recylerview object
    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
        /*
        This makes sure that the recycler view will not change size no matter how many items are in the list, which
        also will increase the performance of the app
        */
     /*   mRecyclerView.setHasFixedSize(true);
        //This sets the layout the user will view
        mLayoutManager = new LinearLayoutManager(this);
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
                Intent intent = new Intent(DHomePage.this, DPatientDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("PATIENT_NAME", mPatientList.get(position).name);
                bundle.putInt("PATIENT_ID", mPatientList.get(position).ID);
                bundle.putStringArrayList("PATIENT_ILLNESS", mPatientList.get(position).illnesses);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void changeItem(int position, String text)
    {
        mPatientList.get(position).setName(text);
        mAdapter.notifyItemChanged(position);
    }*/
}