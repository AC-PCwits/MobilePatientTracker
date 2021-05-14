package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
//@Config(manifest = Config.NONE)

public class DBookingDetailsTest {

    private DBookingDetails activity;

    @Before
    public void setUp() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, DBookingDetails.class);
        Bundle bundle = new Bundle();

        bundle.putString("PATIENT_NAME", "Bob");
        bundle.putString("PATIENT_ID", "1");
        bundle.putString("BOOKING_DATE", "1");
        bundle.putString("BOOKING_TIME", "1");

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(DBookingDetails.class, intent).create().resume().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCorrectAppName() throws Exception {
        String title = activity.getResources().getString(R.string.app_name);
        assertThat(title, equalTo("MobilePatientTracker"));
    }

    @Test
    public void buttonStartsNewActivity() throws Exception
    {
        Button accept = (Button) activity.findViewById(R.id.accept);
        Button reject = (Button) activity.findViewById(R.id.reject);

        accept.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(DoctorFragActivity.class.getCanonicalName(), intent.getComponent().getClassName());

        reject.performClick();
        intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(DoctorFragActivity.class.getCanonicalName(), intent.getComponent().getClassName());

    }



}