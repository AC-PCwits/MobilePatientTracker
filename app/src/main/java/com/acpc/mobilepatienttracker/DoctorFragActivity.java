package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class DoctorFragActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_frag);

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
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_list_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_pending_actions_24);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFrag(new DHomePage(), "Home");
        adapter.addFrag(new DoctorDetails(), "Details");
        adapter.addFrag(new DoctorPatientList(), "Patients");
        adapter.addFrag(new PendingBookings(), "Bookings");
        viewPager.setAdapter(adapter);
    }

}