package com.exenta.permissions;

import java.util.HashMap;

import supportclasses.EmpDetailsSessions;

import com.example.exenta.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MypermissionReqDetails extends Activity{
	TextView permissiondate, ttlhrs,pertype,timing,status,reason;
	EditText cancelreason;
	Button cancelpermission;
	String fromtime,totime,total;
	String reasoncancel;
	String employeeID,companyID;
	EmpDetailsSessions session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permission_myreqdetails);
		
		session =  new EmpDetailsSessions(getApplicationContext().getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		permissiondate=(TextView)findViewById(R.id.permyreq_date);
		ttlhrs=(TextView)findViewById(R.id.permyreq_totalhrs);
		pertype=(TextView)findViewById(R.id.permyreq_type);
		timing=(TextView)findViewById(R.id.permyreq_timing);
		status=(TextView)findViewById(R.id.permyreq_status);
		reason=(TextView)findViewById(R.id.permyreq_reason);
		cancelreason=(EditText)findViewById(R.id.per_resp);
		cancelpermission=(Button)findViewById(R.id.button1);
		MyPermissionReqModel mPermissionreqModel = (MyPermissionReqModel)getIntent().getParcelableExtra("MYREQUEST"); 
		System.out.println(mPermissionreqModel.getAppliedDate());

		fromtime=String.valueOf(mPermissionreqModel.getFromTime());
		totime=String.valueOf(mPermissionreqModel.getToTime());
		total=fromtime+"-"+totime;
		
		permissiondate.setText(String.valueOf(mPermissionreqModel.getPermissionDate()));
		ttlhrs.setText(String.valueOf(mPermissionreqModel.getTotalHours()));
		pertype.setText(String.valueOf(mPermissionreqModel.getResponseReason()));	
		timing.setText(total);
		status.setText(String.valueOf(mPermissionreqModel.getPermissionType()));
		
		reason.setText(String.valueOf(mPermissionreqModel.getStatus()));
		
		
		
		cancelpermission.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				reasoncancel=cancelreason.getText().toString();	
			}
		});
		
		
		
	}

}
