package com.mapleaf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mapleaf.engine.AddressDao;
import com.mapleaf.mobilesafe.R;

public class Addressreceiver extends BroadcastReceiver {
    private WindowManager windowManager;
    private TextView tv;
    private SharedPreferences sp;
    private View view;
    private WindowManager.LayoutParams params;
    @Override
    public void onReceive(Context context, Intent intent) {
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        int[] bgcolor = {0xFF00FFFF,0xFFCBC0FF,0xFF00D7FF,0xFFFF0000,0xFF000000};
        //Toast.makeText(context,"成功发送Toast",Toast.LENGTH_SHORT).show();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;

        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;

        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            ;
        params.x=sp.getInt("x",100);
        params.y=sp.getInt("y",100);

        view = View.inflate(context, R.layout.toast_diy, null);
        tv= (TextView) view.findViewById(R.id.tv_toast_diy);
        String s = AddressDao.AddressParse(context, "18744024281");

        tv.setText(s);
        int colornum = sp.getInt("colornum", 0);
        view.setBackgroundColor(bgcolor[colornum]);
        setTouch();
        windowManager.addView(view,params);
    }

    private void setTouch() {
        view.setOnTouchListener(new View.OnTouchListener() {
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
                        params.x+=dX;
                        params.y+=dY;
                        windowManager.updateViewLayout(view,params);
                        startX=nowX;
                        startY=nowY;
                        break;
                    case MotionEvent.ACTION_UP:
                        int x =params.x;
                        int y =params.y;
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

    public void hideToast(){
        if(windowManager!=null&&tv!=null){
            windowManager.removeViewImmediate(tv);
            windowManager=null;
            tv=null;
        }
    }

}
