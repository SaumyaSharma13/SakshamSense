package com.example.saumya.sakshamsense.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SMS_Records")
public class SMSRecordEntity
{
    @PrimaryKey(autoGenerate = true)
    private int record_id;
    private String number;
    private String type;
    private String date;
    private String time;
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public SMSRecordEntity(String timeStamp, String date, String time,String number, String type)
    {
        this.number=number;
        this.type=type;
        this.date=date;
        this.time=time;
        this.timeStamp=timeStamp;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
