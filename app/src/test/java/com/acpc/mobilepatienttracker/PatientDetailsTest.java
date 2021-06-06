package com.acpc.mobilepatienttracker;

import android.os.Build;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class PatientDetailsTest
{
    private PatientFragActivity activity;
    private PatientDetails fragment = new PatientDetails();

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity(PatientFragActivity.class)
                .create()
                .start()
                .resume()
                .get();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
    }

    @Test
    public void checkNotNull()
    {
        assertNotNull(activity);
        assertNotNull(fragment);
    }
}