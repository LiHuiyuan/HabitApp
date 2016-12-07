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
	private String[] HabitName = null;//用于同步在列表中显示的习惯名字
	private ArrayList<Map<String, Object>> mData = null;//用于绑定列表显示数据
	
    private Habit habit;//habit类，用于获取所有习惯以及删除习惯时更新数据库

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);//启用窗体的扩展特性：自定义标题栏
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);//设置feature
        
        //获取上一个intent传递的信息：用户名
        this.getUser();
        habit = new Habit(context,currentUser);
         
        //初始化listView并展示
        listView = (ListView) findViewById(R.id.HabitList);
        showListView();
        
        //listView注册一个元素单击事件监听器  
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {            	
        	@Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) { 
        		//获取点击的习惯名字
        		 habitname = HabitName[position];  
            	gotoSignAct(MainActivity.this);
            	(MainActivity.this).finish();
        	}
        });

        //listView注册一个元素长按事件监听器
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
    		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
    			//获得选中的习惯     
				habitname = HabitName[arg2]; 
				//对话框
    			new AlertDialog.Builder(MainActivity.this)
    			.setPositiveButton("删除习惯",new DialogInterface.OnClickListener() {
    				 @Override
    				 public void onClick(DialogInterface arg0, int arg1) { 
    					 new AlertDialog.Builder(MainActivity.this).setTitle("删除习惯")
    					 .setMessage("确定删除该习惯？")
    					 .setPositiveButton("是", new DialogInterface.OnClickListener() {
    						 @Override
    						 public void onClick(DialogInterface arg0, int arg1) {
    							 //确定删除之后从数据库中删除该习惯并刷新列表
    							 habit.deleteHabit(habitname);
    							 showListView();
    							 }	
    						 }).setNegativeButton("否", null).show();
    					 }			 
    				 })
    			.setNeutralButton("习惯设置", new DialogInterface.OnClickListener() {
    				@Override
    				public void onClick(DialogInterface arg0, int arg1) {
    					gotoSetAct(MainActivity.this);
    				    (MainActivity.this).finish();    
    				}   
    			}).show();
    
    			return true;	
			}    	
        });
         
        //按钮监听——添加习惯
        Button AddBtn = (Button)findViewById(R.id.AddBtn);
        AddBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				gotoAddAct(MainActivity.this);
	            (MainActivity.this).finish();
			}
        });
        
        //按钮监听——（系统）设置
        Button SettingBtn = (Button)findViewById(R.id.SettingBtn);
        SettingBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//转到系统设置界面
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
    
    //动态显示listview
    private void showListView(){ 
    	mData = new ArrayList<Map<String,Object>>();
    	HabitName = habit.getAllHabit(mData);
   
    	SimpleAdapter adapter = new SimpleAdapter(context,mData,
				android.R.layout.simple_list_item_2,
				new String[] {"title","text"},
				new int[]{android.R.id.text1,android.R.id.text2});
    	listView.setAdapter(adapter);
    	if(HabitName == null ){	
    		Toast.makeText(context,"还没有习惯噢",Toast.LENGTH_LONG).show();
    		TextView empty = (TextView) findViewById(R.id.empty);  
    		empty.setText("没有习惯噢！");
    		listView.setEmptyView(findViewById(R.id.empty));
		}  
        Log.w("setListView","ok");
    }

}


