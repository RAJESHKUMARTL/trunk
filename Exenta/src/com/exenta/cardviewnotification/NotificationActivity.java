package com.exenta.cardviewnotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exenta.leaveapproval.LeaveApprovalActivity;
import com.exenta.leaveapproval.LeaveApprovalData;
import com.exenta.leaveapproval.LeaveApprovalListAdapter;
import com.exentahrms.staffinfo.json.VolleyCallback;
import com.exentahrms.staffinfo.model.GetEmployeeProfile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.cardview.*;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class NotificationActivity extends Activity {

	private static final int DIALOG_LOADING = 1;
	private ListView listView;
	public NotificationListAdapter notListAdapter;
	public long alertID=0;
	EmpDetailsSessions session;
	public List<NotificationList> not_List_details = new ArrayList<NotificationList>();
	String employeeID, companyID;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		  super.onCreate(savedInstanceState);
	      setContentView(R.layout.notification_listview);
	      
	      listView = (ListView) findViewById(R.id.notificationListMsg);
	      
	      session =  new EmpDetailsSessions(getApplicationContext());
	      HashMap<String,String> user =  session.getEmployeeDetails();  
	      			
	      employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
	      companyID = user.get(EmpDetailsSessions.COMPANY_ID);
	      
	      System.out.println("Employee IDD"+employeeID);
	      System.out.println("Employee IDD"+companyID);
	      
	      
	      new NotificationJSON(Const.URL_JSON_ARRAY_NOTIFICATION+employeeID, new VolleyCallback() {
			
			@Override
			public void onSuccessNotificationList(List<NotificationList> not_List) {
				// TODO Auto-generated method stub
				
				
				not_List_details = not_List;
												
				 System.out.println("Emp List: "+not_List.get(0).getEmployeeID());				 
				  
			      notListAdapter = new NotificationListAdapter(NotificationActivity.this, not_List);	
			      listView.setAdapter(notListAdapter);	
								
			}
			
			@Override
			public void onSuccessLeaveApproval(List<LeaveApprovalData> emp_leave_list) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(List<GetEmployeeProfile> emp_details_list) {
				// TODO Auto-generated method stub
				
			}
		});
	      
	      //Call Notification JSON Action
	      	      	      	      
	      
	      registerForContextMenu(listView);
	      
	}
		
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.notificationListMsg) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    System.out.println("List position:"+info.position);
	    
	    System.out.println("Position POint:"+not_List_details.get(info.position).getAlertID());
		
		alertID = not_List_details.get(info.position).getAlertID();
		
		not_List_details.get(info.position).getEmployeeID();		
	    
	    menu.setHeaderTitle("Delete");
	    String[] menuItems = getResources().getStringArray(R.array.contextmenu);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  String[] menuItems = getResources().getStringArray(R.array.contextmenu);
	  String menuItemName = menuItems[menuItemIndex];
	  if(menuItemName.equalsIgnoreCase("yes"))
	  {
		  //Call the notification remove service
		  JsonObjectRequest json_object = new JsonObjectRequest(Const.URL_JSON_ARRAY_REMOVENOTIFICATION+alertID, new Response.Listener<JSONObject>(){

			@SuppressLint("ShowToast")
			@Override
			public void onResponse(JSONObject response) {				
				// TODO Auto-generated method stub
				//removeNotificationResult
				
				JSONArray arryObj;
				try {
					arryObj = response.getJSONArray("removeNotificationResult");
					System.out.println("JSON Array: "+arryObj.toString());
					JSONObject jsonObj = arryObj.getJSONObject(0);
					if(	jsonObj.getString("result").equals("1"))
					{										
						startActivity(getIntent());
						Toast.makeText(NotificationActivity.this, "Removed..", Toast.LENGTH_SHORT);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}} , new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					
					 if( error instanceof NetworkError) {
					    	
					    } else if( error instanceof ServerError) {
					    } else if( error instanceof AuthFailureError) {
					    } else if( error instanceof ParseError) {
					    } else if( error instanceof NoConnectionError) {
					    } else if( error instanceof TimeoutError) {
					    }
					
				}}); 
		  
		  json_object.setRetryPolicy(new DefaultRetryPolicy(10000, 
	                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
	                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(json_object);
		  
		  
	  }
	  
		  
	  
//	  String listItemName = Countries[info.position];
//	  TextView text = (TextView)findViewById(R.id.footer);
//	  text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
	  return true;
	}
	
	
	
}
