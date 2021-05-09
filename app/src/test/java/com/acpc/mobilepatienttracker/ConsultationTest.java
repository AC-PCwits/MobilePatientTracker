package com.acpc.mobilepatienttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConsultationTest
{
    public String pcase = "12";
    public String psymptoms = "fever";
    public String pdiagnosis = "flu";
    public String pdate = "2021/05/04";
    public String ppatientID = "4657981235648";
    public String pdoctorID = "1234567";

    private Consultation testCon = new Consultation(pcase, psymptoms, pdiagnosis, pdate, ppatientID, pdoctorID);

    @Test
    public void testConsultObject()
    {
        assertEquals(pcase, testCon.pcase);
        assertEquals(psymptoms, testCon.psymptoms);
        assertEquals(pdiagnosis, testCon.pdiagnosis);
        assertEquals(pdate, testCon.pdate);
        assertEquals(ppatientID, testCon.ppatientID);
        assertEquals(pdoctorID, testCon.pdoctorID);
        assertNotNull(new Consultation());
    }
}