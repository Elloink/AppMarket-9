package com.mapleaf.mobilesafe;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mapleaf.receiver.Addressreceiver;

public class AToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);
    }
    public void queryGui(View view){
        startActivity(new Intent(AToolsActivity.this,AddressActivity.class));
    }
    public void vibrate(View view){
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{500,100,300,1000,500},-1);
    }
    public void broadcast(View view){

        Intent intent = new Intent();
        intent.setAction("com.mapleaf.com.toast");
        sendBroadcast(intent);
    }


}
