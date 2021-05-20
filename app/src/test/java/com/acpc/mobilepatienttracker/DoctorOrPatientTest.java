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
public class DoctorOrPatientTest {

    private DoctorOrPatient activity;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.setupActivity(DoctorOrPatient.class);
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
        Button docButton = (Button) activity.findViewById(R.id.doctor);
        Button patButton = (Button) activity.findViewById(R.id.patient);

        docButton.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(LoginDoctor.class.getCanonicalName(), intent.getComponent().getClassName());

        patButton.performClick();
        intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(LoginPatient.class.getCanonicalName(), intent.getComponent().getClassName());

    }

}