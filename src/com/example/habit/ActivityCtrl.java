package com.example.habit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

public class ActivityCtrl extends Activity{	
	
	public String currentUser;
	public String habitname;
	
	public Intent mIntent;
	private Bundle data = new Bundle();
	private Intent intent = new Intent();
	
	public void getUser_Habit(){
		mIntent = getIntent();
		//��ȡintent�еİ�bundle,������ȡ��ǰ�û�����ϰ����
        currentUser = mIntent.getExtras().getString("currentUser");
		habitname = mIntent.getExtras().getString("habitname");
	}
	
	public void getUser(){ 
		mIntent = getIntent();
	    currentUser = mIntent.getExtras().getString("currentUser");
	}
	
	public void returnMainAct(Context context){
		//����Ҫ���䵽��һ��activity������	
		data.putString("currentUser",currentUser);		
		intent.putExtras(data);
		intent.setClass(context, MainActivity.class);
		context.startActivity(intent);
	}
	
	public void gotoSignAct(Context context){
		//���ݵ�intent
    	data.putString("currentUser",currentUser);
		data.putString("habitname",habitname);

    	intent.putExtras(data);
        intent.setClass(context, SignActivity.class);
        context.startActivity(intent);
	}
	
	public void gotoSetAct(Context context){
		data.putString("currentUser",currentUser);
		data.putString("habitname",habitname);

		intent.putExtras(data);
	    intent.setClass(context, Habit_SettingActivity.class);				            
	    context.startActivity(intent);
	}
	
	
	public void gotoSettingAct(Context context){
		data.putString("currentUser",currentUser);
		data.putString("habitname",habitname);

		intent.putExtras(data);
	    intent.setClass(context, Sys_SettingActivity.class);				            
	    context.startActivity(intent);
	}
	
	public void gotoRegisterAct(Context context){
		intent.setClass(context,RegisterActivity.class);
		context.startActivity(intent);//����	
	}
	
	public void gotoLoginAct(Context context){
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
	}

	public void gotoAddAct(Context context){		
		data.putString("currentUser",currentUser);
		
		intent.putExtras(data);
        intent.setClass(context, AddActivity.class);
        context.startActivity(intent);
	}
	
	public void gotoTimerAct(Context context){
		data.putString("currentUser",currentUser);
		data.putString("habitname",habitname);
		intent.putExtras(data);
        intent.setClass(context, TimerActivity.class);
        context.startActivity(intent);
	}
	
	//�����ֻ����ذ�ť�������¼�
    private static boolean isExit = false;

    @SuppressLint("HandlerLeak") 
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
                    Toast.LENGTH_SHORT).show();
            // ����handler�ӳٷ��͸���״̬��Ϣ
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
        	this.finish();
        }
    }

    
}
