package com.example.habit;

import java.util.concurrent.atomic.AtomicInteger;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


@SuppressLint("Instantiatable")
public class DBAdapter {	  
    /** 数据库名称 ,数据库版本号，数据库表名**/
    public static final String DATABASE_NAME = "habitApp.db";
    private static final int DATABASE_VERSION = 2;
    private String HABIT_TABLE_NAME;
    private static final String USER_TABLE_NAME = "USER";
    
    /**数据库SQL语句 添加表**/
	private static  String HABIT_TABLE_CREATE;
    private static final String USER_TABLE_CREATE = "create table IF NOT EXISTS "
    			+USER_TABLE_NAME
    			+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,name Text UNIQUE not null,"
    			+ "static Text default 0,password Text not null);";

	
	final Context context;
	private static DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private AtomicInteger mOpenCounter = new AtomicInteger();//引用计数判断是否有线程在使用数据库
	
	public DBAdapter(Context context,String currentUser){
		this.context = context;
		if(currentUser != null) this.HABIT_TABLE_NAME = "HABIT_"+ currentUser;
		DBHelper = new DatabaseHelper(context);
	}
	
	private class DatabaseHelper extends SQLiteOpenHelper {

	    DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    
	    @Override
	    public void onCreate(SQLiteDatabase db) {  	  
		/**创建数据库并向数据库中添加用户表**/
	    	try{
	    		db.execSQL(USER_TABLE_CREATE); 	
	    	} catch(SQLException e){
	    		e.printStackTrace();
	    	}	
	    }

	    
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/**可以拿到当前数据库的版本信息 与之前数据库的版本信息   用来更新数据库**/ 
	    	db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
	    	db.execSQL("DROP TABLE IF EXISTS "+HABIT_TABLE_NAME);
	    	onCreate(db);
	    }

	    /** 删除数据库 **/
	    @SuppressLint("Instantiatable")
		public boolean deleteDatabase(Context context) {
	    	return context.deleteDatabase(DATABASE_NAME);
	    }
	}

	/**打开数据库**/
	@SuppressLint("Instantiatable")
	public DBAdapter open() throws SQLException {	
		db = DBHelper.getWritableDatabase();
		Log.w("openSQL","database version "+DATABASE_VERSION+" name: "+HABIT_TABLE_NAME);
		return this;
	}
	
	/**关闭数据库**/
	public void close(){
		 if(mOpenCounter.decrementAndGet() == 0) {//判断是否有线程在使用这个数据库
			 DBHelper.close();
		 }
	}
	
	/**删除数据库**/
	public void delete(){	
		this.open();
		DBHelper.close();
		DBHelper.deleteDatabase(context);
	}
	
	/**根据用户名查找该用户的习惯表，不存在则创建**/
    private boolean getInstanceTable(String userName){
    	HABIT_TABLE_NAME = "HABIT_" + userName;//该用户的习惯表名
		HABIT_TABLE_CREATE = "create table IF NOT EXISTS "
					+HABIT_TABLE_NAME
		    		+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
		    		+"name Text UNIQUE not null,signcount Text default 0,"
		    		+"if_remind INTEGER default 0,sign_lastdate Text default null,"
		    		+"remindcycle Text default null,remindtime Text default null);";
		
		//建立属于该用户的习惯存储表
    	try{
			db.execSQL(HABIT_TABLE_CREATE);
			return true;
    	} catch(SQLException e){
    		e.printStackTrace();
    	}
    	return false;
    }
	
	/**往习惯表中插入习惯数据**/
    public boolean insertHabit(Habit habit) {
    	this.open();

    	ContentValues cv = new ContentValues();
    	cv.put("name",habit.name);
    	cv.put("if_remind",habit.if_remind);	
    	
    	if(getInstanceTable(habit.currentUser)){//查找习惯表
    		db.insert(HABIT_TABLE_NAME,null,cv);//插入新习惯	
    		this.close();
    		return true;
    	}
    	this.close();
    	return false;
    	
	}
    
    /**往用户表中插入数据，并为该用户创建习惯表**/
    public long insertUser(User user){
    	this.open();
    	
    	ContentValues cv = new ContentValues();
    	cv.put("name",user.userName);
    	cv.put("password",user.password);
    	
    	long id = db.insert(USER_TABLE_NAME,null,cv);
    	getInstanceTable(user.userName);
    	this.close();
		return id;
    }
    
    /**根据列名更新某表中信息**/
    public boolean update(String tableName,String whereArgs,String updateColumn,String value){
    	this.open();
    	long id;
    	
    	ContentValues cv = new ContentValues();
    	cv.put(updateColumn,value);//修改字段
    	id = db.update(getTable(tableName),cv,"name=?",new String[] {whereArgs});
    	
    	this.close();
		return id>0;  	
    }
    
    /**删除某表中某条信息**/
    public boolean deleteData(String tableName,String whereClause,String whereArgs){
    	this.open();
    	long id = db.delete(getTable(tableName),whereClause+"=?",new String[] {whereArgs});
    	this.close();
    	return id>0;
    }
    
    /**删除整个表**/
    public void deletetable(String tableName){
    	this.open();
    	String sql = "DROP TABLE "+getTable(tableName);
    	db.execSQL(sql);
    	this.close();
    }
    
    /**获取习惯表中所有信息**/
    public Cursor getAllHabit(){
    	this.open();
    	Cursor mCursor;
    	try{
    		mCursor= db.query(HABIT_TABLE_NAME,null,null,null,null,null,null);
    	}catch(SQLException e){
    		mCursor = null;
    		e.printStackTrace();
    	}  	
    	this.close();
    	return mCursor;
    }
    
    /**获取某表中某一条信息**/
    public Cursor getData(String tableName,String columnName,String Args) throws SQLException{
    	this.open();
    	Cursor mCursor = null;
    			  
    	try{
    		String[] selectionArgs = new String[] {Args};
        	mCursor = db.query(getTable(tableName),null,
        			columnName+"=?",selectionArgs,null,null,null);
    	}catch(SQLException e){ 
    		mCursor = null;
    	}  			    	
    	if(mCursor != null) {
    		mCursor.moveToFirst();
    	} else mCursor = null;   		
    	this.close();	
    	return mCursor;
    }
    
    /**获得正确的表名**/
    private String getTable(String tableName){
    	if(tableName == "User"||tableName =="USER"||tableName=="user") 
    		tableName = USER_TABLE_NAME;
    	else if(tableName == "Habit"||tableName =="HABIT"||tableName=="habit") 
    		tableName = HABIT_TABLE_NAME;
		return tableName;
    } 
    
    /**当前列表是否有用户**/
    boolean getUserCount(){
    	this.open();
    	Cursor c = null;
    	try{
    		c = db.query(USER_TABLE_NAME,null,null,null,null,null,null);
    	}catch(SQLException e){
    		e.printStackTrace();
    		return false;
    	} 
    	this.close();
    	if(c.getCount()>0) return true;    	
    	return false;
    }
    
}
