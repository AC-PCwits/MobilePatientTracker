package com.acpc.mobilepatienttracker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DocTypeAdapter extends RecyclerView.Adapter<DocTypeAdapter.DTypeListViewHolder> {

    private ArrayList<DType>mDocTypeList;
    private OnItemClickListener mListener;

    //Interface for handling when user clicks on list
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //Sets local Listener to main Listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //For the Adapter to work, we need a viewholder which will hold all items on the activity seen by the user
    public static class DTypeListViewHolder extends RecyclerView.ViewHolder {
        //These are the variables which will be displayed on the list
        public TextView DocTypeText;
        // public TextView bookingDateText;

        /*This is the Constructor for the variables to be displayed, this provides references to our values.
          OnItemClickListener is added to the parameters as a way to reference mListener from inside a static class
         */

        public DTypeListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            DocTypeText = itemView.findViewById(R.id.nameText);
            // bookingDateText = itemView.findViewById(R.id.idText);

            //We handle the click on the cards using the itemView variable
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    In this function when a card is clicked, we check if the card exists(not NULL)
                    and if the position of the list is valid. Once that is done we then parse the
                    listener variable's position to the OnItemClick interface
                     */
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    //This function allows data from the Patient class to be parsed to the Adapter
    public DocTypeAdapter(ArrayList<DType> doctypeList) {
        mDocTypeList =  doctypeList;
    }

    //We pass the layout from the XML file to the variables using this function
    @NonNull
    @Override
    public DTypeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_type_list_item, parent, false);
        DTypeListViewHolder plvh = new DTypeListViewHolder(v, mListener);

        return plvh;
    }

    //We pass values to the referenced variables here
    @Override
    public void onBindViewHolder(@NonNull DTypeListViewHolder holder, int position) {
        DType currentItem = mDocTypeList.get(position);

        //The holder variable allows the values of view to be set by Patient objects
        holder.DocTypeText.setText(currentItem.type);

        //  holder.bookingDateText.setText("Booking Date: " );
    }

    //This function defines how many items are in the list
    @Override
    public int getItemCount() {
        return mDocTypeList.size();
    }




}

