package com.example.saumya.sakshamsense.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Phone_Records")
public class PhoneLockRecordEntity
{
    @PrimaryKey(autoGenerate = true)
    private int record_id;

    private String date;
    private String state;
    private String time;

    public PhoneLockRecordEntity(String date, String time, String state)
    {
        this.date=date;
        this.time=time;
        this.state=state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
