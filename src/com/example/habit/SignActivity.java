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
    
    private String today;//系统时间
    private String last_signDate;//当前习惯最后一次签到时间
    
    private Habit habit;
    
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//自定义标题栏
		setContentView(R.layout.sign);//当前为签到界面
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.signtitle);//设置feature
	    
        this.getUser_Habit();//获取上一intent传递信息——用户名和习惯
        
	    habit = new Habit(context,currentUser);
	    habit.sethabitname(habitname); 
		
		//显示内容——习惯名称
		TextView currenthabit=(TextView)findViewById(R.id.textView1);
		currenthabit.setText(habitname);
		//设置显示内容——今日签到情况
		showTextView();
		today = getToday();

	    //按钮监听：返回主界面
	    Button SignBackBtn = (Button)findViewById(R.id.SignBackBtn);
		SignBackBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				returnMainAct(SignActivity.this);
				(SignActivity.this).finish();
			}
		});
				
		//按钮监听：签到并更新签到信息
		Button SignBtn = (Button)findViewById(R.id.SignBtn);
		SignBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(today.equals(last_signDate)){
					Toast.makeText( SignActivity.this , "今日已签到过了！", 
							Toast.LENGTH_SHORT).show();  
				}  else  { 
					habit.updateSign(today);  //更新数据库
					showTextView();	//更新textView
					Toast.makeText( SignActivity.this , "签到成功！", 
						    		Toast.LENGTH_SHORT).show();
				}
			}	
		});
	
		//点击计时器按钮
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
		 //获取习惯最后签到时间
		last_signDate = habit.getHabit_SignTime();
		//显示内容——已签到天数
		String countSign = habit.getSignDay();
	    TextView SignDays = (TextView)findViewById(R.id.SignDays); 
	    if(countSign != null)	SignDays.setText(countSign);
	    else SignDays.setText("没有信息");
	}
				
	private String getToday(){
		//获取系统时间
		Time t = new Time();  
		t.setToNow(); 
		t.month++;
		String today = t.year + "年" + t.month + "月" + t.monthDay + "日";
		return today;
	}
	
}
