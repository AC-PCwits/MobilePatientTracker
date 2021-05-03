package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientDateTimeBooking extends AppCompatActivity {

    private DatePickerDialog dpd;
    private TextView date;
    private Calendar c;
    TextView tvTimer1;
    int t1Hour, t1Minute;

    String f;
    private TextView name;
    String s;
   // private TextView sname;
    String e;
    private TextView exp;
    Button confirm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_date_time_booking);

        name=findViewById(R.id.name);
        //sname=findViewById(R.id.sname);
        exp=findViewById(R.id.exp);
        confirm=findViewById(R.id.confirmbtn);

        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            f = intent.getExtras().getString("DOCTOR_FNAME");
            s = intent.getExtras().getString("DOCTOR_SNAME");
            e = intent.getExtras().getString("EXPERIENCE");

        }

        name.setText(f+" "+s);
      //  sname.setText(s);
        exp.setText(e);

        date = findViewById(R.id.date_of_birth);
        tvTimer1 = findViewById(R.id.tv_timer1);

        tvTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        PatientDateTimeBooking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                                t1Hour = hourofDay;
                                t1Minute = minute;

                                //Store hour and min in string
                                String time = t1Hour + ":" + t1Minute;

                                //initialize 24 hour time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try{
                                    Date date = f24Hours.parse(time);
                                    //initialize 12 hours time format
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );

                                    //Set selected time in textview
                                    tvTimer1.setText(f12Hours.format(date));

                                }catch (ParseException e){
                                    e.printStackTrace();
                                }

                            }
                        }, 12,0,false
                );
                //Set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //Displayed previous selected time
                timePickerDialog.updateTime(t1Hour,t1Minute);

                //show dialog
                timePickerDialog.show();


            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(PatientDateTimeBooking.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        int mnth = month+1;

                        if(dayOfMonth <10 && mnth >=10){
                            date.setText(year + "/" +(month+1) + "/" + "0"+dayOfMonth);
                        }else if (mnth <10 && dayOfMonth>=10){
                            date.setText(year + "/" +"0"+(month+1) + "/" + dayOfMonth);
                        }else if (mnth <10 && dayOfMonth <10){
                            date.setText(year + "/" +"0"+(month+1) + "/" +"0"+dayOfMonth);
                        }else if (mnth >= 10 && dayOfMonth >= 10){
                            date.setText(year + "/" +(month+1) + "/" + dayOfMonth);
                        }
                    }
                }, year,month,day);
                dpd.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(PatientDateTimeBooking.this, PatientFragActivity.class);
                Toast.makeText(PatientDateTimeBooking.this, "Your Booking Has Been Confirmed", Toast.LENGTH_LONG).show();
                startActivity(start);
            }
        });



    }
}