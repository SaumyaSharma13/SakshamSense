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
import com.example.saumya.sakshamsense.pojo.BaroRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BarometerService extends Service implements SensorEventListener {

    File file;
    FileWriter fw;
    CSVWriter cw;
    private SensorManager mSensorManager;
    private Sensor mBarometer;
    float pressure;
    DataBaseSaksham database;
    public BarometerService() {
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate()
    {
        Log.d("saumya_sensing"," Barometer Service");
        super.onCreate();
//        try
//        {
//            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"barometerRecords.csv");
//            if(!file.exists())
//            {
//                file.createNewFile();
//                writeToCSV(new String[]{"Date","Time","Pressure"});
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> list= mSensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d("saumya",list.size()+" ");
        if(list.size()!=0)
        {
            for(int i=0;i<list.size();i++)
            {
                Log.d("saumya",list.get(i).getName()+" "+list.get(i).getType()+" "+list.get(i).getStringType());
            }
        }
        mBarometer = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(mBarometer==null)
        {
            Log.d("saumya","null ");
        }
        else
        {
            Log.d("saumya",mBarometer.getName());
        }

        mSensorManager.registerListener(this, mBarometer,SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;

    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                database= DataBaseSaksham.getInstance(getApplicationContext());
                Log.d("saumya","looking out for readings barometer");
                pressure = event.values[0];
                SimpleDateFormat sdf1= new SimpleDateFormat("dd-MMM-yyyy");
                String date=sdf1.format(Calendar.getInstance().getTime());
                SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
                String time_curr=sdf.format(Calendar.getInstance().getTime());
                Log.d("saumya","time is "+time_curr);
                Log.d("saumya",pressure+" ");
                String arr[]=new String[5];
                arr[0]=date;
                arr[1]=time_curr;
                arr[2]= Float.toString(pressure);
                BaroRecordEntity ce= new BaroRecordEntity(arr[0],arr[1],arr[2]);
                database.baroRecord().addBaroRecord(ce);
            }
        });

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void writeToCSV(String arr[])
    {
        Log.d("saumya","writting to csv");
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
