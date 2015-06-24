package com.exenta.login;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import supportclasses.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import com.example.exenta.R;
import com.example.exenta.R.id;
import com.example.exenta.R.layout;
import com.exenta.customUI.CustomLoading;
import com.exenta.mainmenu.MainMenuActivity;


public class Login extends Activity {

	private Button logButton;
	private EditText username;
	private EditText password;
	private ArrayList<String> passing;
	public static List<Employee> emp_Details;
	private EmpDetailsSessions empSession = null;
	private CheckBox rememberpsdCheckBox;
//	private TextView tickTextView;
	String usernameVal;
	String passwordval;
	CustomLoading customui = new CustomLoading();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);							
	}
		
	@SuppressWarnings("unchecked")
	public void login(View view)
	{				
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		usernameVal = username.getText().toString();
		passwordval =  password.getText().toString();
		if(usernameVal.isEmpty()||passwordval.isEmpty())
		{
			Toast.makeText(getBaseContext(), "Enter Username/Password", Toast.LENGTH_SHORT).show();
		}
		else
		{
		if(Utilities.isNetworkConnected(this))
		{
		customui.LoadingScreen(this);		
		passing = new ArrayList<String>();
		passing.add(usernameVal);
		passing.add(passwordval);
		LoginValidationClass obj = new LoginValidationClass();
		obj.objContext = getBaseContext();
		obj.execute(passing);
		}
		else
		{
			Utilities.Custom_alert_Internet("Please check Internet Connection",this);
			
		}
		}
		
	
	}
		
	public void forgetPassword(View view)
	{
		Toast.makeText(getBaseContext(), "Forget password", Toast.LENGTH_SHORT).show();
	}
		
//	public void sessionSave(View view)
//	{
//		tickTextView = (TextView) view.findViewById(R.id.remember_psw);
//		int count=0;
//		if(view == tickTextView && count == 0)
//		{
//			tickTextView.setBackgroundResource(R.drawable.tick_icn_red);
//			count=1;
//		}
//		else if(view == tickTextView && count == 1)
//		{
//			tickTextView.setBackgroundResource(R.drawable.tick_icn_green);
//			count=0;
//		}				
//	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	public class LoginValidationClass extends AsyncTask<ArrayList<String>, Void, Employee>
	{		
		Context objContext;		
//		String userNamestr;
//		String passwordstr;
		ArrayList<String> passValue;
		Employee emp = new Employee();
		String httpPortal = Const.HTTP;
		String result = "fail";
		Employee adminEmp = null;				
		@Override
		protected Employee doInBackground(ArrayList<String>... params) 
		{			
			// TODO Auto-generated method stub			
			this.passValue = params[0];
//			this.userNamestr = this.passValue.get(0);
//			this.passwordstr =  this.passValue.get(1);						
			return loginResult();
		}
		
		final Employee loginResult()
		{			
			empSession = new EmpDetailsSessions(getApplicationContext());
			String url = httpPortal +"/login?user=" + usernameVal + "&password="+passwordval;
			System.out.println("URL in Login: "+url);
			List<Employee> emp_details = new ArrayList<Employee>();
			BufferedReader inStream = null;
			try
			{
				HttpClient httpClient =  new DefaultHttpClient();
				HttpGet httpRequest = new HttpGet(url);
				HttpResponse response = httpClient.execute(httpRequest);
				inStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				
				StringBuffer buffer = new StringBuffer("");
				String line = "";
				String NL =  System.getProperty("line.separator");
				System.out.println("NL Value"+NL);
				while((line = inStream.readLine()) != null)
				{
					buffer.append(line+NL);
				}				
				inStream.close();
				result = buffer.toString();
				JSONObject obj = new JSONObject(result);
				Iterator<?> keys = obj.keys();				
				while(keys.hasNext())
				{
					String key = (String)keys.next();
					if(obj.get(key) instanceof JSONObject)
					{
						Object jsonEntity = obj.get(key);
						JSONObject jsonItem = (JSONObject) jsonEntity;
						Iterator<?> itemkeys = jsonItem.keys();
						if(itemkeys.hasNext())
						{
							adminEmp = new Employee();							
							adminEmp.employeeID = jsonItem.get("EmployeeID").toString();
							adminEmp.accessAndPermission = jsonItem.get("AccessAndPermission").toString();
							adminEmp.companyID = jsonItem.get("CompanyID").toString();
							empSession.createEmployeeSession(jsonItem.getString("EmployeeID"),jsonItem.getString("CompanyID"));
							adminEmp.firstName = jsonItem.get("FirstName").toString();							
							adminEmp.lastName = jsonItem.get("LastName").toString();
							adminEmp.middleName = jsonItem.get("MiddleName").toString();
							adminEmp.password = jsonItem.get("Password").toString();
							adminEmp.userName = jsonItem.get("Username").toString();
							adminEmp.photoPath = jsonItem.get("PhotoPath").toString();
							adminEmp.Signature = jsonItem.get("Signature").toString();
							adminEmp.signed = jsonItem.get("signed").toString();						
						}
						System.out.println("Employee-ID:"+Const.employeeID);
						System.out.println("Company-ID:"+Const.companyID);
						}															
				}			
			}
			catch (Exception E)
			{
				E.printStackTrace();
			}
			finally
			{
				if(inStream != null)
				{
					try
					{
						inStream.close();
					}
					catch(Exception E)
					{
						E.printStackTrace();
					}
				}				
			}			
			return adminEmp;
		}
		
		protected void onPostExecute(Employee admin)
		{  
			try{
				
			if(admin.employeeID.equalsIgnoreCase("null"))
	    	{	    		
				customui.LoadingHide();
	    		Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_SHORT).show();	    		
	    	}
	    	else{
	    		SharedPreferences.Editor editor = getSharedPreferences(Const.PREF_NAME,MODE_PRIVATE).edit();
	    		editor.putString("_username", usernameVal);
	    		editor.putString("_password", passwordval);
	    		rememberpsdCheckBox = (CheckBox) findViewById(R.id.remember_psw);
				if(rememberpsdCheckBox.isChecked())
				{					
		    		editor.putString("_status", "exenta");
				}				
	    		editor.commit();
	    		Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_SHORT).show();
	    		Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
	    		startActivity(intent);	
	    		Login.this.finish();
	    	}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}	
						
	}
	
	
	
	
}
