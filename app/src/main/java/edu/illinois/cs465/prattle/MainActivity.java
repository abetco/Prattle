package edu.illinois.cs465.prattle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button button = findViewById(R.id.calendar);//feel free to move/get rid of this button
//        button.setOnClickListener(view -> {
//            Intent intent = new Intent(this,UpdateCalendar.class);
//            startActivity(intent);
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_calendar:
                            Intent intent = new Intent(MainActivity.this, UpdateCalendar.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_plan_hangout:
                            break;
                        case R.id.nav_contacts:
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + item.getItemId());
                    }
                    return false;
                }
            };
}

