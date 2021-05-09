package com.acpc.mobilepatienttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class DTypeTest
{
    public String type = "neuro";

    @Test
    public void testDType()
    {
        DType dType = new DType(type);

        assertEquals(type, dType.type);
        assertNotNull(new DType());
    }
}