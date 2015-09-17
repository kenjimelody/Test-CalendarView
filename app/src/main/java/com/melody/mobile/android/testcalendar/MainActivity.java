package com.melody.mobile.android.testcalendar;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.melody.mobile.android.testcalendar.decorators.EventDecorator;
import com.melody.mobile.android.testcalendar.utils.Global;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Thanisak Piyasaksiri on 9/17/15 AD.
 */
public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private AQuery aq;

    private MaterialCalendarView calendarView;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {

            finish();

        } else {
            Toast.makeText(getBaseContext(), getString(R.string.alert_backpress), Toast.LENGTH_SHORT).show();
        }

        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        aq = new AQuery(this);
        init();
    }

    private void init() {

        setSupportActionBar((Toolbar) aq.id(R.id.home_toolbar).getView());
        getSupportActionBar().setTitle(R.string.app_name);

        ((Toolbar) aq.id(R.id.home_toolbar).getView()).setTitleTextColor(Color.WHITE);
        ((Toolbar) aq.id(R.id.home_toolbar).getView()).setSubtitleTextColor(Color.WHITE);
        ((Toolbar) aq.id(R.id.home_toolbar).getView()).setPadding(0, Global.getStatusBarHeight(this), 0, 0);

        calendarView = (MaterialCalendarView) aq.id(R.id.calendarView).getView();
        calendarView.setSelectedDate(Calendar.getInstance());
        calendarView.setSelectionColor(ContextCompat.getColor(this, R.color.colorPrimary));
        calendarView.setArrowColor(ContextCompat.getColor(this, R.color.colorPrimary));
        calendarView.setShowOtherDates(true);

        calendarView.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
        calendarView.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        calendarView.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);

        calendarView.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date) {

                if (date != null)
                    Toast.makeText(MainActivity.this, FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
            }
        });

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if(isFinishing()) {
                return;
            }

            calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }
}
