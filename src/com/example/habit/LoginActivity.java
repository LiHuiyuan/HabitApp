package com.example.habit;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class LoginActivity extends ActivityCtrl {
	Context context = this;
	
	private EditText mAccount;
	private EditText mPwd;
	
	private User user;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//启用窗体的扩展特性：自定义标题栏

		setContentView(R.layout.login);//当前为登录界面
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.logintitle);//设置feature

		user = new User(context);

		//监听登录按钮
		Button LoginBtn = (Button)findViewById(R.id.LoginBtn);
		LoginBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(isUserNameAndPwdValid()){//验证输入栏是否有效
					if(login()){//验证账户
						//跳转到主界面
						returnMainAct(LoginActivity.this);
						(LoginActivity.this).finish();
					}	
				}	
			}
		});
		
		//监听注册按钮
		Button RegisterBtn = (Button)findViewById(R.id.RegisterBtn);
		RegisterBtn.setOnClickListener(new OnClickListener(){			
			public void onClick(View v) {
				//转到注册界面
				gotoRegisterAct(LoginActivity.this);
				(LoginActivity.this).finish();
			}
		});
				
	}
	
	public boolean login() {
			//获取EditText输入信息
			String userName = mAccount.getText().toString().trim();
			String userPwd = mPwd.getText().toString().trim();
			
			user.setUserName(userName);
			if(user.getUserName() == null){
				Toast.makeText(this, "用户名不存在！",
						Toast.LENGTH_SHORT).show();
				return false;
			}
			if(user.getUserPwd().equals(userPwd)){
				user.setStatic(1);
				Toast.makeText(this,"登录成功",
						Toast.LENGTH_SHORT).show();		
			} else {
				Toast.makeText(this, "密码错误，请重新输入！",
						Toast.LENGTH_SHORT).show();
				return false;
			}
			//更新数据库该用户的登录状态
			user.setStatic(1);
			currentUser = userName;
			return true;
	}
	
	public boolean isUserNameAndPwdValid() {
		mAccount = (EditText)findViewById(R.id.LogUserName);//获取界面输入用户名
		mPwd = (EditText)findViewById(R.id.LogPassWord);//获取界面输入密码
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

