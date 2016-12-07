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
		//获取intent中的绑定bundle,进而获取当前用户名和习惯名
        currentUser = mIntent.getExtras().getString("currentUser");
		habitname = mIntent.getExtras().getString("habitname");
	}
	
	public void getUser(){ 
		mIntent = getIntent();
	    currentUser = mIntent.getExtras().getString("currentUser");
	}
	
	public void returnMainAct(Context context){
		//定义要传输到下一个activity的数据	
		data.putString("currentUser",currentUser);		
		intent.putExtras(data);
		intent.setClass(context, MainActivity.class);
		context.startActivity(intent);
	}
	
	public void gotoSignAct(Context context){
		//传递的intent
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
		context.startActivity(intent);//启动	
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
	
	//设置手机返回按钮触发的事件
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
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
        	this.finish();
        }
    }

    
}
