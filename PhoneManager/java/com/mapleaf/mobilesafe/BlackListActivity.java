package com.mapleaf.mobilesafe;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mapleaf.bean.BlackNumInfo;
import com.mapleaf.db.BlackNumDao;
import com.mapleaf.myutils.MyAsycnTask;

import java.util.List;

public class BlackListActivity extends Activity {

    private ListView lv;
    private ProgressBar pb;
    private BlackNumDao dao;
    private List<BlackNumInfo> list;
    private MyBlackAdapter adapter;
    private static final int MAXNUM=3;
    private int startNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
        dao = new BlackNumDao(getApplicationContext());
        lv = (ListView) findViewById(R.id.lv_blacklist);
        pb = (ProgressBar) findViewById(R.id.pb_blacklist);
        loadData();
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = lv.getLastVisiblePosition();
                    if(lastVisiblePosition==list.size()-1){
                        startNum+=MAXNUM;
                        loadData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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
                if(list==null){
                    list = dao.queryPart(MAXNUM+"",startNum+"");

                }else{
                    list.addAll(dao.queryPart(MAXNUM+"",startNum+""));
                }
            }

            @Override
            public void afterTask() {
                pb.setVisibility(View.INVISIBLE);
                if(adapter==null){
                    adapter = new MyBlackAdapter();
                    lv.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        }.excute();
    }

    private class MyBlackAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final BlackNumInfo numInfo = list.get(position);
            View view;
            ViewHolder viewHolder;
            if(convertView == null){
                view = View.inflate(getApplicationContext(), R.layout.item_blacknum, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_blacknum = (TextView) view.findViewById(R.id.tv_item_blacknum);
                viewHolder.tv_mode = (TextView) view.findViewById(R.id.tv_item_mode);
                viewHolder.btn_delete_blacknum = (ImageView) view.findViewById(R.id.delete_blacknum);
                view.setTag(viewHolder);
            }
            else{
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tv_blacknum.setText(numInfo.getBlackNum());
            viewHolder.tv_mode.setText(numInfo.getMode());
            viewHolder.btn_delete_blacknum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder  builder =new AlertDialog.Builder(BlackListActivity.this);
                    builder.setMessage("确定要删除该黑名单："+numInfo.getBlackNum()+"吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.delete(numInfo.getBlackNum());
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }
            });
            return view;
        }
    }
    private AlertDialog dialog;
    public void addblacknum(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(BlackListActivity.this);
        View addblacknum = View.inflate(BlackListActivity.this,R.layout.addblacknum,null);
        final EditText et = (EditText) addblacknum.findViewById(R.id.et_addblacknum);
        final RadioGroup rg = (RadioGroup) addblacknum.findViewById(R.id.rg_addblacknum);

        Button btn_ok = (Button) addblacknum.findViewById(R.id.btn_addblacknum_ok);
        Button btn_cancle = (Button) addblacknum.findViewById(R.id.btn_addblacknum_cancle);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = rg.getCheckedRadioButtonId();
                String addmode = null;
                String et_text = et.getText().toString();

                switch (checkedRadioButtonId){
                    case R.id.rb_addblacknum_1:
                        addmode="电话拦截";
                        break;
                    case R.id.rb_addblacknum_2:
                        addmode="短信拦截";
                        break;
                    case R.id.rb_addblacknum_3:
                        addmode="全部拦截";
                        break;
                }
                BlackNumInfo blackNumInfo = new BlackNumInfo(et_text,addmode);
                dao.add(et_text,addmode);
                list.add(0,blackNumInfo);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        builder.setView(addblacknum);
        dialog = builder.create();
        dialog.show();

    }

}

class ViewHolder {
    TextView tv_blacknum;
    TextView tv_mode;
    ImageView btn_delete_blacknum;
}