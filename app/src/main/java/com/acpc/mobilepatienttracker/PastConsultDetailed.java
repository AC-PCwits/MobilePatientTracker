package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PastConsultDetailed extends AppCompatActivity {

   /* private TextView dlname,dfname,cell,fname,sname,symptoms, diagnosis, patientid,doctorid,date;
    private Button done;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_consult_detailed);

       /* dlname = (TextView) findViewById(R.id.dConsultLName);
        dfname = (TextView) findViewById(R.id.dConsultName);
        cell = (TextView) findViewById(R.id.pConsultCell);
        fname = (TextView) findViewById(R.id.pConsultName);
        sname = (TextView) findViewById(R.id.pConsultLName);
        symptoms=(TextView) findViewById(R.id.pConsultSymptoms);
        diagnosis=(TextView) findViewById(R.id.pConsultDiagnosis);
        patientid=(TextView) findViewById(R.id.pConsultID);
        doctorid=(TextView) findViewById(R.id.dConsultID);
        date=(TextView) findViewById(R.id.editTextTextPersonName10);

        done = findViewById(R.id.buttonSave);

        Intent intent = getIntent();

        final String patientID = intent.getExtras().getString("PATIENT_ID");
        String patientName = intent.getExtras().getString("PATIENT_NAME");
        String datetime = intent.getExtras().getString("DATE") + " - " + intent.getExtras().getString("TIME");

        patientid.setText(patientID);
        fname.setText(patientName);
        date.setText(datetime);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(PastConsultDetailed.this, DoctorConsultationList.class);
                Bundle bundle = new Bundle();

                bundle.putString("PATIENT_ID", patientID);

                start.putExtras(bundle);
                startActivity(start);
            }
        });*/
    }


    public void GetAdditionalPatientData(final String patientID)
    {
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        database.collection("doctor-data").whereEqualTo("email", user.getEmail())
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful())
//                {
//                    Doctor doctor = task.getResult().toObjects(Doctor.class).get(0);
//
//                    database.collection("booking-history-data").whereEqualTo("doc_id", doctor.p_no).whereEqualTo("id", patientID)
//                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if(task.isSuccessful())
//                            {
//                                ArrayList<AccOrRej> pastConsults = (ArrayList<AccOrRej>) task.getResult().toObjects(AccOrRej.class);
//
//                                Log.d("PAST CONSULTS", "Past consults size: " + pastConsults.size());
//
//                                buildRecyclerView(pastConsults);
//                            }
//                            else
//                            {
//                                Log.w("PAST CONSULTS", "Could not get past consults: ", task.getException());
//                            }
//                        }
//                    });
//
//                }
//                else
//                {
//                    Log.w("PAST CONSULTS", "Could not get doctor info: ", task.getException());
//                }
//            }
//        });
    }
}

