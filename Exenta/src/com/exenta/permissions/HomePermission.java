package com.exenta.permissions;

import com.example.exenta.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class HomePermission extends Fragment {
	LinearLayout permission_req,permission_manage,permission_approval;
	
	
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
		    View rootview = inflater.inflate(R.layout.permission_home, container, false);
		    permission_req=(LinearLayout)rootview.findViewById(R.id.per_myreq);
		    permission_manage=(LinearLayout)rootview.findViewById(R.id.per_manage);
		    permission_approval=(LinearLayout)rootview.findViewById(R.id.per_approve);
		    
		    permission_req.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				Intent req=new Intent(getActivity().getApplicationContext(),MyPermissionRequest.class)	;
				startActivity(req);
				}
			});
		    
		    
		    permission_manage.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent manage=new Intent(getActivity().getApplicationContext(),ManagePermission.class)	;
					startActivity(manage);	
				}
			});
		    permission_approval.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent approval=new Intent(getActivity().getApplicationContext(),PermissionApproval.class)	;
			startActivity(approval);	
		}
	});
   
		    
		    
	       
	        return rootview;
	        
	        
	        
	        
	        
	        
	        
}}
