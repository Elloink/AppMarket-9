package com.mapleaf.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ProtectSetting3Activity extends FatherActivity {

    private EditText et;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_setting3);
        et = (EditText) findViewById(R.id.et_contactnum);
        sp =getSharedPreferences("config",MODE_PRIVATE);

        String phone = sp.getString("phone", "");
        et.setText(phone);
    }


    @Override
    public void topre() {
        startActivity(new Intent(this, ProtectSetting2Activity.class));
        finish();
        overridePendingTransition(R.anim.enter_next, R.anim.exit_next);

    }

    @Override
    public void tonext() {
        startActivity(new Intent(this, ProtectSetting4Activity.class));
        finish();

    }

    public void select_connector(View v) {
        startActivityForResult(new Intent(this, ContactActivity.class),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            SharedPreferences.Editor edit = sp.edit();
            String phone = data.getStringExtra("phone");
            edit.putString("phone",phone);
            edit.commit();
            et.setText(phone);
        }
    }
}
