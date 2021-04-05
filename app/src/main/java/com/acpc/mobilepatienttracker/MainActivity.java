package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String TAG = "AC/PC";


        final TextView logbox = findViewById(R.id.logbox);
        Button add = findViewById(R.id.add);
        Button update = findViewById(R.id.update);
        Button remove = findViewById(R.id.remove);
        Button get = findViewById(R.id.get);
        Button getmultiple = findViewById(R.id.getmultiple);

        Button registration = findViewById(R.id.registration);
        Button add_doc = findViewById(R.id.add_doc);

        registration.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MainActivity.this,PRegistration.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

            }
        });

//////////////////////////////////////////////////////////////

        // All code below will run when the app is launched.
        // These are just instructions on how to use code to code
        // For all Cloud Firestore related stuff, we start of by getting our database (it works off our package name, so no login or authentication is required for now)

        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        // In Cloud Firestore, data is stored in collections. A collection is like a table. 'Collections' are really just folders on the firestore server.
        // Inside a collection there will be multiple documents.
        // Documents are just .json text files that act as entries.
        // In our case, every patient will have their own document which contains their ID, name, medical info etc
        // Also, firebase has a nice option to automatically name documents with a unique ID, so that should probably be used a primary key.

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logbox.setText("executing...");

                // ADDING A NEW ENTRY:
                // The following code adds a new document with a unique ID as its name, and puts a bunch of patient data on it.

                // Firestore lets you serialize custom objects to entry documents automatically. So I made an example custom class called Patient.
                // So first, create a Patient:
                ArrayList illnesses = new ArrayList<String>();
                illnesses.add("TB");
                illnesses.add("Bronchitis");

                Patient patient = new Patient(1, "Ada", illnesses);
