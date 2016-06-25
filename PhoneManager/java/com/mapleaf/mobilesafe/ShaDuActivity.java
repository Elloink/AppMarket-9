package com.mapleaf.mobilesafe;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mapleaf.myutils.Myutils;

import java.util.List;

public class ShaDuActivity extends AppCompatActivity {

    private ImageView iv_scanner;
    private ProgressBar pb;
    private TextView tv_zhengzaishadu;
    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sha_du);
        iv_scanner = (ImageView) findViewById(R.id.iv_shadu_scanner);
        pb = (ProgressBar) findViewById(R.id.pb_shadu);
        ll = (LinearLayout) findViewById(R.id.ll_scanner);
        tv_zhengzaishadu = (TextView) findViewById(R.id.tv_shadu_zhengzaishadu);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(linearInterpolator);
        iv_scanner.startAnimation(rotateAnimation);
        scanner();
    }

    private void scanner() {
        final PackageManager pm = getPackageManager();
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
                pb.setMax(installedPackages.size());
                int count=0;
                for (final PackageInfo packageInfo : installedPackages
                        ) {
                    SystemClock.sleep(30);
                    Signature[] signatures = packageInfo.signatures;
                    String s = signatures[0].toCharsString();
                    String s1 = Myutils.MD5password(s);
                    System.out.println("MD5:----------"+s+"-----------");
                    final String name = packageInfo.applicationInfo.loadLabel(pm).toString().trim();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tv_zhengzaishadu.setText("正在初始化;"+name);
                            TextView textView = new TextView(getApplicationContext());
                            textView.setText(name);
                            ll.addView(textView,0);
                        }
                    });
                    count++;
                    pb.setProgress(count);

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_zhengzaishadu.setText("扫描完成，未发现病毒");
                        iv_scanner.clearAnimation();
                    }
                });
            }
        }.start();

    }
}
