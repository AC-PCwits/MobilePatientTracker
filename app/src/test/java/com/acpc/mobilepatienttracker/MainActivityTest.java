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

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class MainActivityTest
{
    public MainActivity activity;

    @Before
    public void setUp()
    {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void checkNotNull()
    {
        assertNotNull(activity);
    }

    @Test
    public void testButtonNewIntent()
    {
        Button test = (Button) activity.findViewById(R.id.btn_logout);

        test.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(LoginPatient.class.getCanonicalName(), intent.getComponent().getClassName());


        test = (Button) activity.findViewById(R.id.reg_doc);
        test.performClick();
        intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(DRegistration.class.getCanonicalName(), intent.getComponent().getClassName());

        test = (Button) activity.findViewById(R.id.registration);
        test.performClick();
        intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(PRegistration.class.getCanonicalName(), intent.getComponent().getClassName());

    }

}