package supportclasses;

import java.util.HashMap;

import com.exenta.login.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class EmpDetailsSessions {

	
	private SharedPreferences pref;
	private Editor editor;
	private Context _context;
	
	//SharePreference FileName
	public static final String PREF_NAME = "EMPDETAILS";
	
	public static final int private_Mode=0;
	
	//EmployeeID (make variable public to access from outside)
	public static String EMPLOYEE_ID="employeeID";
	
	//CompanyID (make variable public to access from outside)
	public static String COMPANY_ID ="companyID";
	

	//EmpDetails Available
	public static final String IS_EMP_DETAILS_AVAIL = "IsAvail";
	
	
	@SuppressLint("CommitPrefEdits") 
	public EmpDetailsSessions(Context ctx)
	{
		this._context = ctx;
		pref  = _context.getSharedPreferences(PREF_NAME, private_Mode);
		editor = pref.edit();		
	}
	
	
	public void createEmployeeSession(String empID, String companyID)
	{		
		// Storing login value as TRUE
		editor.putBoolean(IS_EMP_DETAILS_AVAIL, true);		
		// Storing name in pref
		editor.putString(EMPLOYEE_ID, empID);		
		// Storing email in pref
		editor.putString(COMPANY_ID, companyID);			
		editor.commit();
	}
	
	
	
	
	public void checkEmpDetails()
	{
		if(!this.isEmpDetailAvail())
		{
			System.out.println("Emp Details is not Available");
			// user is not logged in redirect him to Login Activity
			Intent intent = new Intent(_context,Login.class);
			// Closing all the Activities
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// Add new Flag to start new Activity
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// Starting Login Activity
			_context.startActivity(intent);
		}		
	}	
	
	
	public HashMap<String, String> getEmployeeDetails()
	{
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.clear();
		user.put(EMPLOYEE_ID, pref.getString(EMPLOYEE_ID, null));			
		// user email id
		user.put(COMPANY_ID, pref.getString(COMPANY_ID, null));		
		// return user
		return user;
	}
	
		
	
	public boolean isEmpDetailAvail()
	{
		return pref.getBoolean(IS_EMP_DETAILS_AVAIL, false);
	}
	
	public void logout()
	{
		
	}
	
	
}
