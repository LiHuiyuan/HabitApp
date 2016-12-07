package com.example.habit;

import java.util.ArrayList;
import java.util.Map;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActivityCtrl {
	Context context = this;
	
	ListView listView = null;
	private String[] HabitName = null;//����ͬ�����б�����ʾ��ϰ������
	private ArrayList<Map<String, Object>> mData = null;//���ڰ��б���ʾ����
	
    private Habit habit;//habit�࣬���ڻ�ȡ����ϰ���Լ�ɾ��ϰ��ʱ�������ݿ�

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//���ô������չ���ԣ��Զ��������
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);//����feature
        
        //��ȡ��һ��intent���ݵ���Ϣ���û���
        this.getUser();
        habit = new Habit(context,currentUser);
         
        //��ʼ��listView��չʾ
        listView = (ListView) findViewById(R.id.HabitList);
        showListView();
        
        //listViewע��һ��Ԫ�ص����¼�������  
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {            	
        	@Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) { 
        		//��ȡ�����ϰ������
        		 habitname = HabitName[position];  
            	gotoSignAct(MainActivity.this);
            	(MainActivity.this).finish();
        	}
        });

        //listViewע��һ��Ԫ�س����¼�������
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
    		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
    			//���ѡ�е�ϰ��     
				habitname = HabitName[arg2]; 
				//�Ի���
    			new AlertDialog.Builder(MainActivity.this)
    			.setPositiveButton("ɾ��ϰ��",new DialogInterface.OnClickListener() {
    				 @Override
    				 public void onClick(DialogInterface arg0, int arg1) { 
    					 new AlertDialog.Builder(MainActivity.this).setTitle("ɾ��ϰ��")
    					 .setMessage("ȷ��ɾ����ϰ�ߣ�")
    					 .setPositiveButton("��", new DialogInterface.OnClickListener() {
    						 @Override
    						 public void onClick(DialogInterface arg0, int arg1) {
    							 //ȷ��ɾ��֮������ݿ���ɾ����ϰ�߲�ˢ���б�
    							 habit.deleteHabit(habitname);
    							 showListView();
    							 }	
    						 }).setNegativeButton("��", null).show();
    					 }			 
    				 })
    			.setNeutralButton("ϰ������", new DialogInterface.OnClickListener() {
    				@Override
    				public void onClick(DialogInterface arg0, int arg1) {
    					gotoSetAct(MainActivity.this);
    				    (MainActivity.this).finish();    
    				}   
    			}).show();
    
    			return true;	
			}    	
        });
         
        //��ť�����������ϰ��
        Button AddBtn = (Button)findViewById(R.id.AddBtn);
        AddBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				gotoAddAct(MainActivity.this);
	            (MainActivity.this).finish();
			}
        });
        
        //��ť����������ϵͳ������
        Button SettingBtn = (Button)findViewById(R.id.SettingBtn);
        SettingBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//ת��ϵͳ���ý���
				gotoSettingAct(MainActivity.this);
	            (MainActivity.this).finish();
			}
        });
        
    } 
	
	 @Override  
	    public boolean onCreateOptionsMenu(Menu menu) {  
	        // Inflate the menu; this adds items to the action bar if it is present.  
	        getMenuInflater().inflate(R.menu.main, menu);  
	        return true;  
	    }
    
    //��̬��ʾlistview
    private void showListView(){ 
    	mData = new ArrayList<Map<String,Object>>();
    	HabitName = habit.getAllHabit(mData);
   
    	SimpleAdapter adapter = new SimpleAdapter(context,mData,
				android.R.layout.simple_list_item_2,
				new String[] {"title","text"},
				new int[]{android.R.id.text1,android.R.id.text2});
    	listView.setAdapter(adapter);
    	if(HabitName == null ){	
    		Toast.makeText(context,"��û��ϰ����",Toast.LENGTH_LONG).show();
    		TextView empty = (TextView) findViewById(R.id.empty);  
    		empty.setText("û��ϰ���ޣ�");
    		listView.setEmptyView(findViewById(R.id.empty));
		}  
        Log.w("setListView","ok");
    }

}


