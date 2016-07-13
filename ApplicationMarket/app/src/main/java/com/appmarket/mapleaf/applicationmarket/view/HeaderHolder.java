package com.appmarket.mapleaf.applicationmarket.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/12.
 */
public class HeaderHolder extends MyBaseHolder<ArrayList<String>>{

    private ArrayList<String> picList;
    protected ImageOptions options;
    private ViewPager viewPager;
    private LinearLayout ll;
    private int margin;
    private int curView=0;
    @Override
    public View initView() {
        RelativeLayout relativeLayout = new RelativeLayout(UIUtils.getContext());
        AbsListView.LayoutParams absParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,UIUtils.dp2px(250));
        relativeLayout.setLayoutParams(absParams);
        viewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(viewPager,relParams);
        ll = new LinearLayout(UIUtils.getContext());
        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        margin = UIUtils.dp2px(15);
        llParams.setMargins(margin,margin,margin,margin);
        relativeLayout.addView(ll,llParams);
        return relativeLayout;
    }

    @Override
    public void refreshView(ArrayList<String> data) {
        picList = data;
        viewPager.setAdapter(new MyViewPagerAdapter());
        viewPager.setCurrentItem(Integer.MAX_VALUE/2-(Integer.MAX_VALUE/2)%picList.size());
        for(int i=0;i<data.size();i++){
            ImageView iv = new ImageView(UIUtils.getContext());
            if(i==0){
                iv.setImageResource(R.drawable.indicator_selected);
            }else{
                iv.setImageResource(R.drawable.indicator_normal);
                iv.setPadding(5,0,0,0);
            }
            ll.addView(iv);
        }
        MyTask task = new MyTask();
        task.start();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ImageView view = (ImageView) ll.getChildAt(position%picList.size());
                view.setImageResource(R.drawable.indicator_selected);
                ImageView view1 = (ImageView) ll.getChildAt(curView);
                view1.setImageResource(R.drawable.indicator_normal);
                curView=position%picList.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private class MyTask implements Runnable{

        public void start(){
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this,3000);
        }
        @Override
        public void run() {
            int pos = viewPager.getCurrentItem()+1;
            viewPager.setCurrentItem(pos);
            UIUtils.getHandler().postDelayed(this,3000);
        }
    }
    private class MyViewPagerAdapter extends PagerAdapter{

        public MyViewPagerAdapter(){
            options = new ImageOptions.Builder().setFailureDrawableId(R.drawable.ic_default)
                    .setLoadingDrawableId(R.drawable.ic_default).build();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(UIUtils.getContext());
            String url = "http://127.0.0.1:8090/image?name="+picList.get(position%picList.size());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            x.image().bind(imageView,url,options);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
