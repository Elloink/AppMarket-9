package com.example.mapleaf.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.utils.PrefUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {

    private ViewPager vp_guide;
    private static final int[] pic_guide = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private List<ImageView> list ;
    private LinearLayout ll_guide;
    private int scrollWidth;
    private ImageView view_redPoiont;
    private Button btn_guide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        init();
    }

    private void init() {
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        ll_guide = (LinearLayout) findViewById(R.id.ll_guide);
        view_redPoiont = (ImageView) findViewById(R.id.view_redPoiont);
        btn_guide = (Button) findViewById(R.id.btn_guide);
        btn_guide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PrefUtil.setPref(getApplicationContext(),"guide",true);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });
        list = new ArrayList<>();
        for(int i =0;i<pic_guide.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(pic_guide[i]);
            list.add(imageView);
        }
        for(int i = 0 ;i<pic_guide.length;i++){
            View view = new View(this);
            view.setBackgroundResource(R.drawable.shape_guide_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,30);
            if(i>0){
                params.leftMargin=30;
            }
            view.setLayoutParams(params);
            ll_guide.addView(view);
        }
        vp_guide.setAdapter(new MyPagerAdapter());
        vp_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int len = (int) (scrollWidth * positionOffset+position * scrollWidth);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view_redPoiont.getLayoutParams();
                layoutParams.leftMargin=len;
                view_redPoiont.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                if(position == pic_guide.length-1){
                    btn_guide.setVisibility(View.VISIBLE);
                }else{
                    btn_guide.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ll_guide.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollWidth = ll_guide.getChildAt(1).getLeft() - ll_guide.getChildAt(0).getLeft();
                ll_guide.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
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
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
