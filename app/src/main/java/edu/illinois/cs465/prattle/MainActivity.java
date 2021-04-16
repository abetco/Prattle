package edu.illinois.cs465.prattle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import edu.illinois.cs465.prattle.data.TabsAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // My Hangouts Tab List
    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom Navigation Creation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Hangouts tab creation
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Browse Hangouts"));
        tabLayout.addTab(tabLayout.newTab().setText("My Hangouts"));
        tabLayout.setTabGravity((TabLayout.GRAVITY_FILL));
        final ViewPager viewPager = findViewById(R.id.pager);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

        // My Hangouts List Creation
        listView = findViewById(R.id.my_hangouts_list);
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
//        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
//        listView.setAdapter(adapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_calendar:
                            Intent calIntent = new Intent(MainActivity.this, UpdateCalendar.class);
                            startActivity(calIntent);
                            break;
                        case R.id.nav_plan_hangout:
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

