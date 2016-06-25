package com.mapleaf.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProtectActivity extends AppCompatActivity {


    private TextView tv;
    private SharedPreferences sharedPreferences;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("firstSetting",true)) {
            Toast.makeText(this,"请您先设置手机防盗参数",Toast.LENGTH_SHORT).show();
            SystemClock.sleep(1000);
            startActivity(new Intent(this,ProtectSetting1Activity.class));
            finish();
        }else{
            setContentView(R.layout.activity_protect);
            tv = (TextView) findViewById(R.id.tv_protect_view_number);
            iv = (ImageView) findViewById(R.id.iv_protect_view);
            String phone = sharedPreferences.getString("phone", "110");

            if(sharedPreferences.getBoolean("protected",false)){
                iv.setImageResource(R.drawable.safe);
                tv.setText(phone);
            }else{
                tv.setText("未设置");
            }
        }



    }
    public void reinit(View view){
        startActivity(new Intent(this,ProtectSetting1Activity.class));
    }
}
