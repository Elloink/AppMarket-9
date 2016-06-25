package com.mapleaf.mobilesafe;

import android.content.Intent;
import android.os.Bundle;

public class ProtectSetting1Activity extends FatherActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_setting1);
    }


    @Override
    public void topre() {
        finish();

    }

    @Override
    public void tonext() {
        startActivity(new Intent(ProtectSetting1Activity.this,ProtectSetting2Activity.class));
        finish();
    }


}
