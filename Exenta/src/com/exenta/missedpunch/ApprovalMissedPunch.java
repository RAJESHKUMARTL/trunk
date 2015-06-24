package com.exenta.missedpunch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import supportclasses.Utilities;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;

//test

public class ApprovalMissedPunch extends Activity {
	// Log tag
	private List<ApprovalRegularizationModel> approvalreqularization_list = new ArrayList<ApprovalRegularizationModel>();
	private ListView listView1;
	private ReqApprovalListView adapter;
	ImageView imgview;
ProgressBar progress;
	TextView loading_text;
//SwipeRefreshLayout mSwipeRefreshLayout;
	LinearLayout refreshlay, loadinglay;
	Context context;
	EmpDetailsSessions session;
	String employeeID, companyID;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regular_approval);
		context = this;
		listView1 = (ListView) findViewById(R.id.approval_list_reg);
		imgview = (ImageView) findViewById(R.id.no_result_img);
		refreshlay = (LinearLayout) findViewById(R.id.refreshlayout);
		loadinglay = (LinearLayout) findViewById(R.id.jsonloading);

		loading_text = (TextView) findViewById(R.id.loading);
		adapter = new ReqApprovalListView(this, approvalreqularization_list);
		listView1.setAdapter(adapter);
		
		
		   session =  new EmpDetailsSessions(getApplicationContext());
		      HashMap<String,String> user =  session.getEmployeeDetails();  
		      			
		      employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		      companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		      
		      System.out.println("Employee IDD"+employeeID);
		      System.out.println("Employee IDD"+companyID);


	/*	// Initialize swipe to refresh view
	mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
		mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefreshLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						// Refreshing data on server
						Boolean status = Utilities
								.isNetworkConnected(getApplicationContext());
						System.out.println("Internet Status :" + status);
						if (!status) {
							Utilities.Custom_alert("Please Check the internet",
									context);
							 approvalreqularization_list.clear();
							 adapter.notifyDataSetChanged();
							refreshlay.setVisibility(View.VISIBLE);
							loadinglay.setVisibility(View.GONE);
						} else {
							regularizationMP();
							loadinglay.setVisibility(View.VISIBLE);
							refreshlay.setVisibility(View.GONE);
						}
					}
				});
*/
		Boolean status = Utilities.isNetworkConnected(getApplicationContext());
		System.out.println("Internet Status :" + status);
		if (!status) {
			//Utilities.Custom_alert_Internet("Please Check the internet", this, 2, "Ok","Cancel");
			// Intent intent=new Intent(Settings.ACTION_MAIN);
			// ComponentName cName = new
			// ComponentName("com.android.phone","com.android.phone.NetworkSetting");
			// intent.setComponent(cName);

			refreshlay.setVisibility(View.VISIBLE);
			loadinglay.setVisibility(View.GONE);

		} else {
			loadinglay.setVisibility(View.VISIBLE);
			refreshlay.setVisibility(View.GONE);
			regularizationMP();

		}

		// listening to single list item on click
		/*
		 * pDialog = new ProgressDialog(this); // Showing progress dialog before
		 * making http request pDialog.setMessage("Loading..."); pDialog.show();
		 */

		// ListView Item Click Listener
		listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("List Item Clicked");
				// Passing the employee id to next activity
				final Intent i = new Intent(ApprovalMissedPunch.this,
						ApprovalMissedPunchList.class);

				// Print
				System.out
						.println("Request ID:"
								+ approvalreqularization_list.get(position)
										.getReq_id());
				i.putExtra("EmpCode", approvalreqularization_list.get(position)
						.getEmp_id());
				i.putExtra("Name", approvalreqularization_list.get(position)
						.getFirst_name());
				i.putExtra("JobTitle", approvalreqularization_list
						.get(position).getJobtitle());
				i.putExtra("CompanyName",
						approvalreqularization_list.get(position)
								.getCompany_name());
				i.putExtra("RegType", approvalreqularization_list.get(position)
						.getRegular_type());
				i.putExtra("AppliedDate",
						approvalreqularization_list.get(position)
								.getApplied_date());
				i.putExtra("Reason", approvalreqularization_list.get(position)
						.getRegular_reason());
				i.putExtra("EmpID", approvalreqularization_list.get(position)
						.getEmp_id());
				i.putExtra("RequestID",
						approvalreqularization_list.get(position).getReq_id());
				i.putExtra("PODInfoID",
						approvalreqularization_list.get(position)
								.getRegular_infoID());
				i.putExtra("RecordID", approvalreqularization_list
						.get(position).getRecord_id());
				i.putExtra("Status", approvalreqularization_list.get(position)
						.getStatus());
				i.putExtra("TotalHours",
						approvalreqularization_list.get(position)
								.getTotalhour());
				i.putExtra("FromTime", approvalreqularization_list
						.get(position).getFrom_time());
				i.putExtra("ToTime", approvalreqularization_list.get(position)
						.getTo_time());
				i.putExtra("Type", approvalreqularization_list.get(position)
						.getType());
				i.putExtra("LType", approvalreqularization_list.get(position)
						.getLtype());
				startActivity(i);
				finish();
				// hidePDialog();
			}

		});
	}

	public void regularizationMP() {
		approvalreqularization_list.clear();
		// Creating volley request obj
		JsonObjectRequest jsonobj = new JsonObjectRequest(
				Const.Regularization.REG_APPROVAL_1+companyID+Const.Regularization.REG_APPROVAL_2+employeeID, null,
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						Log.d("JSON", response.toString());
						try {
							JSONArray arrobj = response
									.getJSONArray("listregularResult");
							Log.d("JSON Array", arrobj.toString());
							// Image Change if Listview is empty or not
							if (arrobj.length() != 0) {
								imgview.setImageResource(0);
								loadinglay.setVisibility(View.GONE);
								refreshlay.setVisibility(View.GONE);
							} else {
								imgview.setImageResource(R.drawable.ic_launcher);
								loadinglay.setVisibility(View.GONE);
								refreshlay.setVisibility(View.GONE);
							}
							// Parsing JSON
							for (int i = 0; i < arrobj.length(); i++) {
								JSONObject obj1 = arrobj.getJSONObject(i);
								ApprovalRegularizationModel approvalRegClass = new ApprovalRegularizationModel();
								approvalRegClass.setApplied_date(obj1
										.getString("AppliedDate"));
								approvalRegClass.setCompany_name(obj1
										.getString("CompanyName"));
								approvalRegClass.setFirst_name(obj1
										.getString("FirstName"));
								approvalRegClass.setFrom_date(obj1
										.getString("FromDate"));
								approvalRegClass.setTo_date(obj1
										.getString("ToDate"));
								approvalRegClass.setJobtitle(obj1
										.getString("JobTitleName"));
								approvalRegClass.setType(obj1.getString("Type"));
								approvalRegClass.setRegular_type(obj1
										.getString("RegularizationType"));
								approvalRegClass.setRegular_reason(obj1
										.getString("RegularizationReason"));
								approvalRegClass.setStatus(obj1
										.getString("Status"));
								approvalRegClass.setEmp_id(obj1
										.getInt("EmployeeID"));
								approvalRegClass.setRegular_infoID(obj1
										.getInt("RegularizationInfoID"));
								approvalRegClass.setRecord_id(obj1
										.getInt("recordID"));
								approvalRegClass.setReq_id(obj1
										.getInt("requestedID"));
								approvalRegClass.setFrom_time(obj1
										.getString("fromTime"));
								approvalRegClass.setTo_time(obj1
										.getString("toTime"));
								approvalRegClass.setTotalhour(obj1
										.getString("totalHours"));
								approvalRegClass.setLtype(obj1
										.getString("LType"));
								approvalreqularization_list
										.add(approvalRegClass);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						// notifying list adapter about data changes
					/*	// so that it renders the list view with updated data
						mSwipeRefreshLayout.setRefreshing(false);
						adapter.notifyDataSetChanged();*/
						// hidePDialog();
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						if (error instanceof NetworkError) {
							approvalreqularization_list.clear();
							adapter.notifyDataSetChanged();
						} else if (error instanceof ServerError) {
							approvalreqularization_list.clear();
							adapter.notifyDataSetChanged();
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
							approvalreqularization_list.clear();
							adapter.notifyDataSetChanged();
						} else if (error instanceof TimeoutError) {
						}
						System.out.println("Error: " + error);
					}
				});
		jsonobj.setRetryPolicy(new DefaultRetryPolicy(3000, 3, 3f));

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