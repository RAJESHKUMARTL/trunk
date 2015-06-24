package com.exenta.leaveapproval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;

import com.example.exenta.R;
import com.exenta.cardviewnotification.NotificationList;
import com.exentahrms.staffinfo.json.VolleyCallback;
import com.exentahrms.staffinfo.model.GetEmployeeProfile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LeaveApprovalActivity extends FragmentActivity {

	
	private static final int DIALOG_LOADING = 1;
	private ListView listView;
	private List<LeaveApprovalData> emp_leave_list = new ArrayList<LeaveApprovalData>();
	private LeaveApprovalListAdapter adapter;
	
	EmpDetailsSessions session;
	String employeeID, companyID;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		  super.onCreate(savedInstanceState);
	      setContentView(R.layout.approvallistview);
	      
	      listView = (ListView) findViewById(R.id.approvalListview);
	      
	      session =  new EmpDetailsSessions(getApplicationContext());
	      HashMap<String,String> user =  session.getEmployeeDetails();  
	      			
	      employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
	      companyID = user.get(EmpDetailsSessions.COMPANY_ID);
	      
	      System.out.println("Employee IDD"+employeeID);
	      System.out.println("Employee IDD"+companyID);
	      //http://202.88.244.29:2020/Rajesh/attendenceService.svc/requestList?EmployeeID=1&rowno=0&index=0
	      
	      new LeaveApprovalJSON(Const.URL_LEAVEREQUEST_p1+employeeID+Const.URL_LEAVEREQUEST_p2, new VolleyCallback() {
		
			@Override
			public void onSuccessLeaveApproval(List<LeaveApprovalData> emp_leave_list) {
				// TODO Auto-generated method stub	
				  System.out.println("Emp List: "+emp_leave_list.get(0).getFirstName());				 
				  
				  if(emp_leave_list.size()!=0)
				  {
			      adapter = new LeaveApprovalListAdapter(LeaveApprovalActivity.this, emp_leave_list);	
			      listView.setAdapter(adapter);
				  }
				  else
				  {
					  
				  }
			}
			
			@Override
			public void onSuccess(List<GetEmployeeProfile> emp_details_list) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccessNotificationList(
					List<NotificationList> not_List) {
				// TODO Auto-generated method stub
				
			}
		});
	      
//	      System.out.println("Emp_list_Size"+emp_leave_list.size());
	     
	      
	      listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
										
					
					Intent intent = new Intent(LeaveApprovalActivity.this,LeaveApprovalPage.class);	
															
					String positionVal = ""+position;
					
					Bundle bundle =  new Bundle();
					
					System.out.println("ONCLICK POSITION VALUE"+position);
					
					bundle.putString("position", positionVal);
					
					intent.putExtras(bundle);
					
					startActivity(intent);
					
				}

	});
	}
	
	//Custom Dialog
	protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_LOADING:
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //here we set layout of progress dialog
            dialog.setContentView(R.layout.custom_dialog_box);
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new OnCancelListener() {
            	@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					
				}
            });
        return dialog;  
 
        default:
            return null;
        }
    };

    
    
	
	
	
}
