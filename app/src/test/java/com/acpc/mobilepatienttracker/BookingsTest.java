package com.acpc.mobilepatienttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookingsTest
{
    private String pname = "Bob";
    private String bookingdate = "2021/05/04";
    private String time = "16:15";
    private String id = "1324567894561"; //need this for linking to patient
    private String doc_id = "1234567";

    private Bookings testBook = new Bookings(pname, id, bookingdate, time, doc_id);

    @Test
    public void testBookingObject()
    {
        assertEquals(pname, testBook.pname);
        assertEquals(bookingdate, testBook.bookingdate);
        assertEquals(time, testBook.time);
        assertEquals(id, testBook.id);
        assertEquals(doc_id, testBook.doc_id);
        assertNotNull(new Bookings());
    }
}