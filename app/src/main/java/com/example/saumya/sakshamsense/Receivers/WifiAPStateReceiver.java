package com.example.saumya.sakshamsense.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.saumya.sakshamsense.DataBaseSaksham;
import com.example.saumya.sakshamsense.pojo.WifiAPRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.WIFI_SERVICE;

public class WifiAPStateReceiver extends BroadcastReceiver
{
    File file;
    FileWriter fw;
    CSVWriter cw;
    @Override
    public void onReceive(final Context context, Intent intent)
    {
        Log.d("saumya"," Wifi Service on Receive");
//        if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
//        {
//            Log.i("saumyaaa", "wifi state changed");
//            WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
//            WifiInfo info = wifiManager.getConnectionInfo();
//            String ssid = info.getSSID();
//            String bssid = info.getBSSID();
//            if(wifiManager.isWifiEnabled()) {
//
//                Log.i("saumyaaa", "wifi is ON");
//                Log.d("saumyaaa", "SSID IS " + ssid + " BSSID : " + bssid + " " + info.getIpAddress()+info.getNetworkId());
//                arr[2] = info.getSSID();
//                arr[3] = info.getBSSID();
//                writeToCSV(arr);
//            }
//
//        }
//        if(intent.getAction().equals(WifiManager.NETWORK_IDS_CHANGED_ACTION))
//        {
//            Log.i("saumyaaa", "Net Id changed ");
//            WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
//            WifiInfo info = wifiManager.getConnectionInfo();
//            String ssid = info.getSSID();
//            String bssid = info.getBSSID();
//                Log.i("saumyaaa", "wifi state changed On");
//                Log.d("saumyaaa", "SSID IS " + ssid + " BSSID : " + bssid + " " + info.getIpAddress()+info.getNetworkId());
//                arr[2] = info.getSSID();
//                arr[3] = info.getBSSID();
//                writeToCSV(arr);
//
//        }
        if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION))
        {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run()
                {
                    DataBaseSaksham dataBase= DataBaseSaksham.getInstance(context);
                    Calendar cal= Calendar.getInstance();
                    SimpleDateFormat sd= new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-YYYY");
                    String time=sd.format(cal.getTime());
                    String date=sdf.format(cal.getTime());
                    String arr[]=new String[6];
                    arr[0]=date;
                    arr[1]=time;
                    Log.i("saumya", "Network state changed");
                    WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
                    WifiInfo info = wifiManager.getConnectionInfo();
                    String ssid = info.getSSID();
                    String bssid = info.getBSSID();
                    if(wifiManager.isWifiEnabled()) {
                        Log.d("saumya", "SSID IS " + ssid + " BSSID : " + bssid + " " + info.getIpAddress());
                        arr[2] = info.getSSID();
                        arr[3] = info.getBSSID();
                        arr[4]="Enabled";
                        arr[5]=Integer.toString(info.getRssi());

                    }
                    if(!wifiManager.isWifiEnabled())
                    {
                        Log.i("saumya", "wifi is OFF");
                        arr[2] = info.getSSID();
                        arr[3] = info.getBSSID();
                        arr[4]="Disabled";
                        arr[5]=Integer.toString(info.getRssi());
                    }
                  //  writeToCSV(arr);
                    WifiAPRecordEntity ce= new WifiAPRecordEntity(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5]);
                    dataBase.wifiRecord().addWifiRecord(ce);
                }
            });

        }
    }

    public void writeToCSV(String arr[])
    {
        try
        {
            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"WiFi_AP.csv");
            if(!file.exists())
            {
                file.createNewFile();
                writeToCSV(new String[]{"Date","Time","SSID","BSSID","State","RSSI"});
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
