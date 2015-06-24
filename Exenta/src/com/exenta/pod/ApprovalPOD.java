package com.exenta.pod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import com.example.exenta.R;
import com.exenta.app.AppController;

public class ApprovalPOD extends Activity  {
	// Log tag
	private List<ApprovalPODModel> approvalpod_list = new ArrayList<ApprovalPODModel>();
	private ListView listView1;
	private PODApprovalListView adapter;
	EmpDetailsSessions session;
	String employeeID,companyID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pod_approval);
		//monthsListView = (ListView) findViewById(R.id.approval_list_pod);
		
		session =  new EmpDetailsSessions(getApplicationContext().getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		
		// this-The current activity context.
		// Second param is the resource Id for list layout row item
		// Third param is input array 
		//arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, monthsArray);
		//monthsListView.setAdapter(arrayAdapter);
		listView1 = (ListView) findViewById(R.id.approval_list_pod);
		adapter = new PODApprovalListView(this, approvalpod_list);
		listView1.setAdapter(adapter);
		
		 // listening to single list item on click
        /*pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();*/

		// ListView Item Click Listener
		listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("List Item Clicked");
				// Passing the employee id to next activity
				final Intent i = new Intent(ApprovalPOD.this, ApprovalPODPage.class);

				//Print
				System.out.println("Request ID:"+approvalpod_list.get(position).getReq_id());
				i.putExtra("EmpCode", approvalpod_list.get(position).getEmp_id());
				i.putExtra("Name", approvalpod_list.get(position).getFirst_name());
				i.putExtra("JobTitle", approvalpod_list.get(position).getJobtitle());
				i.putExtra("CompanyName", approvalpod_list.get(position).getCompany_name());
				i.putExtra("PODType", approvalpod_list.get(position).getPod_type());
				i.putExtra("AppliedDate", approvalpod_list.get(position).getApplied_date());
				i.putExtra("TotalDays", approvalpod_list.get(position).getTotal_days());
				i.putExtra("Reason", approvalpod_list.get(position).getReq_reason());
				i.putExtra("EmpID", approvalpod_list.get(position).getEmp_id());
				i.putExtra("RequestID", approvalpod_list.get(position).getReq_id());
				i.putExtra("PODInfoID", approvalpod_list.get(position).getPod_infoID());
				i.putExtra("RecordID", approvalpod_list.get(position).getRecord_id());
				i.putExtra("Status", approvalpod_list.get(position).getStatus());
				startActivity(i);
				//hidePDialog();
			}

		});
		
		
		System.out.println("POD_URL:"+Const.POD.POD_APPROVAL+employeeID);
		
		// Creating volley request obj
		JsonObjectRequest jsonobj = new JsonObjectRequest(
				Const.POD.POD_APPROVAL+employeeID, null,
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						Log.d("JSON", response.toString());
						try {
							JSONArray arrobj = response
									.getJSONArray("PODEmployeeReqListResult");
							Log.d("JSON Array", arrobj.toString());
							// Parsing JSON
							for (int i = 0; i < arrobj.length(); i++) {
								JSONObject obj1 = arrobj.getJSONObject(i);
								ApprovalPODModel approvalPODClass = new ApprovalPODModel();
								approvalPODClass.setApplied_date(obj1.getString("appliedDate"));
								approvalPODClass.setCompany_name(obj1.getString("companyName"));
								approvalPODClass.setFirst_name(obj1.getString("firstName"));
								approvalPODClass.setReq_date(obj1.getString("fromDate"));
								approvalPODClass.setJobtitle(obj1.getString("jobTitle"));
								approvalPODClass.setPod_type(obj1.getString("podType"));
								approvalPODClass.setReq_reason(obj1.getString("reqReason"));
								approvalPODClass.setStatus(obj1.getString("status"));
								approvalPODClass.setEmp_id(obj1.getInt("employeeId"));
								approvalPODClass.setPod_infoID(obj1.getInt("podInfoId"));
								approvalPODClass.setRecord_id(obj1.getInt("recordID"));
								approvalPODClass.setReq_id(obj1.getInt("requestID"));
								approvalPODClass.setTotal_PODreq(obj1
										.getInt("totPodReq"));
								approvalPODClass.setTotal_days(obj1
										.getInt("totalDays"));
								approvalpod_list.add(approvalPODClass);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
						//hidePDialog();
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						 if( error instanceof NetworkError) {
						    	
						    } else if( error instanceof ServerError) {
						    } else if( error instanceof AuthFailureError) {
						    } else if( error instanceof ParseError) {
						    } else if( error instanceof NoConnectionError) {
						    } else if( error instanceof TimeoutError) {
						    }
						}
					});
		jsonobj.setRetryPolicy(new DefaultRetryPolicy(10000, 
			                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
			                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonobj);
	}
	 
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}