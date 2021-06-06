package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)

public class DBookingDetailsTest {

    private String PName = "John";
    private String P_ID = "___NULL_DEV___";
    private String BookingDate = "2021/01/20";
    private String BookingTime = "09:00 AM";
    private String docID = "";

    private Bookings testBooking = new Bookings(PName , P_ID , BookingDate , BookingTime , docID);

    public DBookingDetails activity;

    @Before
    public void setup(){

        Intent intent = new Intent(RuntimeEnvironment.application, DBookingDetails.class);
        Bundle bundle = new Bundle();

        bundle.putString("PATIENT_ID", "___NULL_DEV___");
        bundle.putString("PATIENT_NAME", "John");
        bundle.putString("BOOKING_DATE", "2021");
        bundle.putString("BOOKING_TIME", "09:00 AM");
        bundle.putString("PATH", "___NULL_DEV___");

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(DBookingDetails.class, intent).create().resume().get();
    }

    @Test
    public void checkNotNull() throws Exception {

        assertNotNull(activity);
    }

    @Test
    public void checkDetails() throws Exception {

        assertEquals(PName , testBooking.pname);
        assertEquals(P_ID , testBooking.id);
        assertEquals(BookingDate , testBooking.bookingdate);
        assertEquals(BookingTime , testBooking.time);
        assertEquals(docID , testBooking.doc_id);
        assertNotNull(new Bookings());

    }

    @Test
    public void testAddPatient() throws  Exception {



    }


}