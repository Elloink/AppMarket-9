package com.mapleaf.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.mapleaf.ui.SettingItem;

public class ProtectSetting2Activity extends FatherActivity {
private SettingItem settingItem;
    private TelephonyManager tel;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_setting2);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        settingItem = (SettingItem) findViewById(R.id.bangdingSIM);
        if(sp.getString("sim","1").equals("1")){
            settingItem.setCheck(false);
        }else{
            settingItem.setCheck(true);
        }
        settingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sp.edit();

                if(settingItem.isChecked()){
                    settingItem.setCheck(false);
                    edit.putString("sim","1");
                }else{
                    settingItem.setCheck(true);
                    String simSerialNumber = tel.getSimSerialNumber();
                    edit.putString("sim",simSerialNumber);
                    Log.e("yoyo",simSerialNumber+"");
                }
                edit.commit();
            }
        });
    }


    @Override
    public void topre() {
        startActivity(new Intent(this,ProtectSetting1Activity.class));
        finish();
        overridePendingTransition(R.anim.enter_next,R.anim.exit_next);

    }

    @Override
    public void tonext() {
        startActivity(new Intent(this,ProtectSetting3Activity.class));
        finish();

    }
}
