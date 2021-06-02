package com.acpc.mobilepatienttracker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ConsultationsAdapterTest {

    public ArrayList<Consultation> list = new ArrayList<>();
    {
        list.add(new Consultation("Covid", "no sense of taste", "Covid", "2021/06/1 10:00 AM", "10927547462", "333333"));
    }
    public ConsultationsAdapter.OnItemClickListener listener = new ConsultationsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    @Test
    public void testConsultationsAdapter()
    {
        ConsultationsAdapter adapter = new ConsultationsAdapter(list);

        assertEquals(1, adapter.getItemCount());
        assertNotNull(adapter);
    }

    @Test
    public void testSetOnItemClickListener()
    {
        ConsultationsAdapter adapter = new ConsultationsAdapter(list);

        adapter.setOnItemClickListener(listener);

        assertEquals(listener, adapter.getOnItemClickListner());
    }
    @Test
    public void testOnCreateViewHolder()
    {

    }
    @Test
    public void testOnBindViewHolder()
    {
    }
    @Test
    public void testGetItemCount()
    {
        ConsultationsAdapter adapter = new ConsultationsAdapter(list);

        adapter.setOnItemClickListener(listener);

        assertEquals(list.size(), adapter.getItemCount());
    }
}