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
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//���ô������չ���ԣ��Զ��������

		setContentView(R.layout.login);//��ǰΪ��¼����
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.logintitle);//����feature

		user = new User(context);

		//������¼��ť
		Button LoginBtn = (Button)findViewById(R.id.LoginBtn);
		LoginBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(isUserNameAndPwdValid()){//��֤�������Ƿ���Ч
					if(login()){//��֤�˻�
						//��ת��������
						returnMainAct(LoginActivity.this);
						(LoginActivity.this).finish();
					}	
				}	
			}
		});
		
		//����ע�ᰴť
		Button RegisterBtn = (Button)findViewById(R.id.RegisterBtn);
		RegisterBtn.setOnClickListener(new OnClickListener(){			
			public void onClick(View v) {
				//ת��ע�����
				gotoRegisterAct(LoginActivity.this);
				(LoginActivity.this).finish();
			}
		});
				
	}
	
	public boolean login() {
			//��ȡEditText������Ϣ
			String userName = mAccount.getText().toString().trim();
			String userPwd = mPwd.getText().toString().trim();
			
			user.setUserName(userName);
			if(user.getUserName() == null){
				Toast.makeText(this, "�û��������ڣ�",
						Toast.LENGTH_SHORT).show();
				return false;
			}
			if(user.getUserPwd().equals(userPwd)){
				user.setStatic(1);
				Toast.makeText(this,"��¼�ɹ�",
						Toast.LENGTH_SHORT).show();		
			} else {
				Toast.makeText(this, "����������������룡",
						Toast.LENGTH_SHORT).show();
				return false;
			}
			//�������ݿ���û��ĵ�¼״̬
			user.setStatic(1);
			currentUser = userName;
			return true;
	}
	
	public boolean isUserNameAndPwdValid() {
		mAccount = (EditText)findViewById(R.id.LogUserName);//��ȡ���������û���
		mPwd = (EditText)findViewById(R.id.LogPassWord);//��ȡ������������
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

