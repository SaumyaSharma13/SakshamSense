package com.example.saumya.sakshamsense.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Call_Records")
public class CallRecordEntity
{
    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    @PrimaryKey(autoGenerate = true)
    private int record_id;

    private String number;
    private String type;
    private String date;
    private String time;
    private String duration;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public CallRecordEntity(String timestamp, String date, String time, String number, String duration, String type)
    {
        this.timestamp=timestamp;
        this.number=number;
        this.type=type;
        this.date=date;
        this.time=time;
        this.duration=duration;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


}
