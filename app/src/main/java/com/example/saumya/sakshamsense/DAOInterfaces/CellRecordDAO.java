package com.example.saumya.sakshamsense.DAOInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.saumya.sakshamsense.pojo.CellRecordEntity;
import com.example.saumya.sakshamsense.pojo.LocationRecordEntity;

import java.util.List;

@Dao
public interface CellRecordDAO
{
    @Query("Select * from  Cell_Records")
    List<CellRecordEntity> getCellRecords();

    @Insert
    void addCellRecord(CellRecordEntity cell);

    @Query("DELETE FROM Cell_Records")
    void deleteAll();
}
