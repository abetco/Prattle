package edu.illinois.cs465.prattle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import androidx.appcompat.app.ActionBar;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewCalendarFragment extends Activity {
    private Spinner spinner1;
    private long dateTime;
    private String eventName;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    CompactCalendarView compactCalendar;
    private boolean isEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar);
        Intent recIntent = getIntent();

        if(recIntent.hasExtra("isEvent")) {
            Bundle extras = recIntent.getExtras();
//            dateTime = extras.getLong("event_date", 0L);
//            eventName = getIntent().getExtras().getString("event_name");
            isEvent = getIntent().getExtras().getBoolean("isEvent");
        } else {
            eventName = "";
            dateTime = 0L;
            isEvent = false;
        }

        spinner1 = (Spinner) findViewById(R.id.calendar_task_spinner);
        addListenerOnSpinnerItemSelection();

//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

//        Context context = getApplicationContext();
//        Toast.makeText(context, eventName, Toast.LENGTH_SHORT).show();

        if(isEvent) {
            int eveColor = getResources().getColor(R.color.transparent_white);
            Event ev1 = new Event(eveColor, 1619213533000L, "GOT Movie");
//            Context context = getApplicationContext();
//            Toast.makeText(context, "In statement", Toast.LENGTH_SHORT).show();
            compactCalendar.addEvent(ev1, false);
        }

        compactCalendar.setListener((new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
//                    Toast.makeText(context, dateClicked.toString(), Toast.LENGTH_SHORT).show();
                if (dateClicked.toString().compareTo("Fri Apr 23 00:00:00 CDT 2021") == 0 && isEvent) {
                    Toast.makeText(context, "GOT", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        }));
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (parentView.getItemAtPosition(position).toString().equals("Update Calendar")) {
//                    Log.i("View Calendar", "parentView.getItemAtPosition(position).toString()");
                    Intent intent = new Intent(ViewCalendarFragment.this, UpdateCalendar.class);
                    startActivity(intent);
//                    Toast.makeText(parentView.getContext(),
//                            "OnItemSelectedListener : " + parentView.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
}