package com.exenta.pod;

import com.example.exenta.R;
import com.exenta.leaveapply.Leave_Request;
import com.exenta.leaveapproval.LeaveApprovalActivity;
import com.exenta.permissions.ManagePermission;
import com.exenta.permissions.MyPermissionRequest;
import com.exenta.permissions.PermissionApproval;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class POD_Main extends Fragment {
	
	// Object for Getter and Setter
		
	//LinearLayout POD_req;
	LinearLayout POD_manage;
	LinearLayout POD_approval;

	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		  View rootview = inflater.inflate(R.layout.pod_main, container, false); 
		  
		 // POD_req=(LinearLayout)rootview.findViewById(R.id.pod_myreq);
		  POD_manage=(LinearLayout)rootview.findViewById(R.id.pod_manage);
		  POD_approval=(LinearLayout)rootview.findViewById(R.id.pod_approve);
		    
		/*  POD_req.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				Intent req=new Intent(getActivity().getApplicationContext(),MyPermissionRequest.class)	;
				startActivity(req);
				}
			});*/
		    
		    
		  POD_manage.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent manage=new Intent(getActivity().getApplicationContext(),ApplyPOD.class)	;
					startActivity(manage);	
				}
			});
		  POD_approval.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent approval=new Intent(getActivity().getApplicationContext(),ApprovalPOD.class)	;
			startActivity(approval);	
		}
	});
 	  
		  
		  
	
return rootview;
        }
	
	
}
