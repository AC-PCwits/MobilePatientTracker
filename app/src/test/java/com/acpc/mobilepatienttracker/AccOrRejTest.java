package com.acpc.mobilepatienttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccOrRejTest {

    private String accOrrej = "Accepted";
    private String pname = "Bob";
    private String id = "1";
    private String date = "1";
    private String time = "2";
    private String doc_ID = "12";
    private AccOrRej testA = new AccOrRej(pname, id, date, time, doc_ID,accOrrej);

    @Test
    public void testAcceptRejectObject()
    {
        assertEquals(pname, testA.pname);
        assertEquals(id, testA.id);
        assertEquals(date, testA.bookingdate);
        assertEquals(time, testA.time);
        assertEquals(doc_ID, testA.doc_id);
        assertEquals(accOrrej,testA.accOrRej);

        assertNotNull(new AccOrRej());
    }

}
