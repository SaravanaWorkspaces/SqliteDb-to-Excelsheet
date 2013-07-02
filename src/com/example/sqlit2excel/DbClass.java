package com.example.sqlit2excel;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DbClass extends SQLiteOpenHelper 
{
	
	String table_name="table1";
	
	
	int i=0;

	Context con;
	public DbClass(Context context, String name, CursorFactory factory,int version) 
	{
		super(context, name, factory, version);
	
	}
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String createTab="create table "+ table_name +"(no text,name text)";
		db.execSQL(createTab);
		Log.i("CREATED","CREATED");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Log.i("DROPED","DROPED");
		db.execSQL("DROP TABLE IF EXISTS "+table_name);
		
	}
	public void insert()
	{
		Log.i("INSERT","INSERT");
		SQLiteDatabase db=this.getWritableDatabase();
		i++;
		String val1=String.valueOf(i);
		String insertQuery="insert into "+table_name+" values("+val1+",'k')";
		db.execSQL(insertQuery);
		
	}
	
	public ArrayList<String> getColumn()
	{
		ArrayList<String> aryColumn=new ArrayList<String>();
		SQLiteDatabase db=this.getReadableDatabase();
		//Getting Column Name
		Cursor ti = db.rawQuery("PRAGMA table_info("+table_name+")", null);
		if ( ti.moveToFirst() ) 
		{
		    do
		    {
		       Log.i("col:", "" + ti.getString(1));
		        aryColumn.add(ti.getString(1));
		    }
		    while (ti.moveToNext());
		}
		
		return aryColumn;
	}
	
	public Cursor export_allItems()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		String sql="select * from "+table_name;
		Cursor cur=db.rawQuery(sql, null);
		
		return cur;	
	
	}
}
