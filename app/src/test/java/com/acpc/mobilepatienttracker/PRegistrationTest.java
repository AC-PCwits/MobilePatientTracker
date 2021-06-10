package com.acpc.mobilepatienttracker;

import android.content.DialogInterface;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowDialog;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class PRegistrationTest
{

    private PRegistration pRegistration;

    @Before
    public void setUp() throws Exception
    {
        pRegistration = Robolectric.buildActivity(PRegistration.class).create().resume().get();
    }

    @Test
    public void checkNotNull() throws Exception
    {
        assertNotNull(pRegistration);
    }

    @Test
    public void checkRegistration() throws Exception
    {
        TextView nameText = pRegistration.findViewById(R.id.inname);
        TextView emailText = pRegistration.findViewById(R.id.inemail);
        TextView idText = pRegistration.findViewById(R.id.inid);
        TextView passText = pRegistration.findViewById(R.id.inpassword);

        TextView button = pRegistration.findViewById(R.id.register_p);

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

        assertEquals("ID Number is Required", idText.getError().toString());

        idText.setText("123456789123");

        button.performClick();

        assertEquals("ID Number must be 13 digits", idText.getError().toString());

        idText.setText("1234567891234");

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
        TextView nameText = pRegistration.findViewById(R.id.inname);
        TextView emailText = pRegistration.findViewById(R.id.inemail);
        TextView idText = pRegistration.findViewById(R.id.inid);
        TextView passText = pRegistration.findViewById(R.id.inpassword);

        TextView button = pRegistration.findViewById(R.id.register_p);

        nameText.setText("Bob");
        emailText.setText("Bob@gmail.com");
        idText.setText("1234567891234");
        passText.setText("___NULL_DEV___");

        button.performClick();

        AlertDialog dialog = (AlertDialog) ShadowDialog.getLatestDialog();
        assertTrue(dialog.isShowing());

        pRegistration.uploadUserData(new User("Bob", "Bob@gmail.com", "1234567"), "___NULL_DEV___");

        assertNotNull(dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick());

    }

}