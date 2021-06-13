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

        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());

        loadingDialog.startLoading();


        save = rootView.findViewById(R.id.pd_save);


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

        //Patient details are pulled from database and displayed using this method
        getUserData();

        loadingDialog.dismiss();

        return rootView;
    }

    public void getUserData()
    {
        if(firstname.content.getText().toString().equals("___NULL_DEV___"))
        {
            return;
        }
        FirebasePatient firebasePatient = new FirebasePatient();
        firebasePatient.getUserData(new FirebasePatient.FirebaseCallback(){
            @Override
            public void onResponse(ArrayList<Patient> patients)
            {
                for (Patient patient : patients)
                {

                    activeUser = patient;

                    background =(LinearLayout) getView().findViewById(R.id.dd_backround);
                    background.addView(firstname);
                    firstname.content.setText(activeUser.fname);
                    firstname.content.setTextSize(20);
                    firstname.edit.setVisibility(View.VISIBLE);
                    firstname.originalText = firstname.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.Iden);
                    background.addView(id);
                    id.content.setText(activeUser.idno);
                    id.content.setTextSize(20);
                    id.edit.setVisibility(View.VISIBLE);
                    id.originalText = id.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.birth);
                    background.addView(race);
                    race.content.setText(activeUser.race);
                    race.content.setTextSize(20);
                    race.edit.setVisibility(View.VISIBLE);
                    race.originalText = race.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.nation);
                    background.addView(nationality);
                    nationality.content.setText(activeUser.nationality);
                    nationality.content.setTextSize(20);
                    nationality.edit.setVisibility(View.VISIBLE);
                    nationality.originalText = nationality.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.gen);
                    background.addView(gender);
                    gender.content.setText(activeUser.gender);
                    gender.content.setTextSize(20);
                    gender.edit.setVisibility(View.VISIBLE);
                    gender.originalText = gender.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.marital);
                    background.addView(mstatus);
                    mstatus.content.setText(activeUser.mstatus);
                    mstatus.content.setTextSize(20);
                    mstatus.edit.setVisibility(View.VISIBLE);
                    mstatus.originalText = mstatus.content.getText().toString();


                    background =(LinearLayout) getView().findViewById(R.id.cell);
                    background.addView(cellphone);
                    cellphone.content.setText(activeUser.cellno);
                    cellphone.content.setTextSize(20);
                    cellphone.edit.setVisibility(View.VISIBLE);
                    cellphone.originalText = cellphone.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.em);
                    background.addView(address);
                    address.content.setText(activeUser.address);
                    address.content.setTextSize(20);
                    address.edit.setVisibility(View.VISIBLE);
                    address.originalText = address.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.emCont);
                    background.addView(econtact);
                    econtact.content.setText(activeUser.ename);
                    econtact.content.setTextSize(20);
                    econtact.edit.setVisibility(View.VISIBLE);
                    econtact.originalText = econtact.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.emContno);
                    background.addView(econtactno);
                    econtactno.content.setText(activeUser.econtact);
                    econtactno.content.setTextSize(20);
                    econtactno.edit.setVisibility(View.VISIBLE);
                    econtactno.originalText = econtact.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.pnum);
                    background.addView(illnesses);
                    illnesses.content.setText(activeUser.GetIllnessesString());
                    illnesses.edit.setVisibility(View.VISIBLE);
                    illnesses.content.setTextSize(20);
                    illnesses.originalText = illnesses.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.lenp);
                    background.addView(allergies);
                    allergies.content.setText(activeUser.allergies);
                    allergies.content.setTextSize(20);
                    allergies.edit.setVisibility(View.VISIBLE);
                    allergies.originalText = allergies.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.uni);
                    background.addView(medicalaid);
                    medicalaid.content.setText(activeUser.medaid);
                    medicalaid.content.setTextSize(20);
                    medicalaid.edit.setVisibility(View.VISIBLE);
                    medicalaid.originalText = medicalaid.content.getText().toString();

                }
            }
        });
    }

    public void UpdateField(final String IDnumber, PatientField field, final Object newValue)
    {
        FirebasePatient firebasePatient = new FirebasePatient(getContext(), field, IDnumber, allDetails, activeUser, newValue, save);
        firebasePatient.UpdateField();
    }

    public class DetailView extends LinearLayout {
        public PatientField type;


        public EditText content;
        public ImageButton edit;

        String originalText = "";

        private Boolean isEditting = false;

        private DetailView(PatientField type, Context context) {
            super(context);

            this.type = type;


            content = new EditText(context);
            edit = new ImageButton(context);


            this.addView(content);
            this.addView(edit);


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


            content.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            edit.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));



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