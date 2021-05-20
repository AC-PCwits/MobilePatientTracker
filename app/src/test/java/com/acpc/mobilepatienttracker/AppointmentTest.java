package com.acpc.mobilepatienttracker;

import static org.junit.Assert.*;

public class AppointmentTest {
    private String pname = "Bob";
    private String bookingdate = "2021/05/04";
    private String time = "16:15";
    private String id = "1324567894561"; //need this for linking to patient
    private String doc_id = "1234567";
    private String docName = "Joe";
    private String status = "Accept";

    private Appointment testBook = new Appointment (String pname, String id, String bookingdate, String time, String doc_id,String docName, String status);

    @Test
    public void testAppointmentObject()
    {
        assertEquals(pname, testBook.pname);
        assertEquals(bookingdate, testBook.bookingdate);
        assertEquals(time, testBook.time);
        assertEquals(id, testBook.id);
        assertEquals(doc_id, testBook.doc_id);
        assertEquals(docName,testBook.docName)
        assertEquals(status,testBook.status);
        assertNotNull(new Appointment());
    }

}