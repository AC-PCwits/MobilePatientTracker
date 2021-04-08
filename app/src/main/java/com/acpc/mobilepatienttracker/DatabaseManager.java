package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseManager {

    FirebaseFirestore database;

    public DatabaseManager()
    {
        database = FirebaseFirestore.getInstance();
    }

    public static Boolean RegisterDoctor(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user= new User(name,email);
                            FirebaseDatabase.getInstance().getReference("Users")  //this adds all new users to a collection in the database called "Users"

                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())  //inside brackets will return ID for registered user

                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() { //To check if data has been inserted into database
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(PRegistration.this, "User Has Successfully Been Registered", Toast.LENGTH_LONG).show(); //shows message to tell user registration has been successful


                                        ///////Redirect to login now !

                                        ///////OR redirect to patient form

                                        Intent start = new Intent(PRegistration.this,PatientForm.class); //moving from main screen to reg screen when clicking register button on main screen
                                        startActivity(start);


                                    }// endif
                                    else{
                                        Toast.makeText(PRegistration.this, "Registration Unsuccessful, Try Again", Toast.LENGTH_LONG).show(); //unsuccessful registration message

                                    }
                                }// endof onComplete
                            });

                        }
                        else{
                            Toast.makeText(PRegistration.this, "Registration Unsuccessful, Try Again", Toast.LENGTH_LONG).show();

                        }//else
                    }
                });

        return false;
    }
    public static Boolean RegisterPatient(String name, int ID)
    {
        // TODO...
        return false;
    }

    public static Boolean IsSignedIn()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            return true;
        }

        return false;
    }

    public static String CurrentUserID()
    {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static Boolean IsRegistered()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {

        }

        return false;
    }
}
