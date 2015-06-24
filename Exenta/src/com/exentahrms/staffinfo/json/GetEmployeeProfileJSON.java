package com.exentahrms.staffinfo.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exenta.app.AppController;
import com.exentahrms.staffinfo.model.GetEmployeeProfile;

public class GetEmployeeProfileJSON {
	
	private List<GetEmployeeProfile> emp_details_list = new ArrayList<GetEmployeeProfile>();
	public GetEmployeeProfile emp_details;
	String url =  Const.StaffInfo.GET_EMPLOYEE_DETAILS;
	String result;
	
	public GetEmployeeProfileJSON() {
		// TODO Auto-generated constructor stub
	}

	public GetEmployeeProfileJSON(String employee_id, final VolleyCallback callback) {

		JsonObjectRequest GetempprofileReq = new JsonObjectRequest(url + employee_id, null,
				new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					Log.d("JSON", response.toString());
                   try {
						JSONArray  arrobj = response.getJSONArray("getGetEmployeeDetailResult");
						Log.d("JSON Array", arrobj.toString());
						// Parsing JSON
						for (int i = 0; i < arrobj.length(); i++) {
							
							JSONObject obj1 = arrobj.getJSONObject(i);
							emp_details = new GetEmployeeProfile();
							emp_details.setName(obj1.getString("FullName"));
							emp_details.setEmail(obj1.getString("Email"));
							emp_details.setCmpy_name(obj1.getString("CompanyName"));
							emp_details.setEmp_code(obj1.getString("EmpCode"));
							emp_details.setExperience(obj1.getString("ExperienceValue"));
							emp_details.setJob_title(obj1.getString("JobTitleName"));
							emp_details.setPhoto(obj1.getString("PhotoPath"));
							emp_details.setDepartment(obj1.getString("CategoryName"));
							emp_details.setReport_mananger(obj1.getString("ReportNameTo"));
							emp_details_list.add(emp_details);
							callback.onSuccess(emp_details_list);		
						    }
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}, new Response.ErrorListener() {
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
		GetempprofileReq.setRetryPolicy(new DefaultRetryPolicy(10000, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(GetempprofileReq);
		//return emp_details_list;
	}

	
	
}
