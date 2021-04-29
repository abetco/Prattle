package edu.illinois.cs465.prattle;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.illinois.cs465.prattle.data.HangoutModel;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Collections;

public class PlanHangout extends AppCompatActivity {
    private static final String[] CONTACT_NAMES = new String[] {
            "Sarang Bhagwat", "Archisha Majee", "Maeve Heflin", "Diana Golmeeva", "Albert Cao",  "Yalin Li UIUC Postdoc", "Mark Brewster", "Arhant Shetty",
            "Chantay Sturman","Therese Leis", "Saul Mathias", "Audria Leopard", "Samatha Mollett", "Amber Sturman", "Patty Strang", "Dilip Chaudhary",
            "Alverta Korte", "Joannie Bondy", "Samuel Hand", "Alex Cumberland", "Clemmie Hartin", "Matt Weiss", "Alex Keely", "Yessenia Shirkey",
            "Archie Knappe", "Maudie Troche", "Diana Haskin", "Lonna Fujii"

    };

    private static final String[] DAYS_OF_WEEK = new String[] {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_hangout);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        ArrayList<String> CN = new ArrayList<String>();
        for (int i = 0; i < CONTACT_NAMES.length; i++) {
            CN.add(CONTACT_NAMES[i]);
        }
        Collections.sort(CN);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CN);

        AutoCompleteTextView invitee1 = findViewById(R.id.invitee1);
        invitee1.setAdapter(adapter);
        AutoCompleteTextView invitee2 = findViewById(R.id.invitee1);
        invitee2.setAdapter(adapter);
        AutoCompleteTextView invitee3 = findViewById(R.id.invitee1);
        invitee3.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get values from editText fields in activity_plan
//                String dateTextVal = ((EditText) findViewById(R.id.editTextDate)).getText().toString();
//                String timeTextVal  = ((EditText) findViewById(R.id.editTextTime3)).getText().toString();
//                String eventNameVal = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();

                // create intent and pass values using bundles

                Snackbar.make(view, "Hangout successfully created!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent mIntent = new Intent(PlanHangout.this, ViewCalendarFragment.class);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        /*finish();*/
                        String hangoutTitle = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();
                        int minParticipants = Integer.parseInt(((EditText) findViewById(R.id.editTextNumber2)).getText().toString());
                        int maxParticipants = Integer.parseInt(((EditText) findViewById(R.id.editTextNumber3)).getText().toString());
                        String location = ((EditText) findViewById(R.id.editTextTextMultiLine)).getText().toString();
                        String description = ((EditText) findViewById(R.id.editTextTextMultiLine2)).getText().toString();
                        TimePicker tp = (TimePicker) findViewById(R.id.time_picker);
                        DatePicker dp = (DatePicker) findViewById(R.id.date_picker);
                        int currHour = tp.getCurrentHour();
                        int currMinute = tp.getCurrentMinute();
                        String minute = String.valueOf(currMinute);
                        if (currMinute < 10) {
                            minute = "0" + minute;
                        }
                        String ampm = "AM";
                        if (currHour > 12) {
                            currHour = currHour - 12;
                            ampm = "PM";
                        }
                        String dayName = "";
                        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date myDate = inFormat.parse(String.valueOf(dp.getDayOfMonth()) + "-" + String.valueOf(dp.getMonth()+1) + "-" + String.valueOf(dp.getYear()));
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                            dayName = simpleDateFormat.format(myDate);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String datetime =  dayName + " " +
                                String.valueOf(dp.getMonth()+1) + "/" + String.valueOf(dp.getDayOfMonth()) + ", "
                                + String.valueOf(currHour) + ":" + minute + ampm;
                        ArrayList<HangoutModel> dataModels = MyHangoutsFragment.getMyHangouts(getApplicationContext());
                        dataModels.add(new HangoutModel(hangoutTitle, datetime, location,
                                description, new String[]{"You"}, minParticipants, maxParticipants));
                        MyHangoutsFragment.writeMyHangouts(dataModels, getApplicationContext());

                        Bundle mBundle = new Bundle();
                        mBundle.putBoolean("isEvent", true);
                        mIntent.putExtras(mBundle);
                        startActivity(mIntent);
                    }
                }, 1000);
//                String str = dateTextVal + " " + timeTextVal + "UTC";
//                String str = "April 23 2021 23:11:52.454 UTC";
//                SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
            }
        });
    }
}