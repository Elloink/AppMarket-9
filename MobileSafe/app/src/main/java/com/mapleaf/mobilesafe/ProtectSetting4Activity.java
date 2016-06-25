package com.mapleaf.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class ProtectSetting4Activity extends FatherActivity {

    private SharedPreferences sp;
    private CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_setting4);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        cb = (CheckBox) findViewById(R.id.cb_protect4_protected);
        if(sp.getBoolean("protected",false)){
            cb.setChecked(true);
            cb.setText("已经开启防盗保护");

        }else{
            cb.setChecked(false);
            cb.setText("未开启防盗保护");

        }
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = sp.edit();
                if(isChecked){
                    cb.setText("已经开启防盗保护");
                    edit.putBoolean("protected",true);

                }else{
                    cb.setText("未开启防盗保护");
                    edit.putBoolean("protected",false);

                }
                edit.commit();
            }
        });
    }

    @Override
    public void topre() {
        startActivity(new Intent(this,ProtectSetting3Activity.class));
        finish();
        overridePendingTransition(R.anim.enter_next,R.anim.exit_next);

    }

    @Override
    public void tonext() {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("firstSetting",false);
        edit.commit();
        startActivity(new Intent(this,ProtectActivity.class));
        finish();

    }
}
