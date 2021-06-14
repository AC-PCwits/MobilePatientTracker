package com.acpc.mobilepatienttracker;

import android.content.DialogInterface;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class DRegistrationTest
{

    private DRegistration activity;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity(DRegistration.class).create().resume().get();
    }

    @Test
    public void checkNotNull() throws Exception
    {
        assertNotNull(activity);
    }

    @Test
    public void checkRegistration() throws Exception
    {
        EditText nameText = activity.findViewById(R.id.inname);
        EditText emailText = activity.findViewById(R.id.inemail);
        EditText idText = activity.findViewById(R.id.inprac_no);
        EditText passText = activity.findViewById(R.id.inpassword);

        TextView button = activity.findViewById(R.id.register_doc);

        button.performClick();

        assertEquals("Name is Required", nameText.getError().toString());

        nameText.setText("Bob");

        button.performClick();

        assertEquals("Email is Required", emailText.getError().toString());

        emailText.setText("Bobgmail.com");

        button.performClick();

        assertEquals("Provide Valid Email", emailText.getError().toString());

        emailText.setText("Bob@gmail.com");

        button.performClick();

        assertEquals("Practicing Number is Required", idText.getError().toString());

        idText.setText("123456");

        button.performClick();

        assertEquals("Practice Number must be 7 digits", idText.getError().toString());

        idText.setText("1234567");

        button.performClick();

        assertEquals("Password is Required", passText.getError().toString());

        passText.setText("123");

        button.performClick();

        assertEquals("Password must be at least 6 characters", passText.getError().toString());

        passText.setText("___NULL_DEV___");

        assertNotNull(button.performClick());
    }

    @Test
    public void testAlert() throws Exception
    {
        TextView nameText = activity.findViewById(R.id.inname);
        TextView emailText = activity.findViewById(R.id.inemail);
        TextView idText = activity.findViewById(R.id.inprac_no);
        TextView passText = activity.findViewById(R.id.inpassword);

        TextView button = activity.findViewById(R.id.register_doc);

        nameText.setText("Bob");
        emailText.setText("Bob@gmail.com");
        idText.setText("1234567");
        passText.setText("___NULL_DEV___");

        button.performClick();

        AlertDialog dialog = (AlertDialog) ShadowDialog.getLatestDialog();
        assertTrue(dialog.isShowing());

        activity.uploadUserData(new User("Bob", "Bob@gmail.com", "1234567"), "___NULL_DEV___");

        assertNotNull(dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick());

    }

}