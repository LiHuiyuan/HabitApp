package com.example.habit;

import java.sql.Date;
import java.util.Calendar;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.View.OnClickListener;
import android.app.AlarmManager; 
import android.app.PendingIntent;
import android.widget.Toast;

public class Habit_SettingActivity extends ActivityCtrl {
	Calendar calendar;
	private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String now;
    
    Habit habit;
    
    private ImageView imageView=null;
    private ToggleButton toggleButton=null;
	/*********************************************************/
    Context context = this;
    
    
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//启用窗体的扩展特性：自定义标题栏
		setContentView(R.layout.set);//当前为习惯设置界面
		Log.w("main","1");
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.settitle);//设置feature
		
		this.getUser_Habit();//获取上一intent传递信息——用户名和习惯
		habit = new Habit(context,currentUser);
	    habit.sethabitname(habitname); 
		Log.w("habitname","str"+habitname);
		final EditText ShowRemindDate = (EditText)findViewById(R.id.ShowRemindDate);
		final EditText ShowRemindTime = (EditText)findViewById(R.id.ShowRemindTime);
		
		Time t = new Time();  
		t.setToNow(); 
		String now = t.hour+":"+t.minute;
		
		toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        //注册一个事件监听器
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                toggleButton.setChecked(isChecked);
                if(isChecked){
                	habit.if_remind = 1;
                }else{
                	habit.if_remind = 0;
                }
                //imageView.setImageResource(isChecked?R.drawable.bulb_on:R.drawable.bulb_off);
            }

        });
        
		//初始化Calendar对象
		calendar = Calendar.getInstance();
		
		Date date=new Date(System.currentTimeMillis()); //获取当前日期Date对象
		calendar.setTime(date);//为Calendar对象设置日期和时间为当前日期和时间
		Log.w("date","str"+date);
        year=calendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=calendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=calendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
       
        hour=calendar.get(Calendar.HOUR_OF_DAY); //获取Calendar对象中的时
        minute=calendar.get(Calendar.MINUTE); //获取Calendar对象中的分
        

      //显示当前的年月日
        ShowRemindDate.setText(new StringBuilder()
        	.append(year)
        	.append("-")  
	        .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
	        .append("-")  
	        .append((day < 10) ? "0" + day : day));
      //显示当前的时分
        ShowRemindTime.setText(new StringBuilder()
        	.append((hour < 10 ) ?  "0" + hour : hour)
        	.append(":")  
        	.append((minute < 10) ? "0" + minute : minute));//显示当前的时分
		
		Button RemindDateBtn = (Button)findViewById(R.id.RemindDateBtn);
		RemindDateBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(Habit_SettingActivity.this,
						new DatePickerDialog.OnDateSetListener(){
					public void onDateSet(DatePicker view,int myyear,int monthOfYear,int dayOfMonth){
						//设置日期
						//修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
			            year=myyear;
			            month=monthOfYear;
			            day=dayOfMonth;
			            //更新日期
			            
			            habit.remindcycle = year+"年"+(month+1)+"月"+day+"日";
			            
			            updateDate();
					}
					 //当DatePickerDialog关闭时，更新日期显示
					private void updateDate() {
						//在EditText上显示日期
						ShowRemindDate.setText(new StringBuilder().append(year).append("-")  
					               .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1)).append("-")  
					               .append((day < 10) ? "0" + day : day));
					}
				}
				,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH )).show();
			}
			
		});
		
		Button RemindTimeBtn = (Button)findViewById(R.id.RemindTimeBtn);
		RemindTimeBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerDialog(Habit_SettingActivity.this,
						new TimePickerDialog.OnTimeSetListener(){
					public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
						// TODO Auto-generated method stub
						//设置时间
						//修改hour、minute的变量值，以便以后单击按钮时，TimePickerDialog上显示上一次修改后的值
			            hour=hourOfDay;
			            minute=minuteOfHour;
		
			            //更新时间
			            
			            habit.remindtime = hour+":"+minute;

			            updateTime();
					}

					private void updateTime() {
						// TODO Auto-generated method stub
						//在EditText上显示时间
						ShowRemindTime.setText(new StringBuilder()
					               .append((hour < 10 ) ?  "0" + hour : hour)
					               .append(":")  
					               .append((minute < 10) ? "0" + minute : minute));
					}
				}
				,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
			}
			
		});
		
		Button SaveBtn = (Button)findViewById(R.id.MainSetSaveBtn);
		SaveBtn.setOnClickListener(new OnClickListener(){
			//返回习惯主界面，在数据库中插入新增习惯信息
			@Override
			public void onClick(View v) {
				//new一个habit对象，调用habit类设置提醒
				//Habit habit = new Habit(context,currentUser);
				habit.EditHabit(habitname,habit.if_remind,habit.remindcycle,habit.remindtime);
				
				if(habit.getHabit().getInt(3) == 1){
					Reminder();
				}
				if(habit.getHabit().getInt(3) == 0){
					cancleRmd();
				}			
				returnMainAct(Habit_SettingActivity.this);
				(Habit_SettingActivity.this).finish();
			}
			
		});
		
		
		
		Button CancelSetBtn = (Button)findViewById(R.id.CancelSetBtn);
		CancelSetBtn.setOnClickListener(new OnClickListener(){
			//返回习惯主界面，取消习惯设置
			@Override
			public void onClick(View arg0) {
				returnMainAct(Habit_SettingActivity.this);
				(Habit_SettingActivity.this).finish();
			}
			
		});
		
	}
	
	private String getNow(){
		//获取系统时间
		Time t = new Time();  
		t.setToNow(); 
		String now = t.hour+":"+t.minute;
		return now;
	}
	
	public class AlarmReceiver extends BroadcastReceiver {

		 @Override
		 public void onReceive(Context context, Intent intent) {
		  // TODO Auto-generated method stub
		  Toast.makeText(context, "请完成今天的任务", Toast.LENGTH_LONG).show();
		 }

		}
	
	public void Reminder(){
		Intent intent = new Intent(Habit_SettingActivity.this, AlarmReceiver.class); 
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(Habit_SettingActivity.this, 0, intent, 0);
		
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE); 
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (10 * 1000),(24 * 60 * 60 * 1000), pendingIntent);
            Toast.makeText(context, "提醒开启", Toast.LENGTH_LONG).show();
		
	}
	
	public void cancleRmd(){
		Intent intent = new Intent(Habit_SettingActivity.this, AlarmReceiver.class); 
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(Habit_SettingActivity.this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE); 
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "提醒关闭", Toast.LENGTH_LONG).show();
		
	}
	

}

