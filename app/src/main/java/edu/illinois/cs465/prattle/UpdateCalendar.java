package edu.illinois.cs465.prattle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;


public class UpdateCalendar extends AppCompatActivity {
        private Spinner spinner1;
        private Button btnSubmit;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_calendar);

            GridView gridview = (GridView) findViewById(R.id.week_holder);
            gridview.setAdapter(new ButtonAdapter(this));
            getSupportActionBar().setTitle("Set Free Time");
            spinner1 = (Spinner) findViewById(R.id.update_cal_spinner);
            addListenerOnSpinnerItemSelection();


        }

        public void addListenerOnSpinnerItemSelection() {
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
//                    Log.i("In Listener", "parentView.getItemAtPosition(position).toString()");
                    if (parentView.getItemAtPosition(position).toString().equals("View Calendar")) {
        //                    Log.i("View Calendar", "parentView.getItemAtPosition(position).toString()");
                        Intent intent = new Intent(UpdateCalendar.this, ViewCalendarFragment.class);
                        startActivity(intent);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
        }

        public class ButtonAdapter extends BaseAdapter {
            private Context mContext;
            private int btn_id;
            private int total_btns = 168;

            public ButtonAdapter(Context context) {
                this.mContext = context;
            }

            @Override
            public int getCount() {
                return total_btns;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(final int i, View view, ViewGroup viewGroup) {
                Button btn;

                if (view == null) {
                    btn = new Button(mContext);
                    btn.setTag("a" + (++btn_id));//Button tagging system all buttons currently are taged a0-a167
                } else {
                    btn = (Button) view;
                }

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.btn_list));
                        btn.setSelected(!btn.isSelected());
                    }
                });

                return btn;
            }
        }
    }

    class PrattleCalendar{
        String uid = new String();
        String pwd = new String();
        boolean[][][] hourly_availabilities = new boolean[12][31][24];

        public PrattleCalendar(String userID, String password){
                uid = userID;
                pwd = password;
        }
        protected void wipe_availabilities(boolean[][][] h_a){
            /* naive implementation*/
            for (int i = 0; i < h_a.length; i++) {
                for (int j = 0; j < h_a[i].length; j++) {
                    for (int k = 0; k < h_a[i][j].length; k++) {
                        h_a[i][j][k] = false;
                    }
                }
            }
        }

    }


/*
class PrattleCalendar{ # app has one non-perishable instance associated with user
*   init(userID):
*       self.userID = userID
*    attributes:
*       days_subtracted_array = [0, (3 - is_leap_year*1), 0, 1, 0, 1, 0, 0, 1, 0, 1, 0]
*    properties:
*       current_day, current_week, current_month
*           # setters will be triggered each day
*       boolean[][][] hourly_availabilities_array = new boolean[12][31][24] # indexing: [month][day][hour] # false by default
*           # setter will be triggered each month to pop the last month and add it to the end of the queue (for next year)
*       hangouts: list of Hangout objects
*}
*
class PrattleCalendarView{# one perishable instance at a time associated with one PrattleCalendar instance
*   init(prattle_calendar):
*   self.prattle_calendar = prattle_calendar
*
*   properties:
*       time_being_viewed # tuple # default = (self.prattle_calendar.current_month, 0, .current_day)
*           # if left open, setter will be triggered each day to set month, day to current
*
*   methods:
*       get_hourly_availabilities_slice(indices)
*          # get required slice of self.prattle_calendar.hourly_availabilities
*       get_hangouts_slice(indices)
*          # get required slice of self.prattle_calendar.hangouts
*       view_calendar_day(day=self.day_being_viewed):
*           get_hourly_availabilities_slice([self.month_being_viewed, self.day_being_viewed, :])
*           onShrink:
*               view_calendar_week(month=self.week_being_viewed)
*           onSwipe:
*                view_calendar_day(day = day + swipe_direction)
*           onClick:
*               view_event(
*       view_calendar_week(week=self.current_week):
*           get_hourly_availabilities([self.month_being_viewed, (self.day_being_viewed-3):(self.day_being_viewed+3), :])
*           onExpand:
*               view_calendar_day(day=get_first_day(week))
*           onShrink:
*               view_calendar_month(month=self.month_being_viewed)
*           onSwipe:
*               view_calendar_week(week = week + swipe_direction)
*
*       view_calendar_month(month=self.current_month): # entry point
*           # jump to CalendarView widget with an overlay of availabilities and hangouts
*           get_hourly_availabilities([self.month_being_viewed, :, :])
*           onExpand:
*               view_calendar_week(week=self.month_being_viewed[0])
*           onSwipe:
*           view_calendar_month(month = month + swipe_direction)
*
* class Hangout
*   attributes:
*   properties:
*       name
*       creator
*       participants
*       time
*       location
*   */