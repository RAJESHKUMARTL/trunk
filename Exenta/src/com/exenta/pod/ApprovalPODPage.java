package com.exenta.pod;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
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
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ApprovalPODPage extends Activity {
	
	int emp_id;
	String emp_name,pod_type,applied_date,total_days,reason_,status_;
	int record_id;
	int podinfo_id;
	int request_id;
	EditText response;
	RadioGroup radioGroup;
	RadioButton approveRadio, rejectRadio;
	EmpDetailsSessions session;
	String employeeID, companyID;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pod_apporval_page);
		
		
		 session =  new EmpDetailsSessions(getApplicationContext());
	      HashMap<String,String> user =  session.getEmployeeDetails();  
	      			
	      employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
	      companyID = user.get(EmpDetailsSessions.COMPANY_ID);
	      
	      System.out.println("Employee IDD"+employeeID);
	      System.out.println("Employee IDD"+companyID);
		
		TextView empId = (TextView) findViewById(R.id.empcode);
		TextView empName = (TextView) findViewById(R.id.empname);
		TextView jobTilte = (TextView) findViewById(R.id.jobtitle);
		TextView company = (TextView) findViewById(R.id.company);
		TextView podType = (TextView) findViewById(R.id.podtype);
		TextView appliedDate = (TextView) findViewById(R.id.applieddate);
		TextView totalDays = (TextView) findViewById(R.id.totaldays);
		TextView reason = (TextView) findViewById(R.id.reason);
		response = (EditText) findViewById(R.id.response);
		approveRadio = (RadioButton) findViewById(R.id.approve_radiobtn);
		rejectRadio = (RadioButton) findViewById(R.id.reject_radiobtn);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup1);
		Button submitBtn = (Button) findViewById(R.id.submit_btn);
		Button cancelBtn = (Button) findViewById(R.id.cancel_btn);
		//Bundle to read value from another activity
		Bundle bundle = getIntent().getExtras();
		//Print
		System.out.println("RequestID: "+bundle.getInt("RequestID"));
		//Assigning Value to a variable
		emp_id = bundle.getInt("EmpID");
		request_id = bundle.getInt("RequestID");
		podinfo_id = bundle.getInt("PODInfoID");
		record_id = bundle.getInt("RecordID");
		//status_ = bundle.getString("Status");
        reason_ = bundle.getString("Reason");
		//Adding Text to Textview		
		empId.setText(String.valueOf(emp_id));
		empName.setText(bundle.getString("Name"));
		jobTilte.setText(bundle.getString("JobTitle"));
		company.setText(bundle.getString("CompanyName"));
		podType.setText(bundle.getString("PODType"));
		appliedDate.setText(bundle.getString("AppliedDate"));
		totalDays.setText(""+bundle.getInt("TotalDays"));
		reason.setText(reason_);
			
		submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int selectedId = radioGroup.getCheckedRadioButtonId();
				// find which radioButton is checked by id
				if(selectedId == approveRadio.getId()) {
					status_ = "Approved"; }
				else{
					status_ = "Rejected";
				}
				if(response.getText().length() == 0)
				{
					Toast.makeText(ApprovalPODPage.this, "Please Enter response",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
				ApprovePOD();
				}
			}
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ApprovalPODPage.this,ApprovalPOD.class);
				startActivity(intent);
			}
		});
	}
	
	public void ApprovePOD() {

		String url = Const.POD.POD_APPROVE_OR_NOT + emp_id + "&LoggedEmpID="+ employeeID +"&RequestedID="+request_id+"&PODInfoID="+podinfo_id+"&RecordID="
		  +record_id+"&CompanyID="+companyID+"&status="+status_+"&ResponseValue="+reason_.replaceAll(" ", "%20")+"&empResponseValue="+response.getText().toString().replaceAll(" ", "%20");
		
		System.out.println("URl "+url);
		// showProgressDialog();
		//String url = "http://192.168.1.96:1212/Rajesh/attendenceService.svc/podapprovalpage?EmpID=55&LoggedEmpID=1&RequestedID=93&PODInfoID=93&RecordID=3445&CompanyID=1&status=Pending&ResponseValue=Clioent Visit&empResponseValue=test";
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(url.replaceAll(" ", "%20"),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try{
						//System.out.println("Response :"+response);
						JSONArray arrobj = response.getJSONArray("ApprovePODResult");
						JSONObject arrobj2 = arrobj.getJSONObject(0);
						int result = Integer.parseInt(arrobj2.getString("result"));
						if(result == 0 || result == 3)
						{
							Custom_alert("You dont have approval authority");
						}
						else if(result == 2){ Custom_alert("Higher authority has to approve");}
						else if(result == 1){
							if(status_.equalsIgnoreCase("Approved"))
							{
								Custom_alert("POD Approved");
							}
							else
							{
								Custom_alert("POD Rejected");
							}
							}
						}
						catch(Exception e)
						{
							System.err.println("Error:" +e);
						}
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						//VolleyLog.d("JSON", "Error: " + error.getMessage());
						// hideProgressDialog();
						System.err.println(error);
						 if( error instanceof NetworkError) {
						    	
						    } else if( error instanceof ServerError) {
						    	
								if(status_.equalsIgnoreCase("Approved"))
								{
								Custom_alert("POD Approved");
								}
								else
								{
									Custom_alert("POD Rejected");
								}
						    	
						    } else if( error instanceof AuthFailureError) {
						    } else if( error instanceof ParseError) {
						    } else if( error instanceof NoConnectionError) {
						    } else if( error instanceof TimeoutError) {
						    }
					}
				}) {
		};
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}
	
	// *************************************************************************************//
		// ******************************** ALERT DIALOG ***************************************//
		// *************************************************************************************//
		public void Custom_alert(String msg) {
			AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
					this);

			// Setting Dialog Title
			alertDialog2.setTitle("Exenta");

			// Setting Dialog Message
			alertDialog2.setMessage(msg);

			// Setting Icon to Dialog
//			alertDialog2.setIcon(R.drawable.ic_launcher);

			// Setting Positive "Yes" Btn
			alertDialog2.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							Intent intent = new Intent(ApprovalPODPage.this, ApprovalPOD.class);
							startActivity(intent);
						}
					});
			// Setting Negative "NO" Btn
		/*	alertDialog2.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							Toast.makeText(ApprovalPODPage.this, "Cancelled",
									Toast.LENGTH_SHORT).show();
							dialog.cancel();
						}
					});*/

			// Showing Alert Dialog
			alertDialog2.show();
		}
}
