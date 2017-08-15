package com.ialih.reminders;

/**
 * Created by ali on 28/07/2017.
 */

public class Reminder {
    private Integer id;
    private String reminder, date, time;
    private Boolean status;

    public Reminder(){}

    public Reminder(Integer getId, String getReminder, String getDate, String getTime, Boolean getStatus){
        this.id = getId;
        this.reminder = getReminder;
        this.date = getDate;
        this.time = getTime;
        this.status = getStatus;
    }

    public Reminder(String getReminder, String getDate, String getTime, Boolean getStatus){
        this.reminder = getReminder;
        this.date = getDate;
        this.time = getTime;
        this.status = getStatus;
    }

    public Reminder(Integer getId){
        this.id = getId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
