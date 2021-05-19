package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
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
        Button reg_doc = findViewById(R.id.reg_doc);
        Button logout_btn = findViewById(R.id.btn_logout);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginPatient.class);
                startActivity(intent);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MainActivity.this, PRegistration.class); //moving from main screen to reg screen when clicking register button on main screen
                startActivity(start);

            }
        });


        reg_doc.setOnClickListener(new View.OnClickListener() { //what happens when you click the register button
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MainActivity.this, DRegistration.class); //moving from main screen to reg screen when clicking register button on main screen
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

        /*add.setOnClickListener(new View.OnClickListener() {
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
        });*/


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

     /*   get.setOnClickListener(new View.OnClickListener() {
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
        });*/

      /*  getmultiple.setOnClickListener(new View.OnClickListener() {
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
*/
       /* add_doc.setOnClickListener(new View.OnClickListener() {
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
        });*/



    }
}


// Here is the custom class Patient that is being serialized to the database

class Patient implements Serializable
{
    public String fname;
    public String fsurname;
    public String idno;
    public String cellno;
    public String nationality;
    public String gender;
    public String address;
    public String ename;
    public String econtact;
    public String race;
    public String mstatus;
    public ArrayList<String> cissues;
    public String medaid;
    public String allergies;
    //TODO: Add lastVisited to DB once logic for retrieving info has been completed
    public String lastVisited = "8/04/2021";


    // public Patient(String fname, String fsurname, String idno, String cellno, String nationality, String gender, String address, String ename, String econtact, String race, String mstatus, ArrayList cissues, String medaid, String allergies)
    public Patient (String fname, String fsurname, String idno, String cellno, String nationality, String gender, String address, String ename, String econtact, String race, String mstatus, ArrayList<String> cissues, String medaid, String allergies)
    {
        this.fname= fname;
        this.fsurname=  fsurname;
        this.idno= idno;
        this.cellno= cellno;
        this.nationality= nationality;
        this.gender= gender;
        this.address= address;
        this.ename=ename;
        this.econtact= econtact;
        this.race= race;
        this.mstatus= mstatus;
        this.cissues= cissues;
        this.medaid= medaid;
        this.allergies= allergies;
    }

    public Patient()
    {
    }

    public static String GetFieldName(PatientField field)
    {
        switch (field) {
            case FIRST_NAME:
                return "fname";
            case LAST_NAME:
                return "fsurname";
            case ID:
                return "idno";
            case CELLPHONE:
                return "cellno";
            case NATIONALITY:
                return "nationality";
            case GENDER:
                return "gender";
            case RACE:
                return "race";
            case ADDRESS:
                return "address";
            case MARITAL_STATUS:
                return "mstatus";
            case ILLNESSES:
                return "cissues";
            case ALLERGIES:
                return "allergies";
            case MEDICAL_AID:
                return "medaid";
            case ECONTACT_NAME:
                return "ename";
            case ECONTACT_CELLPHONE:
                return "econtact";
        }
        return "";
    }

    public Object GetFieldValue(PatientField field)
    {
        switch (field) {
            case FIRST_NAME:
                return this.fname;
            case LAST_NAME:
                return this.fsurname;
            case ID:
                return this.idno;
            case CELLPHONE:
                return this.cellno;
            case NATIONALITY:
                return this.nationality;
            case GENDER:
                return this.gender;
            case RACE:
                return this.race;
            case ADDRESS:
                return this.address;
            case MARITAL_STATUS:
                return this.mstatus;
            case ILLNESSES:
                return this.cissues;
            case ALLERGIES:
                return this.allergies;
            case MEDICAL_AID:
                return this.medaid;
            case ECONTACT_NAME:
                return this.ename;
            case ECONTACT_CELLPHONE:
                return this.econtact;
        }
        return null;
    }

    public String GetIllnessesString()
    {
        String illnesses = "";
        for (String illness : this.cissues)
        {
            if (!illness.equals(""))
            {
                illnesses += illness;
            }
        }

        return illnesses;
    }
}

class Doctor {
    public String ID;
    public String fname;
    public String lname;
    public String dob;
    public String doc_type;
    public String gender;
    public String email;
    public String cell_no;
    public String p_no; //practicing number,unique to each doctor
    public String p_length;
    public String uni_name;
    public ArrayList<String> patient_ID;


    public Doctor(String ID, String fname, String lname, String dob, String gender, String email, String p_length, String uni_name, String p_no, String doc_type, String cell_no) {

        this.ID = ID;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.doc_type = doc_type;
        this.gender = gender;
        this.email = email;
        this.cell_no = cell_no;
        this.p_no = p_no;
        this.p_length = p_length;
        this.uni_name = uni_name;
        this.patient_ID = new ArrayList<>();

    }

