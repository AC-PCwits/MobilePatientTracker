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

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)

public class DoctorConsultationListTest {



    public DoctorConsultationList activity;

    @Before
    public void setup(){

        Intent intent = new Intent(RuntimeEnvironment.application, DoctorConsultationList.class);
        Bundle bundle = new Bundle();

        bundle.putString("PATIENT_ID", "1234567891234");

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(DoctorConsultationList.class, intent).create().resume().get();
    }

    @Test
    public void checkNotNull() throws Exception {

        assertNotNull(activity);
    }

    @Test
    public void checkList() throws Exception {

        ArrayList<Consultation> testList = new ArrayList<>();
        testList.add(new Consultation("covid" , "cough" , "covid" , "10/25/2021" , "1234567891234" , "1258963"));

        activity.buildRecyclerView(testList);
        assertNotNull(activity);


    }

}