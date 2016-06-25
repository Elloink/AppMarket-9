package com.mapleaf.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapleaf.bean.AppInfo;
import com.mapleaf.db.WatchDogDao;
import com.mapleaf.engine.AppEngine;
import com.mapleaf.engine.AppUtils;
import com.mapleaf.myutils.MyAsycnTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class SoftManageActivity extends Activity implements View.OnClickListener{

    private ListView lv;
    private ProgressBar pb;
    private List<AppInfo> list;
    private List<AppInfo> userlist;
    private List<AppInfo> systemlist;
    private AppInfo  appInfo;
    private PopupWindow popupWindow;
    private MyAdapter adapter;
    private TextView tv_rom;
    private WatchDogDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_manage);
        dao  = new WatchDogDao(getApplicationContext());
        tv_rom = (TextView) findViewById(R.id.tv_softmanage_rom);
        long availROM = AppUtils.getAvailROM();
        String s = android.text.format.Formatter.formatFileSize(getApplicationContext(), availROM);
        tv_rom.setText("可用内存："+s);
        lv = (ListView) findViewById(R.id.lv_softmanage);
        pb = (ProgressBar) findViewById(R.id.pb_softmanage);
        loadData();
        setlvclicklistener();
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(popupWindow!=null){
                    popupWindow.dismiss();
                    popupWindow=null;
                }
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                appInfo = list.get(position);
                if(dao.query(appInfo.getPackageName())){
                    dao.delete(appInfo.getPackageName());
                }else{
                    dao.add(appInfo.getPackageName());
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void setlvclicklistener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                appInfo = new AppInfo();
                if (position <= userlist.size() - 1) {
                    appInfo = userlist.get(position);

                } else {
                    appInfo = systemlist.get(position - userlist.size());
                }
                if(popupWindow!=null){
                    popupWindow.dismiss();
                    popupWindow=null;
                }
                View popview = View.inflate(getApplicationContext(),R.layout.popupwindow,null);
                LinearLayout ll1 = (LinearLayout) popview.findViewById(R.id.ll_softmanage_1);
                LinearLayout ll2 = (LinearLayout) popview.findViewById(R.id.ll_softmanage_2);
                LinearLayout ll3 = (LinearLayout) popview.findViewById(R.id.ll_softmanage_3);
                LinearLayout ll4 = (LinearLayout) popview.findViewById(R.id.ll_softmanage_4);
                ll1.setOnClickListener(SoftManageActivity.this);
                ll2.setOnClickListener(SoftManageActivity.this);
                ll3.setOnClickListener(SoftManageActivity.this);
                ll4.setOnClickListener(SoftManageActivity.this);

                popupWindow = new PopupWindow(popview, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                int[] location = new int[2];
                view.getLocationInWindow(location);
                popupWindow.showAtLocation(parent, Gravity.TOP|Gravity.LEFT,location[0]+50,location[1]);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
                ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0.5f);
                scaleAnimation.setDuration(1000);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.4f,1.0f);
                alphaAnimation.setDuration(1000);
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(scaleAnimation);
                popview.startAnimation(animationSet);
            }
        });
    }

    private void loadData() {
        new MyAsycnTask() {

            @Override
            public void preTask() {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void doInBack() {
                list = AppEngine.getAppInfos(getApplicationContext());
                userlist = new ArrayList<AppInfo>();
                systemlist = new ArrayList<AppInfo>();
                for (AppInfo info : list) {
                    if (info.isUser()) {
                        userlist.add(info);
                    } else {
                        systemlist.add(info);
                    }
                }
            }

            @Override
            public void afterTask() {
                if(adapter==null){
                    adapter = new MyAdapter();
                    lv.setAdapter(adapter);

                }else{
                    adapter.notifyDataSetChanged();
                }
                pb.setVisibility(View.INVISIBLE);
            }
        }.excute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_softmanage_1:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DELETE");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("package:"+appInfo.getPackageName()));
                startActivityForResult(intent,0);
                break;
            case R.id.ll_softmanage_2:
                PackageManager pm= getPackageManager();
                Intent intent1 = pm.getLaunchIntentForPackage(appInfo.getPackageName());
                startActivity(intent1);
                break;
            case R.id.ll_softmanage_3:

                break;
            case R.id.ll_softmanage_4:
                break;
        }
        if(popupWindow!=null){
            popupWindow.dismiss();
            popupWindow=null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadData();
    }

    private class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return userlist.size() + systemlist.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppInfo appInfo;

            appInfo = list.get(position);
            View view = null;
            ViewHolderr viewHolderr = null;
            if (convertView == null) {
                view = View.inflate(SoftManageActivity.this, R.layout.item_softmanage, null);
                viewHolderr = new ViewHolderr();
                viewHolderr.appName = (TextView) view.findViewById(R.id.item_softmanage_appname);
                viewHolderr.iv = (ImageView) view.findViewById(R.id.item_softmanage_icon);
                viewHolderr.location = (TextView) view.findViewById(R.id.item_softmanage_location);
                viewHolderr.version = (TextView) view.findViewById(R.id.item_softmanage_version);
                viewHolderr.iv_lock = (ImageView) view.findViewById(R.id.iv_item_softmanage_lock);

                view.setTag(viewHolderr);
            } else if (convertView != null && convertView instanceof RelativeLayout) {
                view = convertView;
                viewHolderr = (ViewHolderr) view.getTag();
            }

            viewHolderr.iv.setImageDrawable(appInfo.getIcon());
            viewHolderr.appName.setText(appInfo.getName());
            boolean isSD = appInfo.isSD();
            if (isSD) {
                viewHolderr.location.setText("SD卡");
            } else {
                viewHolderr.location.setText("手机内存");
            }
            viewHolderr.version.setText(appInfo.getVersionName());
            if(appInfo.getPackageName()!=null&&!appInfo.getPackageName().equals("")) {
                if (dao.query(appInfo.getPackageName())) {
                    viewHolderr.iv_lock.setImageResource(android.R.drawable.ic_lock_idle_lock);
                } else {
                    viewHolderr.iv_lock.setImageResource(android.R.drawable.ic_lock_idle_charging);

                }
            }

            return view;
        }
    }

    private class ViewHolderr {
        private ImageView iv,iv_lock;
        private TextView appName;
        private TextView location;
        private TextView version;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(popupWindow!=null){
            popupWindow.dismiss();
            popupWindow=null;
        }
    }
}
