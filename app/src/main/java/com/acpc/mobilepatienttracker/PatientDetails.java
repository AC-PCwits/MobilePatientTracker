package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientDetails extends Fragment {

    private LinearLayout background;
    private TextView info;
    private FloatingActionButton save;

    private Context context;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private DetailView firstname;
    private DetailView lastname;
    private DetailView id;
    private DetailView cellphone;
    private DetailView nationality;
    private DetailView gender;
    private DetailView race;
    private DetailView address;
    private DetailView mstatus;
    private DetailView illnesses;
    private DetailView allergies;
    private DetailView medicalaid;
    private DetailView econtact;
    private DetailView econtactno;

    private ArrayList<DetailView> allDetails;

    private Patient activeUser;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientDetails newInstance(String param1, String param2) {
        PatientDetails fragment = new PatientDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_patient_details, container, false);
        context = getContext();

        background = (LinearLayout) rootView.findViewById(R.id.pd_background);
        save = rootView.findViewById(R.id.pd_save);
        info = rootView.findViewById(R.id.pd_header);
        info.setText("Fetching user data...");

        firstname = new DetailView(PatientField.FIRST_NAME, context);
        lastname = new DetailView(PatientField.LAST_NAME, context);
        id = new DetailView(PatientField.ID, context);
        cellphone = new DetailView(PatientField.CELLPHONE, context);
        nationality = new DetailView(PatientField.NATIONALITY, context);
        gender = new DetailView(PatientField.GENDER, context);
        race = new DetailView(PatientField.RACE, context);
        address = new DetailView(PatientField.ADDRESS, context);
        mstatus = new DetailView(PatientField.MARITAL_STATUS, context);
        illnesses = new DetailView(PatientField.ILLNESSES, context);
        allergies = new DetailView(PatientField.ALLERGIES, context);
        medicalaid = new DetailView(PatientField.MEDICAL_AID, context);
        econtact = new DetailView(PatientField.ECONTACT_NAME, context);
        econtactno = new DetailView(PatientField.ECONTACT_CELLPHONE, context);

        allDetails = new ArrayList<DetailView>();
        allDetails.add(firstname);
        allDetails.add(lastname);
        allDetails.add(id);
        allDetails.add(cellphone);
        allDetails.add(nationality);
        allDetails.add(gender);
        allDetails.add(race);
        allDetails.add(address);
        allDetails.add(mstatus);
        allDetails.add(illnesses);
        allDetails.add(allergies);
        allDetails.add(medicalaid);
        allDetails.add(econtact);
        allDetails.add(econtactno);

        for (DetailView view : allDetails) {
            background.addView(view);
        }

        save.setVisibility(View.INVISIBLE);
        save.setEnabled(false);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (DetailView view : allDetails) {
                    if (view.isEditting) {
                        UpdateField(activeUser.idno, view.type, view.content.getText().toString());
                        view.isEditting = false;

                        view.edit.setImageResource(R.drawable.ic_baseline_edit_24);
                        view.content.setEnabled(false);
                        view.content.setBackgroundColor(Color.TRANSPARENT);

                        save.setVisibility(View.INVISIBLE);
                        save.setEnabled(false);
                        break;
                    }
                }

            }
        });

        //Patient details are pulled from databse and displayed using this method
        getUserData();

        return rootView;
    }

    public void getUserData() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        database.collection("patient-data").whereEqualTo("idno", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<Patient> patients = new ArrayList<>();

                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        patients.add(doc.toObject(Patient.class));
                                    }
                                    for (Patient patient : patients) {
                                        if (patient.idno.equals(ID)) {
                                            info.setText("Personal Details");

                                            firstname.content.setText(patient.fname);
                                            lastname.content.setText(patient.fsurname);
                                            id.content.setText(patient.idno);
                                            cellphone.content.setText(patient.cellno);
                                            nationality.content.setText(patient.nationality);
                                            gender.content.setText(patient.gender);
                                            race.content.setText(patient.race);
                                            address.content.setText(patient.address);
                                            mstatus.content.setText(patient.mstatus);
                                            illnesses.content.setText(patient.GetIllnessesString());
                                            allergies.content.setText(patient.allergies);
                                            medicalaid.content.setText(patient.medaid);
                                            econtact.content.setText(patient.ename);
                                            econtactno.content.setText(patient.econtact);

                                            activeUser = patient;

                                            for (DetailView view : allDetails) {
                                                view.edit.setVisibility(View.VISIBLE);
                                                view.originalText = view.content.getText().toString();
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void UpdateField(final String IDnumber, PatientField field, final Object newValue) {
        final String fieldName = Patient.GetFieldName(field);

        database.collection("patient-data")
                .whereEqualTo("idno", IDnumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String documentID = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentID = document.getId();
                            }
                            final String finalDocumentID = documentID;

                            if (documentID != "") {
                                DocumentReference patient = database.collection("patient-data").document(documentID);

                                for (DetailView view : allDetails) {
                                    if (view.content.getText().toString() != activeUser.GetFieldValue(view.type))

                                        patient.update(fieldName, newValue)

                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("PD", "SUCCESS: Updated field: " + fieldName + " for document ID: " + finalDocumentID);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // failed to update the given field for some reason
                                                        Log.w("PD", "ERROR: Could not update field: " + fieldName + "for document ID: " + finalDocumentID + ": ", e);
                                                        Toast.makeText(getContext(), "Could not save: failed to update details", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                }

                                Toast.makeText(getContext(), "Successfully saved details :)", Toast.LENGTH_LONG).show();
                                save.setEnabled(false);
                                save.setVisibility(View.INVISIBLE);


                                for (DetailView view : allDetails) {
                                    if (view.content.isFocused()) {
                                        view.content.clearFocus();
                                        view.content.setBackgroundColor(Color.TRANSPARENT);
                                    }
                                    view.content.setEnabled(false);
                                    view.originalText = view.content.getText().toString();
                                }
                            } else {
                                // do document was found with the given field
                                Log.w("PD", "QUERY ERROR: No document found with ID number: " + IDnumber);
                                Toast.makeText(getContext(), "Could not save: document not found with that ID number???", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            // query did not complete
                            Log.w("PD", "QUERY ERROR: query did not complete: " + task.getException().getMessage());
                            Toast.makeText(getContext(), "Could not save: query do not complete", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private class DetailView extends LinearLayout {
        public PatientField type;

        public TextView info;
        public EditText content;
        public ImageButton edit;

        String originalText = "";

        private Boolean isEditting = false;

        private DetailView(PatientField type, Context context) {
            super(context);

            this.type = type;

            info = new TextView(context);
            content = new EditText(context);
            edit = new ImageButton(context);

            this.addView(info);
            this.addView(content);
            this.addView(edit);

            info.setTextSize(16);
            info.setGravity(Gravity.CENTER);

            content.setTextSize(20);
            content.setTextColor(Color.parseColor("#565c5c"));
            final Drawable originalContentBackground = content.getBackground();
            content.setBackgroundColor(Color.TRANSPARENT);
            content.setEnabled(false);
            content.setText("...");

            edit.setImageResource(R.drawable.ic_baseline_edit_24);
            edit.setBackgroundColor(Color.TRANSPARENT);
            edit.setColorFilter(Color.parseColor("#9eaeb0"));
            edit.setVisibility(View.INVISIBLE);

            info.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            content.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            edit.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            switch (type) {
                case FIRST_NAME:
                    info.setText("First name: ");
                    content.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                    break;
                case LAST_NAME:
                    info.setText("Last name: ");
                    break;
                case ID:
                    info.setText("ID number: ");
                    content.setEnabled(false);
                    break;
                case CELLPHONE:
                    info.setText("Cell number: ");
                    break;
                case NATIONALITY:
                    info.setText("Nationality: ");
                    break;
                case GENDER:
                    info.setText("Gender: ");
                    break;
                case RACE:
                    info.setText("Race: ");
                    break;
                case ADDRESS:
                    info.setText("Address: ");
                    break;
                case MARITAL_STATUS:
                    info.setText("Marital status: ");
                    break;
                case ILLNESSES:
                    info.setText("Illnesses: ");
                    break;
                case ALLERGIES:
                    info.setText("Allergies: ");
                    break;
                case MEDICAL_AID:
                    info.setText("Medical aid: ");
                    break;
                case ECONTACT_NAME:
                    info.setText("Emergency contact name: ");
                    break;
                case ECONTACT_CELLPHONE:
                    info.setText("Emergency contact number: ");
                    break;
            }

            edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Boolean isAnotherViewBeingEdited = false;
                    for (DetailView view : allDetails)
                    {
                        if (view.isEditting)
                        {
                            isAnotherViewBeingEdited = true;
                        }
                    }



                    if (!isAnotherViewBeingEdited)
                    {
                        isEditting = true;

                        content.setEnabled(true);
                        content.setBackground(originalContentBackground);
                        content.setCursorVisible(true);
                        content.setSelection(content.getText().length());
                        content.requestFocus();
                        edit.setImageResource(R.drawable.ic_baseline_cancel_24);

                        save.setVisibility(VISIBLE);
                        save.setEnabled(true);
                    }
                    else if (isEditting == true)
                    {
                        isEditting = false;

                        edit.setImageResource(R.drawable.ic_baseline_edit_24);
                        content.setEnabled(false);
                        content.setBackgroundColor(Color.TRANSPARENT);
                        content.setText(originalText);

                        save.setVisibility(INVISIBLE);
                        save.setEnabled(false);
                    }
                }
            });
        }
    }
}