package com.acpc.mobilepatienttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class AcceptRejectTest {

    private String accOrrej = "Accepted";
    private Bookings booking = new Bookings();
    private AcceptReject testA = new AcceptReject(booking,accOrrej);

    @Test
    public void testAcceptRejectObject()
    {
        assertEquals(booking, testA.booking);
        assertEquals(accOrrej,testA.accOrRej);

        assertNotNull(new AcceptReject());
    }

}
