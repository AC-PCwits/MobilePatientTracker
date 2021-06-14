//package com.acpc.mobilepatienttracker;
//
//import android.content.Intent;
//import android.graphics.Paint;
//import android.os.Build;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import com.google.firebase.FirebaseApp;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.RuntimeEnvironment;
//import org.robolectric.annotation.Config;
//
//import static com.acpc.mobilepatienttracker.DatabaseManager.GetPastConsult;
//import static org.junit.Assert.*;
//
//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = Build.VERSION_CODES.O_MR1)
//public class ConsultationDetailsTest {
//
//    private ConsultationDetails activity;
//
//    @Before
//    public void setUp() throws Exception
//    {
//        Intent intent = new Intent(RuntimeEnvironment.application, PatientForm.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("PATIENT_ID", "12345678901");
//        bundle.putString("DOCTOR_ID", "1234567");
//        bundle.putString("DATETIME", "2021/06/19 02:00 PM");
//        intent.putExtras(bundle);
//
//        activity = Robolectric.buildActivity(ConsultationDetails.class, intent).create().resume().get();
//
//    }
//
//    @Test
//    public void onCreate() {
//
//        String PFirstName = "gina";
//        String PSurname = "linetti";
//        String PatientID = "12345678901";
//        String Pcell = "0845555555";
//        String DFirstName = "helen";
//        String DSurname = "joesph";
//        String PracticeID = "1234567";
//        String CaseInfo = "acute";
//        String Symptoms = "sore throat";
//        String Diagnosis = "covid";
//        String Date = "2021/06/19 02:00 PM";
//
//        TextView txtConsultationDetailsLabel = activity.findViewById(R.id.txtConsultationDetailsLabel);
//        txtConsultationDetailsLabel.setPaintFlags(txtConsultationDetailsLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);// to underline text
//        final TextView txtPFirstName = activity.findViewById(R.id.txtPFirstname);
//        final TextView txtPSurname = activity.findViewById(R.id.txtPSurname);
//        final TextView txtPatientID = activity.findViewById(R.id.txtID);
//        final TextView txtPcell = activity.findViewById(R.id.txtCell);
//        final TextView txtDFirstName = activity.findViewById(R.id.txtDFirstName);
//        final TextView txtDSurname = activity.findViewById(R.id.txtDSurname);
//        final TextView txtPracticeID = activity.findViewById(R.id.txtDPracticeID);
//        final TextView txtCaseInfo = activity.findViewById(R.id.txtCaseInfo);
//        final TextView txtSymptoms = activity.findViewById(R.id.txtSymptoms);
//        final TextView txtDiagnosis = activity.findViewById(R.id.txtDiagnosis);
//        final TextView txtDate = activity.findViewById(R.id.txtDate);
//
//        Intent intent = activity.getIntent();
//
//        if(intent.getExtras() != null)
//        {
//            String patientID = intent.getExtras().getString("PATIENT_ID");
//            String doctorID = intent.getExtras().getString("DOCTOR_ID");
//            String dateTime = intent.getExtras().getString("DATETIME");
//
//            txtPFirstName.setText(PFirstName);
//            txtPSurname.setText(PSurname);
//            txtPatientID.setText(patientID);
//            txtPcell.setText(Pcell);
//            txtDFirstName.setText(DFirstName);
//            txtDSurname.setText(DSurname);
//            txtPracticeID.setText(doctorID);
//            txtCaseInfo.setText(CaseInfo);
//            txtSymptoms.setText(Symptoms);
//            txtDiagnosis.setText(Diagnosis);
//            txtDate.setText(dateTime);
//
//            assertEquals(PFirstName, txtPFirstName.getText());
//            assertEquals(PSurname, txtPSurname.getText());
//            assertEquals(patientID, txtPatientID.getText());
//            assertEquals(Pcell, txtPcell.getText());
//            assertEquals(DFirstName, txtDFirstName.getText());
//            assertEquals(DSurname, txtDSurname.getText());
//            assertEquals(doctorID, txtPracticeID.getText());
//            assertEquals(CaseInfo, txtCaseInfo.getText());
//            assertEquals(Symptoms, txtSymptoms.getText());
//            assertEquals(Diagnosis, txtDiagnosis.getText());
//            assertEquals(dateTime, txtDate.getText());
//        }
//
//
//
//    }
//}