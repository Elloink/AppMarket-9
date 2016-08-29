package com.appmarket.mapleaf.appmarket.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.appmarket.mapleaf.appmarket.R;
import com.appmarket.mapleaf.appmarket.utils.PrefUtils;

public class SplashActivity extends Activity {

    private RelativeLayout rl_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        animation();
    }

    private void init() {
        rl_splash = (RelativeLayout) findViewById(R.id.rl_splash);
    }

    private void animation() {
        //旋转
        RotateAnimation rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);
        //缩放
        ScaleAnimation scale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);
        //渐变
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        rl_splash.setAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean firstLead = PrefUtils.getSharePreferenceBoolean(SplashActivity.this, "firstLead", true);
                Intent intent = new Intent();
                if(firstLead){
                    intent.setClass(SplashActivity.this,LeadActivity.class);
                }else{
                    intent.setClass(SplashActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
