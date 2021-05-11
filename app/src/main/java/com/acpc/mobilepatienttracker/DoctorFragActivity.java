package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.log_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_logout)
        {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "You have successfully logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), DoctorOrPatient.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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