package com.acpc.mobilepatienttracker;

import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class DoctorConsultFormTest
{
    private DoctorConsultForm activity;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity(DoctorConsultForm.class).create().resume().get();
    }

    @Test
    public void checkNotNull() throws Exception
    {
        assertNotNull(activity);
    }

    @Test

    public void checkSave() throws  Exception{

        RadioGroup radioCase;
        RadioButton case_button;
        EditText symptoms, diagnosis;
        Button save;
        Doctor doc = new Doctor();

        save = activity.findViewById(R.id.buttonSave);

        radioCase=activity.findViewById(R.id.radioGroup4);
        symptoms=activity.findViewById(R.id.pConsultSymptoms);
        diagnosis=activity.findViewById(R.id.pConsultDiagnosis);

        save.performClick();
        assertEquals("Symptoms are empty", symptoms.getError().toString());
        symptoms.setText("fever");

        save.performClick();
        assertEquals("Diagnosis is empty", diagnosis.getError().toString());
        diagnosis.setText("covid");

        radioCase.check(R.id.radioButton2);

        assertNotNull(save.performClick());
    }
}
