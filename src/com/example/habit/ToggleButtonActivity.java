package com.example.habit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ToggleButtonActivity extends Activity {
	//提醒开关设置
    private ToggleButton toggleButton=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        //注册一个事件监听器
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                toggleButton.setChecked(isChecked);
            }

        });
    }
}



