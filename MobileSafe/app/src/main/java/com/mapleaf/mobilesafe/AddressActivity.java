package com.mapleaf.mobilesafe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mapleaf.engine.AddressDao;

import java.io.File;

public class AddressActivity extends AppCompatActivity {

    private EditText et;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        et = (EditText) findViewById(R.id.et_address_num);
        tv = (TextView) findViewById(R.id.tv_address_address);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = s.toString();
                String add = AddressDao.AddressParse(AddressActivity.this,phone);
                tv.setText(add);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void queryAddress(View view){
        String num = et.getText().toString();
        String address = AddressParse(this, num);
        tv.setText(address);
    }
    public  String AddressParse(Context context, String num){
        String location = "";
        File file = new File(context.getFilesDir(),"address.db");
        SQLiteDatabase database = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null,SQLiteDatabase.OPEN_READONLY);
        if(num.matches("^1[34578]\\d{9}$")){
            Cursor cursor = database.rawQuery("select location from data2 where id = (select outkey from data1 where id = ?)",
                    new String[]{num.substring(0, 7)});
            if(cursor.moveToNext()){
                location = cursor.getString(0);
            }
            cursor.close();
        }else{
            location="not found";
        }


        database.close();
        return location;
    }
}
