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
    private String doc_type = "Oncologist";
    private String cell = "1";
    private String email = "a@g.com";

    private Appointment testApp = new Appointment (pname,id,bookingdate,time,doc_id,docName,status,doc_type,cell,email);

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
        assertEquals(doc_type,testApp.doc_type);
        assertEquals(cell,testApp.cell);
        assertEquals(email,testApp.email);
        assertNotNull(new Appointment());
    }


    @Test
    public void getBookingDate(){
    String bdate = "2021/05/04";
    assertEquals(bdate,testApp.getBookingDate());

    }


}