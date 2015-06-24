package com.exenta.leaveapproval;

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
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class LeaveApprovalPage extends Activity {

	
	private TextView  empID,name,leaveDateVal,leaveTypeVal,appliedLeaveVal,totalDaysVal,reasonVal;
	private EditText responsevalue;
	private RadioGroup actionVal;
	private RadioButton radioButtonVal;
	private Button submit, cancel;
	private LeaveApprovalJSON jsonObj =null;
	private	List<LeaveApprovalData> emp_leave_list = new ArrayList<LeaveApprovalData>();
	private String approval_URL = Const.HTTP;
	private EmpDetailsSessions  session ;
	HashMap<String,String> user;     
	private String radioButtonTxt;
	String msg="";
	//Username
	String employeeID;
	
	String companyID;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveapproval);	
		
		session = new EmpDetailsSessions(getApplicationContext());
		user =  session.getEmployeeDetails();            	            	
		//Username
		employeeID =  user.get(EmpDetailsSessions.EMPLOYEE_ID);
		
		companyID = user.get(EmpDetailsSessions.COMPANY_ID); 
		
//		Custom_alert("Test");
		empID = (TextView) findViewById(R.id.empIDVal);
		
		name = (TextView) findViewById(R.id.nameVal);
		
		leaveDateVal = (TextView) findViewById(R.id.leaveDateVal);
		
		leaveTypeVal = (TextView) findViewById(R.id.leaveTypeVal);
		
		appliedLeaveVal = (TextView) findViewById(R.id.appliedLeaveVal);
		
		totalDaysVal = (TextView) findViewById(R.id.totalDaysVal);
		
		reasonVal = (TextView) findViewById(R.id.reasonVal);
		
		responsevalue = (EditText) findViewById(R.id.responsevalue);
		
		radioButtonVal = (RadioButton) findViewById(R.id.approveRadioButton);
		
		submit = (Button) findViewById(R.id.submitBut);
		
		cancel = (Button) findViewById(R.id.cancelBut);
								
		actionVal = (RadioGroup) findViewById(R.id.actionval);
		
		defaultRadioButtonText();
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				Intent intent = new Intent(LeaveApprovalPage.this, LeaveApprovalActivity.class);
				startActivity(intent);				
			}
		});
		
		
		actionVal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
												
				int pos = actionVal.indexOfChild(findViewById(checkedId));
				
				radioButtonVal = (RadioButton) actionVal.getChildAt(pos);
				
				radioButtonTxt = (String) radioButtonVal.getText();
				
				System.out.println("Selected radio Button:"+radioButtonTxt);				
			}
		});
		
		
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
							
				if(responsevalue.getText().toString().equals(""))
				{
					Toast.makeText(LeaveApprovalPage.this, "Enter reason", Toast.LENGTH_SHORT);
				}
				
				else
				{
				
				System.out.println("Approval Url:"+approval_URL);
				
				JsonObjectRequest getLeaveApproval = new JsonObjectRequest(approval_URL, null, new Response.Listener<JSONObject>() {

					@SuppressLint("ShowToast")
					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub												
						JSONArray arryObj;
						try {
							arryObj = response.getJSONArray("ApproveLeaveResult");
							Log.d("JSON Array",arryObj.toString());
							
								JSONObject jsonObj = arryObj.getJSONObject(0);
								jsonObj.getString("result");
								
								if(jsonObj.getString("result").toString().equals("1"))
								{
									if(radioButtonTxt.equals("Approve")){
										 msg="Leave approved";	
											}else{
											 msg="Leave Rejected";	
											}
									
									Custom_alert("Leave"+msg);								
								}
								else if(jsonObj.getString("result").toString().equals("2"))
								{		
									Custom_alert("Higher level authority has to "+radioButtonTxt+" !..");
								}							
								System.out.println("Result-List"+jsonObj.getString("result"));																												
						} 
						catch (JSONException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {

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
				getLeaveApproval.setRetryPolicy(new DefaultRetryPolicy(5000, 
		                5, 
		                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
				AppController.getInstance().addToRequestQueue(getLeaveApproval);
			}
			}
		});
		
		
		  jsonObj = new LeaveApprovalJSON();

		try{
			
			Bundle bundle = getIntent().getExtras();
								
			String position = bundle.getString("position");
			
			final int positionVal = Integer.parseInt(position);
						
		//String appliedDate = bundle.getString("AppliedDate");	
			System.out.println("position"+position);
			System.out.println("positionVal"+positionVal);
				
			try
			{
				JsonObjectRequest getLeaveRequest = new JsonObjectRequest(Const.URL_LEAVEREQUEST_p1+employeeID+Const.URL_LEAVEREQUEST_p2, null,new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub						
						try {
							JSONArray arryObj = response.getJSONArray("requestListResult");
							Log.d("JSON Array",arryObj.toString());
							
								JSONObject jsonObj = arryObj.getJSONObject(positionVal);
								
								LeaveApprovalData leaveObj = new LeaveApprovalData();
								
								leaveObj.setAppliedDate(jsonObj.getString("AppliedDate"));
								
								leaveObj.setCompanyName(jsonObj.getString("CompanyName"));
								
								leaveObj.setDepartmentName(jsonObj.getString("DepartmentName"));
																						
								leaveObj.setEmployeeID((jsonObj.getString("EmployeeID")));
								
								leaveObj.setFirstName(jsonObj.getString("FirstName"));
								
								leaveObj.setFromDate(jsonObj.getString("FromDate"));
								
								leaveObj.setJobTitleName(jsonObj.getString("JobTitleName"));
								
								leaveObj.setLeaveInfoID(jsonObj.getString("LeaveInfoID"));
								
								leaveObj.setLeaveReason(jsonObj.getString("LeaveReason"));
								
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
								
								leaveObj.setCompanyID(jsonObj.getString("companyID"));
															
								empID.setText(leaveObj.getEmployeeID());
								name.setText(leaveObj.getFirstName());
								leaveDateVal.setText(leaveObj.getFromDate());
								leaveTypeVal.setText(leaveObj.getLeaveTypeName());
								appliedLeaveVal.setText(leaveObj.getAppliedDate());
								totalDaysVal.setText(leaveObj.getTotalDays());
								reasonVal.setText(leaveObj.getLeaveReason());
																		
								approval_URL = Const.HTTP+"/approveorreject?EmpID="+leaveObj.getEmployeeID()+"&RequestedID="+leaveObj.getRequestedID()+"&leaveinfoID="+leaveObj.getLeaveInfoID()+"&RecordID="+leaveObj.getRecordID()+"&status="+radioButtonTxt+"&ResponseValue="+leaveObj.getLeaveReason()+"&LType="+leaveObj.getLType()+"&ResponseText="+responsevalue.getText().toString()+"&appEmpID="+employeeID+"&companyID="+leaveObj.getCompanyID();							
																								
								approval_URL = approval_URL.replaceAll(" ", "%20");
								
								
								emp_leave_list.add(leaveObj);																								
								
							
						} catch (Exception e) {
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
				getLeaveRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 
		                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
		                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
				// Adding request to request queue
				AppController.getInstance().addToRequestQueue(getLeaveRequest);								
				
			}
			catch(Exception e)
			{
				
			}
						
		}		
						
		catch(Exception e)
		{
			System.out.println("nbkjnlmnoiknon"+e);
		}
						
	}
					
	private void defaultRadioButtonText()
	{
		
		if(actionVal.getCheckedRadioButtonId()!= -1)
		{
			int id = actionVal.getCheckedRadioButtonId();
			View radioButton = actionVal.findViewById(id);
			int radioID = actionVal.indexOfChild(radioButton);
			radioButton = (RadioButton) actionVal.getChildAt(radioID);
			radioButtonTxt = (String) radioButtonVal.getText();
			System.out.println("SUBMIT RADIO BUTTON:"+radioButtonTxt);
		}
	}
	
	public void Custom_alert(String msg) {
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
				LeaveApprovalPage.this);

		// Setting Dialog Titlevc=``````````````````````````````````````````````````c
		alertDialog2.setTitle("Exenta");

		// Setting Dialog Message
		alertDialog2.setMessage(msg);

		// Setting Icon to Dialog
//		alertDialog2.setIcon(R.drawable.ic_launcher);

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) 
					{
						// Write your code here to execute after dialog						
						Intent intent = new Intent(LeaveApprovalPage.this, LeaveApprovalActivity.class);
						startActivity(intent);						
					}
				});
		alertDialog2.show();
	}
	
	
}
