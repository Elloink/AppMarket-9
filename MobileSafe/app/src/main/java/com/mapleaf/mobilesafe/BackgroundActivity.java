package com.mapleaf.mobilesafe;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class BackgroundActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        ImageView yunt = (ImageView) findViewById(R.id.iv_yunt);
        ImageView yunb = (ImageView) findViewById(R.id.iv_yunb);

        yunt.startAnimation(animation);
        yunb.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },500);
    }
}