    // VERY IMPORTANT: Java JSON deserialization needs a no-argument constructor in order to deserialize custom objects.
    // If you do not include one, your app will crash when you try to deserialize a custom class.
    public Doctor (){

    }
    public static String GetfieldName(DoctorField field)
    {
        switch (field) {
            case FIRST_NAME:
                return "fname";
            case LAST_NAME:
                return "lname";
            case ID:
                return "ID";
            case DOB_TEXT:
                return "dob";
            case GENDER:
                return "gender";
            case Email:
                return "email";
            case CELL_TEXT:
                return "cell_no";
            case PRAC_NUM:
                return "p_no";
            case PRAC_LENGTH:
                return "p_length";
            case UNI_TEXT:
                return "uni_name";
            case SPEC_TEXT:
                return "doc_type";
        }
        return "";
    }

    public Object GetfieldValue(DoctorField field)
    {
        switch (field) {
            case FIRST_NAME:
                return this.fname;
            case LAST_NAME:
                return this .lname;
            case ID:
                return this.ID;
            case DOB_TEXT:
                return this.dob;
            case GENDER:
                return this.gender;
            case Email:
                return this.email;
            case CELL_TEXT:
                return this.cell_no;
            case PRAC_NUM:
                return this.p_no;
            case PRAC_LENGTH:
                return this.p_length;
            case UNI_TEXT:
                return this.uni_name;
            case SPEC_TEXT:
                return this.doc_type;
        }
        return null;
    }
}

enum PatientField
{
    FIRST_NAME,
    LAST_NAME,
    ID,
    CELLPHONE,
    NATIONALITY,
    GENDER,
    RACE,
    ADDRESS,
    MARITAL_STATUS,
    ILLNESSES,
    ALLERGIES,
    MEDICAL_AID,
    ECONTACT_NAME,
    ECONTACT_CELLPHONE
}
enum DoctorField{
    FIRST_NAME,
    LAST_NAME,
    ID,
    DOB_TEXT,
    GENDER,
    Email,
    CELL_TEXT,
    PRAC_NUM,
    PRAC_LENGTH,
    UNI_TEXT,
    SPEC_TEXT
}

class Bookings {

    public String pname;
    public String bookingdate;
    public String time;
    public String id; //need this for linking to patient
    public String doc_id;
    public String path;

    public Bookings(String pname, String id, String bookingdate, String time, String doc_id){
        this.pname = pname;
        this.bookingdate = bookingdate;
        this.id = id;
        this.doc_id = doc_id;
        this.time = time;
        path = "";
    }

    public Bookings(){

    }
}

class Consultation{
    public String pcase;
    public String psymptoms;
    public String pdiagnosis;
    public String pdate;
    public String ppatientID;
    public String pdoctorID;

    public Consultation(String pcase,String psymptoms, String pdiagnosis, String pdate, String ppatientID,String pdoctorID){
       this.pcase=pcase;
        this.psymptoms=psymptoms;
        this.pdiagnosis=pdiagnosis;
        this.pdate=pdate;
        this.ppatientID=ppatientID;
        this.pdoctorID=pdoctorID;
    }

    public Consultation (){

    }
}

class DType {
    public String type;

    public DType(String type) {

        this.type = type;
    }

    public DType() {

    }

}

class DInfo{

    public String fname;
    public String sname;
    public String exp;
    public String doc_ID;
    //public String qual;

    public DInfo(String fname, String sname, String exp, String doc_ID){
        this.fname=fname;
        this.sname=sname;
        this.exp=exp;
        this.doc_ID = doc_ID;

    }

    public DInfo(){

    }

}

/*class AcceptReject{
    public Bookings booking;
    public String accOrRej;

    public AcceptReject(Bookings booking, String accOrRej) {
        this.booking = booking;
        this.accOrRej = accOrRej;

    }

    public AcceptReject(){

    }

}*/

class AccOrRej{
    public String pname;
    public String bookingdate;
    public String time;
    public String id; //need this for linking to patient
    public String doc_id;
    public String accOrRej;

    public AccOrRej(String pname, String id, String bookingdate, String time, String doc_id, String accOrRej){
        this.pname = pname;
        this.bookingdate = bookingdate;
        this.id = id;
        this.doc_id = doc_id;
        this.time = time;
        this.accOrRej = accOrRej;
    }

    public AccOrRej(){

    }

}


class Appointment{

    public String docName;
    public String pname;
    public String bookingdate;
    public String time;
    public String id; //need this for linking to patient
    public String doc_id;
    public String status;


    public Appointment(String pname, String id, String bookingdate, String time, String doc_id,String docName, String status) {
        this.docName = docName;
        this.bookingdate = bookingdate;
        this.time = time;


        this.pname = pname;
        this.bookingdate = bookingdate;
        this.id = id;
        this.doc_id = doc_id;
        this.time = time;
        this.status = status;
    }

    public Appointment(){}
}
