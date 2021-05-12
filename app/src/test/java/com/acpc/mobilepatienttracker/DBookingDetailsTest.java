package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
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
        activity = Robolectric.setupActivity(DBookingDetails.class);
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