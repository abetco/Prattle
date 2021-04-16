package edu.illinois.cs465.prattle;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;


public class UpdateCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_calendar);
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