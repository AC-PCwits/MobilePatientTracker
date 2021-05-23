package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class DPatientDetails extends AppCompatActivity {


    public static String clickedname;
    public static String clickedID;
    public static String clickedcell;

    private String name;
    private String id;
    private String cellno;
    private String nationality;
    private String gender;
    private String address;
    private String ename;
    private String econtact;
    private String race;
    private String mstatus;
    private String medaid;
    private String allergies;
    private ArrayList<String> illnesses;

    private TextView nameText;
    private TextView idText;
    private TextView illText;
    private TextView cellText;
    private TextView nationalityText;
    private TextView genderText;
    private TextView addressText;
    private TextView enameText;
    private TextView econtactText;
    private TextView raceText;
    private TextView mstatusText;
    private TextView medaidText;
    private TextView allergiesText;

    private ExtendedFloatingActionButton pastConsults;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_patient_details);

        final ExtendedFloatingActionButton extendedFloatingActionButton = findViewById(R.id.dpl_past_consults);

        // register the nestedScrollView from the main layout
        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);

        // handle the nestedScrollView behaviour with OnScrollChangeListener
        // to extend or shrink the Extended Floating Action Button
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // the delay of the extension of the FAB is set for 12 items
                if (scrollY > oldScrollY + 12 && extendedFloatingActionButton.isExtended()) {
                    extendedFloatingActionButton.shrink();
                }

                // the delay of the extension of the FAB is set for 12 items
                if (scrollY < oldScrollY - 12 && !extendedFloatingActionButton.isExtended()) {
                    extendedFloatingActionButton.extend();
                }

                // if the nestedScrollView is at the first item of the list then the
                // extended floating action should be in extended state
                if (scrollY == 0) {
                    extendedFloatingActionButton.extend();
                }
            }
        });
        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PastConsults.class);

                Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
                bundle.putString("pID", id);
//Add the bundle to the intent
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        //Instantiation of View Components
        nameText = (TextView)findViewById(R.id.nameText);
        idText = (TextView)findViewById(R.id.idText);
        illText = (TextView)findViewById(R.id.illnessText);
        cellText = (TextView)findViewById(R.id.cellText);
        nationalityText = (TextView)findViewById(R.id.natText);
        genderText = (TextView)findViewById(R.id.genderText);
        addressText = (TextView)findViewById(R.id.addressText);
        enameText = (TextView)findViewById(R.id.enameText);
        econtactText = (TextView)findViewById(R.id.ecellText);
        raceText = (TextView)findViewById(R.id.raceText);
        mstatusText = (TextView)findViewById(R.id.mstatusText);
        medaidText = (TextView)findViewById(R.id.medaidText);
        allergiesText = (TextView)findViewById(R.id.allergiesText);

        pastConsults = (ExtendedFloatingActionButton) findViewById(R.id.dpl_past_consults);


        //Patient details from the list are sent with the intent used to send the user to this screen
        Intent intent = getIntent();

//        Patient Details are set here
        if(intent.getExtras() != null) {
            name = intent.getExtras().getString("PATIENT_NAME");
            id = intent.getExtras().getString("PATIENT_ID");
            cellno = intent.getExtras().getString("PATIENT_CELL");
            nationality = intent.getExtras().getString("PATIENT_NAT");
            gender = intent.getExtras().getString("PATIENT_GENDER");
            address = intent.getExtras().getString("PATIENT_ADDRESS");
            ename = intent.getExtras().getString("PATIENT_ENAME");
            econtact = intent.getExtras().getString("PATIENT_ECONT");
            race = intent.getExtras().getString("PATIENT_RACE");
            mstatus = intent.getExtras().getString("PATIENT_MARRIED");
            illnesses = intent.getExtras().getStringArrayList("PATIENT_ILLNESS");
            medaid = intent.getExtras().getString("PATIENT_MEDAID");
            allergies = intent.getExtras().getString("PATIENT_ALLERGIES");
        }

        String ill = "";

        for(String text: illnesses)
        {
            if(ill.equals(""))
            {
                ill = text;
            }else
            {
                ill = ill + "\n" + text;
            }
        }

        nameText.setText(name);
        idText.setText(id);
        cellText.setText(cellno);
        nationalityText.setText(nationality);
        genderText.setText(gender);
        addressText.setText(address);
        enameText.setText(ename);
        econtactText.setText(econtact);
        raceText.setText(race);
        mstatusText.setText(mstatus);
        illText.setText(ill);
        medaidText.setText(medaid);
        allergiesText.setText(allergies);
        clickedname=name;
        clickedcell=cellno;
        clickedID=id;

        pastConsults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DPatientDetails.this, PastConsults.class);
                Bundle bundle = new Bundle();

                bundle.putString("PATIENT_NAME", name);
                bundle.putString("PATIENT_ID", id);
                bundle.putString("PATIENT_CELL", cellno);
                bundle.putString("PATIENT_NAT", nationality);
                bundle.putString("PATIENT_GENDER", gender);
                bundle.putString("PATIENT_ADDRESS", address);
                bundle.putString("PATIENT_ENAME", ename);
                bundle.putString("PATIENT_ECONT", econtact);
                bundle.putString("PATIENT_RACE", race);
                bundle.putString("PATIENT_MARRIED", mstatus);
                bundle.putString("PATIENT_ILLNESS", allergies);
                bundle.putString("PATIENT_MEDAID", medaid);
                bundle.putString("PATIENT_ALLERGIES", allergies);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.log_consult, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_con)
        {
//            Toast.makeText(getApplicationContext(), "New Consult Form Created", Toast.LENGTH_LONG).show();
//            Intent start = new Intent( getApplicationContext(), DoctorConsultForm.class); //moving from main screen to reg screen when clicking register button on main screen
//            startActivity(start);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}