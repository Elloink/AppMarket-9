package com.mapleaf.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.mapleaf.engine.AddressDao;

public class AddressService extends Service {
    public AddressService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private MyPhoneStateListener myPhoneStateListener;
    private TelephonyManager telephonyManager;
    @Override
    public void onCreate() {
        super.onCreate();
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        myPhoneStateListener = new MyPhoneStateListener();
        telephonyManager.listen(myPhoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
    }
    public class MyPhoneStateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    String s = AddressDao.AddressParse(getApplicationContext(), incomingNumber);
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        telephonyManager.listen(myPhoneStateListener,PhoneStateListener.LISTEN_NONE);

    }
}
