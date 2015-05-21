package com.teste.buscacep;

import android.content.SharedPreferences;

public class StorageAppData 
{
	private MainActivity mContext;
	private SharedPreferences settings;
	
	public StorageAppData(MainActivity context)
	{
		mContext = context;
	}
	
	public void openFile(String archive)
	{
		settings = mContext.getSharedPreferences(archive, 0);
	}
	
	public void writeBoolean(String key, Boolean value)
	{
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value); 
		editor.commit();
	}
	
	public void writeString(String key, String value)
	{
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public void writeInteger(String key, int value)
	{
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public boolean readBooleanValue(String key)
	{
		return settings.getBoolean(key,false);
	}
	
	public String readStringValue(String key)
	{
		return settings.getString(key, "");
	}
	
	public int readIntegerValue(String key)
	{
		return settings.getInt(key, -1);
	}	
}