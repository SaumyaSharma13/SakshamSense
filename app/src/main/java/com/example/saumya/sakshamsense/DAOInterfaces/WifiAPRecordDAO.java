package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.WifiAPRecordEntity;

import java.util.List;

@Dao
public interface WifiAPRecordDAO
{
    @Query("Select * from  WifiAP_Records")
    List<WifiAPRecordEntity> getWifiRecords();

    @Insert
    void addWifiRecord(WifiAPRecordEntity wifi);

    @Query("DELETE FROM WifiAP_Records")
    void deleteAll();
}
