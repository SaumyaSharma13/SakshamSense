package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.GyroRecordEntity;

import java.util.List;

@Dao
public interface GyroRecordDAO
{
    @Query("Select * from  Gyro_Records")
    List<GyroRecordEntity> getGyroRecords();

    @Insert
    void addGyroRecord(GyroRecordEntity gyro);

    @Query("DELETE FROM Gyro_Records")
    void deleteAll();
}
