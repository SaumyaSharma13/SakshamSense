package com.example.saumya.sakshamsense.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import com.example.saumya.sakshamsense.Receivers.WifiAPStateReceiver;

public class WifiService extends Service {
    WifiAPStateReceiver wiFiReceiver;
    public WifiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        Log.d("saumya", "Wifi Service onCreate() Registering wifi Receiver");
        super.onCreate();
        wiFiReceiver = new WifiAPStateReceiver();
        IntentFilter wifiFilter = new IntentFilter();
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wiFiReceiver, wifiFilter);
    }
}
