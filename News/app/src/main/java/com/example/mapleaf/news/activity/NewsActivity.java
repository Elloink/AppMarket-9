package com.example.mapleaf.news.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.mapleaf.news.R;

public class NewsActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news);
        initView();
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setTextZoom(100);
        webView.loadUrl(url);
    }

    private int checkedItem=2;
    private int chooseItem;
    private void initView() {
        webView = (WebView) findViewById(R.id.wv_news);
    }
    public void setTextSize(View view){
        String[] size = new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
        builder.setTitle("字体设置");
        builder.setSingleChoiceItems(size, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chooseItem=i;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                WebSettings settings = webView.getSettings();
                switch (chooseItem){
                    case 0:
                        settings.setTextZoom(200);
                        break;
                    case 1:
                        settings.setTextZoom(150);
                        break;
                    case 2:
                        settings.setTextZoom(100);
                        break;
                    case 3:
                        settings.setTextZoom(70);
                        break;
                    case 4:
                        settings.setTextZoom(50);
                        break;
                }
                checkedItem=chooseItem;
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }
}
