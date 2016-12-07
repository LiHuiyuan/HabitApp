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
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//���ô������չ���ԣ��Զ��������
		setContentView(R.layout.register);//��ǰΪע�����
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.registertitle);//����feature
		
		Button registerOK = (Button)findViewById(R.id.registerOK);
		//����ע�ᰴť
		registerOK.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) { 
				if(isUserNameAndPwdValid()){//��֤�������Ƿ���Ч
					if(register())	{//����ֵ��Чע��						
						//��ת��������
						returnMainAct(RegisterActivity.this);
						(RegisterActivity.this).finish();
					}
				}
			}
		});
		
		Button RegBackBtn = (Button)findViewById(R.id.BackBtn);
		//�������ذ�ť
		RegBackBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//���ص���¼����
				gotoLoginAct(RegisterActivity.this);
	            (RegisterActivity.this).finish();
			}
		});
		
	}
	
	public boolean register() {
		String userName = mAccount.getText().toString().trim();
		String userPwd = mPwd.getText().toString().trim();

		//��֤�û����Ƿ��Ѵ���
		user = new User(context);
		user.setUserName(userName);
		if(user.getUserName() != null){
			Toast.makeText(this,"��ǰ�û����Ѵ���",Toast.LENGTH_SHORT).show();
			return false;
		}
		
		user.setUserPwd(userPwd);//���������������벢�����û���������
		if (user.setStatic(1)) {  //��֤��ǰ�û�ע��ɹ���������Ϊ��¼״̬
			currentUser = userName;
			Toast.makeText(this, "ע��ɹ���",Toast.LENGTH_SHORT).show();	
		}else{
			Toast.makeText(this, "ע��ʧ��",Toast.LENGTH_SHORT).show();
			return false;	
		}
		return true;
	}
	
	public boolean isUserNameAndPwdValid() {
		mAccount = (EditText)findViewById(R.id.RegUserName);//��ȡ���������û���
		mPwd = (EditText)findViewById(R.id.RegPassWord);//��ȡ������������
		if (mAccount.getText().toString().trim().equals("")) {
			Toast.makeText(this, "�û�������Ϊ��",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (mPwd.getText().toString().trim().equals("")) {
			Toast.makeText(this, "���벻��Ϊ��",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
	
	
	
	


	
