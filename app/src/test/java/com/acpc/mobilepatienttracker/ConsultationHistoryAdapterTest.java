package com.acpc.mobilepatienttracker;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConsultationHistoryAdapterTest {
    public ArrayList<Appointment> mList = new ArrayList<>();
    {
        mList.add(new Appointment("John", "1", "1", "1", "1", "1", "1"));
    }
    public ConsultationHistoryAdapter.OnItemClickListener mListener = new ConsultationHistoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    @Test
    public void testConsultationHistoryAdapter()
    {
       Appointment appointment = new Appointment("John", "1", "1", "1", "1", "1", "1");

        ConsultationHistoryAdapter consultationHistoryAdapter = new ConsultationHistoryAdapter(mList);

        assertEquals(1, consultationHistoryAdapter.getItemCount());
        assertNotNull(consultationHistoryAdapter);

    }

  /*  @Test
    public void setOnItemClickListener()
    {
        ConsultationHistoryAdapter listAdapter = new ConsultationHistoryAdapter(mList);

        listAdapter.setOnItemClickListener(mListener);

        assertEquals(mListener, listAdapter.getOnItemClickListner());
    }*/

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
      Appointment appointment = new Appointment( "John", "1", "1", "1", "1", "1", "1");

        ConsultationHistoryAdapter consultationHistoryAdapter = new ConsultationHistoryAdapter(mList);

        assertEquals(mList.size(), consultationHistoryAdapter.getItemCount());
    }

    @Test
    public void testgetColour() {

  ConsultationHistoryAdapter c = new ConsultationHistoryAdapter(mList);

        assertTrue(c.getColour("Accepted") =="#50C878");
        assertTrue(c.getColour("Pending")=="#C3C3C3");
        assertTrue(c.getColour("Past")=="#0E20C5");

        //haven't catered for default

    }
}

