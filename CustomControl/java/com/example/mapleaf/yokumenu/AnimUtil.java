package com.example.mapleaf.yokumenu;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by Mapleaf on 2016/6/26.
 */
public class AnimUtil {
    public static int flag = 0 ;
    public static void close(RelativeLayout rl,int offset){
        for(int i = 0 ; i < rl.getChildCount();i++){
            rl.getChildAt(i).setEnabled(false);
        }
        RotateAnimation animation = new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,1);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setStartOffset(offset);
        animation.setAnimationListener(new MyAnimationListener());
        rl.startAnimation(animation);

    }
    public static void show(RelativeLayout rl){
        for(int i = 0 ; i < rl.getChildCount();i++){
            rl.getChildAt(i).setEnabled(true);
        }
        RotateAnimation animation = new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,1);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setAnimationListener(new MyAnimationListener());
        rl.startAnimation(animation);
    }
    static class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            flag++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            flag--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
