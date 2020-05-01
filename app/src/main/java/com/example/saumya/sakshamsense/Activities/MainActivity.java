package com.example.saumya.sakshamsense.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.saumya.sakshamsense.DataBaseSaksham;
import com.example.saumya.sakshamsense.R;
import com.example.saumya.sakshamsense.Receivers.PhoneLockReceiver;
import com.example.saumya.sakshamsense.Receivers.WifiAPStateReceiver;
import com.example.saumya.sakshamsense.Services.AccelerometerService;
import com.example.saumya.sakshamsense.Services.AppUsageService;
import com.example.saumya.sakshamsense.Services.BarometerService;
import com.example.saumya.sakshamsense.Services.CallService;
import com.example.saumya.sakshamsense.Services.GyroService;
import com.example.saumya.sakshamsense.Services.LocationService;
import com.example.saumya.sakshamsense.Services.PhoneRecordService;
import com.example.saumya.sakshamsense.Services.SMSService;
import com.example.saumya.sakshamsense.Services.WifiService;
import com.example.saumya.sakshamsense.Utilities;
import com.example.saumya.sakshamsense.pojo.AccelerometerRecordEntity;
import com.example.saumya.sakshamsense.pojo.AppRecordEntity;
import com.example.saumya.sakshamsense.pojo.BaroRecordEntity;
import com.example.saumya.sakshamsense.pojo.CallRecordEntity;
import com.example.saumya.sakshamsense.pojo.CellRecordEntity;
import com.example.saumya.sakshamsense.pojo.GyroRecordEntity;
import com.example.saumya.sakshamsense.pojo.LocationRecordEntity;
import com.example.saumya.sakshamsense.pojo.PhoneLockRecordEntity;
import com.example.saumya.sakshamsense.pojo.SMSRecordEntity;
import com.example.saumya.sakshamsense.pojo.WifiAPRecordEntity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

public class MainActivity extends AppCompatActivity
{
    LinearLayout call_records,sms_records,app_records,phone_records,loc_records,action_records,wifi_records,cell_records,gyroscope_records,barometer_records;
    PhoneLockReceiver lockReceiver;
    WifiAPStateReceiver wiFiReceiver;
    Button start_sensing;
    File file=null;
    CSVWriter cw;
    FileWriter fw;
    final Utilities ut=new Utilities();
    final Activity activity=this;
    Calendar calendar= Calendar.getInstance();
    AlarmManager alarmManager;
    DataBaseSaksham database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        call_records= findViewById(R.id.CallRecords);
        sms_records=findViewById(R.id.SMSRecords);
        app_records=findViewById(R.id.AppUsage);
        phone_records=findViewById(R.id.PhoneUsage);
        loc_records=findViewById(R.id.LocationRecord);
        action_records=findViewById(R.id.accelerometer);
        wifi_records=findViewById(R.id.WifiAccessPoint);
        cell_records=findViewById(R.id.CellTower);
        gyroscope_records=findViewById(R.id.Gyroscope);
        barometer_records=findViewById(R.id.Barometer);
        start_sensing=findViewById(R.id.sense_button);

        database=DataBaseSaksham.getInstance(getApplicationContext());

