package com.example.saumya.sakshamsense.Services;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.example.saumya.sakshamsense.DataBaseSaksham;
import com.example.saumya.sakshamsense.pojo.AppRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppUsageService extends Service {
    File file;
    FileWriter fw;
    CSVWriter cw;
    public AppUsageService() { }

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void onCreate()
    {
        Log.d("saumya_sensing"," AppUsage Service");
//        try
//        {
//            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"appUsage_records.csv");
//            if(!file.exists())
//            {
//                file.createNewFile();
//                writeToCSV(new String[]{"Date","Time","App Name","Time spent"});
//            }
//        }
//        catch(Exception e)
//        { e.printStackTrace(); }
    }
    public int onStartCommand(Intent intent, int flags, int startId)
    {
       // Log.d("saumya","App Usage Service onStartCommand");
        getAppDetails();
        return START_STICKY;
    }

    public void onDestroy()
    {
      //  Log.d("saumya","App Usage Service onDestroy");
        super.onDestroy();
    }
    @TargetApi(22)
    public void getAppDetails()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DataBaseSaksham database= DataBaseSaksham.getInstance(getApplicationContext());
                UsageStatsManager us= (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE) ;
                StringBuffer sb=new StringBuffer();
                Calendar cal1= Calendar.getInstance();
                cal1.add(Calendar.DATE,-1);
                long start=cal1.getTimeInMillis();
                List<UsageStats> stats= us.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,start,System.currentTimeMillis());
                Date d=Calendar.getInstance().getTime();
                SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
                String current=sdf.format(d);
                sdf= new SimpleDateFormat("HH:mm:ss");
                String time=sdf.format(Calendar.getInstance().getTime());
                for(UsageStats x:stats)
                {
                    //  Log.d("saumya",x.getPackageName()+" "+x.getTotalTimeInForeground());
                    String arr[]=new String[4];
                    arr[0]=current;
                    arr[1]=time;
                    arr[2]=x.getPackageName();
                    arr[3]=Long.toString(x.getTotalTimeInForeground());
                    AppRecordEntity ce= new AppRecordEntity(arr[0],arr[1],arr[2],arr[3]);
                    database.appRecord().addAppRecord(ce);
                //    writeToCSV(arr);
                }
              //  List<AppRecordEntity> list=database.appRecord().getAppRecords();
             //   Log.d("App Record count",": "+list.size());
            }
        });

      //  Log.d("saumya","stopping app rec service");
        stopSelf();
    }

    public void writeToCSV(String arr[])
    {
        try {
            fw = new FileWriter(file.getAbsolutePath(), true);
            cw = new CSVWriter(fw);
            cw.writeNext(arr);
            cw.flush();
            cw.close();
        }catch(Exception r)
        {
            r.printStackTrace();
        }

    }
}
