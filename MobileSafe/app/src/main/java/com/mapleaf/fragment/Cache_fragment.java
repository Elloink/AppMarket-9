package com.mapleaf.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mapleaf.mobilesafe.R;
import com.mapleaf.myutils.Myutils;

import java.util.List;

/**
 * Created by Mapleaf on 2016/6/24.
 */
public class Cache_fragment extends Fragment{
    private TextView tv_cache;
    private ProgressBar pb_cache;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cache_fragment, container, false);
        tv_cache = (TextView) view.findViewById(R.id.tv_cache);
        pb_cache = (ProgressBar) view.findViewById(R.id.pb_cache);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scanner();
    }

    private void scanner() {
        final PackageManager pm = getActivity().getPackageManager();
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
                pb_cache.setMax(installedPackages.size());
                int count=0;
                for (final PackageInfo packageInfo : installedPackages
                        ) {
                    SystemClock.sleep(10);
                    final String name = packageInfo.applicationInfo.loadLabel(pm).toString().trim();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tv_cache.setText("正在初始化;"+name);
                        }
                    });
                    count++;
                    pb_cache.setProgress(count);

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_cache.setText("扫描完成，缓存已清理");
                        pb_cache.setVisibility(View.GONE);
                    }
                });
            }
        }.start();

    }
}
