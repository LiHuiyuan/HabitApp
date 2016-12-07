package com.example.habit;

import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class AddActivity extends ActivityCtrl {
	Context context = this;

	EditText text_habit;//输入内容
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//启用窗体的扩展特性：自定义标题栏
		setContentView(R.layout.add);//当前为习惯添加界面
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.addtitle);//设置feature
		
		//获取当前用户名
		this.getUser();

		//按钮监听——设置提醒
		Button SetBtn = (Button)findViewById(R.id.SetBtn);
		SetBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(isHabitName()){//输入内容有效
					if(addHabit()){
						//跳转到习惯设置界面,并传递信息
						gotoSetAct(AddActivity.this);
						(AddActivity.this).finish();
					} else {
						Toast.makeText(context,"习惯名已存在!",Toast.LENGTH_SHORT).show();
					}	
				}
			}
		});
		
		//按钮监听——完成
		Button AddSaveBtn = (Button)findViewById(R.id.AddSaveBtn);
		AddSaveBtn.setOnClickListener(new OnClickListener(){
			@Override	
			public void onClick(View arg0) {
				if(isHabitName()){//输入内容有效
					if(addHabit()){
						//跳转到主界面，继续传递当前用户名
						returnMainAct(AddActivity.this);	
						(AddActivity.this).finish();
					} else {
						Toast.makeText(context,"习惯名已存在!",Toast.LENGTH_SHORT).show();
					}	
				}	
			}	
		});
		
		//按钮监听——返回
		Button AddBackBtn = (Button)findViewById(R.id.AddBackBtn);
		AddBackBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//跳转到主界面，继续传递当前用户名
				returnMainAct(AddActivity.this);
				(AddActivity.this).finish();
			}
			
		});
		
	}
	
	private boolean isHabitName() {
		//获取用户输入的信息
		text_habit = (EditText)findViewById(R.id.InputAddHabit);
        habitname = text_habit.getText().toString();
        
		if (text_habit.getText().toString().trim().equals("")) {
			Toast.makeText(this, "用户名不可为空",
					Toast.LENGTH_SHORT).show();
			return false;
		} 
		return true;
	}

	private boolean addHabit(){
		Habit habit = new Habit(context,currentUser);
		return habit.CreateHabit(habitname);
	}
	
}

