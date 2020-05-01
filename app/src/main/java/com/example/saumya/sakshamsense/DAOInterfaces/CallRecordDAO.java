package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.CallRecordEntity;

import java.util.List;

@Dao
public interface CallRecordDAO
{
    @Query("Select * from  Call_Records")
    List<CallRecordEntity> getCallRecords();

    @Insert
    void addCallRecord(CallRecordEntity call);

    @Query("DELETE FROM Call_Records")
    void deleteAll();
}
