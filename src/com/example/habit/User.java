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

	//�����ݿ��ȡ���û�����
	public String getUserPwd() {
		Cursor c = DB.getData("user","name",this.userName);
		if(c.moveToFirst()){
			password = c.getString(3);
		}else{
			return null;
		}
		return password;
	}

	//�����û��������뵽���ݿ�
	public void setUserPwd(String userPwd) {
		this.password = userPwd;
		DB.insertUser(this);
	}
	
	//����еĻ���ȡ��ǰ�ѵ�¼���û���,û���򷵻ؿ�
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
	
	//���õ�ǰ�û���¼��Ϣ,����iΪ0��ʾ�ǵ�¼״̬��1Ϊ��¼״̬
	public boolean setStatic(int i){
		return DB.update("user",this.userName,"static",""+i);
	}
}
