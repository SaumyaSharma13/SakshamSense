package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.AppRecordEntity;

import java.util.List;

@Dao
public interface AppRecordDAO
{
    @Query("Select * from  App_Records")
    List<AppRecordEntity> getAppRecords();

    @Insert
    void addAppRecord(AppRecordEntity appRecord);

    @Query("DELETE FROM App_Records")
    void deleteAll();
}
