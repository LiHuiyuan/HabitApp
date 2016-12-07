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
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//���ô������չ���ԣ��Զ��������
		setContentView(R.layout.settings);//��ǰΪ���ý���
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.settingstitle);//����feature
		
		this.getUser();//��ȡ��һintent������Ϣ�����û���
		user.setUserName(currentUser);
		
		//��ť������������
		Button AboutBtn = (Button)findViewById(R.id.AboutBtn);
		AboutBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Sys_SettingActivity.this)
				.setTitle("����")
				.setMessage("Habit   Version1.1.0"
						+"\nwriter:���Դ")
				.setPositiveButton("ȷ��", null)
				.show();
			}
		});
		
		//��ť���������˳���ǰ�û�
		Button ExitBtn = (Button)findViewById(R.id.ExitBtn);
		ExitBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				 new AlertDialog.Builder(Sys_SettingActivity.this).setTitle("�˳�").setMessage("ȷ���˳���ǰ�˻���")
				 .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					 @Override
					 public void onClick(DialogInterface arg0, int arg1) {
						 //ȷ���˳�֮����¸��û���¼״̬����ת����¼����
						 user.setStatic(0);
						 gotoLoginAct(Sys_SettingActivity.this);
						 (Sys_SettingActivity.this).finish(); 
					 }
				 }).setNegativeButton("ȡ��", null).show();
			}			 	
		});
		
		//��ť�����������أ������棩
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

