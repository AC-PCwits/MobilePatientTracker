package com.acpc.mobilepatienttracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PRegistration extends AppCompatActivity implements View.OnClickListener{

    private EditText inname, inemail, inpassword, in_id;
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_registration);

        inname= findViewById(R.id.inname);
        inemail= findViewById(R.id.inemail);
        inpassword= findViewById(R.id.inpassword);
        in_id = findViewById(R.id.inid);
        reg = findViewById(R.id.reg);
        reg.setOnClickListener(this);

        in_id.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg:
                registerUser();
                break;
        }
    }
    private void registerUser() {
        //taking in input for email,name,password and converting to string for database to understand
        final String name = inname.getText().toString().trim();
        final String email = inemail.getText().toString().trim();
        final String id_no = in_id.getText().toString().trim();
        String password = inpassword.getText().toString().trim();

        //these if statements ensure that none of the inputs fields(name,email,password) is left empty when registering
        if (name.isEmpty()) {
            inname.setError("Name is Required");
            inname.requestFocus(); //this allows the error message to be viewed for a long enough time to read
            return;
        }

        if (email.isEmpty()) {
            inemail.setError("Email is Required");
            inemail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { //ensures that authentic email is entered (with @ sign etc)
            inemail.setError("Provide Valid Email");
            inemail.requestFocus();
            return;
        } else if (id_no.isEmpty()) {
            in_id.setError("ID Number is Required");
            in_id.requestFocus();
        } else if (id_no.length() != 13) {
            in_id.setError("ID Number must be 13 digits");
            in_id.requestFocus();
        } else if (password.isEmpty()) {
            inpassword.setError("Password is Required");
            inpassword.requestFocus();
            return;
        } else if (password.length() < 6) {  //firebase requires password with min 6 characters
            inpassword.setError("Password must be at least 6 characters");
            inpassword.requestFocus();
            return;
        }
        openDialog(new User(name,email,id_no), password);

    }//end of register user

    public void openDialog(final User user, final String password)
    {
        WebView webView = new WebView(this);
        webView.loadUrl("file:///android_asset/Privacy.html");
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Privacy Policy")
                .setView(webView)
                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        uploadUserData(user, password);
                    }
                });
        builder.show();
    }

    public void uploadUserData(User user, String password)
    {
        FirebasePatient firebasePatient = new FirebasePatient(user, "Patient", password, getApplicationContext());
        firebasePatient.patientRealtimeReg();
    }



}