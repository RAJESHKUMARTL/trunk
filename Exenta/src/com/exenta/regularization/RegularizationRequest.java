package com.exenta.regularization;

import java.util.HashMap;

import supportclasses.EmpDetailsSessions;

import com.example.exenta.R;
import com.exenta.leaveapply.CompensatoryOff;
import com.exenta.leaveapply.General;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class RegularizationRequest extends FragmentActivity{
	
	EmpDetailsSessions session;
	String employeeID, companyID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regularization_mainactivity);
		Spinner category = (Spinner) findViewById(R.id.regul_category);
		
		session =  new EmpDetailsSessions(getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		
		category.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				if (position == 0) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, new RegularizationMissedPunch()).commit();
				} else if(position == 1) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, new RegularizationPOD())
							.commit();
				}
				else if(position == 2)
				{
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new RegularizationLeave())
					.commit();					
				}
				else
				{
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new RegularizationPermission())
					.commit();					
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		
		});
	}

}
