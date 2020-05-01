package com.example.saumya.sakshamsense.Services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import com.example.saumya.sakshamsense.DataBaseSaksham;
import com.example.saumya.sakshamsense.pojo.SMSRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SMSService extends Service {
    File file;
    FileWriter fw;
    CSVWriter cw;
    DataBaseSaksham database;
    public SMSService() { }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        Log.d("saumya_sensing","SMS Service");
//        try
//        {
//            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"sms_records.csv");
//            if(!file.exists()) {
//                file.createNewFile();
//                writeToCSV(new String[]{"Date", "Time", "Type", "Number", "Body","Timestamp"});
//            }
//        }
//        catch(Exception e)
//        { e.printStackTrace(); }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
     //   Log.d("saumya","SMS Service onStartCommand");
        try {
            getSMSDetails();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    public void onDestroy()
    {
     //   Log.d("saumya"," SMS Service onDestroy");
        super.onDestroy();
    }

    public void getSMSDetails() throws ParseException
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                database=DataBaseSaksham.getInstance(getApplicationContext());
                database.smsRecord().deleteAll();
                StringBuffer sb= new StringBuffer();
                Cursor cursor2 = getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null, null);
                Log.d("saumya","size of cursor: "+cursor2.getColumnCount()+" "+cursor2.getCount());
                Date da= Calendar.getInstance().getTime();
                SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
                String current=sdf.format(da);
                sdf= new SimpleDateFormat("HH:mm:ss");
                String time=sdf.format(Calendar.getInstance().getTime());
                String timestamp=current+"--"+time;
                int number = cursor2.getColumnIndex(Telephony.Sms.ADDRESS);
                int type = cursor2.getColumnIndex(Telephony.Sms.TYPE);
                int date = cursor2.getColumnIndex(Telephony.Sms.DATE);
                int body = cursor2.getColumnIndex(Telephony.Sms.BODY);
                cursor2.moveToFirst();
                while (cursor2.moveToNext())
                {
                    String arr[]=new String[6];
                    String d=cursor2.getString(date);
                    Date callDayTime = new Date(Long.valueOf(d));
                    arr[0]=timestamp;
                    arr[1] = new SimpleDateFormat("MM-dd-yyyy").format(callDayTime);
                    arr[2]= new SimpleDateFormat("hh:mm:ss").format(callDayTime);
                    String type_sms=cursor2.getString(type);
                    Log.d("saumyaa","TYPE IS :"+type_sms);
                    if(Integer.parseInt(type_sms)==1)
                    {
                        arr[5] = "Inbox";
                    }
                    else if(Integer.parseInt(type_sms)==2)
                    {
                        arr[5] = "Outbox";
                    }
                    else if(Integer.parseInt(type_sms)==3)
                    {
                        arr[5] = "Draft";
                    }
                    arr[3] = cursor2.getString(number);
                    arr[4]=cursor2.getString(body);
                    SMSRecordEntity ce= new SMSRecordEntity(arr[0],arr[1],arr[2],arr[3],arr[5]);
                    database.smsRecord().addSMSRecord(ce);
                   // writeToCSV(arr);
                    sb.append(arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]+"\n");

                    Log.d("saumya ",sb.toString());
                }
                List<SMSRecordEntity> list=database.smsRecord().getSMSRecords();
                Log.d("SMS Record count",": "+list.size());
            }
        });
        stopSelf();
    }

    public void writeToCSV(String arr[])
    {
        try {
            fw = new FileWriter(file.getAbsolutePath(), true);
            cw = new CSVWriter(fw);
          //  Log.d("saumya", ": " + arr.length);
            cw.writeNext(arr);
            cw.flush();
            cw.close();
        }catch(Exception r)
        {
            r.printStackTrace();
        }

    }
}
