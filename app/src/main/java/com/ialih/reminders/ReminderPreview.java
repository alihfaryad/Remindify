package com.ialih.reminders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ali on 28/07/2017.
 */

public class ReminderPreview extends BaseAdapter {
    Context context;
    ArrayList<Integer> id = new ArrayList<Integer>();
    ArrayList<String> reminder = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<Boolean> status = new ArrayList<Boolean>();
    Integer count;

    public ReminderPreview(Context getContext, ArrayList<Integer> getId, ArrayList<String> getReminder, ArrayList<String> getDate, ArrayList<String> getTime, ArrayList<Boolean> getStatus, String getCount){
        context = getContext;
        id = getId;
        reminder = getReminder;
        date = getDate;
        time = getTime;
        status = getStatus;
        count = Integer.valueOf(getCount);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return id.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.reminder_preview, null);
        final ConnectDb db = new ConnectDb(context);

        final TextView Reminder = (TextView) convertView.findViewById(R.id.reminder);
        TextView Date = (TextView) convertView.findViewById(R.id.date);
        final TextView Time = (TextView) convertView.findViewById(R.id.time);
        CheckBox Status = (CheckBox) convertView.findViewById(R.id.checkbox);
        RelativeLayout listItem = (RelativeLayout) convertView.findViewById(R.id.listItem);

        Reminder.setText(reminder.get(position));
        Date.setText(date.get(position));
        Time.setText(time.get(position));
        Status.setChecked(status.get(position));

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
        Reminder.setTypeface(typeface);

        Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateReminder(new Reminder("changed Outside", date.get(position), time.get(position), status.get(position)));
            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateReminder.class);
                intent.putExtra("id", id.get(position));
                intent.putExtra("reminder", reminder.get(position));
                intent.putExtra("date", date.get(position));
                intent.putExtra("time", time.get(position));
                intent.putExtra("status", status.get(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
