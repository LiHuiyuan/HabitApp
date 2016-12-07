package com.example.habit;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;


public class SignActivity extends ActivityCtrl{
	Context context = this;
    
    private String today;//ϵͳʱ��
    private String last_signDate;//��ǰϰ�����һ��ǩ��ʱ��
    
    private Habit habit;
    
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//�Զ��������
		setContentView(R.layout.sign);//��ǰΪǩ������
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.signtitle);//����feature
	    
        this.getUser_Habit();//��ȡ��һintent������Ϣ�����û�����ϰ��
        
	    habit = new Habit(context,currentUser);
	    habit.sethabitname(habitname); 
		
		//��ʾ���ݡ���ϰ������
		TextView currenthabit=(TextView)findViewById(R.id.textView1);
		currenthabit.setText(habitname);
		//������ʾ���ݡ�������ǩ�����
		showTextView();
		today = getToday();

	    //��ť����������������
	    Button SignBackBtn = (Button)findViewById(R.id.SignBackBtn);
		SignBackBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				returnMainAct(SignActivity.this);
				(SignActivity.this).finish();
			}
		});
				
		//��ť������ǩ��������ǩ����Ϣ
		Button SignBtn = (Button)findViewById(R.id.SignBtn);
		SignBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(today.equals(last_signDate)){
					Toast.makeText( SignActivity.this , "������ǩ�����ˣ�", 
							Toast.LENGTH_SHORT).show();  
				}  else  { 
					habit.updateSign(today);  //�������ݿ�
					showTextView();	//����textView
					Toast.makeText( SignActivity.this , "ǩ���ɹ���", 
						    		Toast.LENGTH_SHORT).show();
				}
			}	
		});
	
		//�����ʱ����ť
		Button TimerBtn = (Button)findViewById(R.id.TimerBtn);
		TimerBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				gotoTimerAct(SignActivity.this);
				(SignActivity.this).finish();
			}
			
		});
		
	}
	
	private void showTextView(){
		 //��ȡϰ�����ǩ��ʱ��
		last_signDate = habit.getHabit_SignTime();
		//��ʾ���ݡ�����ǩ������
		String countSign = habit.getSignDay();
	    TextView SignDays = (TextView)findViewById(R.id.SignDays); 
	    if(countSign != null)	SignDays.setText(countSign);
	    else SignDays.setText("û����Ϣ");
	}
				
	private String getToday(){
		//��ȡϵͳʱ��
		Time t = new Time();  
		t.setToNow(); 
		t.month++;
		String today = t.year + "��" + t.month + "��" + t.monthDay + "��";
		return today;
	}
	
}
