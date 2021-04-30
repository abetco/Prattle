package edu.illinois.cs465.prattle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import edu.illinois.cs465.prattle.data.HangoutModel;
import edu.illinois.cs465.prattle.data.MyHangoutsAdapter;

public class ViewCalendarFragment extends AppCompatActivity {
    private Spinner spinner1;
    private ArrayList<HangoutModel> dataModels;
    TextView result;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    CompactCalendarView compactCalendar;
    ArrayList<Long> epochDates = new ArrayList<>();
    ArrayList<String> eventDates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar);
//        getSupportActionBar().setTitle("April- 2021");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("April- 2021");

        dataModels = getMyHangouts(this);
        MyHangoutsAdapter adapter = new MyHangoutsAdapter(dataModels);

//        long[] epochDates = new long[dataModels.size()];
        for (int i = 0; i < dataModels.size(); i++) {
            String time = dataModels.get(i).getDate();
            String[] datetime = time.split(", ");
//            String[] date = datetime[0].split(",");
//            String date = datetime[0];
            String eveDate = datetime[1] + "/2021";
            eventDates.add(eveDate);
            long epoch = 0L;
            try {
                epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(eveDate + " 00:00:00").getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }

            epochDates.add(epoch);
        }

        result = (TextView) findViewById(R.id.tvResult);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        // Add Events
        for (int i = 0; i < epochDates.size(); i++) {
            int eveColor = getResources().getColor(R.color.transparent_white);
            Event ev1 = new Event(eveColor, epochDates.get(i), dataModels.get(i).getTitle());
            compactCalendar.addEvent(ev1, false);
        }

        compactCalendar.setListener((new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                String date = convertStringToDate(dateClicked);
                Boolean isEvent = false;
                ArrayList<HangoutModel> eventHangouts = new ArrayList<>();
                for (int i = 0; i < eventDates.size(); i++) {
//                    Toast.makeText(context, eventDates.get(i), Toast.LENGTH_SHORT).show();
                    if (eventDates.get(i).equals(date)) {
                        result.setVisibility(View.INVISIBLE);
                        isEvent = true;
                        eventHangouts.add(dataModels.get(i));

                    }
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_viewCalendar);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    MyHangoutsAdapter adapter = new MyHangoutsAdapter(eventHangouts);
                    recyclerView.setAdapter(adapter);
                }

                if(!isEvent) {
                    result.setVisibility(View.VISIBLE);
                    result.setText("No hangouts.");
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cal_toolbar, menu);
        MenuItem item = menu.findItem(R.id.item1);
        spinner1 = (Spinner) item.getActionView();

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.calendar_tasks_array, R.layout.spinner_item);
        spinner1.setAdapter(mSpinnerAdapter);
        addListenerOnSpinnerItemSelection();
        return true;
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (parentView.getItemAtPosition(position).toString().equals("Update Availability")) {
//                    Log.i("View Calendar", "parentView.getItemAtPosition(position).toString()");
                    Intent intent = new Intent(ViewCalendarFragment.this, UpdateCalendar.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    public static ArrayList<HangoutModel> getMyHangouts(Context context) {
        JSONArray myHangoutsList = null;
        try {
            File f = new File("/data/data/" + context.getPackageName() + "/myHangouts.json");
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            myHangoutsList = new JSONArray(mResponse);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        ArrayList<HangoutModel> newHangouts = new ArrayList<>();
        if (myHangoutsList != null) {
            for (int i = 0; i < myHangoutsList.length(); i++) {
                try {
                    JSONObject hangout = myHangoutsList.getJSONObject(i);
                    JSONArray participantsJSON = hangout.getJSONArray("participants");
                    String participants[] = new String[participantsJSON.length()];
                    for (int j = 0; j < participantsJSON.length(); j++) {
                        participants[j] = participantsJSON.get(j).toString();
                    }
                    newHangouts.add(new HangoutModel(hangout.getString("title"), hangout.getString("date"),
                            hangout.getString("location"), hangout.getString("description"),
                            participants, hangout.getInt("minParticipants"), hangout.getInt("maxParticipants")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return newHangouts;
    }

    public String convertStringToDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }
}