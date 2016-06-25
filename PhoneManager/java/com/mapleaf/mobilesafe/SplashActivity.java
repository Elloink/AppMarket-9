package com.mapleaf.mobilesafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.mapleaf.myutils.Myutils;
import com.mapleaf.service.AddressService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;
    private TextView tv_progress;
    private TextView tv_pro;
    private String versionName;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    showAlertDialog();
            }
        }
    };

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新版本提示").setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 更新版本

                download();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 取消更新
                Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }).setCancelable(false).setMessage("新版本来袭，牛的一逼").show();
    }

    private void download() {
        HttpUtils httpUtils=new HttpUtils(5000);
        httpUtils.download("http://www-eu.apache.org/dist/tomcat/tomcat-8/v8.0.35/bin/apache-tomcat-8.0.35-windows-x64.zip", "/data/data/com.mapleaf.mobilesafe/apaqi.zip", new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                tv_pro.setVisibility(View.VISIBLE);
                tv_pro.setText(current+"/"+total);
            }
        });
    }

    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("版本号："+getVersion());
        tv_pro = (TextView) findViewById(R.id.tv_pro);

//        if(sp.getBoolean("update",true)){
//
//            update();
//        }else{
//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
                  //  SystemClock.sleep(2000);
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
//
//                }
//            }.start();
//
//        }
        AssetManager assetManager = getAssets();
        try {
            InputStream assets = assetManager.open("address.db");
            FileOutputStream fio = new FileOutputStream(new File(getFilesDir(),"address.db"));
            byte[] b = new byte[1024];
            int len = -1;
            while((len=assets.read(b))!=-1){
                fio.write(b,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        startService(new Intent(this, AddressService.class));
        shortcut();
    }

    private void shortcut() {
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"我叼我最叼");
        Bitmap value = BitmapFactory.decodeResource(getResources(),R.drawable.safe);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,value);
        Intent intent1 =new Intent();
        intent1.setAction("com.mapleaf.home");
        intent1.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,intent1);
        sendBroadcast(intent);
    }

    private void update() {

        new Thread(){
            @Override
            public void run() {
                int curr = (int) SystemClock.currentThreadTimeMillis();
                int end = 0;
                super.run();
                Message message = new Message();
                message.what=1;
                try {
                    URL url = new URL("https://www.baidu.com");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    int responseCode = conn.getResponseCode();
                    if(responseCode==200){
                        Log.e("Splash","success");
                        InputStream inputStream = conn.getInputStream();
                        String s = Myutils.parseInputStream(inputStream);
                        end = (int) SystemClock.currentThreadTimeMillis();
                        int x= end-curr;
                        if(x<2000){
                            SystemClock.sleep(2000-x);
                        }
                        handler.sendMessage(message);
                    }
                    else{
                        Log.e("Splash","false");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private String getVersion(){
        PackageManager packageManager=getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.mapleaf.mobilesafe", 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
