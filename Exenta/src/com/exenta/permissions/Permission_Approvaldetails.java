package com.exenta.permissions;


import java.util.HashMap;

import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.CircularNetworkImageView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exenta.pod.ApplyPOD;

public class Permission_Approvaldetails extends Activity {
	EmpDetailsSessions session;
	public static  String URL_PermissionApproval;
	String employeeID, companyID;
	CircularNetworkImageView image;
	TextView name,jobtitle,empid,company,applieddate,permissiondate,totalhrs,timing,pertype;
	EditText reason,response;
	Button submit,cancel;
	private RadioGroup radioGroup;
	String selectradio="";
	String msg;
	String approvalresponse;
	PermissionapprovalModel mPermissionapprovalModel;
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permission_approvaldetails);
		
		image=(CircularNetworkImageView)findViewById(R.id.permissionappdetails);
		name=(TextView)findViewById(R.id.per_name);
		jobtitle=(TextView)findViewById(R.id.per_jobtitle);
		empid=(TextView)findViewById(R.id.per_empid);
		company=(TextView)findViewById(R.id.per_com);
		applieddate=(TextView)findViewById(R.id.per_appdate);
		permissiondate=(TextView)findViewById(R.id.per_permdate);
		totalhrs=(TextView)findViewById(R.id.per_thrs);
		timing=(TextView)findViewById(R.id.per_timing);
		pertype=(TextView)findViewById(R.id.per_permtype);
		reason=(EditText)findViewById(R.id.per_reason);
		response=(EditText)findViewById(R.id.per_resp);
		radioGroup=(RadioGroup)findViewById(R.id.radioGroup1);
		submit=(Button)findViewById(R.id.button1);
		cancel=(Button)findViewById(R.id.button2);

		session =  new EmpDetailsSessions(getApplicationContext().getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		
	 mPermissionapprovalModel = (PermissionapprovalModel)getIntent().getParcelableExtra("APPROVEDATA"); 
		System.out.println(mPermissionapprovalModel.getAppliedDate());
		System.out.println(mPermissionapprovalModel.getCompanyID());
		
		image.setImageUrl(String.valueOf(Const.Approval_listImage_URL+mPermissionapprovalModel.getPhotopath()), imageLoader);
		name.setText(String.valueOf(mPermissionapprovalModel.getFirstName()));
		jobtitle.setText(String.valueOf(mPermissionapprovalModel.getJobTitle()));
		empid.setText(String.valueOf(mPermissionapprovalModel.getEmpId()));
		company.setText(String.valueOf(mPermissionapprovalModel.getCompanyName()));
		applieddate.setText(String.valueOf(mPermissionapprovalModel.getAppliedDate()));
		permissiondate.setText(String.valueOf(mPermissionapprovalModel.getPermissionDate()));
		totalhrs.setText(String.valueOf(mPermissionapprovalModel.getTotalDays()));
		reason.setText(String.valueOf(mPermissionapprovalModel.getReasonEmp()));
		timing.setText(String.valueOf(mPermissionapprovalModel.getTimings()));
		pertype.setText(String.valueOf(mPermissionapprovalModel.getPermissionType()));
		
		
		
		
		
		
		
		
	      radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
           	
              @Override
  	
  	            public void onCheckedChanged(RadioGroup group, int checkedId) {
  	
  	                // find which radio button is selected
  	
  	                if(checkedId == R.id.radio0) {
  	                	selectradio="Approve"; 
  	                
	                    
  	                    Toast.makeText(getApplicationContext(),selectradio,Toast.LENGTH_SHORT).show();
  	                  
  	                }
  	                    else if(checkedId == R.id.radio1){
  	                    	selectradio="Reject"; 
  	                    	
  	     	
  	                    Toast.makeText(getApplicationContext(), selectradio,Toast.LENGTH_SHORT).show();
  	
  	                }
  
              }  
  
  	        }); 
		
		
	      submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				approvalresponse=response.getText().toString();
				System.out.println("sssssssssssssssssss"+selectradio);
			if(approvalresponse.equals(""))	{
				
				Toast.makeText(getApplicationContext(), "Please enter response", Toast.LENGTH_SHORT).show();
				System.out.println("sssssssssssssssssss"+selectradio);
			}else if(selectradio.equals(""))
			{
	Toast.makeText(getApplicationContext(), "Please Select Approve/Reject", Toast.LENGTH_SHORT).show();
				//Custom_alert("Please select Approve/Reject");	
			}
			
			else{
				permission_approval() ;
				
			}
			}
		});
		
		
		
		

	      cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
//				response.setText("");
				Intent i=new Intent(Permission_Approvaldetails.this,PermissionApproval.class);
				startActivity(i);

				
			}
		});
		
		
				
		
		
	}
	
	
//http://192.168.1.87:8010/Rajesh/attendenceService.svc/PermissionApproval?record={recordID}&status={status}
	//&permissionInfoId={permissionInfoID}&EmpID={EmpID}& sessionEmpId={sessionEmpID}&requestID={requestID}
	//&response={txtResponseValue}&reason={reason}&companyID={companyID}	
	private void permission_approval() {
		String recordid=String.valueOf(mPermissionapprovalModel.getRecordID());
		String status=selectradio;
		String permissionInfoId=String.valueOf(mPermissionapprovalModel.getPermissionInfoID());
		String EmpID=String.valueOf(mPermissionapprovalModel.getEmpId());
		String sessionEmpId=employeeID;
		String requestID=String.valueOf(mPermissionapprovalModel.getRequestedID());
		String response=approvalresponse;
		String reason=String.valueOf(mPermissionapprovalModel.getReasonEmp());
		String compnyid=String.valueOf(mPermissionapprovalModel.getCompanyID());
		
URL_PermissionApproval=Const.URL_PermissionApproval0+recordid+Const.URL_PermissionApproval1+status+Const.URL_PermissionApproval2+permissionInfoId+Const.URL_PermissionApproval3+EmpID+Const.URL_PermissionApproval4+sessionEmpId+Const.URL_PermissionApproval5+requestID+Const.URL_PermissionApproval6+response+Const.URL_PermissionApproval7+reason+
Const.URL_PermissionApproval8+companyID;
	
URL_PermissionApproval = URL_PermissionApproval.replaceAll(" ", "%20");

// timeIn=2015-04-01%2009:00:00.000&timeOut=2015-01-02%2018:00:00.000
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				URL_PermissionApproval, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println(response.toString());
						try {

							JSONObject jsonObject = new JSONObject(response
									.toString().trim());
							String Approve_response = jsonObject
									.getString("checkApprovalRuleResult");
							System.out.println(Approve_response);
							if(Approve_response.equals("1")){
								
								if(selectradio.equals("Approve")){
							 msg="Permission approved";	
								}else{
								 msg="Permission Rejected";	
								}
									Custom_alert(msg);	
								}else if(Approve_response.equals("2")){
									Custom_alert("Higher authority to be Approve/Reject");		
								}
								else{
									Custom_alert("Process unsuccessfull");		
							}
							
						
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("TAG", "Error: " + error.getMessage());

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}
	
	public void Custom_alert(String msg) {
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
				Permission_Approvaldetails.this);

		// Setting Dialog Title
		alertDialog2.setTitle("Exenta");

		// Setting Dialog Message
		alertDialog2.setMessage(msg);

		alertDialog2.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						Intent intent = new Intent(Permission_Approvaldetails.this, PermissionApproval.class);
						startActivity(intent);						
					}
				});	
		// Showing Alert Dialog
		alertDialog2.show();
	}
	
	
	
	
	
	
	
	

}
