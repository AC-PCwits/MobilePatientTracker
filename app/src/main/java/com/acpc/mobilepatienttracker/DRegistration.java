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


//This code should not be confused with DoctorRegistration.java
//This code is how a doctor's login details will be linked to the database once they create their account

public class DRegistration extends AppCompatActivity implements View.OnClickListener {

    private Button reg;

    private EditText inname, inemail, inpassword, inprac_no;
    private FirebaseAuth authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_registration);

        authorization = FirebaseAuth.getInstance();
        inname= findViewById(R.id.inname);
        inemail= findViewById(R.id.inemail);
        inpassword= findViewById(R.id.inpassword);
        inprac_no = findViewById(R.id.inprac_no);
        reg = findViewById(R.id.register_doc);
        reg.setOnClickListener(this);

        inprac_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_doc:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        Log.d("DOCTOR", "Registering doctor...");

        final String name= inname.getText().toString().trim();
        final String email= inemail.getText().toString().trim();
        final String prac_no = inprac_no.getText().toString().trim();
        String password= inpassword.getText().toString().trim();



        if(name.isEmpty()){
            inname.setError("Name is Required");
            inname.requestFocus();
            return;
        }

        if(email.isEmpty()){
            inemail.setError("Email is Required");
            inemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inemail.setError("Provide Valid Email");
            inemail.requestFocus();
            return;
        }

        if(prac_no.isEmpty()){
            inprac_no.setError("Practicing Number is Required");
            inprac_no.requestFocus();
        }

        if(prac_no.length()!=7){
            inprac_no.setError("Practice Number must be 7 digits");
            inprac_no.requestFocus();
        }

        if(password.isEmpty()){
            inpassword.setError("Password is Required");
            inpassword.requestFocus();
            return;
        }

        if(password.length()<6){  //firebase requires password with min 6 characters
            inpassword.setError("Min Length = 6");
            inpassword.requestFocus();
            return;
        }

        openDialog(new User(name,email,prac_no), password);

    }

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
        final String prac_no = user.id;

        authorization.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user= new User(name,email,prac_no);
                            FirebaseDatabase.getInstance().getReference("Doctors")

                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(DRegistration.this, "You have successfully been registered as a doctor.", Toast.LENGTH_LONG).show();

                                        Intent start = new Intent(DRegistration.this, DoctorForm.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("PID", prac_no);
                                        bundle.putString("EMAIL", email);
                                        start.putExtras(bundle);
                                        startActivity(start);
                                        finish();

                                    }
                                    else{
                                        Toast.makeText(DRegistration.this, "Doctor Registration Unsuccessful, Try Again", Toast.LENGTH_LONG).show();
                                        Log.d("DOCTOR", task.getException().getMessage());

                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(DRegistration.this, "Doctor Registration Unsuccessful, Try Again", Toast.LENGTH_LONG).show();
                            Log.d("DOCTOR", task.getException().getMessage());
                        }
                    }
                });
    }


}
