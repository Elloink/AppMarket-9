package com.example.mapleaf.yokumenu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class SlideMenuActivity extends Activity {

    private SlideMenu slideMenu;
    private ImageView iv_main_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_slide_menu);
        slideMenu = (SlideMenu) findViewById(R.id.slideMenu);
        iv_main_menu = (ImageView) findViewById(R.id.iv_main_menu);
        iv_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideMenu.changeMenu();
            }
        });
    }
}
