package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class PatientFormTest
{

    private PatientForm activity;

    @Before
    public void setUp() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PatientForm.class);
        Bundle bundle = new Bundle();
        bundle.putString("PID", "1234567891234");
        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PatientForm.class, intent).create().resume().get();
    }

    @Test
    public void checkNotNull() throws Exception
    {
        assertNotNull(activity);
    }

    @Test
    public void checkSubmit() throws Exception
    {
        Button submit = activity.findViewById(R.id.submit);

        RadioGroup radioRace= activity.findViewById(R.id.radioGroup);
        RadioGroup radioMarital= activity.findViewById(R.id.radioGroup2);
        RadioGroup radioGender= activity.findViewById(R.id.rgpGender);

        EditText pname= activity.findViewById(R.id.pname);
        EditText psurname= activity.findViewById(R.id.psurname);
        EditText pcell= activity.findViewById(R.id.pCell);
        EditText pNationality= activity.findViewById(R.id.pNationality);
        EditText pAddress= activity.findViewById(R.id.pAddress);
        EditText pemname= activity.findViewById(R.id.pemname);
        EditText pemcell= activity.findViewById(R.id.pemcell);
        EditText Allergies= activity.findViewById(R.id.Allergies);
        CheckBox chkMedaid= activity.findViewById(R.id.chkMedaid);
        CheckBox chkHIV= activity.findViewById(R.id.chkHIV);
        CheckBox chkTB= activity.findViewById(R.id.chkTB);
        CheckBox chKDiabetes= activity.findViewById(R.id.chkDiabetes);
        CheckBox chkHyp= activity.findViewById(R.id.chkHyp);
        CheckBox chkNone= activity.findViewById(R.id.chkNone);

        radioGender.check(R.id.radFemale);
        radioMarital.check(R.id.radMarried);
        radioRace.check(R.id.radBlack);

        submit.performClick();
        assertEquals("Empty first name", pname.getError().toString());
        pname.setText("___NULL_DEV___");

        submit.performClick();
        assertEquals("Empty last name", psurname.getError().toString());
        psurname.setText("Smith");

        submit.performClick();
        assertEquals("Enter cell number", pcell.getError().toString());
        pcell.setText("1230456");

        submit.performClick();
        assertEquals("Enter Nationality", pNationality.getError().toString());
        pNationality.setText("SA");

        submit.performClick();
        assertEquals("Enter Address", pAddress.getError().toString());
        pAddress.setText("12 street");

        submit.performClick();
        assertEquals("Emergency Contact is required", pemname.getError().toString());
        pemname.setText("John");

        submit.performClick();
        assertEquals("Emergency Contact Number is required", pemcell.getError().toString());
        pemcell.setText("123");

        submit.performClick();
        assertEquals("Select a Common Issue option!", ShadowToast.getTextOfLatestToast());
        chKDiabetes.setChecked(true);
        chkHIV.setChecked(true);
        chkHyp.setChecked(true);
        chkNone.setChecked(true);
        chkTB.setChecked(true);

        activity.checkAid(activity.getCurrentFocus());

        chkMedaid.setChecked(true);

        assertNotNull(submit.performClick());



    }

}