package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button btnLogin;
    FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btn_login);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser != null) {
                    Toast.makeText(LoginPatient.this, "You are logged in.",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginPatient.this, MainActivity.class);
                    startActivity(i);
                }

                else{
                    Toast.makeText(LoginPatient.this, "Please log in.",Toast.LENGTH_SHORT).show();
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
                                Intent intent = new Intent(LoginPatient.this , MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginPatient.this, "Error occurred.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

}
