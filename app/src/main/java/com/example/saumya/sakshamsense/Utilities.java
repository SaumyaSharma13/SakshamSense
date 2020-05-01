package com.example.saumya.sakshamsense;

import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

public class Utilities
{

    public boolean checkAllPerm(Context context)
    {
        int r1 = ActivityCompat.checkSelfPermission(context, READ_CONTACTS);
        int r2= ActivityCompat.checkSelfPermission(context, READ_CALL_LOG);
        int r3= ActivityCompat.checkSelfPermission(context, READ_SMS);
        int r4= ActivityCompat.checkSelfPermission(context, RECEIVE_SMS);
        int r5= ActivityCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int r6= ActivityCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        int r7= ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION);
        int r8= ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION);
        int r9= ActivityCompat.checkSelfPermission(context, INTERNET);
        int r10= ActivityCompat.checkSelfPermission(context, ACCESS_NETWORK_STATE);
        int r11= ActivityCompat.checkSelfPermission(context, ACCESS_WIFI_STATE);
        int r12= ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE);
        return (r1== PackageManager.PERMISSION_GRANTED && r2 == PackageManager.PERMISSION_GRANTED && r3 == PackageManager.PERMISSION_GRANTED
                && r4 == PackageManager.PERMISSION_GRANTED&& r5 == PackageManager.PERMISSION_GRANTED&& r6 == PackageManager.PERMISSION_GRANTED
                && r7 == PackageManager.PERMISSION_GRANTED&& r8 == PackageManager.PERMISSION_GRANTED&& r9 == PackageManager.PERMISSION_GRANTED
                && r10 == PackageManager.PERMISSION_GRANTED && r11 == PackageManager.PERMISSION_GRANTED && r12 == PackageManager.PERMISSION_GRANTED);
    }
    public boolean checkCallPerm(Context context)
    {
        int r1 = ActivityCompat.checkSelfPermission(context, READ_CONTACTS);
        int r2= ActivityCompat.checkSelfPermission(context, READ_CALL_LOG);
        return (r1== PackageManager.PERMISSION_GRANTED && r2 == PackageManager.PERMISSION_GRANTED );
    }

    public boolean checkSMSPerm(Context context)
    {
        int r1 = ActivityCompat.checkSelfPermission(context, READ_SMS);
        int r2= ActivityCompat.checkSelfPermission(context, RECEIVE_SMS);
        return (r1== PackageManager.PERMISSION_GRANTED && r2 == PackageManager.PERMISSION_GRANTED );
    }

    public boolean checkStoragePerm(Context context)
    {
        int r1 = ActivityCompat.checkSelfPermission(context,WRITE_EXTERNAL_STORAGE);
        int r2= ActivityCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        return (r1== PackageManager.PERMISSION_GRANTED && r2 == PackageManager.PERMISSION_GRANTED );
    }

    public boolean checkAppPerm(Context context)
    {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            int uid = info.uid;
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, uid, context.getPackageName());
            return mode == MODE_ALLOWED;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkLocPerm(Context context)
    {
        int r1 = ActivityCompat.checkSelfPermission(context,ACCESS_FINE_LOCATION);
        int r2 = ActivityCompat.checkSelfPermission(context,ACCESS_COARSE_LOCATION);
        int r3 = ActivityCompat.checkSelfPermission(context,INTERNET);
        return (r1== PackageManager.PERMISSION_GRANTED && r2==PackageManager.PERMISSION_GRANTED && r3== PackageManager.PERMISSION_GRANTED);
    }


    public boolean checkWifiPerm(Context context)
    {
        int r1 = ActivityCompat.checkSelfPermission(context, ACCESS_WIFI_STATE);
        int r2= ActivityCompat.checkSelfPermission(context, ACCESS_NETWORK_STATE);
        return (r1== PackageManager.PERMISSION_GRANTED && r2 == PackageManager.PERMISSION_GRANTED );
    }
    public boolean checkCellPerm(Context context)
    {
        int r1 = ActivityCompat.checkSelfPermission(context,ACCESS_FINE_LOCATION);
        int r2 = ActivityCompat.checkSelfPermission(context,ACCESS_COARSE_LOCATION);
        int r3 = ActivityCompat.checkSelfPermission(context,INTERNET);
        int r4=ActivityCompat.checkSelfPermission(context,READ_PHONE_STATE);
        return (r1== PackageManager.PERMISSION_GRANTED && r2==PackageManager.PERMISSION_GRANTED && r3== PackageManager.PERMISSION_GRANTED&& r4==PackageManager.PERMISSION_GRANTED);
    }
}

