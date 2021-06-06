package com.acpc.mobilepatienttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DPatientDetails extends AppCompatActivity {

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
                Intent intent = new Intent(getApplicationContext(), DoctorConsultationList.class);

                Bundle bundle = new Bundle();
                bundle.putString("PATIENT_ID", id);
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
        //clickedname=name;
      //  clickedcell=cellno;
       // clickedID=id;


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
        int id_n = item.getItemId();

        if(id_n == R.id.action_con)
        {
            Toast.makeText(getApplicationContext(), "New Consult Form Created", Toast.LENGTH_LONG).show();
            Intent start = new Intent( getApplicationContext(), DoctorConsultForm.class); //moving from main screen to reg screen when clicking register button on main screen
            Bundle bundle = new Bundle();
            bundle.putString("PATIENT_ID", id);
            String[] splitter= name.split(" ", 2);
            bundle.putString("PATIENT_FName", splitter[0]);
            bundle.putString("PATIENT_LName", splitter[1]);
            bundle.putString("PATIENT_Cell", cellno);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
            Date currentdate = new Date();
            String date = formatter.format(currentdate);
            bundle.putString("DATE",date);

            start.putExtras(bundle);
            startActivity(start);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}