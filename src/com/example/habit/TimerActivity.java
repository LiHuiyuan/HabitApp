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
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//�Զ��������
        setContentView(R.layout.timer); 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.timertitle);//����feature
        chronometer = (Chronometer) findViewById(R.id.chronometer);   
        final EditText edtSetTime = (EditText) findViewById(R.id.edt_settime); 
       
        this.getUser_Habit();//��ȡ��һintent������Ϣ�����û�����ϰ��
        
    	try {
        	mp = MediaPlayer.create(this, R.raw.song);
			mp.prepare();
		  } catch (IllegalStateException e) {
			//�ж���Դ�Ƿ����
			if(this == null){
		  Toast.makeText(this,"���ֲ����ڣ�",Toast.LENGTH_SHORT).show();
			}
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
       
    	//
        Button btnStart =  (Button) findViewById(R.id.btnStart);   
        Button btnStop  =  (Button) findViewById(R.id.btnStop);   
        Button btnRest  =  (Button) findViewById(R.id.btnReset); 
       
        //������ʼ��ť
        btnStart.setOnClickListener(new View.OnClickListener() {   
        	  @Override  
             public void onClick(View v) {   
                String ss = edtSetTime.getText().toString();   
                if (!(ss.equals("") && ss != null)) {   
                    startTime = Integer.parseInt(edtSetTime.getText().toString());   
                    chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    	@Override
                    	public void onChronometerTick(Chronometer chronometer) {
                    	  // ���õ�  
                    	  if (SystemClock.elapsedRealtime()- chronometer.getBase() > startTime * 1000) {
                    		  chronometer.stop();
                    		  if(!mp.isPlaying()){
                    			  mp.start();
                    		  }
                              // ���û���ʾ   
                    		  showDialog();   
                    	  }   
                      }   
                    }); 
                }   
                // ���ÿ�ʼ��ʱʱ��   
                chronometer.setBase(SystemClock.elapsedRealtime());   
                // ��ʼ��ʱ   
                chronometer.start();   
               }   
            });   
                
        btnStop.setOnClickListener(new View.OnClickListener() {   
            @Override  
            public void onClick(View v) {   
            	// ֹͣ��ʱ
                chronometer.stop();   
            }   
  
        });   
         
        btnRest.setOnClickListener(new View.OnClickListener() {   
            @Override  
            public void onClick(View v) {   
            	 // ����   
                chronometer.setBase(SystemClock.elapsedRealtime());   
            }   
        });  
                          
      //��ť����������������
	    Button TimerBackBtn = (Button)findViewById(R.id.TimerBackBtn);
	    TimerBackBtn.setOnClickListener(new OnClickListener(){		
			//���ص�������
			public void onClick(View v) {
				returnMainAct(TimerActivity.this);
				(TimerActivity.this).finish();
			}
		});
        
    }   
  
    protected void showDialog() {   
        AlertDialog.Builder builder = new AlertDialog.Builder(this);   
        // builder.setIcon(R.drawable.eb28d25);   
        builder.setMessage("�趨��ʱ���Ѿ�����")   
       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {   
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

    
    
    
    
    
    
    
    
    


    

