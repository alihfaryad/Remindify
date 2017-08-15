package com.ialih.reminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 28/07/2017.
 */

public class ConnectDb extends SQLiteOpenHelper {

    //Static Variables
    private static final Integer DB_VERSION = 1;
    private static final String DB_NAME = "ReminderRecords";
    private static final String DB_TBL_NAME = "reminders";
    private static final String TBL_CLM_ID = "id";
    private static final String TBL_CLM_REMINDER = "reminder";
    private static final String TBL_CLM_DATE = "date";
    private static final String TBL_CLM_TIME = "time";
    private static final String TBL_CLM_STATUS = "status";

    public ConnectDb(Context mContext){
        super(mContext, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATING_TBL = "CREATE TABLE "+DB_TBL_NAME+" ("+TBL_CLM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+TBL_CLM_REMINDER+" TEXT, "+TBL_CLM_DATE+" TEXT, "+TBL_CLM_TIME+" TEXT, "+TBL_CLM_STATUS+" BOOLEAN)";
        db.execSQL(CREATING_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DB_TBL_NAME);
        onCreate(db);
    }

    public void addReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_CLM_REMINDER, reminder.getReminder());
        values.put(TBL_CLM_DATE, reminder.getDate());
        values.put(TBL_CLM_TIME, reminder.getTime());
        values.put(TBL_CLM_STATUS, reminder.getStatus());
        db.insert(DB_TBL_NAME, null, values);
        db.close();
    }

    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TBL_NAME, TBL_CLM_ID + " = ?", new String[] { String.valueOf(reminder.getId()) });
        db.close();
    }

    public void updateReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_CLM_REMINDER, reminder.getReminder());
        values.put(TBL_CLM_DATE, reminder.getDate());
        values.put(TBL_CLM_TIME, reminder.getTime());
        values.put(TBL_CLM_STATUS, reminder.getStatus());

        // updating row
        db.update(DB_TBL_NAME, values, TBL_CLM_ID + " = ?", new String[] { String.valueOf(reminder.getId()) });
        db.close();
    }

    public List<Reminder> getAllReminders(){
        List<Reminder> reminderList = new ArrayList<Reminder>();
        String selectQuery = "SELECT * FROM " + DB_TBL_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(0));
                reminder.setReminder(cursor.getString(1));
                reminder.setDate(cursor.getString(2));
                reminder.setTime(cursor.getString(3));
                reminder.setStatus(Boolean.parseBoolean(cursor.getString(4)));
                // Adding contact to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }

        // return contact list
        return reminderList;
    }

    public long getRemindersCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, DB_TBL_NAME);
        db.close();
        return cnt;
    }


}
