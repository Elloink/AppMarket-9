package com.example.mapleaf.yokumenu;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private EditText et_search;
    private String[] str;
    private ListView listView;
    private PopupWindow popupWindow;
    private boolean isExist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        et_search = (EditText) findViewById(R.id.et_search);
        str = new String[]{"90001","90002","90003","90004","90005","90006","90007","90008","90009","900010","900011"};

        listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.listview_background);
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(new MylvAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                et_search.setText(str[i]);
                popupWindow.dismiss();
            }
        });
    }
    public void down(View v){
        if(isExist){
            popupWindow.dismiss();
            isExist=false;
        }else{
            if(popupWindow==null){
                popupWindow = new PopupWindow(listView,et_search.getWidth(),500);

                popupWindow.showAsDropDown(et_search,0,10);

            }else{

                popupWindow.showAsDropDown(et_search,0,10);
            }
            isExist=true;
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
        }


    }
    class MylvAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return str.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View item = View.inflate(SearchActivity.this,R.layout.adapter_search,null);
            TextView tv_downsearch = (TextView) item.findViewById(R.id.tv_down_search);
            ImageView iv_delete = (ImageView) item.findViewById(R.id.iv_delete);
            tv_downsearch.setText(str[i]);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return item;
        }
    }
}
