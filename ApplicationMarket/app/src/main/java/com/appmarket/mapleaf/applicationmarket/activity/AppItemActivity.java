package com.appmarket.mapleaf.applicationmarket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;
import com.appmarket.mapleaf.applicationmarket.http.AppItemProtocol;
import com.appmarket.mapleaf.applicationmarket.http.BaseProtocol;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;
import com.appmarket.mapleaf.applicationmarket.view.App_detail_title_holder;
import com.appmarket.mapleaf.applicationmarket.view.Download_holder;
import com.appmarket.mapleaf.applicationmarket.view.LoadingPage;

import java.util.ArrayList;

public class AppItemActivity extends AppCompatActivity {

    private String pack;
    private AppInfo data;
    private LoadingPage loadingPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        pack = intent.getStringExtra("packageName");
        loadingPage =new LoadingPage(this) {
            @Override
            public int onLoad() {
                return AppItemActivity.this.onLoad();
            }

            @Override
            public View onCreateSuccessView() {
                return AppItemActivity.this.onCreateSuccessView();
            }
        };
        setContentView(loadingPage);
        loadingPage.loadData();
    }
    private int onLoad(){
        AppItemProtocol protocol = new AppItemProtocol(pack);
        data = protocol.getData(0);
        if(data!=null){
            return LoadingPage.STATER_SUCCESS;
        }else{
            return LoadingPage.STATER_ERROR;
        }
    }
    private View onCreateSuccessView(){
        LinearLayout pageView = (LinearLayout) UIUtils.inflate(R.layout.app_detail_page);
        FrameLayout fl_appitem1 = (FrameLayout) pageView.findViewById(R.id.fl_appitem1);
        FrameLayout fl_appitem2 = (FrameLayout) pageView.findViewById(R.id.fl_appitem2);
        App_detail_title_holder holder = new App_detail_title_holder();
        holder.setData(data);
        fl_appitem1.addView(holder.getRootView());
        Download_holder download_holder = new Download_holder();
        download_holder.setData(data);
        fl_appitem2.addView(download_holder.getRootView());
        return pageView;
    }

}
