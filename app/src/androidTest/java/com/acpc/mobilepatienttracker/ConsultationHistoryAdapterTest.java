package com.acpc.mobilepatienttracker;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConsultationHistoryAdapterTest extends TestCase {

    public ArrayList<Appointment> list = new ArrayList<>();
    {
        list.add(new Appointment("John", "12345678901", "2021/06/1", "10:00 AM", "333333", "Kanye", "Accepted"));
    }
    public ConsultationHistoryAdapter.OnItemClickListener listener = new ConsultationHistoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    @Test
    public void testConsultationHistoryAdapter()
    {
        ConsultationHistoryAdapter adapter = new ConsultationHistoryAdapter(list);

        assertEquals(1, adapter.getItemCount());
        assertNotNull(adapter);
    }

    @Test
    public void testSetOnItemClickListener()
    {
        ConsultationHistoryAdapter adapter = new ConsultationHistoryAdapter(list);

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
    public void testGetColour()
    {

        ConsultationHistoryAdapter adapter = new ConsultationHistoryAdapter(list);

        adapter.setOnItemClickListener(listener);

        for (int i =0; i < 4; i++)
        {
            String status = "";

            switch (i)
            {
                case 0:
                    status = "Accepted";
                    break;
                case 1:
                    status = "Pending";
                    break;
                case 2:
                    status = "Past";
                    break;
                case 3:
                    status = "Rejected";
                    break;
            }


            switch (status)
            {
                case "Accepted":
                    assertEquals("#50C878", adapter.getColour(status));
                    break;
                case "Pending":
                    assertEquals("#C3C3C3", adapter.getColour(status));
                    break;
                case "Past":
                    assertEquals("#0E20C5", adapter.getColour(status));
                    break;
                default:
                    assertEquals("#FF0000", adapter.getColour(status));
                    break;
            }
        }
    }
    @Test
    public void testGetItemCount()
    {
        ConsultationHistoryAdapter adapter = new ConsultationHistoryAdapter(list);

        adapter.setOnItemClickListener(listener);

        assertEquals(list.size(), adapter.getItemCount());
    }
}