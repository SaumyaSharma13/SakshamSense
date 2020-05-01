package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.BaroRecordEntity;

import java.util.List;

@Dao
public interface BaroRecordDAO
{
    @Query("Select * from  Baro_Records")
    List<BaroRecordEntity> getBaroRecords();

    @Insert
    void addBaroRecord(BaroRecordEntity baro);

    @Query("DELETE FROM Baro_Records")
    void deleteAll();
}
