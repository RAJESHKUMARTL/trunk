package com.exenta.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.app.Activity;
import android.app.ProgressDialog;
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



public class PermissionApproval extends Activity {
	EmpDetailsSessions session;
	ListView approvelist;
	String employeeID, companyID;
	private ProgressDialog pDialog;
	private List<PermissionapprovalModel> permission_approval_list = new ArrayList<PermissionapprovalModel>();
	PermissionapprovalModel permissionapprove;
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	public static String URL_Permission_Approval = null;
	int JSON_Length;
	private PermissionApproval_Adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permission_approval);

		approvelist = (ListView) findViewById(R.id.permission_approvelist);

		session =  new EmpDetailsSessions(getApplicationContext().getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		make_ApprovalList_Request();
		
	//	adapter.notifyDataSetChanged();
		
	approvelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				//PermissionapprovalModel selItem = (PermissionapprovalModel) adapter.getItem(position);
				PermissionapprovalModel data=(PermissionapprovalModel)permission_approval_list.get(position);
		
			 System.out.println(data.toString());
			 
				 Intent in=new Intent(PermissionApproval.this,Permission_Approvaldetails.class);
				 Bundle mBundle = new Bundle(); 
				 mBundle.putParcelable("APPROVEDATA",data); 
				  in.putExtras(mBundle); 
				
				 startActivity(in);
				
			}
		});
	}
	
	
	

	private void make_ApprovalList_Request() {
	//	showProgressDialog();
		URL_Permission_Approval = Const.URL_PERMISSION_APPROVAL1 + companyID
				+ Const.URL_PERMISSION_APPROVAL2 + employeeID;
		System.out.println(URL_Permission_Approval);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				URL_Permission_Approval,null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println(response.toString());

						if (response.toString() != null) {

							try {
								JSONObject jsonObj;
								JSONArray arrobj = response
										.getJSONArray("permissionRequesterListResult");
								JSON_Length = arrobj.length();
								System.out.println("jarrayyy" + JSON_Length);
								for (int i = 0; i < arrobj.length(); i++) {

									jsonObj = arrobj.getJSONObject(i);

									permissionapprove = new PermissionapprovalModel();
									permissionapprove.setAppliedDate(jsonObj.getString("appliedDate"));
									permissionapprove.setCompanyID(jsonObj.getString("companyID"));
									permissionapprove.setCompanyName(jsonObj.getString("companyName"));
									permissionapprove.setEmpId(jsonObj.getInt("empId"));
									permissionapprove.setFirstName(jsonObj.getString("firstName"));
									permissionapprove.setJobTitle(jsonObj.getString("jobTitle"));
									permissionapprove.setPermissionDate(jsonObj.getString("permissionDate"));
									permissionapprove.setPermissionInfoID(jsonObj.getInt("permissionInfoID"));
									permissionapprove.setPhotopath(jsonObj.getString("photopath"));
									permissionapprove.setReasonEmp(jsonObj.getString("reasonEmp"));
									permissionapprove.setRecordID(jsonObj.getInt("recordID"));
									permissionapprove.setRequestedID(jsonObj.getInt("requestedID"));
									permissionapprove.setStatus(jsonObj.getString("status"));
									permissionapprove.setTotalDays(jsonObj.getInt("totalhours"));
									permissionapprove.setTimings(jsonObj.getString("Timings"));
									permissionapprove.setPermissionType(jsonObj.getString("permissionType"));
									permission_approval_list.add(permissionapprove);	
									
					
									// customui.LoadingHide();
								}
								System.out.println(permissionapprove.getAppliedDate());
								adapter=new PermissionApproval_Adapter(PermissionApproval.this,permission_approval_list);	
								approvelist.setAdapter(adapter);
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

private void showProgressDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideProgressDialog() {
		if (pDialog.isShowing())
			pDialog.hide();
	}

}