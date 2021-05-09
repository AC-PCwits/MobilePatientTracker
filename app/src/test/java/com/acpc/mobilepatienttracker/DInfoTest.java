package com.acpc.mobilepatienttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class DInfoTest
{
    public String fname;
    public String sname;
    public String exp;

    @Test
    public void testDInfoObject()
    {
        DInfo dInfo = new DInfo(fname, sname, exp);

        assertEquals(fname, dInfo.fname);
        assertEquals(sname, dInfo.sname);
        assertEquals(exp, dInfo.exp);
        assertNotNull(new DInfo());
    }
}