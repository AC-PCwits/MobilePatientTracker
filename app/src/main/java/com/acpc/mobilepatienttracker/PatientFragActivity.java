package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class PatientFragActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_frag);

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons()
    {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_info_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_add_box_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_history_24);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFrag(new PHomePage(), "Home");
        adapter.addFrag(new PatientDetails(), "Details");
        adapter.addFrag(new PatientBookings(), "Bookings");
        adapter.addFrag(new PatientConsultationHistory(), "History");
        viewPager.setAdapter(adapter);
    }
}