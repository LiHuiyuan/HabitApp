package com.example.habit;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Sys_SettingActivity extends ActivityCtrl {
	
    Context context = this;
    private User user = new User(context);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//启用窗体的扩展特性：自定义标题栏
		setContentView(R.layout.settings);//当前为设置界面
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.settingstitle);//设置feature
		
		this.getUser();//获取上一intent传递信息——用户名
		user.setUserName(currentUser);
		
		//按钮监听——关于
		Button AboutBtn = (Button)findViewById(R.id.AboutBtn);
		AboutBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Sys_SettingActivity.this)
				.setTitle("关于")
				.setMessage("Habit   Version1.1.0"
						+"\nwriter:黎慧源")
				.setPositiveButton("确定", null)
				.show();
			}
		});
		
		//按钮监听——退出当前用户
		Button ExitBtn = (Button)findViewById(R.id.ExitBtn);
		ExitBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				 new AlertDialog.Builder(Sys_SettingActivity.this).setTitle("退出").setMessage("确定退出当前账户？")
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					 @Override
					 public void onClick(DialogInterface arg0, int arg1) {
						 //确定退出之后更新该用户登录状态并跳转到登录界面
						 user.setStatic(0);
						 gotoLoginAct(Sys_SettingActivity.this);
						 (Sys_SettingActivity.this).finish(); 
					 }
				 }).setNegativeButton("取消", null).show();
			}			 	
		});
		
		//按钮监听——返回（主界面）
		Button SetBackBtn = (Button)findViewById(R.id.SetBackBtn);
		SetBackBtn.setOnClickListener(new OnClickListener(){		
			@Override
			public void onClick(View arg0) {
				returnMainAct(Sys_SettingActivity.this);	
				(Sys_SettingActivity.this).finish();
			}
		});	
		
	}

}

