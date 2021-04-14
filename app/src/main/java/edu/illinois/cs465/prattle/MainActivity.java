package edu.illinois.cs465.prattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.page_1);//feel free to move/get rid of this button
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this,UpdateCalendar.class);
            startActivity(intent);
        });
//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//                when(item.itemId) {
//            R.id.item1 -> {
//                // Respond to navigation item 1 click
//                true
//            }
//            R.id.item2 -> {
//                // Respond to navigation item 2 click
//                true
//            }
//        else -> false
//        }
//        }
    }
}

