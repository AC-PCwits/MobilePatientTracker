package com.acpc.mobilepatienttracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PRegistration extends AppCompatActivity implements View.OnClickListener{

    private EditText inname, inemail, inpassword, in_id;
    private Button reg;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_registration);

        mAuth = FirebaseAuth.getInstance();
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
        final String name = user.fname;
        final String email = user.email;
        final String id_no = user.id;

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user= new User(name,email,id_no);
                            FirebaseDatabase.getInstance().getReference("Patient")

                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(PRegistration.this, "You have successfully been registered as a patient.", Toast.LENGTH_LONG).show();

                                        Intent start = new Intent(PRegistration.this, PatientForm.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("PID", id_no);
                                        bundle.putString("EMAIL", email);
                                        start.putExtras(bundle);
                                        startActivity(start);
                                        finish();

                                    }
                                    else{
                                        Toast.makeText(PRegistration.this, "Patient Registration Unsuccessful, Try Again", Toast.LENGTH_LONG).show();
                                        Log.d("PATIENT", task.getException().getMessage());

                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(PRegistration.this, "Patient Registration Unsuccessful, Try Again", Toast.LENGTH_LONG).show();
                            Log.d("PATIENT", task.getException().getMessage());
                        }
                    }
                });
    }



}