package com.example.habit;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

@SuppressLint("Instantiatable")
public class Habit{
	Context context;
	private DBAdapter DB;
	
	public String currentUser;
	public String name;
	public int if_remind;
	@SuppressLint("Instantiatable")
	public String remindcycle;
	public String remindtime;
	public String lastSigntime;
	
	@SuppressLint("Instantiatable")
	public Habit(Context c,String currentUser){
		context = c;
		this.currentUser = currentUser;
		this.name = null;
		this.if_remind = 0;
		remindcycle = null;
		remindtime = null;
		DB = new DBAdapter(context,this.currentUser);
	}
	
	public void sethabitname(String name){
		this.name = name;
	}
	
	public boolean CreateHabit(String name) {
		this.name = name;
		if(getHabit() == null){		
			return DB.insertHabit(this);
		}
		return false;	
	}
	
	public void EditHabit(String habitname,int if_remind,String remindcycle,String remindtime){
		//�޸ĸ�ϰ�ߣ��µ����֣��Ƿ��������ѣ��������ڣ�����ʱ��
		this.if_remind = if_remind;	
		this.remindcycle = remindcycle;
		this.remindtime = remindtime;
		DB.update("habit",habitname,"if_remind",""+if_remind);
		DB.update("habit",habitname,"remindcycle",remindcycle);
		DB.update("habit",habitname,"remindtime",remindtime);
	}
	
	//����ǩ����Ϣ���������һ��ǩ������
	public void updateSign(String SignTime){
		this.lastSigntime = SignTime;
		int a = Integer.parseInt(DB.getData("habit","name",name).getString(2));
		a++;
		DB.update("habit",name,"signcount",""+a);
		DB.update("habit",name,"sign_lastdate",SignTime);
	}
	
	public void deleteHabit(String habitname){
		DB.deleteData("habit","name",habitname);
	}
	
	public String[] getAllHabit(ArrayList<Map<String,Object>> mData){
		
		Cursor c = DB.getAllHabit();
		String[] habit = new String[c.getCount()];
		int i = 0;
		if(c.moveToFirst()){		
    		do{	
    			HashMap<String,Object> item = new HashMap<String,Object>();
    			item.put("title",c.getString(1));//�����ݿ��ȡϰ����
    			item.put("text","��ǩ��"+c.getString(2)+"��");
    			mData.add(item);//�������
    			
    			habit[i++] = c.getString(1);
    		}while(c.moveToNext());	
		} else {
			habit = null;
		}
		return habit;
	}
	
	public Cursor getHabit(){
		Cursor c = DB.getData("habit","name",this.name);
		if(c.moveToFirst()){
			return c;
		}
		return null;
	}
	
	public String getHabit_SignTime(){
		Cursor c = DB.getData("habit","name",this.name);
		if(c.moveToFirst()){
			return c.getString(4);
		}
		return null;
	}

	public String getSignDay(){
		return getHabit().getString(2);
	}
	
	public void Reminder(){
		
	}
}
