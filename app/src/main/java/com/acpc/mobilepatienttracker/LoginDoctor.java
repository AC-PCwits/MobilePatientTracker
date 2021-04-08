package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginDoctor extends AppCompatActivity {

    EditText email , password;
    Button btnLogin;
    TextView d_signup , textViewReset;
    FirebaseAuth mAuth;
    EditText inputEmail;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextDocEmail);
        password = findViewById(R.id.editTextDocPassword);
        btnLogin = findViewById(R.id.btnDocLogin);
        d_signup = findViewById(R.id.textViewDocSignUp);
        textViewReset = findViewById(R.id.textViewResetDocPass);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser != null) {
                    Toast.makeText(LoginDoctor.this, "You are logged in.",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginDoctor.this, DoctorForm.class);
                    startActivity(i);
                }

                else{
                    Toast.makeText(LoginDoctor.this, "Please log in.",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml = email.getText().toString();
                String pswrd = password.getText().toString();

                if(eml.isEmpty()){
                    email.setError("Please enter Email.");
                    email.requestFocus();
                }
                else if(pswrd.isEmpty()){
                    password.setError("Please enter Password");
                    password.requestFocus();
                }
                else if(eml.isEmpty() && pswrd.isEmpty()){
                    Toast.makeText(LoginDoctor.this, "Please enter details.",Toast.LENGTH_SHORT).show();
                }
                else if(!(eml.isEmpty() && pswrd.isEmpty())){
                    mAuth.signInWithEmailAndPassword(eml,pswrd).addOnCompleteListener(LoginDoctor.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginDoctor.this, "Login error, Please try again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent = new Intent(LoginDoctor.this , DoctorForm.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginDoctor.this, "Error occurred.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        d_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginDoctor.this , DRegistration.class);
                startActivity(intent);
            }
        });

        inputEmail = (EditText) findViewById(R.id.editTextDocEmail);
        textViewReset = (TextView)findViewById(R.id.textViewResetDocPass);
        mAuth = FirebaseAuth.getInstance();
        textViewReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                if(email.isEmpty()){
                    inputEmail.setError("Password is Required");
                    inputEmail.requestFocus();
                    return;
                }
                mAuth.sendPasswordResetEmail(email)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginDoctor.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginDoctor.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

}