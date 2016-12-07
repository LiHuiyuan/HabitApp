package com.example.habit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

@SuppressLint("Instantiatable")
public class User {
	String userName;
	String password;
	private DBAdapter DB;
	Context context;
	
	@SuppressLint("Instantiatable")
	public User(Context context){
		this.context = context;
	}
	
	public String getUserName() {
		if(!DB.getUserCount()) return null;
		Cursor c = DB.getData("user","name",this.userName);
		if(c.moveToFirst()){
			userName = c.getString(1);
		}else{
			return null;
		}
		
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
		DB = new DBAdapter(context,this.userName);
	}

	//从数据库获取该用户密码
	public String getUserPwd() {
		Cursor c = DB.getData("user","name",this.userName);
		if(c.moveToFirst()){
			password = c.getString(3);
		}else{
			return null;
		}
		return password;
	}

	//保存用户名和密码到数据库
	public void setUserPwd(String userPwd) {
		this.password = userPwd;
		DB.insertUser(this);
	}
	
	//如果有的话获取当前已登录的用户名,没有则返回空
	public String getCurrentUser(){
		if(!DB.getUserCount()) return null;
		int i = 1;
		Cursor c = DB.getData("user","static",""+i);
		if(c.moveToFirst()){
			userName = c.getString(1);
		}else{
			return null;
		}
		return this.userName;
	}
	
	//设置当前用户登录信息,参数i为0表示非登录状态，1为登录状态
	public boolean setStatic(int i){
		return DB.update("user",this.userName,"static",""+i);
	}
}
