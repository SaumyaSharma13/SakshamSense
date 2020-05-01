package com.example.saumya.sakshamsense;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.saumya.sakshamsense.DAOInterfaces.AccelerometerRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.AppRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.BaroRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.CallRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.CellRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.GyroRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.LocationRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.PhoneLockRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.SMSRecordDAO;
import com.example.saumya.sakshamsense.DAOInterfaces.WifiAPRecordDAO;
import com.example.saumya.sakshamsense.pojo.AccelerometerRecordEntity;
import com.example.saumya.sakshamsense.pojo.AppRecordEntity;
import com.example.saumya.sakshamsense.pojo.BaroRecordEntity;
import com.example.saumya.sakshamsense.pojo.CallRecordEntity;
import com.example.saumya.sakshamsense.pojo.CellRecordEntity;
import com.example.saumya.sakshamsense.pojo.GyroRecordEntity;
import com.example.saumya.sakshamsense.pojo.LocationRecordEntity;
import com.example.saumya.sakshamsense.pojo.PhoneLockRecordEntity;
import com.example.saumya.sakshamsense.pojo.SMSRecordEntity;
import com.example.saumya.sakshamsense.pojo.WifiAPRecordEntity;

@Database(entities = {CallRecordEntity.class, SMSRecordEntity.class, AppRecordEntity.class, LocationRecordEntity.class, PhoneLockRecordEntity.class, WifiAPRecordEntity.class, CellRecordEntity.class, AccelerometerRecordEntity.class, GyroRecordEntity.class, BaroRecordEntity.class},exportSchema = false,version = 4)
public abstract class DataBaseSaksham extends RoomDatabase
{
    private static final String databaseName="SakshamDB";
    private static DataBaseSaksham instance;

    public static synchronized DataBaseSaksham getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),DataBaseSaksham.class,databaseName).fallbackToDestructiveMigration().build();
        }
       return instance;
    }
    public abstract CallRecordDAO callRecord();
    public abstract SMSRecordDAO smsRecord();
    public abstract AppRecordDAO appRecord();
    public  abstract LocationRecordDAO locationRecord();
    public abstract PhoneLockRecordDAO phoneRecord();
    public abstract WifiAPRecordDAO wifiRecord();
    public abstract CellRecordDAO cellRecord();
    public abstract AccelerometerRecordDAO accRecord();
    public abstract BaroRecordDAO baroRecord();
    public abstract GyroRecordDAO gyroRecord();

}
