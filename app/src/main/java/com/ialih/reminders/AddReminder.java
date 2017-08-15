package com.ialih.reminders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ali on 27/07/2017.
 */

public class AddReminder extends Activity {
    EditText getReminder;
    Button datePickerButton, timePickerButton;
    DatePickerDialog.OnDateSetListener datePickerListener;
    TimePickerDialog.OnTimeSetListener timePickerListener;
    RadioGroup dateGroup, timeGroup;
    TextView displayDate, displayTime;
    RelativeLayout reminderDisplay;
    ImageView getData, displayReminderDelete;

    String date, time;

    ConnectDb db;
    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder);

        db = new ConnectDb(this);
        mainActivity = new MainActivity();

        reminderDisplay = (RelativeLayout) findViewById(R.id.ar_reminderDisplay);
        displayDate = (TextView) findViewById(R.id.ar_displayDate);
        displayTime = (TextView) findViewById(R.id.ar_displayTime);
        dateGroup = (RadioGroup) findViewById(R.id.ar_date);
        timeGroup = (RadioGroup) findViewById(R.id.ar_time);
        datePickerButton = (Button) findViewById(R.id.ar_setDate);
        timePickerButton = (Button) findViewById(R.id.ar_setTime);
        getReminder = (EditText) findViewById(R.id.ar_new);
        getData = (ImageView) findViewById(R.id.ar_save);
        displayReminderDelete = (ImageView) findViewById(R.id.ar_reminderDisplay_delete);
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getreminder = getReminder.getText().toString();
                String getDate = displayDate.getText().toString();
                String getTime = displayTime.getText().toString();
                Boolean status = false;

                db.addReminder(new Reminder(getreminder, getDate, getTime, status));

                //going back to MainActivity
                Intent goToMainActivity = new Intent(AddReminder.this, MainActivity.class);
                startActivity(goToMainActivity);
            }
        });

        displayReminderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.ar_date_today:
                        displayDate.setText(mainActivity.getTodayDate());
                        reminderDisplay.setVisibility(View.VISIBLE);
                        date = mainActivity.getTodayDate();
                        break;
                    case R.id.ar_date_tomorrow:
                        displayDate.setText(mainActivity.getTomorrowDate());
                        reminderDisplay.setVisibility(View.VISIBLE);
                        date = mainActivity.getTomorrowDate();
                        break;
                    case R.id.ar_setDate:
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
                    case R.id.ar_time_9:
                        displayTime.setText("9:00 AM");
                        break;
                    case R.id.ar_time_12:
                        displayTime.setText("12:00 PM");
                        break;
                    case R.id.ar_time_3:
                        displayTime.setText("3:00 PM");
                        break;
                    case R.id.ar_setTime:
                        displayTime.setText(time);
                        break;
                    default:
                        break;
                }
                if (date == null){
                    RadioButton today = (RadioButton) findViewById(R.id.ar_date_today);
                    today.setChecked(true);
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

                DatePickerDialog dialog = new DatePickerDialog(AddReminder.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                TimePickerDialog dialog = new TimePickerDialog(AddReminder.this,timePickerListener,hour,minute,false);
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

        //Going to MainActivity Page
        ImageView closeBtn = (ImageView) findViewById(R.id.ar_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
