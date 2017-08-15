package com.ialih.reminders;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView currentDate , nd_count, md_count, td_count, tm_count, wk_count, n30d_count, all_count;
    ConnectDb db = new ConnectDb(this);

    ArrayList<Integer> id = new ArrayList<Integer>();
    ArrayList<String> reminder = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<Boolean> status = new ArrayList<Boolean>();
    ArrayList<Integer> nd_id = new ArrayList<Integer>();
    ArrayList<String> nd_reminder = new ArrayList<String>();
    ArrayList<String> nd_date = new ArrayList<String>();
    ArrayList<String> nd_time = new ArrayList<String>();
    ArrayList<Boolean> nd_status = new ArrayList<Boolean>();
    ArrayList<Integer> md_id = new ArrayList<Integer>();
    ArrayList<String> md_reminder = new ArrayList<String>();
    ArrayList<String> md_date = new ArrayList<String>();
    ArrayList<String> md_time = new ArrayList<String>();
    ArrayList<Boolean> md_status = new ArrayList<Boolean>();
    ArrayList<Integer> td_id = new ArrayList<Integer>();
    ArrayList<String> td_reminder = new ArrayList<String>();
    ArrayList<String> td_date = new ArrayList<String>();
    ArrayList<String> td_time = new ArrayList<String>();
    ArrayList<Boolean> td_status = new ArrayList<Boolean>();
    ArrayList<Integer> tm_id = new ArrayList<Integer>();
    ArrayList<String> tm_reminder = new ArrayList<String>();
    ArrayList<String> tm_date = new ArrayList<String>();
    ArrayList<String> tm_time = new ArrayList<String>();
    ArrayList<Boolean> tm_status = new ArrayList<Boolean>();
    ArrayList<Integer> wk_id = new ArrayList<Integer>();
    ArrayList<String> wk_reminder = new ArrayList<String>();
    ArrayList<String> wk_date = new ArrayList<String>();
    ArrayList<String> wk_time = new ArrayList<String>();
    ArrayList<Boolean> wk_status = new ArrayList<Boolean>();
    ArrayList<Integer> n30d_id = new ArrayList<Integer>();
    ArrayList<String> n30d_reminder = new ArrayList<String>();
    ArrayList<String> n30d_date = new ArrayList<String>();
    ArrayList<String> n30d_time = new ArrayList<String>();
    ArrayList<Boolean> n30d_status = new ArrayList<Boolean>();

    ListView nodateReminderListView, missedReminderListView, todayReminderListView, tomorrowReminderListView, weekReminderListView, new30daysReminderListView, allReminderListView;
    ImageView settingsBtn, addReminder;

    RelativeLayout nodateLayout, missedLayout, todayLayout, tomorrowLayout, weekLayout, next30daysLayout, allLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Views
        nodateLayout = (RelativeLayout) findViewById(R.id.main_nodate);
        missedLayout = (RelativeLayout) findViewById(R.id.main_missed);
        todayLayout = (RelativeLayout) findViewById(R.id.main_today);
        tomorrowLayout = (RelativeLayout) findViewById(R.id.main_tomorrow);
        weekLayout = (RelativeLayout) findViewById(R.id.main_week);
        next30daysLayout = (RelativeLayout) findViewById(R.id.main_next30days);
        allLayout = (RelativeLayout) findViewById(R.id.main_all);
        nodateReminderListView = (ListView) findViewById(R.id.nodate_reminder_list);
        missedReminderListView = (ListView) findViewById(R.id.missed_reminder_list);
        todayReminderListView = (ListView) findViewById(R.id.today_reminder_list);
        tomorrowReminderListView = (ListView) findViewById(R.id.tommorrow_reminder_list);
        weekReminderListView = (ListView) findViewById(R.id.week_reminder_list);
        new30daysReminderListView = (ListView) findViewById(R.id.next30days_reminder_list);
        allReminderListView = (ListView) findViewById(R.id.all_reminder_list);
        addReminder = (ImageView) findViewById(R.id.addReminder);
        settingsBtn = (ImageView) findViewById(R.id.settingsBtn);
        nd_count = (TextView) findViewById(R.id.nd_count);
        md_count = (TextView) findViewById(R.id.md_count);
        td_count = (TextView) findViewById(R.id.td_count);
        tm_count = (TextView) findViewById(R.id.tm_count);
        wk_count = (TextView) findViewById(R.id.wk_count);
        n30d_count = (TextView) findViewById(R.id.n30d_count);
        all_count = (TextView) findViewById(R.id.all_count);

        nodateLayout.setVisibility(View.GONE);
        missedLayout.setVisibility(View.GONE);
        todayLayout.setVisibility(View.GONE);
        tomorrowLayout.setVisibility(View.GONE);
        weekLayout.setVisibility(View.GONE);
        next30daysLayout.setVisibility(View.GONE);
        allLayout.setVisibility(View.GONE);

        //Getting Date
        getCurrentDate();

        //Getting Data From Db
        List<Reminder> reminders = db.getAllReminders();
        for (Reminder re : reminders) {
            id.add(re.getId());
            reminder.add(re.getReminder());
            date.add(re.getDate());
            time.add(re.getTime());
            status.add(re.getStatus());
        }

        //Sorting Reminder into Proper Tabs According to Date and Time
        for(int a = 0; a < reminder.size(); a++){
            String dateVal = String.valueOf(date.get(a));
            if(dateVal.equals(getTodayDate())){
                td_id.add(id.get(a));
                td_reminder.add(reminder.get(a));
                td_date.add(date.get(a));
                td_time.add(time.get(a));
                td_status.add(status.get(a));
            }
            else if(dateVal.equals(getTomorrowDate())){
                tm_id.add(id.get(a));
                tm_reminder.add(reminder.get(a));
                tm_date.add(date.get(a));
                tm_time.add(time.get(a));
                tm_status.add(status.get(a));
            }
            else if (compareDates(dateVal, getTodayDate()) == true){
                md_id.add(id.get(a));
                md_reminder.add(reminder.get(a));
                md_date.add(date.get(a));
                md_time.add(time.get(a));
                md_status.add(status.get(a));
            }
            else if (dateVal.equals("")){
                nd_id.add(id.get(a));
                nd_reminder.add(reminder.get(a));
                nd_date.add(date.get(a));
                nd_time.add(time.get(a));
                nd_status.add(status.get(a));
            }
            for (int b = 2; b < 8; b++){
                if(dateVal.equals(getIncrementDates(b))){
                    wk_id.add(id.get(a));
                    wk_reminder.add(reminder.get(a));
                    wk_date.add(date.get(a));
                    wk_time.add(time.get(a));
                    wk_status.add(status.get(a));
                }
            }
            for (int c = 8; c < 38; c++){
                if (dateVal.equals(getIncrementDates(c))){
                    n30d_id.add(id.get(a));
                    n30d_reminder.add(reminder.get(a));
                    n30d_date.add(date.get(a));
                    n30d_time.add(time.get(a));
                    n30d_status.add(status.get(a));
                }
            }
        }

        //Displaying Data
        ////Setting in Today
        if (nd_reminder.size() != 0){
            nodateLayout.setVisibility(View.VISIBLE);
            nd_count.setText(String.valueOf(nd_reminder.size()));
            ReminderPreview nodateRemindersList = new ReminderPreview(this, nd_id, nd_reminder, nd_date, nd_time, nd_status, String.valueOf(nd_reminder.size()));
            nodateReminderListView.setAdapter(nodateRemindersList);
        }
        if (md_reminder.size() != 0){
            missedLayout.setVisibility(View.VISIBLE);
            md_count.setText(String.valueOf(md_reminder.size()));
            ReminderPreview missedRemindersList = new ReminderPreview(this, md_id, md_reminder, md_date, md_time, md_status, String.valueOf(md_reminder.size()));
            missedReminderListView.setAdapter(missedRemindersList);
        }
        if (td_reminder.size() != 0) {
            todayLayout.setVisibility(View.VISIBLE);
            td_count.setText(String.valueOf(td_reminder.size()));
            ReminderPreview todayRemindersList = new ReminderPreview(this, td_id, td_reminder, td_date, td_time, td_status, String.valueOf(td_reminder.size()));
            todayReminderListView.setAdapter(todayRemindersList);
        }
        if (tm_reminder.size() != 0){
            tomorrowLayout.setVisibility(View.VISIBLE);
            tm_count.setText(String.valueOf(tm_reminder.size()));
            ReminderPreview tomorrowRemindersList = new ReminderPreview(this, tm_id, tm_reminder, tm_date, tm_time, tm_status, String.valueOf(tm_reminder.size()));
            tomorrowReminderListView.setAdapter(tomorrowRemindersList);
        }
        if (wk_reminder.size() != 0){
            weekLayout.setVisibility(View.VISIBLE);
            wk_count.setText(String.valueOf(wk_reminder.size()));
            ReminderPreview weekRemindersList = new ReminderPreview(this, wk_id, wk_reminder, wk_date, wk_time, wk_status, String.valueOf(wk_reminder.size()));
            weekReminderListView.setAdapter(weekRemindersList);
        }
        if (n30d_reminder.size() != 0){
            next30daysLayout.setVisibility(View.VISIBLE);
            n30d_count.setText(String.valueOf(n30d_reminder.size()));
            ReminderPreview next30daysRemindersList = new ReminderPreview(this, n30d_id, n30d_reminder, n30d_date, n30d_time, n30d_status, String.valueOf(n30d_reminder.size()));
            new30daysReminderListView.setAdapter(next30daysRemindersList);
        }

        String dbCount = String.valueOf(db.getRemindersCount());
        //allLayout.setVisibility(View.VISIBLE);
        //ReminderPreview allReminderPreview = new ReminderPreview(this, id, reminder, date, time, status, dbCount);
        //allReminderListView.setAdapter(allReminderPreview);

        //Going to Add Reminder Page
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddReminder = new Intent(MainActivity.this, AddReminder.class);
                startActivity(goToAddReminder);
            }
        });

        //Going to Settings Page
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(go);
            }
        });

        TextView heading = (TextView) findViewById(R.id.td_label);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        heading.setTypeface(typeface);

    }

    public String getTodayDate(){
        String getDate = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH).format(new Date());
        return getDate;
    }

    public String getTomorrowDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        String getDate = dateFormat.format(tomorrow);
        return getDate;
    }

    public String getIncrementDates(int dayCount){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, dayCount);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        String getDate = dateFormat.format(date);
        return getDate;
    }

    public static boolean compareDates(String d1,String d2) {
        boolean result = false;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            if(date1.before(date2)){
                result = true;
            }
            else {
                result = false;
            }
        }
        catch(ParseException ex){
            ex.printStackTrace();
        }
        return result;
    }

    public void getCurrentDate(){
        currentDate = (TextView) findViewById(R.id.currentDate);
        String getDate = new SimpleDateFormat("EE dd MMM", Locale.ENGLISH).format(new Date());
        currentDate.setText(getDate);
    }

}
