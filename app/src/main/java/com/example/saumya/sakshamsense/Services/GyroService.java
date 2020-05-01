package com.example.saumya.sakshamsense.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.example.saumya.sakshamsense.DataBaseSaksham;
import com.example.saumya.sakshamsense.pojo.GyroRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GyroService extends Service implements SensorEventListener
{
    File file;
    FileWriter fw;
    CSVWriter cw;
    private SensorManager mSensorManager;
    private Sensor mGyroscope;
    DataBaseSaksham database;
    float x;
    float y;
    float z;
    public GyroService() {
    }

    public void onCreate()
    {
        Log.d("saumya_sensing"," Gyro Service");
        super.onCreate();
//        try
//        {
//            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"gyroRecords.csv");
//            if(!file.exists())
//            {
//                file.createNewFile();
//                writeToCSV(new String[]{"Date","Time","x-rotation","y-rotation","z-rotation"});
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGyroscope= mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this,mGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;

    }

    @Override
    public void onSensorChanged(final SensorEvent event)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                database= DataBaseSaksham.getInstance(getApplicationContext());
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                SimpleDateFormat sdf1= new SimpleDateFormat("dd-MMM-yyyy");
                String date=sdf1.format(Calendar.getInstance().getTime());
                SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
                String time_curr=sdf.format(Calendar.getInstance().getTime());
                String arr[]=new String[5];
                arr[0]=date;
                arr[1]=time_curr;
                arr[2]= Float.toString(x);
                arr[3]=Float.toString(y);
                arr[4]=Float.toString(z);
                GyroRecordEntity ce= new GyroRecordEntity(arr[0],arr[1],arr[2],arr[3],arr[4]);
                database.gyroRecord().addGyroRecord(ce);
               // writeToCSV(arr);
            }
        });

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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
