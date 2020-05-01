package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.LocationRecordEntity;

import java.util.List;

@Dao
public interface LocationRecordDAO
{
    @Query("Select * from  Location_Records")
    List<LocationRecordEntity> getLocationRecords();

    @Insert
    void addLocationRecord(LocationRecordEntity loc);

    @Query("DELETE FROM Location_Records")
    void deleteAll();
}
