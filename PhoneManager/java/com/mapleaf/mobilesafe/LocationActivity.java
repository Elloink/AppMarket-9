package com.mapleaf.mobilesafe;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    private LinearLayout ll;
    private SharedPreferences sp;
    private int width1;
    private int height1;
    private TextView tvtop;
    private TextView tvbot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tvtop = (TextView) findViewById(R.id.tv_locationtop);
        tvbot = (TextView) findViewById(R.id.tv_locationbot);
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        width1 = metrics.widthPixels;
        height1 = metrics.heightPixels;
        sp = getSharedPreferences("config",MODE_PRIVATE);
        ll = (LinearLayout) findViewById(R.id.ll_location);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll.getLayoutParams();
        params.leftMargin=sp.getInt("x",0);
        params.topMargin=sp.getInt("y",0);
        if(sp.getInt("y",0)<=height1/2){
            tvtop.setVisibility(View.INVISIBLE);
            tvbot.setVisibility(View.VISIBLE);
        }
        if(sp.getInt("y",0)>height1/2){
            tvbot.setVisibility(View.INVISIBLE);
            tvtop.setVisibility(View.VISIBLE);
        }
        ll.setLayoutParams(params);
        ll.setOnClickListener(new View.OnClickListener() {
            long[] m = new long[2];
            @Override
            public void onClick(View v) {
                System.arraycopy(m,1,m,0,m.length-1);
                m[m.length-1]= SystemClock.uptimeMillis();
                if(m[0]>=(SystemClock.uptimeMillis()-500)){
                    int l = (width1-ll.getWidth())/2;
                    int t = (height1-ll.getHeight())/2;
                    ll.layout(l,t,l+ll.getWidth(),t+ll.getHeight());
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putInt("x",l);
                    edit.putInt("y",t);
                    edit.commit();
                }
            }
        });
        ll.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int dX=nowX-startX;
                        int dY=nowY-startY;
                        int left = ll.getLeft();
                        int top = ll.getTop();
                        int width = ll.getWidth();
                        int height = ll.getHeight();
                        if((left+dX)<0||(top+dY)<0||(left+dX+width)>width1||(top+dY+height)>height1){
                            break;
                        }

                        ll.layout(left+dX,top+dY,left+dX+width,top+dY+height);
                        int top1 = ll.getTop();
                        if(top1<=height1/2){
                            tvtop.setVisibility(View.INVISIBLE);
                            tvbot.setVisibility(View.VISIBLE);
                        }
                        if(top1>height1/2){
                            tvbot.setVisibility(View.INVISIBLE);
                            tvtop.setVisibility(View.VISIBLE);
                        }
                        startX=nowX;
                        startY=nowY;
                        break;
                    case MotionEvent.ACTION_UP:
                        int x = ll.getLeft();
                        int y = ll.getTop();
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putInt("x",x);
                        edit.putInt("y",y);
                        edit.commit();
                        break;
                }
                return false;
            }
        });
    }
}
