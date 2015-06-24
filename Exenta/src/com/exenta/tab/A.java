package com.exenta.tab;


import com.example.exenta.R;
import com.exenta.leaveapply.Leave_Request;
import com.exenta.leaveapproval.LeaveApprovalActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class A extends Fragment {
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View android = inflater.inflate(R.layout.a_frag, container, false);
	        //((TextView)android.findViewById(R.id.textView)).setText("A");
	        LinearLayout list_1 = (LinearLayout) android.findViewById(R.id.leave);
	        LinearLayout list_2 = (LinearLayout) android.findViewById(R.id.pod);
//	        LinearLayout list_3 = (LinearLayout) android.findViewById(R.id.permission);
	        list_1.setOnClickListener(new OnClickListener()
     	   {
     	             @Override
     	             public void onClick(View v)
     	             {
     	            	 Toast.makeText(getActivity(),"Leave Apply",Toast.LENGTH_SHORT).show();
     	            	 Intent intent = new Intent(getActivity(),Leave_Request.class);
     	            	 startActivity(intent);     	            	  
     	             } 
     	   });
	        	        
	        list_2.setOnClickListener(new OnClickListener()
	     	   {
	     	             @Override
	     	             public void onClick(View v)
	     	             {	     	            	 	     	            
	     	            	  Toast.makeText(getActivity(),"Leave Approval",Toast.LENGTH_SHORT).show();
	     	            	  Intent intent = new Intent(getActivity(), LeaveApprovalActivity.class);
	     	            	  startActivity(intent);
	     	             } 
	     	   });     
    return android;
	        }
	
}
