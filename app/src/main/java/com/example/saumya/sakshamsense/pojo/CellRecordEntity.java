package com.example.saumya.sakshamsense.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cell_Records")
public class CellRecordEntity
{
    @PrimaryKey(autoGenerate = true)
    private int record_id;

    private String date;
    private String time;
    private String netOpName;
    private String netOpID;
    private String cellID;
    private String tac;
    private String dbm;
    private String type;

    public CellRecordEntity(String date, String time, String netOpName, String netOpID, String cellID, String tac,String dbm,String type)
    {
        this.date=date;
        this.time=time;
        this.netOpName=netOpName;
        this.netOpID=netOpID;
        this.cellID=cellID;
        this.tac=tac;
        this.dbm=dbm;
        this.type=type;
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

    public String getNetOpName() {
        return netOpName;
    }

    public void setNetOpName(String netOpName) {
        this.netOpName = netOpName;
    }

    public String getNetOpID() {
        return netOpID;
    }

    public void setNetOpID(String netOpID) {
        this.netOpID = netOpID;
    }

    public String getCellID() {
        return cellID;
    }

    public void setCellID(String cellID) {
        this.cellID = cellID;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getDbm() {
        return dbm;
    }

    public void setDbm(String dbm) {
        this.dbm = dbm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
