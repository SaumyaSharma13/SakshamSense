package com.example.saumya.sakshamsense.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.saumya.sakshamsense.DataBaseSaksham;
import com.example.saumya.sakshamsense.pojo.PhoneLockRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PhoneLockReceiver extends BroadcastReceiver {
    File file;
    FileWriter fw;
    CSVWriter cw;
    @Override
    public void onReceive(final Context context, final Intent intent)
    {
        Log.d("saumya"," Phone lock Service on Receive");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DataBaseSaksham database= DataBaseSaksham.getInstance(context);
                Calendar cal= Calendar.getInstance();
                SimpleDateFormat sd= new SimpleDateFormat("HH:mm:ss");
                String time=sd.format(cal.getTime());
                SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
                String date=sdf.format(cal.getTime());
                String arr[]=new String[3];
                arr[0]=date;
                arr[1]=time;
                if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
                {
                    Log.i("saumya", "Screen On"+ " Time is : "+arr[1]);
                    arr[2]="ON";

                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
                {
                    Log.i("saumya", "Screen Off"+ " Time is : "+arr[1]);
                    arr[2]="OFF";

                }
                PhoneLockRecordEntity ce= new PhoneLockRecordEntity(arr[0],arr[1],arr[2]);
                database.phoneRecord().addPhoneLockRecord(ce);
                List<PhoneLockRecordEntity> list=database.phoneRecord().getPhoneLockRecords();
                Log.d("Phone Record count",": "+list.size());
            }
        });

    }
    public void writeToCSV(String arr[])
    {
        try
        {
            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"PhoneLock.csv");
            if(!file.exists())
            {
                file.createNewFile();
                writeToCSV(new String[]{"Date","Time","Lock-State"});
            }
        }
        catch(Exception e)
        { e.printStackTrace();
        }

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
