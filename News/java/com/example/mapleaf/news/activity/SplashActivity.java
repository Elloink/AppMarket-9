package com.example.mapleaf.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.utils.PrefUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class SplashActivity extends Activity {

    private RelativeLayout rl_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setContentView(R.layout.activity_splash);
        init();
        addAnim();
    }

    private void init() {
        rl_splash = (RelativeLayout) findViewById(R.id.rl_splash);

    }

    private void addAnim() {
        RotateAnimation rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);
        ScaleAnimation scale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        alpha.setDuration(1000);
        alpha.setFillAfter(true);
        AnimationSet set  = new AnimationSet(false);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        rl_splash.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean flag = PrefUtil.getPref(getApplicationContext(), "guide", false);
                if(flag){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this,GuideActivity.class));

                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
