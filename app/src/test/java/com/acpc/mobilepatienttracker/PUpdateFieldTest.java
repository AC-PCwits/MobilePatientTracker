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
public class PUpdateFieldTest
{

    private PUpdateField activity;

    @Test
    public void checkNotNull() throws Exception
    {
        activity = Robolectric.buildActivity(PUpdateField.class).create().resume().get();
        assertNotNull(activity);
    }

    @Test
    public void checkName() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.FIRST_NAME);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkLName() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.LAST_NAME);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkID() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.ID);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkCell() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.CELLPHONE);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkNat() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.NATIONALITY);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkGender() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.GENDER);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkRace() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.RACE);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkAddr() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.ADDRESS);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkMar() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.MARITAL_STATUS);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkIll() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.ILLNESSES);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkAllergies() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.ALLERGIES);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkMedAid() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.MEDICAL_AID);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkEName() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.ECONTACT_NAME);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }

    @Test
    public void checkECell() throws Exception
    {
        Intent intent = new Intent(RuntimeEnvironment.application, PUpdateField.class);
        Bundle bundle = new Bundle();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        bundle.putSerializable("patient", new Patient("1","1","1","1",
                "1","1","1","1","1","1","1",
                arrayList,"1","1", "1"));
        bundle.putSerializable("field", PatientField.ECONTACT_CELLPHONE);

        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(PUpdateField.class, intent).create().resume().get();
    }


}