package com.example.saumya.sakshamsense.Services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.example.saumya.sakshamsense.DataBaseSaksham;
import com.example.saumya.sakshamsense.pojo.LocationRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocationService extends Service implements LocationListener {
    File file;
    FileWriter fw;
    CSVWriter cw;
    int option=0;

    public LocationService() {
    }

    public void onCreate()
    {
        Log.d("saumya_sensing"," Location Service");
        super.onCreate();
//        try
//        {
//            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"location.csv");
//            if(!file.exists())
//            {
//                file.createNewFile();
//                writeToCSV(new String[]{"Date","Time","Lat","Long","Provider"});
//            }
//
//         //   Log.d("saumyaaa",file.getAbsolutePath());
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
     //   Log.d("saumya","Location Service onStartCommand");
        getLoc();
        return START_STICKY;
    }

    @SuppressLint("MissingPermission")
    protected void getLoc()
    {
        Log.d("saumyaaa","Getting loc");
        LocationManager lm= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Boolean gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.d("saumyaaa","GPS :"+gps_enabled+" network : "+network_enabled);
        Date d= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
        String current_date=sdf.format(d);
        if(gps_enabled) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 900000,0,this);
            option=1;
            @SuppressLint("MissingPermission") Location loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc!=null) {
                Log.d("saumyaaa"," got last loc from gps "+loc.getLatitude()+" "+loc.getLongitude());
                String arr[] = new String[5];
                arr[0]=current_date;
                Date date=new Date(loc.getTime());
                SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
                arr[1] =  sdf1.format(date);;
                arr[2] = Double.toString(loc.getLatitude());
                arr[3] = Double.toString(loc.getLongitude());
                arr[4] = "GPS";
           //     writeToCSV(arr);
            }
            else
            {
                Location loc1=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 900000, 0, this);
                option=2;
                Log.d("saumyaaa","loc : "+loc1.getLatitude()+loc1.getLongitude());
            }
        }
        else
        {
            Location loc1=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 900000, this);
            option=2;
            Log.d("saumyaaa","loc : "+loc1.getLatitude()+loc1.getLongitude());
        }


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

    @Override
    public void onLocationChanged(final Location location)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DataBaseSaksham database= DataBaseSaksham.getInstance(getApplicationContext());
                Date d= Calendar.getInstance().getTime();
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
                String current_date=sdf.format(d);
                Log.d("saumyaaa","location changed..");
                String arr[]=new String[5];
                arr[0]=current_date;
                Date date=new Date(location.getTime());
                SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
                arr[1] =  sdf1.format(date);;
                arr[2]=Double.toString(location.getLatitude());
                arr[3]=Double.toString(location.getLongitude());
                if(option==1)
                {
                    arr[4]="GPS";
                }
                else
                {
                    arr[4]="Net";
                }
                LocationRecordEntity ce= new LocationRecordEntity(arr[0],arr[1],arr[2],arr[3],arr[4]);
                database.locationRecord().addLocationRecord(ce);
             //   writeToCSV(arr);
                List<LocationRecordEntity> list=database.locationRecord().getLocationRecords();
                Log.d("Location Record count",": "+list.size());
            }

        });

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
