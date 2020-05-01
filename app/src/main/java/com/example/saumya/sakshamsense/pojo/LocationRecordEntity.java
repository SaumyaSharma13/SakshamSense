package com.example.saumya.sakshamsense.pojo;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Location_Records")
public class LocationRecordEntity
{
    @PrimaryKey(autoGenerate = true)
    private int record_id;

    private String date;
    private String time;
    private String lat;
    private String longg;
    private String provider;

    public LocationRecordEntity(String date, String time, String lat, String longg, String provider)
    {
        this.date=date;
        this.time=time;
        this.lat=lat;
        this.longg=longg;
        this.provider=provider;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongg() {
        return longg;
    }

    public void setLongg(String longg) {
        this.longg = longg;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
