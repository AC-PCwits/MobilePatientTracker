package com.acpc.mobilepatienttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest
{
    private String fname;
    private String email;
    private String id;

    @Test
    public void userObjectTest()
    {
        fname = "Bob";
        email = "Bob@gmail.com";
        id = "4657981234568";

        User testUser = new User(fname, email, id);
        User nullUser = new User();

        assertEquals(fname, testUser.fname);
        assertEquals(email, testUser.email);
        assertEquals(id, testUser.id);
        assertNotNull(nullUser);
    }

}