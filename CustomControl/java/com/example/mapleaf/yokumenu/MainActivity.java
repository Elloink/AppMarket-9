package com.example.mapleaf.yokumenu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_home;
    private RelativeLayout rl_2;
    private RelativeLayout rl_3;
    private ImageView iv_menu;
    private boolean isShow2 = true;
    private boolean isShow3 = true;
    private ViewPager viewPager;
    private List<Ad> list = new ArrayList<>();
    private LinearLayout ll_dot;
    private ImageView iv_reflesh;
    private ImageView iv_slideMenu;

    private TextView tv_pager;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0,2000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_home.setOnClickListener(this);
        rl_2 = (RelativeLayout) findViewById(R.id.rl_2);
        rl_3 = (RelativeLayout) findViewById(R.id.rl_3);
        iv_reflesh = (ImageView) findViewById(R.id.iv_reflesh);
        iv_reflesh.setOnClickListener(this);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.vp_image);
        ll_dot = (LinearLayout) findViewById(R.id.ll_dot);
        tv_pager = (TextView) findViewById(R.id.tv_pager);
        iv_slideMenu = (ImageView) findViewById(R.id.iv_slideMenu);
        iv_slideMenu.setOnClickListener(this);

        list.add(new Ad(R.drawable.a,"新垣结衣最美，美不胜收！"));
        list.add(new Ad(R.drawable.b,"新垣结衣最美，美不胜收！！"));
        list.add(new Ad(R.drawable.c,"新垣结衣最美，美不胜收！！！"));
        list.add(new Ad(R.drawable.d,"新垣结衣最美，美不胜收！！！！"));
        list.add(new Ad(R.drawable.e,"新垣结衣最美，美不胜收！！！！！"));
        for(int i =0;i<list.size();i++){
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15,15);
            params.leftMargin = 8;
            view.setBackgroundResource(R.drawable.select_dot);
            view.setLayoutParams(params);
            ll_dot.addView(view);
        }
        viewPager.setAdapter(new MyviewPager());
        int currentItem = viewPager.getCurrentItem()%5;
        tv_pager.setText(list.get(currentItem).getText());
        for(int i = 0 ;i<ll_dot.getChildCount();i++){
            ll_dot.getChildAt(i).setEnabled(i==currentItem);
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = viewPager.getCurrentItem()%5;
                tv_pager.setText(list.get(currentItem).getText());
                for(int i = 0 ;i<ll_dot.getChildCount();i++){
                    ll_dot.getChildAt(i).setEnabled(i==currentItem);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler.sendEmptyMessageDelayed(0,2000);
    }

    class MyviewPager extends PagerAdapter{

        @Override
        public int getCount() {
            return 1000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(MainActivity.this,R.layout.adapter_imageview,null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_adpater);
            Ad ad = list.get(position%5);
            iv.setImageResource(ad.getImageViewId());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
    @Override
    public void onClick(View view) {
        if(AnimUtil.flag>0){
            return;
        }
        switch (view.getId()){
            case R.id.iv_home:
                if(isShow2){
                    int offset = 0 ;
                    if(isShow3){
                        AnimUtil.close(rl_3,offset);
                        offset+=200;
                        isShow3=false;
                    }
                    AnimUtil.close(rl_2,offset);
                }else{
                    AnimUtil.show(rl_2);
                }
                isShow2 = !isShow2;
                break;
            case R.id.iv_menu:
                if(isShow3){
                    AnimUtil.close(rl_3,0);
                }else{
                    AnimUtil.show(rl_3);
                }
                isShow3 = !isShow3;
                break;
            case R.id.iv_reflesh:
                startActivity(new Intent(this,RefleshActivity.class));
                break;
            case R.id.iv_slideMenu:
                startActivity(new Intent(this,SlideMenuActivity.class));
                break;
        }
    }
    public void search(View v){
        startActivity(new Intent(this,SearchActivity.class));
    }
    public void setting(View v){
        startActivity(new Intent(this,SettingActivity.class));
    }
}
