package com.example.saumya.sakshamsense.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Gyro_Records")
public class GyroRecordEntity
{

    @PrimaryKey(autoGenerate = true)
    private int record_id;

    private String date;
    private String time;
    private String x;
    private String y;
    private String z;

    public GyroRecordEntity(String date, String time,String x, String y, String z)
    {
        this.date=date;
        this.time=time;
        this.x=x;
        this.y=y;
        this.z=z;
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

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

}
