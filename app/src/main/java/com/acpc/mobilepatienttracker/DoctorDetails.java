package com.acpc.mobilepatienttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorDetails extends Fragment {

    private LinearLayout background;

    private String name;
    private String id;
    private String cellno;
    private String dob;
    private String gender;
    private String email;
    private String prac_length;
    private String prac_num;
    private String uni;
    private String spec;

    private Context context;

    private TextView nameText;
    private TextView idText;
    private TextView dobText;
    private TextView cellText;
    private TextView emailText;
    private TextView genderText;
    private TextView pracLengthText;
    private TextView pracNumText;
    private TextView uniText;
    private TextView specText;

    private DetailView  NameText;
    private DetailView  IdText;
    private DetailView DobText;
    private DetailView CellText;
    private DetailView EmailText;
    private DetailView GenderText;
    private DetailView PracLengthText;
    private DetailView  PracNumText;
    private DetailView UniText;
    private DetailView SpecText;
    private Doctor activeUser;

    private FloatingActionButton save;

    //        if(bundle != null)
//        {
//            name = bundle.getString("DOC_NAME");
//            id = bundle.getString("DOC_ID");
//            dob = bundle.getString("DOC_DOB");
//            gender = bundle.getString("DOC_GENDER");
//            email = bundle.getString("DOC_EMAIL");
//            prac_length = bundle.getString("DOC_PRACYEARS");
//            prac_num = bundle.getString("DOC_PRACNUM");
//            uni = bundle.getString("DOC_UNI");
//            spec = bundle.getString("DOC_SPEC");
//            cellno = bundle.getString("DOC_CELL");
//
//            nameText.setText(name);
//            idText.setText(id);
//            dobText.setText(dob);
//            genderText.setText(gender);
//            emailText.setText(email);
//            pracLengthText.setText(prac_length);
//            pracNumText.setText(prac_num);
//            uniText.setText(uni);
//            specText.setText(spec);
//            cellText.setText(cellno);
//        }
//        else
//        {
//            nameText.setText("ERROR: NULL VALUE PASSED");
//        }
    private ArrayList<DetailView> allDetails = new ArrayList<DetailView>();

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private Doctor doc = new Doctor();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorDetails() {
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
    public static DoctorDetails newInstance(String param1, String param2) {
        DoctorDetails fragment = new DoctorDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Redbox Doctor");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_doctor_details, container, false);

        context = getContext();

        save = rootView.findViewById(R.id.dd_save);

        NameText = new DetailView(DoctorField.FIRST_NAME, context);
        IdText= new DetailView(DoctorField.ID,context);
        DobText = new DetailView(DoctorField.DOB_TEXT, context);
        CellText = new DetailView(DoctorField.CELL_TEXT, context);
        EmailText = new DetailView(DoctorField.Email, context);
        GenderText = new DetailView(DoctorField.GENDER, context);
        PracLengthText = new DetailView(DoctorField.PRAC_LENGTH, context);
        PracNumText = new DetailView(DoctorField.PRAC_NUM, context);
        UniText = new DetailView(DoctorField.UNI_TEXT, context);
        SpecText = new DetailView(DoctorField.SPEC_TEXT, context);

        //Bundle bundle = getIntent().getExtras();

        allDetails = new ArrayList<DetailView>();
        allDetails.add(NameText);
        allDetails.add(DobText);
        allDetails.add(IdText);
        allDetails.add(CellText);
        allDetails.add(EmailText);
        allDetails.add(GenderText);
        allDetails.add(PracLengthText);
        allDetails.add(PracNumText);
        allDetails.add(UniText);
        allDetails.add(SpecText);



        save.setVisibility(View.INVISIBLE);
        save.setEnabled(false);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (DetailView view : allDetails)
                {
                    if (view.isEditting)
                    {
                        UpdateField(activeUser.ID, view.type, view.content.getText().toString());
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

        getDocDet();


        return rootView;
    }

    public void getDocDet()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


//        final String[] str = {""};
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
//        reference.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren())
//                {
//                    if(dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail()))
//                    {
////                            str[0] = dataSnapshot.child("IDnum").getValue().toString();
//                        testView.setText(dataSnapshot.child("IDnum").getValue().toString());
//                    }
//                }
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        database.collection("doctor-data").whereEqualTo("email", user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Doctor> doctor1 = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult())
                    {
                        doctor1.add(doc.toObject(Doctor.class));
                    }

                    String s = "";

                    for(Doctor doctor2 : doctor1)
                    {
                        if(doctor2.email.equals(user.getEmail()))
                        {
                            doc = doctor2;
                        }

                    }
                    activeUser = doc;


                    background =(LinearLayout) getView().findViewById(R.id.dd_backround);
                    background.addView(NameText);
                    NameText.content.setText(doc.fname);
                    NameText.edit.setVisibility(View.VISIBLE);
                    NameText.originalText = NameText.content.getText().toString();

                    background = (LinearLayout) getView().findViewById(R.id.Iden);
                    background.addView(IdText);
                    IdText.content.setText(doc.ID);
                    IdText.edit.setVisibility(View.VISIBLE);
                    IdText.originalText = IdText.content.getText().toString();

                    background = (LinearLayout) getView().findViewById(R.id.birth);
                    background.addView(DobText);
                    DobText.content.setText(doc.dob);
                    DobText.edit.setVisibility(View.VISIBLE);
                    DobText.originalText = DobText.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.gen);
                    background.addView(GenderText);
                    GenderText.content.setText(doc.gender);
                    GenderText.edit.setVisibility(View.VISIBLE);
                    GenderText.originalText = GenderText.content.getText().toString();

                    background =(LinearLayout)  getView().findViewById(R.id.em);
                    background.addView(EmailText);
                    EmailText.content.setText(doc.email);
                    EmailText.edit.setVisibility(View.VISIBLE);
                    EmailText.originalText = EmailText.content.getText().toString();

                    background = (LinearLayout) getView().findViewById(R.id.cell);
                    background.addView(CellText);
                    CellText.content.setText(doc.cell_no);
                    CellText.edit.setVisibility(View.VISIBLE);
                    CellText.originalText = CellText.content.getText().toString();

                    background = (LinearLayout) getView().findViewById(R.id.pnum);
                    background.addView(PracNumText);
                    PracNumText.content.setText(doc.p_no);
                    PracNumText.edit.setVisibility(View.INVISIBLE);
                    //PracNumText.originalText = PracNumText.content.getText().toString();

                    background = (LinearLayout) getView().findViewById(R.id.lenp);
                    background.addView(PracLengthText);
                    PracLengthText.content.setText(doc.p_length);
                    PracLengthText.edit.setVisibility(View.VISIBLE);
                    PracLengthText.originalText = PracLengthText.content.getText().toString();

                    background =(LinearLayout) getView().findViewById(R.id.uni);
                    background.addView(UniText);
                    UniText.content.setText(doc.uni_name);
                    UniText.edit.setVisibility(View.VISIBLE);
                    UniText.originalText = UniText.content.getText().toString();

                    background = (LinearLayout) getView().findViewById(R.id.spec);
                    background.addView(SpecText);
                    SpecText.content.setText(doc.doc_type);
                    SpecText.edit.setVisibility(View.VISIBLE);
                    SpecText.originalText = SpecText.content.getText().toString();



                }
            }
        });
    }
    private class DetailView extends LinearLayout {
        public DoctorField type;

        public EditText content;
        public ImageButton edit;

        String originalText = "";

        private Boolean isEditting = false;

        private DetailView(DoctorField type, Context context) {
            super(context);

            this.type = type;

            content = new EditText(context);
            edit = new ImageButton(context);

            this.addView(content);
            this.addView(edit);


            content.setTextSize(24);
            content.setTextColor(Color.parseColor("#565c5c"));
            final Drawable originalContentBackground = content.getBackground();
            content.setBackgroundColor(Color.TRANSPARENT);
            content.setEnabled(false);
            //content.setText("...");

            edit.setImageResource(R.drawable.ic_baseline_edit_24);
            edit.setBackgroundColor(Color.TRANSPARENT);
            edit.setColorFilter(Color.parseColor("#9eaeb0"));
            edit.setVisibility(View.INVISIBLE);

            content.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            edit.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));



            edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Boolean isAnotherViewBeingEdited = false;
                    for ( DetailView view : allDetails)
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
    public void UpdateField(final String IDnumber, DoctorField field, final Object newValue)
    {
        final String fieldName = Doctor.GetfieldName(field);

        database.collection("doctor-data")
                .whereEqualTo("ID", IDnumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            String documentID = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentID = document.getId();
                            }
                            final String finalDocumentID = documentID;

                            if (documentID != "")
                            {
                                DocumentReference doctor  = database.collection("doctor-data").document(documentID);

                                for (DetailView view : allDetails)
                                {
                                    if (view.content.getText().toString() != activeUser.GetfieldValue(view.type))

                                        doctor.update(fieldName, newValue)

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

                                for (DetailView view : allDetails)
                                {
                                    if (view.content.isFocused())
                                    {
                                        view.content.clearFocus();
                                        view.content.setBackgroundColor(Color.TRANSPARENT);
                                    }
                                    view.content.setEnabled(false);
                                }
                            }
                            else
                            {
                                // do document was found with the given field
                                Log.w("PD", "QUERY ERROR: No document found with ID number: " + IDnumber);
                                Toast.makeText(getContext(), "Could not save: document not found with that ID number???", Toast.LENGTH_LONG).show();
                            }


                        }
                        else
                        {
                            // query did not complete
                            Log.w("PD", "QUERY ERROR: query did not complete: " + task.getException().getMessage());
                            Toast.makeText(getContext(), "Could not save: query do not complete", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}