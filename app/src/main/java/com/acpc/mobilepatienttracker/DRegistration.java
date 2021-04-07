package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;


//This code should not be confused with DoctorRegistration.java
//This code is how a doctor's login details will be linked to the database once they create their account

public class DRegistration extends AppCompatActivity {

    private Button register_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_registration);

        register_doc = findViewById(R.id.register_doc);

        register_doc.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                Intent start = new Intent(DRegistration.this,DoctorRegistration.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

            }
        });

    }


}


  /*  private void execute() {
        register_doc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent s = new Intent(DRegistration.this,DoctorRegistration.class);
            }
        });
    }

}*/

