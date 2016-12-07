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
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//���ô������չ���ԣ��Զ��������
		setContentView(R.layout.set);//��ǰΪϰ�����ý���
		Log.w("main","1");
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.settitle);//����feature
		
		this.getUser_Habit();//��ȡ��һintent������Ϣ�����û�����ϰ��
		habit = new Habit(context,currentUser);
	    habit.sethabitname(habitname); 
		Log.w("habitname","str"+habitname);
		final EditText ShowRemindDate = (EditText)findViewById(R.id.ShowRemindDate);
		final EditText ShowRemindTime = (EditText)findViewById(R.id.ShowRemindTime);
		
		Time t = new Time();  
		t.setToNow(); 
		String now = t.hour+":"+t.minute;
		
		toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        //ע��һ���¼�������
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
        
		//��ʼ��Calendar����
		calendar = Calendar.getInstance();
		
		Date date=new Date(System.currentTimeMillis()); //��ȡ��ǰ����Date����
		calendar.setTime(date);//ΪCalendar�����������ں�ʱ��Ϊ��ǰ���ں�ʱ��
		Log.w("date","str"+date);
        year=calendar.get(Calendar.YEAR); //��ȡCalendar�����е���
        month=calendar.get(Calendar.MONTH);//��ȡCalendar�����е���
        day=calendar.get(Calendar.DAY_OF_MONTH);//��ȡ����µĵڼ���
       
        hour=calendar.get(Calendar.HOUR_OF_DAY); //��ȡCalendar�����е�ʱ
        minute=calendar.get(Calendar.MINUTE); //��ȡCalendar�����еķ�
        

      //��ʾ��ǰ��������
        ShowRemindDate.setText(new StringBuilder()
        	.append(year)
        	.append("-")  
	        .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
	        .append("-")  
	        .append((day < 10) ? "0" + day : day));
      //��ʾ��ǰ��ʱ��
        ShowRemindTime.setText(new StringBuilder()
        	.append((hour < 10 ) ?  "0" + hour : hour)
        	.append(":")  
        	.append((minute < 10) ? "0" + minute : minute));//��ʾ��ǰ��ʱ��
		
		Button RemindDateBtn = (Button)findViewById(R.id.RemindDateBtn);
		RemindDateBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(Habit_SettingActivity.this,
						new DatePickerDialog.OnDateSetListener(){
					public void onDateSet(DatePicker view,int myyear,int monthOfYear,int dayOfMonth){
						//��������
						//�޸�year��month��day�ı���ֵ���Ա��Ժ󵥻���ťʱ��DatePickerDialog����ʾ��һ���޸ĺ��ֵ
			            year=myyear;
			            month=monthOfYear;
			            day=dayOfMonth;
			            //��������
			            
			            habit.remindcycle = year+"��"+(month+1)+"��"+day+"��";
			            
			            updateDate();
					}
					 //��DatePickerDialog�ر�ʱ������������ʾ
					private void updateDate() {
						//��EditText����ʾ����
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
						//����ʱ��
						//�޸�hour��minute�ı���ֵ���Ա��Ժ󵥻���ťʱ��TimePickerDialog����ʾ��һ���޸ĺ��ֵ
			            hour=hourOfDay;
			            minute=minuteOfHour;
		
			            //����ʱ��
			            
			            habit.remindtime = hour+":"+minute;

			            updateTime();
					}

					private void updateTime() {
						// TODO Auto-generated method stub
						//��EditText����ʾʱ��
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
			//����ϰ�������棬�����ݿ��в�������ϰ����Ϣ
			@Override
			public void onClick(View v) {
				//newһ��habit���󣬵���habit����������
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
			//����ϰ�������棬ȡ��ϰ������
			@Override
			public void onClick(View arg0) {
				returnMainAct(Habit_SettingActivity.this);
				(Habit_SettingActivity.this).finish();
			}
			
		});
		
	}
	
	private String getNow(){
		//��ȡϵͳʱ��
		Time t = new Time();  
		t.setToNow(); 
		String now = t.hour+":"+t.minute;
		return now;
	}
	
	public class AlarmReceiver extends BroadcastReceiver {

		 @Override
		 public void onReceive(Context context, Intent intent) {
		  // TODO Auto-generated method stub
		  Toast.makeText(context, "����ɽ��������", Toast.LENGTH_LONG).show();
		 }

		}
	
	public void Reminder(){
		Intent intent = new Intent(Habit_SettingActivity.this, AlarmReceiver.class); 
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(Habit_SettingActivity.this, 0, intent, 0);
		
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE); 
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (10 * 1000),(24 * 60 * 60 * 1000), pendingIntent);
            Toast.makeText(context, "���ѿ���", Toast.LENGTH_LONG).show();
		
	}
	
	public void cancleRmd(){
		Intent intent = new Intent(Habit_SettingActivity.this, AlarmReceiver.class); 
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(Habit_SettingActivity.this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE); 
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "���ѹر�", Toast.LENGTH_LONG).show();
		
	}
	

}

