package com.example.mapleaf.yokumenu;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RefleshActivity extends AppCompatActivity {

    private RefleshListView refleshListView;
    private MyRefleshListView adapter;
    private ArrayList<String> list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflesh);
        list = new ArrayList<>();
        for(int i = 0 ;i<15;i++){
            list.add("refleshlistview原有的数据+"+i);
        }
        refleshListView = (RefleshListView) findViewById(R.id.rlv_diy);
        adapter = new MyRefleshListView();
        refleshListView.setAdapter(adapter);
        refleshListView.setOnRefleshListener(new RefleshListView.OnRefleshListener() {
            @Override
            public void onReflesh() {
                requestFromService();
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
            refleshListView.completeReflesh();
        }
    };
    private void requestFromService() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                list.add(0,"下拉刷新新item");
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    class MyRefleshListView extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(RefleshActivity.this);
            textView.setText(list.get(i));
            textView.setTextColor(Color.RED);
            textView.setPadding(15,15,15,15);
            textView.setTextSize(25);
            return textView;
        }
    }

}
