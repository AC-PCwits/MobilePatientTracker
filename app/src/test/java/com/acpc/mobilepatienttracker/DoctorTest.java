package com.acpc.mobilepatienttracker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DoctorTest {

    private String id = "1234567891234";
    private String fname = "Bob";
    private String lname = "Smith";
    private String dob = "1999/02/10";
    private String doc_type = "neuro";
    private String gender = "male";
    private String email = "bobsmith@gmail.com";
    private String cell_no = "0702993441";
    private String p_no = "1234567";
    private String  p_length = "20";
    private String uni_name = "Wits";
    private ArrayList<String> patient_ID;

    private Doctor testDoc = new Doctor(id, fname, lname, dob, gender
            , email, p_length, uni_name, p_no, doc_type, cell_no);

    @Test
    public void testDocObject()
    {
        Doctor nullDoc = new Doctor();

        assertEquals(id, testDoc.ID);
        assertEquals(fname, testDoc.fname);
        assertEquals(lname, testDoc.lname);
        assertEquals(dob, testDoc.dob);
        assertEquals(gender, testDoc.gender);
        assertEquals(email, testDoc.email);
        assertEquals(p_length, testDoc.p_length);
        assertEquals(uni_name, testDoc.uni_name);
        assertEquals(p_no, testDoc.p_no);
        assertEquals(doc_type, testDoc.doc_type);
        assertEquals(cell_no, testDoc.cell_no);
        assertNotNull(nullDoc);
    }

    @Test
    public void getfieldName()
    {
        DoctorField field = null;
        assertEquals("fname", Doctor.GetfieldName(field.FIRST_NAME));
        assertEquals("lname", Doctor.GetfieldName(field.LAST_NAME));
        assertEquals("ID", Doctor.GetfieldName(field.ID));
        assertEquals("dob", Doctor.GetfieldName(field.DOB_TEXT));
        assertEquals("gender", Doctor.GetfieldName(field.GENDER));
        assertEquals("email", Doctor.GetfieldName(field.Email));
        assertEquals("cell_no", Doctor.GetfieldName(field.CELL_TEXT));
        assertEquals("p_no", Doctor.GetfieldName(field.PRAC_NUM));
        assertEquals("p_length", Doctor.GetfieldName(field.PRAC_LENGTH));
        assertEquals("uni_name", Doctor.GetfieldName(field.UNI_TEXT));
        assertEquals("doc_type", Doctor.GetfieldName(field.SPEC_TEXT));
    }

    @Test
    public void getfieldValue()
    {
        DoctorField field = null;
        assertEquals(fname, testDoc.GetfieldValue(field.FIRST_NAME));
        assertEquals(lname, testDoc.GetfieldValue(field.LAST_NAME));
        assertEquals(id, testDoc.GetfieldValue(field.ID));
        assertEquals(dob, testDoc.GetfieldValue(field.DOB_TEXT));
        assertEquals(gender, testDoc.GetfieldValue(field.GENDER));
        assertEquals(email, testDoc.GetfieldValue(field.Email));
        assertEquals(cell_no, testDoc.GetfieldValue(field.CELL_TEXT));
        assertEquals(p_no, testDoc.GetfieldValue(field.PRAC_NUM));
        assertEquals(p_length, testDoc.GetfieldValue(field.PRAC_LENGTH));
        assertEquals(uni_name, testDoc.GetfieldValue(field.UNI_TEXT));
        assertEquals(doc_type, testDoc.GetfieldValue(field.SPEC_TEXT));
    }
}