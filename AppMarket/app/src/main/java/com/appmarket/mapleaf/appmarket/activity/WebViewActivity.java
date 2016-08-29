package com.appmarket.mapleaf.appmarket.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.appmarket.mapleaf.appmarket.R;

public class WebViewActivity extends Activity implements View.OnClickListener{

    private ImageView iv_back;
    private ImageView iv_textsize;
    private ImageView iv_share;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_view);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_textsize = (ImageView) findViewById(R.id.iv_textsize);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        mWebView = (WebView) findViewById(R.id.wv_news);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_refresh);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView.loadUrl("http://192.168.1.108:8080/zhbj/10007/724D6A55496A11726628.html");

        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_textsize.setOnClickListener(this);

        float density = getResources().getDisplayMetrics().density; //设备密度

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });
    }

    private int curTextSize = 2;
    private int tempTextSize ;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_textsize:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("字体大小");
                String[] arr = new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};

                builder.setSingleChoiceItems(arr, curTextSize, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tempTextSize = i;
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WebSettings settings = mWebView.getSettings();
                        switch (tempTextSize){
                            case 0:
                                settings.setTextZoom(150);
                                break;
                            case 1:
                                settings.setTextZoom(130);
                                break;
                            case 2:
                                settings.setTextZoom(100);
                                break;
                            case 3:
                                settings.setTextZoom(50);
                                break;
                            case 4:
                                settings.setTextZoom(20);
                                break;
                        }
                    curTextSize = tempTextSize;
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
                break;
            case R.id.iv_share:
                break;
        }
    }

}
