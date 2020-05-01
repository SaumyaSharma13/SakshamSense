package com.example.saumya.sakshamsense.Services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyActivityRecognition extends IntentService implements SensorEventListener {
    File file;
    FileWriter fw;
    CSVWriter cw;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    float x;
    float y;
    float z;
    public MyActivityRecognition() {
        super("MyActivityRecognition");
    }
    @Override
    public void onCreate()
    {

        super.onCreate();
//        try
//        {
//            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"activityRecognition.csv");
//            if(!file.exists())
//            {
//                file.createNewFile();
//                writeToCSV(new String[]{"Date","Time","x","y","z","Activity Recognised","Confidence"});
//            }
//
//            Log.d("saumya",file.getAbsolutePath());
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d("saumya","Call to onHandle Intent using AlARM");
        PendingIntent p3= PendingIntent.getService(this,0,intent,0);
        ActivityRecognitionClient mActivityRecognitionClient= new ActivityRecognitionClient(this);
        mActivityRecognitionClient.requestActivityUpdates(50000,p3);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, 40000,40000);
        mSensorManager.unregisterListener(this);
        if(ActivityRecognitionResult.hasResult(intent))
        {
            ActivityRecognitionResult res= ActivityRecognitionResult.extractResult(intent);
            ArrayList<DetectedActivity> activities= (ArrayList)res.getProbableActivities();
            Log.d("saumya","found activities "+activities.size());
            SimpleDateFormat sdf1= new SimpleDateFormat("dd-MMM-yyyy");
            String date=sdf1.format(Calendar.getInstance().getTime());
            for(DetectedActivity ac:activities)
            {
                long time=System.currentTimeMillis();
                Date d=new Date(time);
                SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
                String time_curr=sdf.format(d);
                int type=ac.getType();
                String aName=getName(type);
                int confidence=ac.getConfidence();
                Log.d("saumya","Activity : "+aName+" with "+confidence);
                String arr[]=new String[7];
                arr[0]=date;
                arr[1]=time_curr;
                arr[2]= Float.toString(x);
                arr[3]=Float.toString(y);
                arr[4]=Float.toString(z);
                arr[5]=aName;
                arr[6]=Integer.toString(confidence);
                writeToCSV(arr);
            }
        }
        Log.d("saumya","stopinnggg");
        stopSelf();
    }
    public String getName(int type)
    {
        switch (type)
        {
            case(0):
                return "IN_VEHICLE";
            case(1):
                return "ON_BICYCLE";
            case(2):
                return "ON_FOOT";
            case(3):
                return "STILL";
            case(5):
                return "TILTING";
            case(7):
                return "WALKING";
            case(8):
                return "RUNNING";
            default:
                return "UNKNOWN";

        }
    }

    public void writeToCSV(String arr[])
    {
      //  Log.d("saumya","writting to csv");
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

    @Override
    public void onSensorChanged(SensorEvent event) {
     //   Log.d("saumya","looking out for readings acc");
         x = event.values[0];
         y = event.values[1];
         z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
