package edu.illinois.cs465.prattle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import edu.illinois.cs465.prattle.data.HangoutModel;
import edu.illinois.cs465.prattle.data.TabsAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabsAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom Navigation Creation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setItemIconTintList(null);

        // Hangouts tab creation
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Browse Hangouts"));
        tabLayout.addTab(tabLayout.newTab().setText("My Hangouts"));
        tabLayout.setTabGravity((TabLayout.GRAVITY_FILL));
        final ViewPager viewPager = findViewById(R.id.pager);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Write initial data to JSON file
        ArrayList<HangoutModel> dataModels = new ArrayList<>();
        dataModels.add(new HangoutModel("Dinner at McDonald's", "Friday, 4/23, 7:00 PM", "Urbana, IL",
                "For anybody craving some McDonald's", new String[]{"Ethan", "Sally", "You"}, 2, 4));
        dataModels.add(new HangoutModel("Swimming at the ARC", "Saturday 4/24, 4:00 PM", "Champaign, IL",
                "Going swimming at the ARC", new String[]{"Albert", "You"}, 1, 6));
        MyHangoutsFragment.writeMyHangouts(dataModels, getApplicationContext());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_calendar:
                            Intent calIntent = new Intent(MainActivity.this, ViewCalendarFragment.class);
                            startActivity(calIntent);
                            break;
                        case R.id.nav_plan_hangout:
                            Intent planIntent = new Intent(MainActivity.this, PlanHangout.class);
                            startActivity(planIntent);
                            break;
                        case R.id.nav_contacts:
                            Intent conIntent = new Intent(MainActivity.this, UpdateContacts.class);
                            startActivity(conIntent);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + item.getItemId());
                    }
                    return false;
                }
            };
}

