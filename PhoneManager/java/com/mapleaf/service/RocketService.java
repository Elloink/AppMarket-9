package com.mapleaf.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapleaf.mobilesafe.BackgroundActivity;
import com.mapleaf.mobilesafe.R;

public class RocketService extends Service {

    private TextView tv_fashecao;
    private WindowManager windowManager;
    private View view;
    private WindowManager.LayoutParams params;
    private int height;
    private int width;
    private SharedPreferences sp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        height = windowManager.getDefaultDisplay().getHeight();
        width = windowManager.getDefaultDisplay().getWidth();
        view = View.inflate(getApplicationContext(), R.layout.rocket,null);
     //   tv_fashecao = (TextView) view.findViewById(R.id.tv_fashecao);
        ImageView iv= (ImageView) view.findViewById(R.id.iv_rocket);
        AnimationDrawable drawable = (AnimationDrawable) iv.getBackground();
        drawable.start();
        params = new WindowManager.LayoutParams();
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags=WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format= PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        windowManager.addView(view,params);
        setTouch();
    }

    private void setTouch() {
        view.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
              //          tv_fashecao.setVisibility(View.VISIBLE);
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int dX = nowX - startX;
                        int dY = nowY - startY;
                        params.x += dX;
                        params.y += dY;
                        windowManager.updateViewLayout(view, params);
                        startX = nowX;
                        startY = nowY;
                        break;
                    case MotionEvent.ACTION_UP:
                        if(params.y>290 && params.x>100&&params.x<200){
                            sendRocket();
                            Intent intent = new Intent(RocketService.this, BackgroundActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            params.y-=10;
            windowManager.updateViewLayout(view,params);
        }
    };
    private void sendRocket() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i = 0 ;i<200;i++){
                    SystemClock.sleep(5);
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(windowManager!=null&&view!=null){
            windowManager.removeView(view);
            windowManager=null;
            view=null;
        }
    }
}
