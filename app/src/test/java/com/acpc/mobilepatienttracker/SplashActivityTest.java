package com.acpc.mobilepatienttracker;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class SplashActivityTest
{

    private SplashActivity activity;

    @Before
    public void setUp()
    {
        activity = Robolectric.buildActivity(SplashActivity.class).create().resume().get();
    }

    @Test
    public void checkNotNull()
    {
        assertNotNull(activity);
    }

}