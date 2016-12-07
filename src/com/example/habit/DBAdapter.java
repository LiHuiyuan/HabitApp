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
    /** ���ݿ����� ,���ݿ�汾�ţ����ݿ����**/
    public static final String DATABASE_NAME = "habitApp.db";
    private static final int DATABASE_VERSION = 2;
    private String HABIT_TABLE_NAME;
    private static final String USER_TABLE_NAME = "USER";
    
    /**���ݿ�SQL��� ��ӱ�**/
	private static  String HABIT_TABLE_CREATE;
    private static final String USER_TABLE_CREATE = "create table IF NOT EXISTS "
    			+USER_TABLE_NAME
    			+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,name Text UNIQUE not null,"
    			+ "static Text default 0,password Text not null);";

	
	final Context context;
	private static DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private AtomicInteger mOpenCounter = new AtomicInteger();//���ü����ж��Ƿ����߳���ʹ�����ݿ�
	
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
		/**�������ݿⲢ�����ݿ�������û���**/
	    	try{
	    		db.execSQL(USER_TABLE_CREATE); 	
	    	} catch(SQLException e){
	    		e.printStackTrace();
	    	}	
	    }

	    
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/**�����õ���ǰ���ݿ�İ汾��Ϣ ��֮ǰ���ݿ�İ汾��Ϣ   �����������ݿ�**/ 
	    	db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
	    	db.execSQL("DROP TABLE IF EXISTS "+HABIT_TABLE_NAME);
	    	onCreate(db);
	    }

	    /** ɾ�����ݿ� **/
	    @SuppressLint("Instantiatable")
		public boolean deleteDatabase(Context context) {
	    	return context.deleteDatabase(DATABASE_NAME);
	    }
	}

	/**�����ݿ�**/
	@SuppressLint("Instantiatable")
	public DBAdapter open() throws SQLException {	
		db = DBHelper.getWritableDatabase();
		Log.w("openSQL","database version "+DATABASE_VERSION+" name: "+HABIT_TABLE_NAME);
		return this;
	}
	
	/**�ر����ݿ�**/
	public void close(){
		 if(mOpenCounter.decrementAndGet() == 0) {//�ж��Ƿ����߳���ʹ��������ݿ�
			 DBHelper.close();
		 }
	}
	
	/**ɾ�����ݿ�**/
	public void delete(){	
		this.open();
		DBHelper.close();
		DBHelper.deleteDatabase(context);
	}
	
	/**�����û������Ҹ��û���ϰ�߱��������򴴽�**/
    private boolean getInstanceTable(String userName){
    	HABIT_TABLE_NAME = "HABIT_" + userName;//���û���ϰ�߱���
		HABIT_TABLE_CREATE = "create table IF NOT EXISTS "
					+HABIT_TABLE_NAME
		    		+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
		    		+"name Text UNIQUE not null,signcount Text default 0,"
		    		+"if_remind INTEGER default 0,sign_lastdate Text default null,"
		    		+"remindcycle Text default null,remindtime Text default null);";
		
		//�������ڸ��û���ϰ�ߴ洢��
    	try{
			db.execSQL(HABIT_TABLE_CREATE);
			return true;
    	} catch(SQLException e){
    		e.printStackTrace();
    	}
    	return false;
    }
	
	/**��ϰ�߱��в���ϰ������**/
    public boolean insertHabit(Habit habit) {
    	this.open();

    	ContentValues cv = new ContentValues();
    	cv.put("name",habit.name);
    	cv.put("if_remind",habit.if_remind);	
    	
    	if(getInstanceTable(habit.currentUser)){//����ϰ�߱�
    		db.insert(HABIT_TABLE_NAME,null,cv);//������ϰ��	
    		this.close();
    		return true;
    	}
    	this.close();
    	return false;
    	
	}
    
    /**���û����в������ݣ���Ϊ���û�����ϰ�߱�**/
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
    
    /**������������ĳ������Ϣ**/
    public boolean update(String tableName,String whereArgs,String updateColumn,String value){
    	this.open();
    	long id;
    	
    	ContentValues cv = new ContentValues();
    	cv.put(updateColumn,value);//�޸��ֶ�
    	id = db.update(getTable(tableName),cv,"name=?",new String[] {whereArgs});
    	
    	this.close();
		return id>0;  	
    }
    
    /**ɾ��ĳ����ĳ����Ϣ**/
    public boolean deleteData(String tableName,String whereClause,String whereArgs){
    	this.open();
    	long id = db.delete(getTable(tableName),whereClause+"=?",new String[] {whereArgs});
    	this.close();
    	return id>0;
    }
    
    /**ɾ��������**/
    public void deletetable(String tableName){
    	this.open();
    	String sql = "DROP TABLE "+getTable(tableName);
    	db.execSQL(sql);
    	this.close();
    }
    
    /**��ȡϰ�߱���������Ϣ**/
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
    
    /**��ȡĳ����ĳһ����Ϣ**/
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
    
    /**�����ȷ�ı���**/
    private String getTable(String tableName){
    	if(tableName == "User"||tableName =="USER"||tableName=="user") 
    		tableName = USER_TABLE_NAME;
    	else if(tableName == "Habit"||tableName =="HABIT"||tableName=="habit") 
    		tableName = HABIT_TABLE_NAME;
		return tableName;
    } 
    
    /**��ǰ�б��Ƿ����û�**/
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
