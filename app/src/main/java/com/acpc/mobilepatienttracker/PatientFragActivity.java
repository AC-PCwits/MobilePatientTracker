package com.acpc.mobilepatienttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class PatientFragActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_frag);

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        getSupportActionBar().setTitle("Calendar");
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Patient Details");
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Create A Booking");
                        break;
                    case 3:
                        getSupportActionBar().setTitle("Consultation History");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    public void callPause()
    {
        onPause();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}