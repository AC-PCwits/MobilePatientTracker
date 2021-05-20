package com.acpc.mobilepatienttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppointmentTest {
    private String pname = "Bob";
    private String bookingdate = "2021/05/04";
    private String time = "16:15";
    private String id = "1324567894561"; //need this for linking to patient
    private String doc_id = "1234567";
    private String docName = "Joe";
    private String status = "Accept";

    private Appointment testApp = new Appointment (pname,id,bookingdate,time,doc_id,docName,status);

    @Test
    public void testAppointmentObject()
    {
        assertEquals(pname, testApp.pname);
        assertEquals(bookingdate, testApp.bookingdate);
        assertEquals(time, testApp.time);
        assertEquals(id, testApp.id);
        assertEquals(doc_id, testApp.doc_id);
        assertEquals(docName,testApp.docName);
        assertEquals(status,testApp.status);
        assertNotNull(new Appointment());
    }

}