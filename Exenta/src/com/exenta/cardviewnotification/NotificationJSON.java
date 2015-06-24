package com.exenta.cardviewnotification;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.exenta.app.AppController;
import com.exenta.leaveapproval.LeaveApprovalData;
import com.exentahrms.staffinfo.json.VolleyCallback;

public class NotificationJSON {
	
	
	List<NotificationList> emp_Notfication_list = new ArrayList<NotificationList>();
	
	
	public NotificationJSON(String url, final VolleyCallback volleyCallback)
	{
		@SuppressWarnings("unused")
		JsonObjectRequest jsonObjRequest =  new JsonObjectRequest(url, null,new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) 
			{
				//TODO Auto-generated method stub
				JSONArray arryObj;
				try {
					arryObj = response.getJSONArray("notificationDetailsResult");					
					for(int i=0; i< arryObj.length(); i++)
					{
						JSONObject jsonObj = arryObj.getJSONObject(i);
						
						NotificationList not_Obj = new NotificationList();
						
						not_Obj.setAlertID(jsonObj.getLong("alertID"));
						not_Obj.setDescription(jsonObj.getString("description"));
						not_Obj.setEmployeeID(jsonObj.getLong("employeeID"));
						not_Obj.setModuleID(jsonObj.getLong("moduleID"));
						emp_Notfication_list.add(not_Obj);
						volleyCallback.onSuccessNotificationList(emp_Notfication_list);						
					}	
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
																											
			}
		}, new ErrorListener()
		{
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
			}						
		});
			jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 
	                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
	                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(jsonObjRequest);		
	}

}
