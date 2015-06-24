package com.exenta.permissions;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;

public class MyPermissionRequest extends Activity {
	
	ListView myrequest;	
	String employeeID, companyID;
	int day,month,year;
	int JSON_Length;
	EmpDetailsSessions session;
	public static String URL_My_Permission_Request = null;
	MyPermissionReqModel myreqmodel;
	private List<MyPermissionReqModel> mypermissionlist = new ArrayList<MyPermissionReqModel>();
	private MyPermissionReq_Adapter adapter;
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permission_myreq);
		
		myrequest=(ListView)findViewById(R.id.permission_reqlist);	
		
		session =  new EmpDetailsSessions(getApplicationContext().getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);

	Calendar cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH)+1;
		year = cal.get(Calendar.YEAR);
		
		My_Permission_Request();
		
		
		
		
		myrequest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				//PermissionapprovalModel selItem = (PermissionapprovalModel) adapter.getItem(position);
				MyPermissionReqModel details=(MyPermissionReqModel)mypermissionlist.get(position);
		
			 System.out.println(details.toString());
			 
				 Intent in=new Intent(MyPermissionRequest.this,MypermissionReqDetails.class);
				 Bundle mBundle = new Bundle(); 
				 mBundle.putParcelable("MYREQUEST",details); 
				  in.putExtras(mBundle); 
				
				 startActivity(in);
				
			}
		});	
		
		
		
		
		
	}
	private void My_Permission_Request() {
	//	showProgressDialog();
		URL_My_Permission_Request = Const.URL_MYPERMISSION1 + employeeID
				+ Const.URL_MYPERMISSION2+month+Const.URL_MYPERMISSION3+year;
		System.out.println(URL_My_Permission_Request);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				URL_My_Permission_Request,null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println(response.toString());

						if (response.toString() != null) {

							try {
								JSONObject jsonObj;
								JSONArray arrobj = response
										.getJSONArray("myPermissionReqResult");
								JSON_Length = arrobj.length();
								System.out.println("jarrayyy" + JSON_Length);
								for (int i = 0; i < arrobj.length(); i++) {

									jsonObj = arrobj.getJSONObject(i);

									myreqmodel = new MyPermissionReqModel();
					myreqmodel.setAppliedDate(jsonObj.getString("appliedDate"));	
					myreqmodel.setEmployeeID(jsonObj.getInt("employeeID"));
					myreqmodel.setFromTime(jsonObj.getString("fromTime"));
					myreqmodel.setPermissionDate(jsonObj.getString("permissionDate"));				
					myreqmodel.setPermissionInfoID(jsonObj.getInt("permissionInfoID"));				
					
					myreqmodel.setPermissionType(jsonObj.getString("permissionType"));				
					myreqmodel.setResponseReason(jsonObj.getString("responseReason"));				
					myreqmodel.setStatus(jsonObj.getString("status"));				
					myreqmodel.setTotalHours(jsonObj.getString("totalHours"));				
					myreqmodel.setToTime(jsonObj.getString("toTime"));				
									
				
					mypermissionlist.add(myreqmodel);	
									// customui.LoadingHide();
								}
								System.out.println(myreqmodel.getAppliedDate());
								adapter=new MyPermissionReq_Adapter(MyPermissionRequest.this,mypermissionlist);	
								myrequest.setAdapter(adapter);
							//hideProgressDialog();
							} catch (Exception e) {
								System.out.println(e);
							}
							
						
						} else {
							Log.e("jsonerror",
									"Couldn't get any data from the url");
						}

						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("TAG", "Error: " + error.getMessage());
					//adapter.notifyDataSetChanged();
				//hideProgressDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}

}
