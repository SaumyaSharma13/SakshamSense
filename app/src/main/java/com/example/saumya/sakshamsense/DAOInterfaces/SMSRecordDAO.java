package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.saumya.sakshamsense.pojo.SMSRecordEntity;
import java.util.List;

@Dao
public interface SMSRecordDAO
{
    @Query("Select * from  SMS_Records")
    List<SMSRecordEntity> getSMSRecords();

    @Insert
    void addSMSRecord(SMSRecordEntity sms);

    @Query("DELETE FROM SMS_Records")
    void deleteAll();
}
