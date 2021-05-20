package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class DocTypeListTest
{
    private DocTypeList activity;

    @Test
    public void checkNotNull() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, DocTypeList.class);

        Bundle bundle = new Bundle();

        bundle.putString("DOC_TYPE", "___NULL_DEV___");

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(DocTypeList.class, intent).create().start().resume().get();

        assertNotNull(activity);
    }

    @Test
    public void checkRecycler() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, DocTypeList.class);

        Bundle bundle = new Bundle();

        bundle.putString("DOC_TYPE", "__EXAMPLE_BUILD___");

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(DocTypeList.class, intent).create().start().resume().get();

        assertNotNull(activity);
    }
}