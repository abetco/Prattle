package edu.illinois.cs465.prattle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class UpdateCalendar extends AppCompatActivity {
        private Spinner spinner1;
        String[] week_days;
        RecyclerView recyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_calendar);
            getSupportActionBar().setTitle("Set Free Time");

            week_days = getResources().getStringArray(R.array.weekdays);

            recyclerView = findViewById(R.id.recycler_freetime);

            FreetimeAdapter freetimeAdapter = new FreetimeAdapter(this,week_days);

            recyclerView.setAdapter(freetimeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.cal_toolbar, menu);
            MenuItem item = menu.findItem(R.id.item1);
            spinner1 = (Spinner) item.getActionView();
            SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.calendar2_tasks_array, android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(mSpinnerAdapter);
            addListenerOnSpinnerItemSelection();
            return true;
        }

        public void addListenerOnSpinnerItemSelection() {
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if (parentView.getItemAtPosition(position).toString().equals("View Calendar")) {
                        Intent intent = new Intent(UpdateCalendar.this, ViewCalendarFragment.class);
                        startActivity(intent);
                    }

                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });
        }
}