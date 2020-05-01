package com.example.saumya.sakshamsense.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "App_Records")
public class AppRecordEntity
{
    @PrimaryKey(autoGenerate = true)
    private int record_id;

    private String date;
    private String appName;
    private String time;
    private String curr_time;

    public String getCurr_time() {
        return curr_time;
    }

    public void setCurr_time(String curr_time) {
        this.curr_time = curr_time;
    }

    public AppRecordEntity(String date, String curr_time, String appName, String time)
    {
        this.date=date;
        this.time=time;
        this.curr_time=curr_time;
        this.appName=appName;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