        start_sensing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(!ut.checkAllPerm(getApplicationContext()))
                {
                    getAllPerm();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Already Sensing",Toast.LENGTH_LONG).show();
                }
                if(!ut.checkAppPerm(getApplicationContext()))
                {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                }
                checkLocEnabledPerm();
            }
        });

        call_records.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(),"Call Records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(1);
            }
        });

        sms_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(),"SMS Records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(2);
            }
        });

        app_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(),"APP usage Records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(3);
            }
        });

        phone_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(),"Phone Lock Records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(4);
            }
        });

        loc_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Location Records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(5);
            }
        });

        action_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(),"Action Records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(6);
            }
        });

        wifi_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Toast.makeText(getApplicationContext(),"Wifi Records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(7);
            }
        });

        cell_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(),"Cell Records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(8);
            }
        });

        gyroscope_records.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Gyro records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(9);
            }
        });

        barometer_records.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Barometer records downloaded",Toast.LENGTH_LONG).show();
                getFromRoom(10);
            }
        });

    }

    public void getAllPerm()
    {
        String permissions_all[]={WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE,READ_CALL_LOG,READ_CONTACTS,READ_SMS,RECEIVE_SMS,ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION,INTERNET,READ_PHONE_STATE,ACCESS_WIFI_STATE,ACCESS_NETWORK_STATE} ;
        ActivityCompat.requestPermissions(activity,permissions_all,100);
    }

    public boolean checkLocEnabledPerm()
    {
        LocationManager lm = (LocationManager)MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        boolean flag=false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled)
        {
            new AlertDialog.Builder(MainActivity.this).setMessage("Enable the location Services")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            MainActivity.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel",null).show();
            return false;
        }
        if(gps_enabled||network_enabled)
        {
            flag=true;
        }
        return flag;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch(requestCode)
        {
            case 100:
                if(grantResults.length>0)
                {
                    boolean r1Permission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean r2Permission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean r3Permission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean r4Permission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean r5Permission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean r6Permission = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean r7Permission = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean r8Permission = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    boolean r9Permission = grantResults[8] == PackageManager.PERMISSION_GRANTED;
                    boolean r10Permission = grantResults[9] == PackageManager.PERMISSION_GRANTED;
                    boolean r11Permission = grantResults[10] == PackageManager.PERMISSION_GRANTED;
                    boolean r12Permission = grantResults[11] == PackageManager.PERMISSION_GRANTED;
                    if (r1Permission&& r2Permission&& r3Permission && r4Permission && r5Permission && r6Permission&& r7Permission&&
                            r8Permission&& r9Permission&& r10Permission&& r11Permission&& r12Permission)
                    {
                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        Toast.makeText(getApplicationContext(), "Permission Granteddd", Toast.LENGTH_LONG).show();
                        //calls
                        Intent intent_calls= new Intent(getApplicationContext(), CallService.class);
                        PendingIntent pendingIntent1 = PendingIntent.getService(this, 0,intent_calls, 0);
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY,pendingIntent1);
                        //  getApplicationContext().startService(intent_calls);
                        //sms
                        Intent intent_sms= new Intent(getApplicationContext(), SMSService.class);
                        PendingIntent pendingIntent2 = PendingIntent.getService(this, 0,intent_sms, 0);
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY,pendingIntent2);
                        //  getApplicationContext().startService(intent_sms);
                        //appUsage
                        Intent app_usage= new Intent(getApplicationContext(), AppUsageService.class);
                        PendingIntent pendingIntent3 = PendingIntent.getService(this, 0,app_usage, 0);
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY,pendingIntent3);
                        //  getApplicationContext().startService(app_usage);
                        //screen lock
                        Intent intent_onoff= new Intent(getApplicationContext(), PhoneRecordService.class);
                        getApplicationContext().startService(intent_onoff);
                        //loc
                        Intent loc_intent= new Intent(getApplicationContext(), LocationService.class);
                        getApplicationContext().startService(loc_intent);
                        //acc
                        Intent intent_acRecognition= new Intent(getApplicationContext(), AccelerometerService.class);
                        getApplicationContext().startService(intent_acRecognition);

                        //gyro
                        Intent intent_gyro= new Intent(getApplicationContext(), GyroService.class);
                        getApplicationContext().startService(intent_gyro);

                        //wifi
                        Intent intent_wifi= new Intent(getApplicationContext(), WifiService.class);
                        getApplicationContext().startService(intent_wifi);

                        //cellular
                        getCellRecords();

                        //barometer
                        Intent intent_baro= new Intent(getApplicationContext(), BarometerService.class);
                        getApplicationContext().startService(intent_baro);


                    } else {
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public void getCellRecords()
    {
        Log.d("saumya_sensing"," Cell Service");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DataBaseSaksham database= DataBaseSaksham.getInstance(getApplicationContext());
                TelephonyManager tel = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);
                int phoneTypeInt = tel.getPhoneType();
                String phoneType = null;
                phoneType = phoneTypeInt == TelephonyManager.PHONE_TYPE_GSM ? "gsm" : phoneType;
                phoneType = phoneTypeInt == TelephonyManager.PHONE_TYPE_CDMA ? "cdma" : phoneType;
                try
                {
                    if (phoneType != null)
                    {
                        Log.d("saumya"," Phone type is :"+ phoneType);
                    }
                } catch (Exception e) { }
                String net_op=tel.getNetworkOperatorName();
                Log.d("saumya","Net operator"+net_op+" IMEI : "+tel.getImei()+"net operator: "+tel.getNetworkOperator());
                List<CellInfo> infos = tel.getAllCellInfo();
                if(infos!=null||infos.size()!=0)
                {
                    for (int i = 0; i < infos.size(); ++i)
                    {
                        Log.d("saumya","id no "+i);
                        String arr[] = new String[8];
                        Date date= Calendar.getInstance().getTime();
                        arr[0]= new SimpleDateFormat("dd-MMM-yyyy").format(date);
                        arr[1]=new SimpleDateFormat("hh:mm:ss").format(date);
                        try {
                            CellInfo info = infos.get(i);
                            if (info instanceof CellInfoGsm) {
                                Log.d("saumya", "GSM");
                                CellSignalStrengthGsm gsm = ((CellInfoGsm) info).getCellSignalStrength();
                                CellIdentityGsm identityGsm = ((CellInfoGsm) info).getCellIdentity();
                                Log.d("cellId", Integer.toString(identityGsm.getCid()));
                                Log.d("bsic", String.valueOf(identityGsm.getBsic()));
                                Log.d("lac", Integer.toString(identityGsm.getLac()));
                                Log.d("dbm", Integer.toString(gsm.getDbm()));
                                arr[2] = Integer.toString(identityGsm.getCid());
                                arr[3]=" ";
                                arr[4] = String.valueOf(identityGsm.getBsic());
                                arr[5] = Integer.toString(identityGsm.getLac());
                                arr[6] = Integer.toString(gsm.getDbm());
                                arr[7] = "GSM";
                            }
                            else if (info instanceof CellInfoLte) {
                                Log.d("saumya", "LTE");
                                CellSignalStrengthLte lte = ((CellInfoLte) info).getCellSignalStrength();
                                CellIdentityLte identityLte = ((CellInfoLte) info).getCellIdentity();
                                arr[2]=tel.getNetworkOperatorName();
                                arr[4] = Integer.toString(identityLte.getCi());
                                Log.d("cellId", Integer.toString(identityLte.getCi()));
                                arr[3] = identityLte.getMobileNetworkOperator();
                                Log.d("operator", identityLte.getMobileNetworkOperator());
                                arr[5] = Integer.toString(identityLte.getTac());
                                Log.d("Tac", Integer.toString(identityLte.getTac()));
                                arr[6] = Integer.toString(lte.getDbm());
                                Log.d("dbm", Integer.toString(lte.getDbm()));
                                arr[7] = String.valueOf(tel.getNetworkType());
                                CellRecordEntity ce= new CellRecordEntity(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7]);
                                database.cellRecord().addCellRecord(ce);
                            }
                        } catch (Exception e) { }
                    }
                }
            }
        });

    }

    public void onDestroy()
    {
//        if(lockReceiver!=null)
//     //   unregisterReceiver(lockReceiver);
//        if(wiFiReceiver!=null)
//        unregisterReceiver(wiFiReceiver);
        super.onDestroy();
    }

    public void getFromRoom(final int code)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run()
            {
                int res_size;
                switch(code)
                {
                    case 1:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"call_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            boolean b=file.createNewFile();
                            Log.d("saumya",b+"");
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"TimeStamp", "Date", "Time","Number", "Duration","Type"});
                            List<CallRecordEntity> call_list= database.callRecord().getCallRecords();
                            res_size=call_list.size();
                            Log.d("saumya",res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                Log.d("saumya","Getting entry number: "+i);
                                CallRecordEntity temp=call_list.get(i);
                                String arr[]=new String[6];
                                arr[0]= temp.getTimestamp();
                                arr[1]= temp.getDate();
                                arr[2]= temp.getTime();
                                arr[3]= temp.getNumber();
                                arr[4]= temp.getDuration();
                                arr[5]=temp.getType();
                                cw.writeNext(arr);
                            }
                            Log.d("saumya","DONE");
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 2:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"sms_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"TimeStamp", "Date", "Time","Number","Type"});
                            List<SMSRecordEntity> sms_list= database.smsRecord().getSMSRecords();
                            res_size=sms_list.size();
                            Log.d("saumya",res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                SMSRecordEntity temp=sms_list.get(i);
                                String arr[]=new String[5];
                                arr[0]= temp.getTimeStamp();
                                arr[1]= temp.getDate();
                                arr[2]= temp.getTime();
                                arr[3]= temp.getNumber();
                                arr[4]=temp.getType();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 3:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"AppUsage_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"Date","Time", "AppName","Time Spent"});
                            List<AppRecordEntity> app_list= database.appRecord().getAppRecords();
                            res_size=app_list.size();
                            Log.d("saumya","App usage"+res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                AppRecordEntity temp=app_list.get(i);
                                String arr[]=new String[4];
                                arr[0]= temp.getDate();
                                arr[1]=temp.getCurr_time();
                                arr[2]=temp.getAppName();
                                arr[3]= temp.getTime();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 4:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"phoneUsage_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"Date","time","state"});
                            List<PhoneLockRecordEntity> phone_list= database.phoneRecord().getPhoneLockRecords();
                            res_size=phone_list.size();
                            Log.d("saumya","phone usage:"+res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                PhoneLockRecordEntity temp=phone_list.get(i);
                                String arr[]=new String[3];
                                arr[0]= temp.getDate();
                                arr[1]= temp.getTime();
                                arr[2]= temp.getState();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 5:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"location_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"Date", "Time","lat","long","provider"});
                            List<LocationRecordEntity> loc_list= database.locationRecord().getLocationRecords();
                            res_size=loc_list.size();
                            Log.d("saumya","loc: "+res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                LocationRecordEntity temp=loc_list.get(i);
                                String arr[]=new String[5];
                                arr[0]= temp.getDate();
                                arr[1]= temp.getTime();
                                arr[2]= temp.getLat();
                                arr[3]=temp.getLongg();
                                arr[4]=temp.getProvider();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 6:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"acc_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"Date", "Time","x","y","z"});
                            List<AccelerometerRecordEntity> acc_list= database.accRecord().getAccRecords();
                            res_size=acc_list.size();
                            Log.d("saumya",res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                AccelerometerRecordEntity temp=acc_list.get(i);
                                String arr[]=new String[5];
                                arr[0]= temp.getDate();
                                arr[1]= temp.getTime();
                                arr[2]= temp.getX();
                                arr[3]=temp.getY();
                                arr[4]=temp.getZ();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 7:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"wifi_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"Date", "Time","ssid","bssid","state","rssi"});
                            List<WifiAPRecordEntity> wifi_list= database.wifiRecord().getWifiRecords();
                            res_size=wifi_list.size();
                            Log.d("saumya",res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                WifiAPRecordEntity temp=wifi_list.get(i);
                                String arr[]=new String[6];
                                arr[0]= temp.getDate();
                                arr[1]= temp.getTime();
                                arr[2]= temp.getSsid();
                                arr[3]=temp.getBssid();
                                arr[4]=temp.getState();
                                arr[5]=temp.getRssi();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 8:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"cell_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"Date","Time","NetOperator Name","Operator ID","cellID","Tracking area code","dbm","type"});
                            List<CellRecordEntity> cell_list=database.cellRecord().getCellRecords();
                            res_size=cell_list.size();
                            Log.d("saumya","cell "+res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                CellRecordEntity temp=cell_list.get(i);
                                String arr[]=new String[8];
                                arr[0]= temp.getDate();
                                arr[1]= temp.getTime();
                                arr[2]= temp.getNetOpName();
                                arr[3]=temp.getNetOpID();
                                arr[4]=temp.getCellID();
                                arr[5]=temp.getTac();
                                arr[6]=temp.getDbm();
                                arr[7]=temp.getType();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 9:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"gyro_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"Date", "Time","x","y","z"});
                            List<GyroRecordEntity> gyro_list= database.gyroRecord().getGyroRecords();
                            res_size=gyro_list.size();
                            Log.d("saumya","gyro: "+res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                GyroRecordEntity temp=gyro_list.get(i);
                                String arr[]=new String[5];
                                arr[0]= temp.getDate();
                                arr[1]= temp.getTime();
                                arr[2]= temp.getX();
                                arr[3]=temp.getY();
                                arr[4]=temp.getZ();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case 10:
                        try {
                            file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"baro_records.csv");
                            if(file.exists()){
                                file.delete();
                            }
                            file.createNewFile();
                            fw = new FileWriter(file.getAbsolutePath(), true);
                            cw = new CSVWriter(fw);
                            cw.writeNext(new String[]{"Date", "Time","Pressure"});
                            List<BaroRecordEntity> baro_list= database.baroRecord().getBaroRecords();
                            res_size=baro_list.size();
                            Log.d("saumya",res_size+"");
                            for(int i=0;i<res_size;i++)
                            {
                                BaroRecordEntity temp=baro_list.get(i);
                                String arr[]=new String[3];
                                arr[0]= temp.getDate();
                                arr[1]= temp.getTime();
                                arr[2]= temp.getPressure();
                                cw.writeNext(arr);
                            }
                            cw.flush();
                            cw.close();
                        } catch (IOException e) { e.printStackTrace(); }
                        break;
                }
            }
        });

    }

}
