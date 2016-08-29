package com.appmarket.mapleaf.appmarket.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appmarket.mapleaf.appmarket.R;
import com.appmarket.mapleaf.appmarket.utils.PrefUtils;

import java.util.ArrayList;

public class LeadActivity extends Activity {

    private ViewPager vp_guide;
    private ArrayList<ImageView> list ;
    private View redPoint;
    private View grayPoint;
    private int detal;
    private Button btn_guide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lead);

        initData();
        btn_guide  = (Button) findViewById(R.id.btn_guide);

        redPoint = findViewById(R.id.redPoint);
        grayPoint = findViewById(R.id.grayPoint);
        redPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                detal = grayPoint.getLeft()-redPoint.getLeft();
                redPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        vp_guide.setAdapter(new MyPagerAdapter());
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int dis = (int) (positionOffset * detal)+position * detal;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) redPoint.getLayoutParams();
                layoutParams.leftMargin = dis;
                redPoint.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                if(position<2){
                    btn_guide.setVisibility(View.INVISIBLE);
                }else{
                    btn_guide.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        ImageView iv1 = new ImageView(this);
        ImageView iv2 = new ImageView(this);
        ImageView iv3 = new ImageView(this);
        iv1.setBackgroundResource(R.mipmap.guide_1);
        iv2.setBackgroundResource(R.mipmap.guide_2);
        iv3.setBackgroundResource(R.mipmap.guide_3);
        list.add(iv1);
        list.add(iv2);
        list.add(iv3);
    }
    private class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = list.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    public void click(View view){
        PrefUtils.setSharePreferenceBoolean(getApplicationContext(),"firstLead",false);
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
