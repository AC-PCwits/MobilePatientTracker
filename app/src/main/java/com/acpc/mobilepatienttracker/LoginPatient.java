package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPatient extends AppCompatActivity {

    EditText email , password;
    TextView btnLogin;
    TextView p_signup , btnReset;
    FirebaseAuth mAuth;
    EditText inputEmail;


    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_plogin);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextPEmail);
        password = findViewById(R.id.editTextPPassword);
        btnLogin = findViewById(R.id.btn_login);
        p_signup = findViewById(R.id.textViewPSignUp);
        btnReset = findViewById(R.id.textViewResetPPass);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser != null) {
                    Toast.makeText(LoginPatient.this, "You are logged in.",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginPatient.this, PatientFragActivity.class);
                    startActivity(i);
                    finish();
                }

                else{
                    Toast.makeText(LoginPatient.this, "Please log in.",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eml = email.getText().toString();
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
                    Toast.makeText(LoginPatient.this, "Please enter details.",Toast.LENGTH_SHORT).show();
                }
                else if(!(eml.isEmpty() && pswrd.isEmpty())){
                    mAuth.signInWithEmailAndPassword(eml,pswrd).addOnCompleteListener(LoginPatient.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginPatient.this, "Login error, Please try again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent = new Intent(LoginPatient.this , PatientFragActivity.class);
                                startActivity(intent);
                                Bundle bundle = new Bundle();
                                bundle.putString("USER_EMAIL", eml);
                                intent.putExtras(bundle);
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginPatient.this, "Error occurred.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        p_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPatient.this , PRegistration.class);
                startActivity(intent);
                finish();
            }
        });

        inputEmail = (EditText) findViewById(R.id.editTextPEmail);
        btnReset = (TextView)findViewById(R.id.textViewResetPPass);
        mAuth = FirebaseAuth.getInstance();
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                if(email.isEmpty()){
                    inputEmail.setError("Email is Required");
                    inputEmail.requestFocus();
                    return;
                }
                mAuth.sendPasswordResetEmail(email)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginPatient.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginPatient.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

}
