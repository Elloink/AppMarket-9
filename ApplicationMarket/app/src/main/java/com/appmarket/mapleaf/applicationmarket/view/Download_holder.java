package com.appmarket.mapleaf.applicationmarket.view;

import android.view.View;
import android.widget.Button;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;
import com.appmarket.mapleaf.applicationmarket.bean.DownloadInfo;
import com.appmarket.mapleaf.applicationmarket.manage.DownloadManager;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

/**
 * Created by Mapleaf on 2016/7/15.
 */
public class Download_holder extends MyBaseHolder<AppInfo>{
    private Button btn_shoucang;
    private Button btn_download;
    private Button btn_share;
    private DownloadManager downloadManager;
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.download_layout);
        btn_shoucang = (Button) view.findViewById(R.id.btn_shoucang);
        btn_download = (Button) view.findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager.download(getData());
            }
        });
        btn_share = (Button) view.findViewById(R.id.btn_share);
        downloadManager =DownloadManager.getInstance();
        downloadManager.registerObserver(new DownloadManager.DownloadObserver() {
            @Override
            public void onDownloadStateChanged(DownloadInfo info) {

            }

            @Override
            public void onDownloadProgressChanged(DownloadInfo info) {

            }
        });
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

    }
}
