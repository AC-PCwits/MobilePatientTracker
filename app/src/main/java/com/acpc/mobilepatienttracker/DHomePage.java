package com.acpc.mobilepatienttracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

public class DHomePage extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView booking_date, booking_time, booking_patient, patientID;

    private FloatingActionButton newCForm;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private ArrayList<AccOrRej> accept_rejects = new ArrayList<>();
    private HorizontalPicker picker;

    private SparseArray<Group> groups = new SparseArray<>();


    public interface DateCallBack
    {
        void onResponse(ArrayList<AccOrRej> list);
    }



    public DHomePage() {
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
    public static DHomePage newInstance(String param1, String param2) {
        DHomePage fragment = new DHomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Calendar");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_d_home_page, container, false);

        final Loading loadingDialog = new Loading(getActivity());

        loadingDialog.startLoading();

      /*  Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        }, 5000);*/

        final ExpandableListView listView = rootView.findViewById(R.id.listView);
        groups.clear();

        newCForm = rootView.findViewById(R.id.newCForm);
        newCForm.setVisibility(View.INVISIBLE);


        newCForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DoctorConsultForm.class);
                startActivity(intent);

            }
        });


        picker = rootView.findViewById(R.id.datePicker);


        getUserData(picker, new DHomePage.DateCallBack() {
            @Override
            public void onResponse(final ArrayList<AccOrRej> list) {

                if(!isVisible())
                {
                    list.clear();
                }

                groups.clear();
                picker.setListener(new DatePickerListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDateSelected(DateTime dateSelected)
                    {
                        groups.clear();
                        String [] date = dateSelected.toString().substring(0,10).split("-");
                        String formdate = date[0] + "/" + date[1] + "/" + date[2];

                        ArrayList<String[]> strings = new ArrayList<>();
                        for(AccOrRej acceptReject : list)
                        {
                            boolean TimeAlreadyExists = false;

                            for (int j = 0; j < strings.size(); j++)
                            {
                                if (acceptReject.time.equals(strings.get(j)[1]))
                                {
                                    TimeAlreadyExists = true;
                                    break;
                                }
                            }

                            if (TimeAlreadyExists)
                            {
                                continue;
                            }
                            if(acceptReject.bookingdate.equals(formdate))
                            {
                                String [] str = new String[5];

                                str[0] = acceptReject.bookingdate;
                                str[1] = acceptReject.time;
                                str[2] = acceptReject.id;
                                str[3] = acceptReject.pname;
                                str[4] = acceptReject.status;

                                strings.add(str);
                            }
                        }

                        if(strings.size() > 0)
                        {
                            ArrayList<String[]> sortedList = new ArrayList<>();
                            sortedList.addAll(sortByTime(strings));

                            for(int i = 0; i < sortedList.size(); i++)
                            {
                                Group group = new Group(sortedList.get(i)[1]);

                                group.children.add("Date: " + sortedList.get(i)[0]);
                                group.children.add("Time: " + sortedList.get(i)[1]);
                                group.children.add("Patient ID: " + sortedList.get(i)[2]);
                                group.children.add("Patient: " + sortedList.get(i)[3]);
                                group.children.add("Status: " + sortedList.get(i)[4]);

                                groups.append(i, group);
                            }

                        }
                        else
                        {
                            Group group = new Group("There Are No Events For Today");
                            groups.append(0, group);
                        }
                        buildList(groups, listView);
                    }
                })
                        .showTodayButton(true)
                        .setOffset(3)
                        .init();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();


                ArrayList<String[]> strings = new ArrayList<>();
                for(AccOrRej acceptReject : list)
                {
                    boolean TimeAlreadyExists = false;

                    for (int j = 0; j < strings.size(); j++)
                    {
                        if (acceptReject.time.equals(strings.get(j)[1]))
                        {
                            TimeAlreadyExists = true;
                            break;
                        }
                    }

                    if (TimeAlreadyExists)
                    {
                        continue;
                    }
                    if(acceptReject.bookingdate.equals(formatter.format(date)))
                    {
                        String [] str = new String[5];

                        str[0] = acceptReject.bookingdate;
                        str[1] = acceptReject.time;
                        str[2] = acceptReject.id;
                        str[3] = acceptReject.pname;
                        str[4] = acceptReject.status;

                        strings.add(str);
                    }
                }
                if(strings.size() > 0)
                {
                    ArrayList<String[]> sortedList = new ArrayList<>();
                    sortedList.addAll(sortByTime(strings));


                    for(int i = 0; i < sortedList.size(); i++)
                    {
                        Group group = new Group(sortedList.get(i)[1]);

                        group.children.add("Date: " + sortedList.get(i)[0]);
                        group.children.add("Time: " + sortedList.get(i)[1]);
                        group.children.add("Patient ID: " + sortedList.get(i)[2]);
                        group.children.add("Patient: " + sortedList.get(i)[3]);
                        group.children.add("Status: " + sortedList.get(i)[4]);

                        groups.append(i, group);
                    }

                }
                else
                {
                    Group group = new Group("There Are No Events For Today");
                    groups.append(0, group);
                }
                buildList(groups, listView);
                loadingDialog.dismiss();

            }
        });

        groups.clear();

        return rootView;

    }

    public ArrayList<String[]> sortByTime(ArrayList<String[]> strings)
    {

        for(int i = 0; i < strings.size() - 1; i++)
        {
            for(int j  = i + 1; j <strings.size(); j++)
            {
                String [] split1 = strings.get(i)[1].split(" ");
                String [] split2 = strings.get(j)[1].split(" ");

                if(split1[1].equals("PM") && split2[1].equals("AM"))
                {
                    String [] temp = new String[4];
                    temp[0] = strings.get(i)[0];
                    temp[1] = strings.get(i)[1];
                    temp[2] = strings.get(i)[2];
                    temp[3] = strings.get(i)[3];
                    strings.get(i)[0] = strings.get(j)[0];
                    strings.get(i)[1] = strings.get(j)[1];
                    strings.get(i)[2] = strings.get(j)[2];
                    strings.get(i)[3] = strings.get(j)[3];
                    strings.get(j)[0] = temp[0];
                    strings.get(j)[1] = temp[1];
                    strings.get(j)[2] = temp[2];
                    strings.get(j)[3] = temp[3];
                    continue;
                }
                else if(split1[0].compareTo(split2[0]) > 0)
                {
                    String [] temp = new String[4];
                    temp[0] = strings.get(i)[0];
                    temp[1] = strings.get(i)[1];
                    temp[2] = strings.get(i)[2];
                    temp[3] = strings.get(i)[3];
                    strings.get(i)[0] = strings.get(j)[0];
                    strings.get(i)[1] = strings.get(j)[1];
                    strings.get(i)[2] = strings.get(j)[2];
                    strings.get(i)[3] = strings.get(j)[3];
                    strings.get(j)[0] = temp[0];
                    strings.get(j)[1] = temp[1];
                    strings.get(j)[2] = temp[2];
                    strings.get(j)[3] = temp[3];
                    continue;
                }
            }
        }

        return strings;
    }

    public void buildList(final SparseArray<Group> groups, ExpandableListView listView)
    {
        DHomePageAdapter adapter = new DHomePageAdapter(getActivity(), groups);
        listView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DHomePageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Group group) {

                if(group.children.get(4).toString().equals("Incomplete")){

                    Bundle bundle = new Bundle();

                    String [] id = group.children.get(2).toString().split(" ");
                    final String [] date = group.children.get(0).toString().split(" ");
                    final String [] time = group.children.get(1).toString().split(" ");


                    FirebasePatient firebasePatient = new FirebasePatient(group.children.get(2));
                    firebasePatient.getUserData(new FirebasePatient.FirebaseCallback() {
                        @Override
                        public void onResponse(ArrayList<Patient> patients)
                        {

                            Bundle bundle = new Bundle();

                            bundle.putString("PATIENT_ID", patients.get(0).idno);
                            bundle.putString("PATIENT_FName", patients.get(0).fname);
                            bundle.putString("PATIENT_LName", patients.get(0).fsurname);
                            bundle.putString("PATIENT_Cell", patients.get(0).cellno);
                            bundle.putString("DATE", date[1]);
                            bundle.putString("TIME", time[1] + " " + time[2]);
                            bundle.putString("STATUS", "Completed");


                            goToConsult(bundle);

                        }
                    },id[2]);


                }
                else{
                    Toast.makeText(getContext(),"Consultation Completed",Toast.LENGTH_SHORT).show();
                }




            }
        });
    }


    public void getUserData(final HorizontalPicker picker, final DHomePage.DateCallBack callBack) {

        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        database.collection("acc-rej-data").whereEqualTo("doc_id", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot b : task.getResult()) {
                                        AccOrRej accOrRej = b.toObject(AccOrRej.class);

                                        if (accOrRej.accOrRej.equals("Accepted"))
                                        {
                                            accept_rejects.add(accOrRej);
                                        }
                                    }

//                                    for (AccOrRej ar : accept_rejects)
//                                    {
//                                        Log.d("ACC-REJ LIST", ar.accOrRej);
//                                        Log.d("ACC-REJ LIST", ar.id);
//                                        Log.d("ACC-REJ LIST", ar.doc_id);
//                                        Log.d("ACC-REJ LIST", ar.pname);
//                                        Log.d("ACC-REJ LIST", ar.bookingdate);
//                                        Log.d("ACC-REJ LIST", ar.time);
//                                    }

                                    callBack.onResponse(accept_rejects);
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

    public void goToConsult(Bundle bundle)
    {
        Intent intent = new Intent(getContext(), DoctorConsultForm.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
