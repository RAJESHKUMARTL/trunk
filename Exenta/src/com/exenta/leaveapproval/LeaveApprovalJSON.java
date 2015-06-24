package com.exenta.leaveapproval;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.ResponseConnControl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exenta.app.AppController;
import com.exentahrms.staffinfo.json.VolleyCallback;

public class LeaveApprovalJSON 
{
	
	List<LeaveApprovalData> emp_leave_list = new ArrayList<LeaveApprovalData>();
	
	public LeaveApprovalJSON() {
		
	}
	
	public LeaveApprovalJSON(String url, final VolleyCallback volleyCallback) {
	
		try
		{
			System.out.println("URL in Leave Approval:"+url);
			JsonObjectRequest getLeaveRequest = new JsonObjectRequest(url,null,
					new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					// TODO Auto-generated method stub
					System.out.println("JSON Array: "+response);
					try {
						JSONArray arryObj = response.getJSONArray("requestListResult");
						System.out.println("JSON Array: "+arryObj.toString());
						for(int i=0; i< arryObj.length(); i++)
						{
							JSONObject jsonObj = arryObj.getJSONObject(i);
							
							LeaveApprovalData leaveObj = new LeaveApprovalData();
							
							leaveObj.setAppliedDate(jsonObj.getString("AppliedDate"));
							
							leaveObj.setCompanyName(jsonObj.getString("CompanyName"));
							
							leaveObj.setDepartmentName(jsonObj.getString("DepartmentName"));
																					
							leaveObj.setEmployeeID((jsonObj.getString("EmployeeID")));
							
							leaveObj.setFirstName(jsonObj.getString("FirstName"));
							
							leaveObj.setFromDate(jsonObj.getString("FromDate"));
							
							leaveObj.setJobTitleName(jsonObj.getString("JobTitleName"));
							
							leaveObj.setLeaveInfoID(jsonObj.getString("LeaveInfoID"));
							
							String reason = jsonObj.getString("LeaveReason");
																				
							leaveObj.setLeaveReason(reason);
							
							leaveObj.setLeaveTypeID(jsonObj.getString("LeaveTypeID"));
														
							leaveObj.setLeaveTypeName(jsonObj.getString("LeaveTypeName"));
							
							leaveObj.setTotalDays(jsonObj.getString("TotalDays"));
							
							leaveObj.setStatus(jsonObj.getString("Status"));
													
							
							leaveObj.setLType(jsonObj.getString("LType"));			
							
							leaveObj.setModuleID(jsonObj.getString("ModuleID"));
							
							leaveObj.setModuleName(jsonObj.getString("ModuleName"));
							
							leaveObj.setRecordID(jsonObj.getString("RecordID"));
							
							leaveObj.setReferenceID(jsonObj.getString("ReferenceID"));
							
							leaveObj.setRequestID(jsonObj.getString("RequestID"));
							
							leaveObj.setRequestedID(jsonObj.getString("RequestedID"));
							
							leaveObj.setRuleID(jsonObj.getString("RuleID"));
							
							leaveObj.setType(jsonObj.getString("Type"));
							
							leaveObj.setRegularizaId(jsonObj.getString("regularizaId"));
																																	
							emp_leave_list.add(leaveObj);								
							volleyCallback.onSuccessLeaveApproval(emp_leave_list);
							
						}																							
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					System.err.println(error);
					// TODO Auto-generated method stub
					 // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
				    // For AuthFailure, you can re login with user credentials.
				    // For ClientError, 400 & 401, Errors happening on client side when sending api request.
				    // In this case you can check how client is forming the api and debug accordingly.
				    // For ServerError 5xx, you can do retry or handle accordingly.
				    if( error instanceof NetworkError) {
				    	
				    } else if( error instanceof ServerError) {
				    } else if( error instanceof AuthFailureError) {
				    } else if( error instanceof ParseError) {
				    } else if( error instanceof NoConnectionError) {
				    } else if( error instanceof TimeoutError) {
				    }
				}
			});
			getLeaveRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 
	                5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(getLeaveRequest);
			
		}
		catch(Exception e)
		{
			
		}
		
	}

}