/////////
                // Now we add it to a specified collection (table) in the database with database.collection().add()
                // This way will give the new document an auto-generated unique ID as the file name. This can be used like a primary key

                database.collection("patient-data") // specify the collection name here
                        .add(patient)
                        // Add a success listener so we can be notified if the operation was successfuly.
                        // i think success/failure listeners are optional, but if you don't use them you won't know if entry was actually added
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // If we are here, the app successfully connected to Firestore and added a new entry
                                Log.d(TAG, "SUCCESS: Added new document with ID: " + documentReference.getId());
                                logbox.setText("SUCCESS:\nAdded new document with ID: " + documentReference.getId());
                            }
                        })
                        // Add a failure listener so we can be notified if something does wrong
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // If we are here, the entry could not be added for some reason (e.g no internet connection)
                                Log.w(TAG, "ERROR: Failed to add document", e);
                                logbox.setText("ERROR:\nCould not add document. Here is what went wrong: \n" + e.getMessage());
                            }
                        });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logbox.setText("executing...");

                // UPDATING AN ENTRY
                // The following code tries to find a document called 'patient-1' inside the collection 'patient-data' and sets it ID to 10.

                // Updating works in the same way as adding
                // The following code updates the ID of the patient with document name 'patient-1' to 10

                // Create a DocumentReference so that we can specify which document we want to update.
                // Again, 'patient-data' is the collection name, and 'patient-1' is the document name
                DocumentReference patient_1  = database.collection("patient-data").document("patient-1");


                patient_1.update("ID", 10)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "SUCCESS: Updated document.");
                                logbox.setText("SUCCESS:\nUpdated document.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "ERROR:Could not update document: ", e);
                                logbox.setText("ERROR:\nCould not update document. Here is what went wrong: \n" + e.getMessage());
                            }
                        });
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logbox.setText("executing...");

                // REMOVING AN ENTRY
                // The following code removes the document named 'patient-2' from the database
                // Also works in the same way as adding and updating

                database.collection("patient-data").document("patient-2")
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "SUCCESS: Deleted document.");
                                logbox.setText("SUCCESS:\nDeleted document, or document did not exist in the first place.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "ERROR: Could not delete document. Here is what went wrong: \n", e);
                                logbox.setText("ERROR:\nCould not delete document. Here is what went wrong: \n" + e.getMessage());
                            }
                        });
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logbox.setText("executing...");

                // GETTING A SINGLE ENTRY (Not sure how useful this will be but it's here anyway):
                // The following code gets the document named 'patient-4' from the database

                // To get a single document from Firestore, we create a DocumentReference so that we can specify which document we want to update
                // Then we call .Get() which will retrieve the specified document with an OnComplete listener.
                // 'patient-data' is the collection name, and 'patient-4' is the document name
                DocumentReference patient_0 = database.collection("patient-data").document("patient-4");
                patient_0.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            // If we are here, the app successfully connected to Firestore.
                            DocumentSnapshot document = task.getResult();

                            // Now we will check if the document exists.
                            if (document.exists()) {

                                // We now have our document. We can convert it directly into our custom Patient object:
                                Patient patient = document.toObject(Patient.class);

                                // And now we can handle the data of this patient - e.g log their data:
                                logbox.setText("SUCCESS:\n\nPatient ID: " + patient.ID + " - " + patient.name + " - " + patient.illnesses);

                            } else {
                                Log.d(TAG, "ERROR: Document not found");
                                logbox.setText("ERROR:\nDocument not found");
                            }

                        } else {

                            // If the connection task failed for some reason (e.g no internet connection), check for errors in '4:Run' tab.
                            Log.d(TAG, "ERROR: Could not connect to Firestore. Here is what went wrong: ", task.getException());
                            logbox.setText("ERROR:\nGet query failed. Here is what went wrong:\n" + task.getException().getMessage());
                        }
                    }
                });
            }
        });

        getmultiple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logbox.setText("executing...");


                // GETTING MULTIPLE ENTRIES FROM COLLECTION

                database.collection("patient-data")
                        .whereEqualTo("ID", 1)  // You can specify an identifier here, or remove this line to get all patients in the collection
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    // If query task was successful, we can get a list of patients from it
                                    ArrayList<Patient> patients = Patient.GetPatientsFromQuery(task);

                                    // handle the list of patients in some way

                                    logbox.setText("SUCCESS:\n\n");

                                    if (patients.size() == 0)
                                    {
                                        logbox.setText((String) logbox.getText() + "Query succeeded but list was empty :)");
                                    }
                                    else
                                    {
                                        logbox.setText((String) logbox.getText() + "Patients:");
                                        for (Patient patient : patients)
                                        {
                                            logbox.setText((String) logbox.getText() + "\nPatient ID: " + patient.ID + " - " + patient.name + " - " + patient.illnesses);
                                        }
                                    }
                                } else {
                                    Log.d(TAG, "ERROR: Could not get documents from query", task.getException());
                                    logbox.setText("ERROR:\nGetMultiple query failed. Here is what went wrong:\n" + task.getException().getMessage());
                                }
                            }
                        });
            }
        });

        add_doc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logbox.setText("executing...");

                // ADDING A NEW ENTRY:
                // The following code adds a new document with a unique ID as its name, and puts a bunch of patient data on it.

                // Firestore lets you serialize custom objects to entry documents automatically. So I made an example custom class called Patient.
                // So first, create a Patient:

                Doctor doctor = new Doctor (8, "Tim", "Surgeon");


                // Now we add it to a specified collection (table) in the database with database.collection().add()
                // This way will give the new document an auto-generated unique ID as the file name. This can be used like a primary key

                database.collection("doctor-data") // specify the collection name here
                        .add(doctor)
                        // Add a success listener so we can be notified if the operation was successfuly.
                        // i think success/failure listeners are optional, but if you don't use them you won't know if entry was actually added
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // If we are here, the app successfully connected to Firestore and added a new entry
                                Log.d(TAG, "SUCCESS: Added new document with ID: " + documentReference.getId());
                                logbox.setText("SUCCESS:\nAdded new document with ID: " + documentReference.getId());
                            }
                        })
                        // Add a failure listener so we can be notified if something does wrong
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // If we are here, the entry could not be added for some reason (e.g no internet connection)
                                Log.w(TAG, "ERROR: Failed to add document", e);
                                logbox.setText("ERROR:\nCould not add document. Here is what went wrong: \n" + e.getMessage());
                            }
                        });
            }
        });



    }
}


// Here is the custom class Patient that is being serialized to the database
class Patient
{
    public int ID;
    public String name;
    public ArrayList<String> illnesses;

    public Patient(int ID, String name, ArrayList<String> illnesses)
    {
        this.ID = ID;
        this.name = name;
        this.illnesses = illnesses;
    }

    // VERY IMPORTANT: Java JSON deserialization needs a no-argument constructor in order to deserialize custom objects.
    // If you do not include one, your app will crash when you try to deserialize a custom class.
    public Patient()
    {
    }

    // ALSO VERY IMPORTANT: Haven't tested it in Java, but I am aware that in C# your custom class's properties need to all be public in order for JSON to serialize them
    // and if they are not public, JSON will serialize blank properties and not throw any errors (leaving you wondering why your data isn't saving to the database).
    // So just to be safe, don't forget to make your custom class's members public

    // This method is just for converting a query task with multiple entries into a list a patients for easier use
    public static ArrayList<Patient> GetPatientsFromQuery(Task<QuerySnapshot> task)
    {
        ArrayList patients = new ArrayList<Patient>();

        for (QueryDocumentSnapshot document : task.getResult())
        {
            patients.add(document.toObject(Patient.class));
        }

        return patients;
    }
}

class Doctor {
    public int doc_ID;
    public String name;
    public String doc_type;

    public Doctor(int doc_ID, String name, String doc_type) {
        this.doc_ID = doc_ID;
        this.name = name;
        this.doc_type = doc_type;
    }

    // VERY IMPORTANT: Java JSON deserialization needs a no-argument constructor in order to deserialize custom objects.
    // If you do not include one, your app will crash when you try to deserialize a custom class.
    public Doctor() {
    }

}




