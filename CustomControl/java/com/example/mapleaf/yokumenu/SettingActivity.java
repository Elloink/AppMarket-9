package com.example.mapleaf.yokumenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private Toggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toggle = (Toggle) findViewById(R.id.toggle);
        toggle.setBackgroundRes(R.drawable.switch_background);
        toggle.setSlideRes(R.drawable.slide_button_background);
        toggle.setToggleState(Toggle.ToggleState.open);
        toggle.setOnToggleStateChangeListener(new Toggle.OnToggleStateChangeListener() {
            @Override
            public void onToggleStateChange(Toggle.ToggleState state) {
                Toast.makeText(SettingActivity.this, state== Toggle.ToggleState.open?"开启":"关闭", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
