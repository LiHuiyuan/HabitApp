package com.example.habit;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends ActivityCtrl{
	Context context = this;
	
	private EditText mAccount;
	private EditText mPwd;
	
	User user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//启用窗体的扩展特性：自定义标题栏
		setContentView(R.layout.register);//当前为注册界面
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.registertitle);//设置feature
		
		Button registerOK = (Button)findViewById(R.id.registerOK);
		//监听注册按钮
		registerOK.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) { 
				if(isUserNameAndPwdValid()){//验证输入栏是否有效
					if(register())	{//输入值有效注册						
						//跳转到主界面
						returnMainAct(RegisterActivity.this);
						(RegisterActivity.this).finish();
					}
				}
			}
		});
		
		Button RegBackBtn = (Button)findViewById(R.id.BackBtn);
		//监听返回按钮
		RegBackBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//返回到登录界面
				gotoLoginAct(RegisterActivity.this);
	            (RegisterActivity.this).finish();
			}
		});
		
	}
	
	public boolean register() {
		String userName = mAccount.getText().toString().trim();
		String userPwd = mPwd.getText().toString().trim();

		//验证用户名是否已存在
		user = new User(context);
		user.setUserName(userName);
		if(user.getUserName() != null){
			Toast.makeText(this,"当前用户名已存在",Toast.LENGTH_SHORT).show();
			return false;
		}
		
		user.setUserPwd(userPwd);//不存在则设置密码并保存用户名和密码
		if (user.setStatic(1)) {  //验证当前用户注册成功并且已设为登录状态
			currentUser = userName;
			Toast.makeText(this, "注册成功！",Toast.LENGTH_SHORT).show();	
		}else{
			Toast.makeText(this, "注册失败",Toast.LENGTH_SHORT).show();
			return false;	
		}
		return true;
	}
	
	public boolean isUserNameAndPwdValid() {
		mAccount = (EditText)findViewById(R.id.RegUserName);//获取界面输入用户名
		mPwd = (EditText)findViewById(R.id.RegPassWord);//获取界面输入密码
		if (mAccount.getText().toString().trim().equals("")) {
			Toast.makeText(this, "用户名不可为空",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (mPwd.getText().toString().trim().equals("")) {
			Toast.makeText(this, "密码不可为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
	
	
	
	


	
