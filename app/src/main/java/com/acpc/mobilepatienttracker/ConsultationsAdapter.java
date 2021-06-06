package com.acpc.mobilepatienttracker;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ConsultationsAdapter extends RecyclerView.Adapter<ConsultationsAdapter.ConsultViewHolder>{


    private ArrayList<Consultation> mPatientList;
    private ConsultationsAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    //Sets local Listener to main Listener
    public void setOnItemClickListener(ConsultationsAdapter.OnItemClickListener listener)
    {
        mListener = listener;
    }

    public ConsultationsAdapter.OnItemClickListener getOnItemClickListner()
    {
        return this.mListener;
    }

    //For the Adapter to work, we need a viewholder which will hold all items on the activity seen by the user
    public static class ConsultViewHolder extends RecyclerView.ViewHolder
    {
        //These are the variables which will be displayed on the list
        public TextView symptomsText;
        public TextView dateText;
        public TextView statusText;
        public Drawable other;
     //   public Drawable accepted;
     //   public Drawable rejected;
        public Drawable past;

        /*This is the Constructor for the variables to be displayed, this provides references to our values.
          OnItemClickListener is added to the parameters as a way to reference mListener from inside a static class
         */

        public ConsultViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            symptomsText = itemView.findViewById(R.id.symptomsText);
            dateText = itemView.findViewById(R.id.dateText);
            statusText = itemView.findViewById(R.id.statusText);
            other = ContextCompat.getDrawable(itemView.getContext(), R.drawable.rounded_corner_pending);
           // accepted = ContextCompat.getDrawable(itemView.getContext(), R.drawable.rounded_corner_accepted);
           // rejected = ContextCompat.getDrawable(itemView.getContext(), R.drawable.rounded_corner_rejected);
            past = ContextCompat.getDrawable(itemView.getContext(), R.drawable.rounded_corner_past);

            //We handle the click on the cards using the itemView variable
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    /*
                    In this function when a card is clicked, we check if the card exists(not NULL)
                    and if the position of the list is valid. Once that is done we then parse the
                    listener variable's position to the OnItemClick interface
                     */
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    //This function allows data from the Patient class to be parsed to the Adapter
    public ConsultationsAdapter(ArrayList<Consultation> patientList)
    {
        mPatientList = patientList;
    }

    //We pass the layout from the XML file to the variables using this function
    @NonNull
    @Override
    public ConsultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doclist_item, parent, false);
        ConsultViewHolder plvh = new ConsultViewHolder(v, mListener);

        return plvh;
    }

    //We pass values to the referenced variables here
    @Override
    public void onBindViewHolder(@NonNull final ConsultViewHolder holder, int position) {
        final Consultation currentItem = mPatientList.get(position);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(user.getEmail())) {

                        final String ID = dataSnapshot.child("id").getValue().toString();
                        String CurrDoc = "";

                        if(currentItem.pdoctorID.equals(ID)){
                            CurrDoc = "Past";
                            holder.statusText.setBackground(holder.past);
                        }
                        else{
                            CurrDoc = "Other";
                            holder.statusText.setBackground(holder.other);
                        }

                    /*    switch (CurrDoc)
                        {
                            case "Past" :
                                holder.statusText.setBackground(holder.past);

                                break;
                            default:
                                holder.statusText.setBackground(holder.other);

                                break;
                        }*/

                        final String colour = getColour(CurrDoc);

                        holder.statusText.setText(CurrDoc);
                        holder.statusText.setTextColor(Color.parseColor(colour));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        holder.symptomsText.setText(currentItem.pcase);
        holder.dateText.setText("Consult time: " + currentItem.pdate);


    }

    public String getColour(String status)
    {
        switch (status)
        {

            case "Past":
                return "#0E20C5";
            default:
                return "#C3C3C3";
        }
    }

    //This function defines how many items are in the list
    @Override
    public int getItemCount()
    {
        return mPatientList.size();
    }
}
