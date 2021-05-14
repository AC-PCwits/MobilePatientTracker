package com.acpc.mobilepatienttracker;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class PatientListAdapterTest {

    public ArrayList<Patient> mPatientList = new ArrayList<>();
    {
        ArrayList<String> illness = new ArrayList<>();
        illness.add("TB");
        mPatientList.add(new Patient("John", "Smith", "1001", "0600606600", "SA", "Male"
                ,"1234 street", "Emily Dow", "1110100001", "Caucasian", "Single", illness, "Yes", "None"));
    }
    public PatientListAdapter.OnItemClickListener mListener = new PatientListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    @Test
    public void testPatientAdapter()
    {
        ArrayList<String> illness = new ArrayList<>();
        illness.add("TB");
        Patient patient = new Patient("John", "Smith", "1001", "0600606600", "SA", "Male"
                ,"1234 street", "Emily Dow", "1110100001", "Caucasian", "Single", illness, "Yes", "None");

        PatientListAdapter patientListAdapter = new PatientListAdapter(mPatientList);

        assertEquals(1, patientListAdapter.getItemCount());
        assertNotNull(patientListAdapter);

    }

    @Test
    public void setOnItemClickListener()
    {
        PatientListAdapter listAdapter = new PatientListAdapter(mPatientList);

        listAdapter.setOnItemClickListener(mListener);

        assertEquals(mListener, listAdapter.getOnItemClickListner());
    }

    @Test
    public void onCreateViewHolder()
    {

    }

    @Test
    public void onBindViewHolder() {
    }

    @Test
    public void getItemCount()
    {
        ArrayList<String> illness = new ArrayList<>();
        illness.add("TB");
        Patient patient = new Patient("John", "Smith", "1001", "0600606600", "SA", "Male"
                ,"1234 street", "Emily Dow", "1110100001", "Caucasian", "Single", illness, "Yes", "None");

        PatientListAdapter patientListAdapter = new PatientListAdapter(mPatientList);

        assertEquals(1, patientListAdapter.getItemCount());
    }
}