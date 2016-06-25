package com.mapleaf.mobilesafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapleaf.myutils.Myutils;
import com.mapleaf.service.WatchDogService;

public class HomeActivity extends AppCompatActivity {


    private GridView gv;
    private EditText et_pwd;
    private EditText et_rpwd;
    private SharedPreferences sp;
    private Boolean flag=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gv = (GridView) findViewById(R.id.gv_home_gridview);
        sp = getSharedPreferences("config",MODE_PRIVATE);

        gv.setAdapter(new Myadapter());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if(sp.getString("password","").isEmpty()) {
                            showSetPwdDialog();
                        }else{
                            showPwdDialog();
                        }

                        break;
                    case 1:
                        startActivity(new Intent(HomeActivity.this,BlackListActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this,SoftManageActivity.class));

                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity.this,TaskManageActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(HomeActivity.this,LiuliangActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(HomeActivity.this,ShaDuActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(HomeActivity.this,ClearCacheActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(HomeActivity.this,AToolsActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                }
            }
        });

    startService(new Intent(this, WatchDogService.class));
    }

    private void showPwdDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.protect_pwd_dialog,null);
        ImageButton ib = (ImageButton) view.findViewById(R.id.ib_protect_eye);
        final EditText et_password = (EditText) view .findViewById(R.id.et_protect_pwd);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag) {
                    et_password.setInputType(0);
                    flag=false;
                }else{
                    et_password.setInputType(129);
                    flag=true;
                }

            }
        });
        builder.setView(view);
        builder.setCancelable(false);
         final EditText et_pwd = (EditText) view.findViewById(R.id.et_protect_pwd);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = sp.getString("password", "");
                String pwd = et_password.getText().toString().trim();
                if(Myutils.MD5password(pwd).equals(password)){
                    enterProtectActivity();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


    }

    private void showSetPwdDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.protect_setpwd_dialog,null);
        builder.setView(view);
        et_pwd = (EditText) view.findViewById(R.id.et_protect_setpwd);
        et_rpwd = (EditText) view.findViewById(R.id.et_protect_resetpwd);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pwd = et_pwd.getText().toString();
                if(pwd.isEmpty()||pwd.equals("")){
                    Toast.makeText(HomeActivity.this, "重新输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String rpwd = et_rpwd.getText().toString();
                if(rpwd.isEmpty()||rpwd.equals("")){
                    Toast.makeText(HomeActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(pwd.equals(rpwd)){
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("password", Myutils.MD5password(pwd));
                    edit.commit();
                    Toast.makeText(HomeActivity.this, "密码保存成功", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(HomeActivity.this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        builder.show();
    }

    private void enterProtectActivity() {
        startActivity(new Intent(HomeActivity.this,ProtectActivity.class));
    }

    private class Myadapter extends BaseAdapter {
        int[] imageID = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app
                , R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
                R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings};
        String[] names = {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计",
                "手机杀毒", "缓存清理", "高级工具", "设置中心"};

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                View inflate = View.inflate(HomeActivity.this, R.layout.item_home, null);
                ImageView iv = (ImageView) inflate.findViewById(R.id.iv_home_item);
                TextView tv = (TextView) inflate.findViewById(R.id.tv_home_item);
                iv.setImageResource(imageID[position]);
                tv.setText(names[position]);
                return inflate;
            } else return convertView;
        }
    }
}
