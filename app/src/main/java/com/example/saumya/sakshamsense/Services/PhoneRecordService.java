package com.example.saumya.sakshamsense.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.example.saumya.sakshamsense.Receivers.PhoneLockReceiver;

public class PhoneRecordService extends Service {
    public PhoneRecordService() {
    }
PhoneLockReceiver lockReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        Log.d("saumya", "PhoneRecordService Service onCreate() Registering ScreenONOFF Receiver");
        super.onCreate();
        lockReceiver = new PhoneLockReceiver();
        IntentFilter lockFilter = new IntentFilter();
        lockFilter.addAction(Intent.ACTION_SCREEN_ON);
        lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(lockReceiver, lockFilter);
    }
}
