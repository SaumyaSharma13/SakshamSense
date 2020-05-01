package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.PhoneLockRecordEntity;

import java.util.List;

@Dao
public interface PhoneLockRecordDAO
{
    @Query("Select * from  Phone_Records")
    List<PhoneLockRecordEntity> getPhoneLockRecords();

    @Insert
    void addPhoneLockRecord(PhoneLockRecordEntity phone);

    @Query("DELETE FROM Phone_Records")
    void deleteAll();
}
