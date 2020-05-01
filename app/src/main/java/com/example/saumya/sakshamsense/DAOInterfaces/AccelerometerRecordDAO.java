package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.AccelerometerRecordEntity;
import com.example.saumya.sakshamsense.pojo.AppRecordEntity;

import java.util.List;

@Dao
public interface AccelerometerRecordDAO
{
    @Query("Select * from  Acc_Records")
    List<AccelerometerRecordEntity> getAccRecords();

    @Insert
    void addAccRecord(AccelerometerRecordEntity acc);

    @Query("DELETE FROM Acc_Records")
    void deleteAll();
}
