package com.example.habit;

import java.io.IOException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class TimerActivity extends ActivityCtrl {
	private int startTime = 0; 
	private Chronometer chronometer;
	MediaPlayer mp=new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//自定义标题栏
        setContentView(R.layout.timer); 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.timertitle);//设置feature
        chronometer = (Chronometer) findViewById(R.id.chronometer);   
        final EditText edtSetTime = (EditText) findViewById(R.id.edt_settime); 
       
        this.getUser_Habit();//获取上一intent传递信息——用户名和习惯
        
    	try {
        	mp = MediaPlayer.create(this, R.raw.song);
			mp.prepare();
		  } catch (IllegalStateException e) {
			//判断资源是否存在
			if(this == null){
		  Toast.makeText(this,"音乐不存在！",Toast.LENGTH_SHORT).show();
			}
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
       
    	//
        Button btnStart =  (Button) findViewById(R.id.btnStart);   
        Button btnStop  =  (Button) findViewById(R.id.btnStop);   
        Button btnRest  =  (Button) findViewById(R.id.btnReset); 
       
        //监听开始按钮
        btnStart.setOnClickListener(new View.OnClickListener() {   
        	  @Override  
             public void onClick(View v) {   
                String ss = edtSetTime.getText().toString();   
                if (!(ss.equals("") && ss != null)) {   
                    startTime = Integer.parseInt(edtSetTime.getText().toString());   
                    chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    	@Override
                    	public void onChronometerTick(Chronometer chronometer) {
                    	  // 设置的  
                    	  if (SystemClock.elapsedRealtime()- chronometer.getBase() > startTime * 1000) {
                    		  chronometer.stop();
                    		  if(!mp.isPlaying()){
                    			  mp.start();
                    		  }
                              // 给用户提示   
                    		  showDialog();   
                    	  }   
                      }   
                    }); 
                }   
                // 设置开始计时时间   
                chronometer.setBase(SystemClock.elapsedRealtime());   
                // 开始记时   
                chronometer.start();   
               }   
            });   
                
        btnStop.setOnClickListener(new View.OnClickListener() {   
            @Override  
            public void onClick(View v) {   
            	// 停止计时
                chronometer.stop();   
            }   
  
        });   
         
        btnRest.setOnClickListener(new View.OnClickListener() {   
            @Override  
            public void onClick(View v) {   
            	 // 重置   
                chronometer.setBase(SystemClock.elapsedRealtime());   
            }   
        });  
                          
      //按钮监听：返回主界面
	    Button TimerBackBtn = (Button)findViewById(R.id.TimerBackBtn);
	    TimerBackBtn.setOnClickListener(new OnClickListener(){		
			//返回到主界面
			public void onClick(View v) {
				returnMainAct(TimerActivity.this);
				(TimerActivity.this).finish();
			}
		});
        
    }   
  
    protected void showDialog() {   
        AlertDialog.Builder builder = new AlertDialog.Builder(this);   
        // builder.setIcon(R.drawable.eb28d25);   
        builder.setMessage("设定的时间已经到咯")   
       .setPositiveButton("确定", new DialogInterface.OnClickListener() {   
                    @Override  
        public void onClick(DialogInterface dialog, int which) {
                    	 mp.stop();
                    	 mp.release();
                    }   
                });   
  
        AlertDialog dialog = builder.create();   
        dialog.show();   
        }   
  
}

    
    
    
    
    
    
    
    
    


    

