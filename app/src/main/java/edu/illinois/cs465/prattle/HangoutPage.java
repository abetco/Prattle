package edu.illinois.cs465.prattle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HangoutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangout_page);

        Bundle b = getIntent().getExtras();
        String title = "";
        String date = "";
        String location = "";
        String description = "";
        if (b != null) {
            title = b.getString("title");
            TextView titleView = findViewById(R.id.hangout_page_title);
            titleView.setText(title);

            date = b.getString("date");
            TextView dateView = findViewById(R.id.hangout_page_date);
            dateView.setText(date);

            location = b.getString("location");
            TextView locationView = findViewById(R.id.hangout_page_location);
            locationView.setText(location);

            description = b.getString("description");
            TextView descriptionView = findViewById(R.id.hangout_page_description);
            descriptionView.setText(description);

        }
    }
}