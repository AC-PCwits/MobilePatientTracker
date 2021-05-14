package com.acpc.mobilepatienttracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = Build.VERSION_CODES.O_MR1)
public class DoctorFormTest
{

//    public DoctorForm activity;
//
//    @Before
//    public void setUp()
//    {
//        Intent intent = new Intent(RuntimeEnvironment.application, DoctorForm.class);
//        Bundle bundle = new Bundle();
//
//        bundle.putString("PID", "1234567");
//        bundle.putString("EMAIL", "jnfjk;njnfnf@gmail.com");
//
//        intent.putExtras(bundle);
//
//        activity = Robolectric.buildActivity(DoctorForm.class, intent).create().resume().get();
//    }
//
//    @Test
//    public void checkNotNull()
//    {
//        assertNotNull(activity);
//    }
//
//    @Test
//    public void checkButtons()
//    {
//        TextView test = activity.findViewById(R.id.date_of_birth);
//
//        test.performClick();
//        DatePickerDialog dialog = (DatePickerDialog) ShadowDatePickerDialog.getLatestAlertDialog();
//        dialog.updateDate(2020, 10, 10);
//        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).performClick();
//        test.setText("2020-10-10");
//        assertEquals("2020-10-10", test.getText().toString());
//    }

}