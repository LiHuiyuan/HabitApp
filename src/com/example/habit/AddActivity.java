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

	EditText text_habit;//��������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//���ô������չ���ԣ��Զ��������
		setContentView(R.layout.add);//��ǰΪϰ����ӽ���
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.addtitle);//����feature
		
		//��ȡ��ǰ�û���
		this.getUser();

		//��ť����������������
		Button SetBtn = (Button)findViewById(R.id.SetBtn);
		SetBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(isHabitName()){//����������Ч
					if(addHabit()){
						//��ת��ϰ�����ý���,��������Ϣ
						gotoSetAct(AddActivity.this);
						(AddActivity.this).finish();
					} else {
						Toast.makeText(context,"ϰ�����Ѵ���!",Toast.LENGTH_SHORT).show();
					}	
				}
			}
		});
		
		//��ť�����������
		Button AddSaveBtn = (Button)findViewById(R.id.AddSaveBtn);
		AddSaveBtn.setOnClickListener(new OnClickListener(){
			@Override	
			public void onClick(View arg0) {
				if(isHabitName()){//����������Ч
					if(addHabit()){
						//��ת�������棬�������ݵ�ǰ�û���
						returnMainAct(AddActivity.this);	
						(AddActivity.this).finish();
					} else {
						Toast.makeText(context,"ϰ�����Ѵ���!",Toast.LENGTH_SHORT).show();
					}	
				}	
			}	
		});
		
		//��ť������������
		Button AddBackBtn = (Button)findViewById(R.id.AddBackBtn);
		AddBackBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//��ת�������棬�������ݵ�ǰ�û���
				returnMainAct(AddActivity.this);
				(AddActivity.this).finish();
			}
			
		});
		
	}
	
	private boolean isHabitName() {
		//��ȡ�û��������Ϣ
		text_habit = (EditText)findViewById(R.id.InputAddHabit);
        habitname = text_habit.getText().toString();
        
		if (text_habit.getText().toString().trim().equals("")) {
			Toast.makeText(this, "�û�������Ϊ��",
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

