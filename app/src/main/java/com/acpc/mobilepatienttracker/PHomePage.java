package com.acpc.mobilepatienttracker;

import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Collections;
import java.util.Date;

public class PHomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView bookingDate, bookingTime, bookingDoc, docID;

    private ArrayList<AccOrRej> acceptRejects = new ArrayList<>();
    private HorizontalPicker picker;

    private SparseArray<Group> groups = new SparseArray<>();

    public interface DateCallBack
    {
        void onResponse(ArrayList<AccOrRej> list);
    }

    public interface DocNameCallback
    {
        void onResponse(ArrayList<Doctor> list);
    }

    public PHomePage() {
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
    public static PHomePage newInstance(String param1, String param2) {
        PHomePage fragment = new PHomePage();
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
        View rootView = inflater.inflate(R.layout.activity_p_home_page, container, false);

        final Loading loadingDialog = new Loading (getActivity());

        loadingDialog.startLoading();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        }, 5000);

        final ExpandableListView listView = rootView.findViewById(R.id.listView);

        picker = rootView.findViewById(R.id.datePicker);

        getDocNames(new DocNameCallback() {
            @Override
            public void onResponse(final ArrayList<Doctor> doclist) {

                getUserData(picker, new DateCallBack() {
                    @Override
                    public void onResponse(final ArrayList<AccOrRej> list) {

                        picker.setListener(new DatePickerListener() {
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
                                        for(Doctor doctor: doclist)
                                        {
                                            if(doctor.p_no.equals(acceptReject.doc_id))
                                            {
                                                String [] str = new String[4];

                                                str[0] = acceptReject.bookingdate;
                                                str[1] = acceptReject.time;
                                                str[2] = acceptReject.doc_id;
                                                str[3] = doctor.fname + " " + doctor.lname;

                                                strings.add(str);
                                            }
                                        }
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
                                        group.children.add("Doctor Practice Number: " + sortedList.get(i)[2]);
                                        group.children.add("Doctor: " + sortedList.get(i)[3]);

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
                                for(Doctor doctor: doclist)
                                {
                                    if(doctor.p_no.equals(acceptReject.doc_id))
                                    {
                                        String [] str = new String[4];

                                        str[0] = acceptReject.bookingdate;
                                        str[1] = acceptReject.time;
                                        str[2] = acceptReject.doc_id;
                                        str[3] = doctor.fname + " " + doctor.lname;

                                        strings.add(str);
                                    }
                                }
                            }
                        }
                        if(strings.size() > 0)
                        {
                            ArrayList<String[]> sortedList = new ArrayList<>();
                            sortedList.addAll(sortByTime(strings));

                            for(int i = 0; i < sortedList.size(); i++)
                            {
                                Group group = new Group(strings.get(i)[1]);

                                group.children.add("Date: " + sortedList.get(i)[0]);
                                group.children.add("Time: " + sortedList.get(i)[1]);
                                group.children.add("Doctor Practice Number: " + sortedList.get(i)[2]);
                                group.children.add("Doctor: " + sortedList.get(i)[3]);

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
                });
            }
        });

        //   loadingDialog.dismiss();

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

    public void buildList(SparseArray<Group> groups, ExpandableListView listView)
    {
        PHomepageAdapter adapter = new PHomepageAdapter(getActivity(), groups);
        listView.setAdapter(adapter);
    }

    public void getUserData(final HorizontalPicker picker, final DateCallBack callBack) {

        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {
                        final String ID = dataSnapshot.child("id").getValue().toString();

                        database.collection("acc-rej-data").whereEqualTo("id", ID)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot b : task.getResult()) {
                                        AccOrRej accOrRej = b.toObject(AccOrRej.class);

                                        if (accOrRej.accOrRej.equals("Accepted"))
                                        {
                                            acceptRejects.add(accOrRej);
                                        }
                                    }

                                    callBack.onResponse(acceptRejects);
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

    public void getDocNames(final DocNameCallback callback )
    {
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("doctor-data")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Doctor> doctor = new ArrayList<>();

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        doctor.add(doc.toObject(Doctor.class));
                    }

                    callback.onResponse(doctor);
                }
            }
        });
    }
}