package com.acpc.mobilepatienttracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDatePickerDialog;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class DoctorFormTest
{

    public DoctorForm activity;

    @Before
    public void setUp()
    {
        Intent intent = new Intent(RuntimeEnvironment.application, DoctorForm.class);
        Bundle bundle = new Bundle();

        bundle.putString("PID", "1234567");
        bundle.putString("EMAIL", "jnfjk;njnfnf@gmail.com");

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(DoctorForm.class, intent).create().resume().get();
    }

    @Test
    public void checkNotNull()
    {
        assertNotNull(activity);
    }

    @Test
    public void checkButtons()
    {
        RadioGroup gender_group = activity.findViewById(R.id.genderGroup);
        TextView first_name = activity.findViewById(R.id.first_name);
        TextView last_name = activity.findViewById(R.id.last_name);
        TextView date_of_birth = activity.findViewById(R.id.date_of_birth);
        TextView id_number = activity.findViewById(R.id.IDnu);
        TextView length_practice = activity.findViewById(R.id.LengthOfPractice);
        TextView institution= activity.findViewById(R.id.Institution);
        TextView doctorSpec = activity.findViewById(R.id.Doctor_speciality);
        TextView cellNum = activity.findViewById(R.id.Cellnu);

        Button button = activity.findViewById(R.id.signUpButtonId);

        button.performClick();
        assertEquals("Empty first name", first_name.getError().toString());
        first_name.setText("___NULL_DEV___");

        button.performClick();
        assertEquals("Empty last name", last_name.getError().toString());
        last_name.setText("Smith");

        button.performClick();
        assertEquals("Select date of birth", date_of_birth.getError().toString());
        date_of_birth.setText("12/04/2021");

        button.performClick();
        assertEquals("Date format should be 'yyyy/mm/dd", date_of_birth.getError().toString());
        date_of_birth.setText("2021/04/12");

        button.performClick();
        assertEquals("Empty ID number", id_number.getError().toString());
        id_number.setText("1234567891234");

        button.performClick();
        assertEquals("Empty length of practice", length_practice.getError().toString());
        length_practice.setText("12");

        button.performClick();
        assertEquals("Empty name of institution", institution.getError().toString());
        institution.setText("Wits");

        button.performClick();
        assertEquals("Empty Doctor speciality", doctorSpec.getError().toString());
        doctorSpec.setText("Neurologist");

        button.performClick();
        assertEquals("Empty cell number", cellNum.getError().toString());
        cellNum.setText("0215647895");

        gender_group.check(R.id.radioMaleButton);

        assertNotNull(button.performClick());

    }

}