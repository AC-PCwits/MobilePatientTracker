package com.acpc.mobilepatienttracker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BookingsAdapterTest {

    public ArrayList<Bookings> mList = new ArrayList<>();
    {
        mList.add(new Bookings("John", "1", "1", "1", "1"));
    }
    public BookingsAdapter.OnItemClickListener mListener = new BookingsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    @Test
    public void testPatientAdapter()
    {
        Bookings bookings = new Bookings("John", "1", "1", "1", "1");

        BookingsAdapter bookingsAdapter = new BookingsAdapter(mList);

        assertEquals(1, bookingsAdapter.getItemCount());
        assertNotNull(bookingsAdapter);

    }

    @Test
    public void setOnItemClickListener()
    {
        BookingsAdapter listAdapter = new BookingsAdapter(mList);

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
        Bookings bookings = new Bookings("John", "1", "1", "1", "1");

        BookingsAdapter bookingsAdapter = new BookingsAdapter(mList);

        assertEquals(mList.size(), bookingsAdapter.getItemCount());
    }
}