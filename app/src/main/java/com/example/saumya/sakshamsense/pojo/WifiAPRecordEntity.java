package com.example.saumya.sakshamsense.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "WifiAP_Records")
public class WifiAPRecordEntity
{
    @PrimaryKey(autoGenerate = true)
    private int record_id;

    private String date;
    private String time;
    private String ssid;
    private String bssid;
    private String state;
    private String rssi;

    public WifiAPRecordEntity(String date, String time, String ssid, String bssid, String state, String rssi)
    {
        this.date=date;
        this.time=time;
        this.ssid=ssid;
        this.bssid=bssid;
        this.state=state;
        this.rssi=rssi;
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

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }
}
