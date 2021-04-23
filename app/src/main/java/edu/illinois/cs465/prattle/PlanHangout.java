package edu.illinois.cs465.prattle;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Collections;

public class PlanHangout extends AppCompatActivity {
    private static final String[] CONTACT_NAMES = new String[] {
            "Sarang Bhagwat", "Archisha Majee", "Maeve Heflin", "Diana Golmeeva", "Albert Cao",  "Yalin Li UIUC Postdoc", "Mark Brewster", "Arhant Shetty",
            "Chantay Sturman","Therese Leis", "Saul Mathias", "Audria Leopard", "Samatha Mollett", "Amber Sturman", "Patty Strang", "Dilip Chaudhary",
            "Alverta Korte", "Joannie Bondy", "Samuel Hand", "Alex Cumberland", "Clemmie Hartin", "Matt Weiss", "Alex Keely", "Yessenia Shirkey",
            "Archie Knappe", "Maudie Troche", "Diana Haskin", "Lonna Fujii"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_hangout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
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
                Snackbar.make(view, "Hangout successfully created!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        finish();
                        ((MyApplication) getApplication()).setClicked(true);
                    }
                }, 2000);
            }
        });


    }
}

