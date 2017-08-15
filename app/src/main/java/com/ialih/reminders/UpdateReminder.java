package com.ialih.reminders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by ali on 12/08/2017.
 */

public class UpdateReminder extends AppCompatActivity {

    Button deleteBtn, markCompletedBtn;
    EditText newReminder;
    Button datePickerButton, timePickerButton;
    DatePickerDialog.OnDateSetListener datePickerListener;
    TimePickerDialog.OnTimeSetListener timePickerListener;
    TextView displayDate, displayTime;
    RelativeLayout reminderDisplay;
    RadioGroup dateGroup, timeGroup;
    RadioButton todaybtn, tomorrowbtn, datepickerbtn, time9, time12, time3, timepickerbtn;
    ImageView close, save;
    ConnectDb db = new ConnectDb(this);
    Integer id;
    String getReminder, getDate, getTime, reminder, date, time;
    Boolean getStatus, status;

    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_reminder);

        mainActivity = new MainActivity();

        close = (ImageView) findViewById(R.id.close);
        save = (ImageView) findViewById(R.id.save);
        newReminder = (EditText) findViewById(R.id.newReminder);
        datePickerButton = (Button) findViewById(R.id.setDate);
        timePickerButton = (Button) findViewById(R.id.setTime);
        displayDate = (TextView) findViewById(R.id.displayDate);
        displayTime = (TextView) findViewById(R.id.displayTime);
        reminderDisplay = (RelativeLayout) findViewById(R.id.reminderDisplay);
        dateGroup = (RadioGroup) findViewById(R.id.date);
        timeGroup = (RadioGroup) findViewById(R.id.time);
        todaybtn = (RadioButton) findViewById(R.id.date_today);
        tomorrowbtn = (RadioButton) findViewById(R.id.date_tomorrow);
        datepickerbtn = (RadioButton) findViewById(R.id.setDate);
        time9 = (RadioButton) findViewById(R.id.time_9);
        time12 = (RadioButton) findViewById(R.id.time_12);
        time3 = (RadioButton) findViewById(R.id.time_3);
        timepickerbtn = (RadioButton) findViewById(R.id.setTime);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        markCompletedBtn = (Button) findViewById(R.id.markCompletedBtn);

        final Intent goBack = new Intent(UpdateReminder.this, MainActivity.class);
        Intent getData = this.getIntent();

        id = getData.getExtras().getInt("id");
        getReminder = getData.getExtras().getString("reminder");
        getDate = getData.getExtras().getString("date");
        getTime = getData.getExtras().getString("time");
        getStatus = getData.getExtras().getBoolean("status");

        date = getDate;

        newReminder.setText(getReminder);
        displayDate.setText(getDate);
        displayTime.setText(getTime);
        if (getDate == null){
            reminderDisplay.setVisibility(View.GONE);
        } else if (getDate.equals(mainActivity.getTodayDate())){
            todaybtn.setChecked(true);
        } else if (getDate.equals(mainActivity.getTomorrowDate())){
            tomorrowbtn.setChecked(true);
        } else {
            datepickerbtn.setChecked(true);
        }
        if (getTime == null){

        } else if (getTime.equals("9:00 AM")){
            time9.setChecked(true);
        } else if (getTime.equals("12:00 PM")){
            time12.setChecked(true);
        } else if (getTime.equals("3:00 PM")){
            time3.setChecked(true);
        } else {
            timepickerbtn.setChecked(true);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goBack);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = displayDate.getText().toString();
                time = displayTime.getText().toString();
                db.updateReminder(new Reminder(id, newReminder.getText().toString(), date, time, getStatus));
                startActivity(goBack);
            }
        });

        markCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = displayDate.getText().toString();
                time = displayTime.getText().toString();
                db.updateReminder(new Reminder(id, newReminder.getText().toString(), date, time, true));
                startActivity(goBack);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteReminder(new Reminder(id));
                startActivity(goBack);
            }
        });

        dateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.date_today:
                        displayDate.setText(mainActivity.getTodayDate());
                        reminderDisplay.setVisibility(View.VISIBLE);
                        date = mainActivity.getTodayDate();
                        break;
                    case R.id.date_tomorrow:
                        displayDate.setText(mainActivity.getTomorrowDate());
                        reminderDisplay.setVisibility(View.VISIBLE);
                        date = mainActivity.getTomorrowDate();
                        break;
                    case R.id.setDate:
                        displayDate.setText(date);
                        break;
                    default:
                        break;
                }
            }
        });
        timeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.time_9:
                        displayTime.setText("9:00 AM");
                        break;
                    case R.id.time_12:
                        displayTime.setText("12:00 PM");
                        break;
                    case R.id.time_3:
                        displayTime.setText("3:00 PM");
                        break;
                    case R.id.setTime:
                        displayTime.setText(time);
                        break;
                    default:
                        break;
                }
                if (date == null){
                    todaybtn.setChecked(true);
                }

            }
        });


        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateReminder.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        datePickerListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date =  day+ "/" + month + "/" + year;
                datePickerButton.setText(date);
                displayDate.setText(date);
                if (date != null){
                    reminderDisplay.setVisibility(View.VISIBLE);
                }
            }
        };

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(UpdateReminder.this,timePickerListener,hour,minute,false);
                dialog.getWindow();
                dialog.show();
            }
        });
        timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour_string =String.valueOf(hourOfDay);
                String minute_string = String.valueOf(minute);
                String AM_PM;
                if (hourOfDay > 12){
                    hour_string = String.valueOf(hourOfDay - 12);
                    AM_PM = "PM";
                } else {
                    AM_PM = "AM";
                }
                if (hourOfDay == 0){
                    hour_string = "12";
                }
                if (minute <10){
                    minute_string = "0" +String.valueOf(minute);
                }
                time = hour_string + ":" + minute_string + " " + AM_PM;
                timePickerButton.setText(time);
                displayTime.setText(time);
            }
        };

    }
}
