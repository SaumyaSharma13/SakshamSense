package com.example.saumya.sakshamsense.Services;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.provider.CallLog;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.saumya.sakshamsense.DataBaseSaksham;
import com.example.saumya.sakshamsense.pojo.CallRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;

public class CallService extends Service {
    File file;
    FileWriter fw;
    CSVWriter cw;
    DataBaseSaksham database;
    public CallService() { }
    @Override
    public IBinder onBind(Intent intent)
    { throw new UnsupportedOperationException("Not yet implemented"); }

    public void onCreate()
    {
        Log.d("saumya_sensing"," CallService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        getCallDetails();
        return START_STICKY;
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    public void getCallDetails()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                database=DataBaseSaksham.getInstance(getApplicationContext());
                database.callRecord().deleteAll();
                int flag=0;
                Log.d("saumya","getting call details");
                Date d= Calendar.getInstance().getTime();
                SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
                String current=sdf.format(d);
                sdf= new SimpleDateFormat("HH:mm:ss");
                String time=sdf.format(Calendar.getInstance().getTime());
                String timestamp=current+"--"+time;
                Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
                int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
                int date = cursor.getColumnIndex(CallLog.Calls.DATE);
                int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
                while (cursor.moveToNext()) {
                    String phNumber = cursor.getString(number); // mobile number
                    String callType = cursor.getString(type); // call type
                    String callDate = cursor.getString(date); // call date
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    SimpleDateFormat sdf1=new SimpleDateFormat("dd-MMM-yyyy");
                    SimpleDateFormat sdf2=new SimpleDateFormat("HH:MM:SS");
                    String callDuration = cursor.getString(duration);
                    String dir = null;
                    int dircode = Integer.parseInt(callType);
                    switch (dircode) {
                        case CallLog.Calls.OUTGOING_TYPE:
                            dir = "OUTGOING";
                            break;

                        case CallLog.Calls.INCOMING_TYPE:
                            dir = "INCOMING";
                            break;

                        case CallLog.Calls.MISSED_TYPE:
                            dir = "MISSED";
                            break;
                    }
                    String arr[]=new String[6];
                    arr[0]=timestamp;
                    arr[1]=sdf1.format(callDayTime);
                    arr[2]=sdf2.format(callDayTime);
                    arr[3]=phNumber;
                    arr[4]=callDuration;
                    arr[5]=dir;
                    Log.d("saumya:","----------------------------------------");
                    CallRecordEntity ce= new CallRecordEntity(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5]);
                    database.callRecord().addCallRecord(ce);
                 //   writeToCSV(arr);
                }
//               List<CallRecordEntity> list= database.callRecord().getCallRecords();
//                Log.d("ANSWER",list.size()+"");
                cursor.close();
                stopSelf();
            }
        });
    }

    public void writeToCSV(String arr[])
    {
        try {
            fw = new FileWriter(file.getAbsolutePath(), true);
            cw = new CSVWriter(fw);
            Log.d("saumya", ": " + arr.length);
            cw.writeNext(arr);
            cw.flush();
            cw.close();
        }catch(Exception r)
        {r.printStackTrace();}

    }
}
