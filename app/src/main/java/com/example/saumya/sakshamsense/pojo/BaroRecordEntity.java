package com.example.saumya.sakshamsense.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Baro_Records")
public class BaroRecordEntity
{

    @PrimaryKey(autoGenerate = true)
    private int record_id;

    private String date;
    private String time;
    private String pressure;


    public BaroRecordEntity(String date, String time,String pressure)
    {
        this.date=date;
        this.time=time;
        this.pressure=pressure;
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

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }
}
