package com.acpc.mobilepatienttracker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DocTypeAdapterTest {

    public ArrayList<DType> mList = new ArrayList<>();
    {
        mList.add(new DType("Cardiologist"));
    }

    public DocTypeAdapter.OnItemClickListener mListener = new DocTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    @Test
    public void testPatientAdapter()
    {
        DType DType = new DType("Cardiologist");

        DocTypeAdapter DocTypeAdapter = new DocTypeAdapter(mList);

        assertEquals(1, DocTypeAdapter.getItemCount());
        assertNotNull(DocTypeAdapter);

    }

    @Test
    public void setOnItemClickListener()
    {
        DocTypeAdapter listAdapter = new DocTypeAdapter(mList);

        listAdapter.setOnItemClickListener(mListener);

        assertEquals(mListener, listAdapter.getOnItemClickListner());
    }


    @Test
    public void onCreateViewHolder()
    {

    }

    @Test
    public void onBindViewHolder() {
    }

    @Test
    public void getItemCount()
    {
        DType DType = new DType("Cardiologist");

        DocTypeAdapter DocTypeAdapter = new DocTypeAdapter(mList);

        assertEquals(mList.size(), DocTypeAdapter.getItemCount());
    }

}
