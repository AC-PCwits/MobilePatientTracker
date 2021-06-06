package com.acpc.mobilepatienttracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import androidx.core.widget.NestedScrollView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDatePickerDialog;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)

public class DPatientDetailsTest {
    public DPatientDetails activity;

    @Before
    public void setUp()
    {
        Intent intent = new Intent(RuntimeEnvironment.application, DPatientDetails.class);
        Bundle bundle = new Bundle();
        ArrayList<String> ill = new ArrayList<>();

        bundle.putString("PATIENT_NAME", "John");
        bundle.putString("PATIENT_ID", "1");
        bundle.putString("PATIENT_CELL", "081");
        bundle.putString("PATIENT_NAT","RSA");
        bundle.putString("PATIENT_GENDER","Male");
        bundle.putString("PATIENT_ADDRESS","1 Avenue");
        bundle.putString("PATIENT_ENAME", "Jo");
        bundle.putString("PATIENT_ECONT", "061");
        bundle.putString("PATIENT_RACE", "Black");
        bundle.putString("PATIENT_MARRIED", "Single");
        bundle.putStringArrayList("PATIENT_ILLNESS", ill );
        bundle.putString("PATIENT_MEDAID", "Yes");
        bundle.putString("PATIENT_ALLERGIES", "Peanuts");

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(DPatientDetails.class, intent).create().resume().get();
    }

    @Test
    public void checkNotNull()
    {
        assertNotNull(activity);
    }

    @Test
    public void checkFloatingButton(){
        ExtendedFloatingActionButton extendedFloatingActionButton = activity.findViewById(R.id.dpl_past_consults);

        extendedFloatingActionButton.performClick();
        assertNotNull(extendedFloatingActionButton.performClick());

    }



}