package edu.illinois.cs465.prattle;

import androidx.appcompat.app.AppCompatActivity;
import edu.illinois.cs465.prattle.data.HangoutModel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
        String participants[];
        int minParticipants = 0;
        int maxParticipants = 0;
        boolean isMyHangouts = false;
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

            participants = b.getStringArray("participants");
            minParticipants = b.getInt("minParticipants");
            maxParticipants = b.getInt("maxParticipants");
            TextView namesViews[] = new TextView[]{findViewById(R.id.hangout_page_name_1),
                    findViewById(R.id.hangout_page_name_2),
                    findViewById(R.id.hangout_page_name_3),
                    findViewById(R.id.hangout_page_name_4)};
            ImageView profilesViews[] = new ImageView[]{findViewById(R.id.hangout_page_icon_1),
                    findViewById(R.id.hangout_page_icon_2),
                    findViewById(R.id.hangout_page_icon_3),
                    findViewById(R.id.hangout_page_icon_4)};
            for (int i = 0; i < participants.length; i++) {
                namesViews[i].setText(participants[i]);
                profilesViews[i].setImageResource(R.drawable.ic_baseline_account_circle_24);
                if (participants[i].equals("Sally")) {
                    profilesViews[i].setImageResource(R.drawable.sally);
                }
                else if (participants[i].equals("Joe")) {
                    profilesViews[i].setImageResource(R.drawable.joe);
                }
                else if (participants[i].equals("Albert")) {
                    profilesViews[i].setImageResource(R.drawable.albert);
                }
                else if (participants[i].equals("Amy")) {
                    profilesViews[i].setImageResource(R.drawable.amy);
                }
                else if (participants[i].equals("Billy")) {
                    profilesViews[i].setImageResource(R.drawable.billy);
                }
                else if (participants[i].equals("Bob")) {
                    profilesViews[i].setImageResource(R.drawable.bob);
                }
                else if (participants[i].equals("Ethan")) {
                    profilesViews[i].setImageResource(R.drawable.ethan);
                }
            }
            String participantsText = "Attendees: " + String.valueOf(participants.length) + "/"
                    + String.valueOf(maxParticipants);
            TextView participantsView = findViewById(R.id.hangout_page_participants);
            participantsView.setText(participantsText);
            TextView minParticipantsView = findViewById(R.id.hangout_page_min_participants);
            String minParticipantsText = "minimum: " + String.valueOf(minParticipants);
            minParticipantsView.setText(minParticipantsText);

            isMyHangouts = b.getBoolean("isMyHangouts");
            Button bottomButton = findViewById(R.id.hangout_page_bottom_button);
            Button commentButton = findViewById(R.id.submit_comment);
            if (isMyHangouts) {
                bottomButton.setText("Leave Hangout");
                String finalTitle = title;
                bottomButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ArrayList<HangoutModel> dataSet = MyHangoutsFragment.getMyHangouts(getApplicationContext());
                        int indexRemove = 0;
                        for (int i = 0; i < dataSet.size(); i++) {
                            if (dataSet.get(i).getTitle().equals(finalTitle)) {
                                indexRemove = i;
                                break;
                            }
                        }
                        dataSet.remove(indexRemove);
                        MyHangoutsFragment.writeMyHangouts(dataSet, getApplicationContext());
                        Toast.makeText(HangoutPage.this, "Left Hangout", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                ListView listView = findViewById(R.id.comments_list);
                String[] comments = new String[] {"Albert\nHello friends!", "Bob\nGoodbye friends!"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(HangoutPage.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, comments);
                listView.setAdapter(adapter);
                commentButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String comment = ((EditText) findViewById(R.id.comment_input)).getText().toString();
                        String[] newComments = new String[] {"Albert\nHello friends!", "Bob\nGoodbye friends!", "You\n" + comment};
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(HangoutPage.this,
                                android.R.layout.simple_list_item_1, android.R.id.text1, newComments);
                        listView.setAdapter(adapter);
                    }
                });
            }
            else {
                bottomButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(HangoutPage.this, "Sent Request!", Toast.LENGTH_SHORT).show();
                    }
                });
                commentButton.setVisibility(View.GONE);
                EditText commentTextView = findViewById(R.id.comment_input);
                commentTextView.setVisibility(View.GONE);
            }

        }
    }
}