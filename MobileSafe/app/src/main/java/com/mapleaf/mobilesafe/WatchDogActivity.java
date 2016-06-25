package com.mapleaf.mobilesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class WatchDogActivity extends AppCompatActivity {

    private EditText et ;
    private String packageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_dog);
        et = (EditText) findViewById(R.id.et_watchdog_unlock);
        Intent intent =getIntent();
        packageName = intent.getStringExtra("packageName");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==  KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    public void unlock(View view){
        String pass = et.getText().toString().trim();
        if("123".equals(pass)){
            Intent intent = new Intent();
            intent.setAction("com.mapleaf.unlock");
            intent.putExtra("pack",packageName);
            sendBroadcast(intent);
            finish();
        }
    }
}
