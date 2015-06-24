package com.exenta.timesheet;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SessionDateMaintain 
{
	private SharedPreferences pref;
	private Editor editor;
	private Context _context;
	
	int private_Mode=0;
	
	//SharePreference FileName
	public static final String PREF_NAME = "EXENTA";
	// All Shared Preferences Keys
	public static final String IS_LOGIN = "IsLoggedIn";
	
	//EmpDetails Available
	public static final String IS_EMP_DETAILS_AVAIL = "IsAvail";
	
	
	//Timeentry
	public static String SYSTEM_DATE="System_date";
	public static String SYSTEM_TIME="System_time";
	public static final String IS_TIMEENTRY_DETAILS_AVAIL = "IsAvail";
	
			
	@SuppressLint("CommitPrefEdits") public SessionDateMaintain(Context ctx)
	{
		this._context = ctx;
		pref  = _context.getSharedPreferences(PREF_NAME, private_Mode);
		editor = pref.edit();		
	}

	


	public void createTimeEntrySession(String Date, String Time)
	{
		// Storing login value as TRUE
		editor.putBoolean(IS_TIMEENTRY_DETAILS_AVAIL, true);		
		// Storing name in pref
		editor.putString(SYSTEM_DATE, Date);		
		// Storing email in pref
		editor.putString(SYSTEM_TIME, Time);			
		editor.commit();
	}
	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getSessionDatetimeDetails()
	{
		HashMap<String, String> dateandtime = new HashMap<String, String>();
		// user name
		dateandtime.put(SYSTEM_DATE, pref.getString(SYSTEM_DATE, null));		
		// user email id
		dateandtime.put(SYSTEM_TIME, pref.getString(SYSTEM_TIME, null));		
		// return user
		return dateandtime;
	}
	

}
