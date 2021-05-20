package com.acpc.mobilepatienttracker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PatientTest {

    private String fname = "John";
    private String fsurname = "Jones";
    private String idno = "4681256895234";
    private String cellno = "0482742645";
    private String nationality = "South African";
    private String gender = "male";
    private String address = "1 str";
    private String ename = "Jill";
    private String econtact = "0253697845";
    private String race = "black";
    private String mstatus = "married";
    private ArrayList<String> cissues = new ArrayList<String>();
    {
        cissues.add("TB");
    }
    private String medaid = "yes";
    private String allergies = "None";
    private String lastVisited = "8/04/2021";

    private Patient testPatient = new Patient(fname, fsurname, idno, cellno, nationality, gender, address
    , ename, econtact, race, mstatus, cissues, medaid, allergies,lastVisited);

    @Test
    public void testPatientObject()
    {
        assertEquals(fname, testPatient.fname);
        assertEquals(fsurname, testPatient.fsurname);
        assertEquals(idno, testPatient.idno);
        assertEquals(cellno, testPatient.cellno);
        assertEquals(nationality, testPatient.nationality);
        assertEquals(gender, testPatient.gender);
        assertEquals(address, testPatient.address);
        assertEquals(ename, testPatient.ename);
        assertEquals(econtact, testPatient.econtact);
        assertEquals(race, testPatient.race);
        assertEquals(mstatus, testPatient.mstatus);
        assertEquals(cissues, testPatient.cissues);
        assertEquals(medaid, testPatient.medaid);
        assertEquals(allergies, testPatient.allergies);
        assertEquals(lastVisited,testPatient.lastVisited);
        assertNotNull(new Patient());
    }

    @Test
    public void getFieldName()
    {
        PatientField patientField = null;
        assertEquals("fname", Patient.GetFieldName(patientField.FIRST_NAME));
        assertEquals("fsurname", Patient.GetFieldName(patientField.LAST_NAME));
        assertEquals("idno", Patient.GetFieldName(patientField.ID));
        assertEquals("cellno", Patient.GetFieldName(patientField.CELLPHONE));
        assertEquals("nationality", Patient.GetFieldName(patientField.NATIONALITY));
        assertEquals("gender", Patient.GetFieldName(patientField.GENDER));
        assertEquals("address", Patient.GetFieldName(patientField.ADDRESS));
        assertEquals("ename", Patient.GetFieldName(patientField.ECONTACT_NAME));
        assertEquals("econtact", Patient.GetFieldName(patientField.ECONTACT_CELLPHONE));
        assertEquals("race", Patient.GetFieldName(patientField.RACE));
        assertEquals("mstatus", Patient.GetFieldName(patientField.MARITAL_STATUS));
        assertEquals("cissues", Patient.GetFieldName(patientField.ILLNESSES));
        assertEquals("medaid", Patient.GetFieldName(patientField.MEDICAL_AID));
        assertEquals("allergies", Patient.GetFieldName(patientField.ALLERGIES));
    }

    @Test
    public void getFieldValue()
    {
        PatientField patientField = null;
        assertEquals(fname, testPatient.GetFieldValue(patientField.FIRST_NAME));
        assertEquals(fsurname, testPatient.GetFieldValue(patientField.LAST_NAME));
        assertEquals(idno, testPatient.GetFieldValue(patientField.ID));
        assertEquals(cellno, testPatient.GetFieldValue(patientField.CELLPHONE));
        assertEquals(nationality, testPatient.GetFieldValue(patientField.NATIONALITY));
        assertEquals(gender, testPatient.GetFieldValue(patientField.GENDER));
        assertEquals(address, testPatient.GetFieldValue(patientField.ADDRESS));
        assertEquals(ename, testPatient.GetFieldValue(patientField.ECONTACT_NAME));
        assertEquals(econtact, testPatient.GetFieldValue(patientField.ECONTACT_CELLPHONE));
        assertEquals(race, testPatient.GetFieldValue(patientField.RACE));
        assertEquals(mstatus, testPatient.GetFieldValue(patientField.MARITAL_STATUS));
        assertEquals(cissues, testPatient.GetFieldValue(patientField.ILLNESSES));
        assertEquals(medaid, testPatient.GetFieldValue(patientField.MEDICAL_AID));
        assertEquals(allergies, testPatient.GetFieldValue(patientField.ALLERGIES));
    }

    @Test
    public void getIllnessesString()
    {
        String illnesses = "";
        for (String illness : this.cissues)
        {
            if (!illness.equals(""))
            {
                illnesses += illness;
            }
        }

        assertEquals(illnesses, testPatient.GetIllnessesString());
    }
}